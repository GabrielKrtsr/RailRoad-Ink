use std::collections::{HashSet, VecDeque};
use std::net::SocketAddr;
use std::sync::Arc;
use tokio::net::TcpListener;
use tokio::sync::{broadcast, Mutex};
use tokio_tungstenite::accept_async;
use tokio_tungstenite::tungstenite::Message;
use futures_util::{StreamExt, SinkExt};
use clap::{Arg, Command};

#[tokio::main]
async fn main() {
    let matches = Command::new("WebSocket Server")
        .version("1.0")
        .author("Leroy")
        .about("Serveur WebSocket")
        .arg(
            Arg::new("port")
                .long("port")
                .value_name("PORT")
                .default_value("8000")
                .help("Le port sur lequel le serveur écoute")
                .value_parser(clap::value_parser!(u16)),
        )
        .arg(
            Arg::new("add")
                .long("add")
                .value_name("ACTION")
                .action(clap::ArgAction::Append)
                .help("Ajoute une action à la liste des actions possibles"),
        )
        .get_matches();

    let port: u16 = *matches.get_one::<u16>("port").unwrap_or(&8080);

    let add_actions = matches.get_many::<String>("add")
        .map(|values| values.cloned().collect::<Vec<String>>())
        .unwrap_or_else(Vec::new);

    let valid_actions = Arc::new(Mutex::new(HashSet::from([
        "ENTERS".to_string(),
        "LEAVES".to_string(),
        "THROWS".to_string(),
        "ELECTS".to_string(),
        "PLACES".to_string(),
        "YIELDS".to_string(),
        "BLAMES".to_string(),
        "PLAYS".to_string(),
        "SCOREROUND".to_string(),
        "SCORES".to_string(),
        "GRANTS".to_string(),
        "AGREES".to_string(),
    ])));

    if !add_actions.is_empty() {
        let mut actions_lock = valid_actions.lock().await;
        for action in add_actions {
            actions_lock.insert(action.clone());
            println!("Nouvelle action ajoutée : {}", action);
        }
        println!("Nombre total d'actions valides : {}", actions_lock.len());
    }

    let addr = format!("127.0.0.1:{}", port).parse::<SocketAddr>().unwrap();
    let listener = TcpListener::bind(&addr).await.unwrap();
    println!("Serveur WebSocket en écoute sur {} ...", addr);

    let (tx, _) = broadcast::channel::<String>(100);
    let clients = Arc::new(Mutex::new(HashSet::new()));
    let message_history = Arc::new(Mutex::new(VecDeque::new()));

    while let Ok((stream, _)) = listener.accept().await {
        let tx_clone = tx.clone();
        let clients_clone = Arc::clone(&clients);
        let message_history_clone = Arc::clone(&message_history);
        let valid_actions_clone = Arc::clone(&valid_actions);

        tokio::spawn(async move {
            if let Ok(ws_stream) = accept_async(stream).await {
                println!("Nouvelle connexion !");
                let (mut ws_sender, mut ws_receiver) = ws_stream.split();
                
                let mut client_id = String::new();

                let history = message_history_clone.lock().await.clone();
                for msg in history {
                    if ws_sender.send(Message::Text(msg)).await.is_err() {
                        return;
                    }
                }

                let mut rx = tx_clone.subscribe();

                'message_loop: loop {
                    tokio::select! {
                        ws_msg = ws_receiver.next() => {
                            match ws_msg {
                                Some(Ok(msg)) => {
                                    if let Message::Text(text) = msg {
                                        let parts: Vec<&str> = text.splitn(3, ' ').collect();
                                        if parts.len() < 2 {
                                            println!("Client misbehaved");
                                            break 'message_loop;
                                        }

                                        let id = parts[0];
                                        let action = parts[1];
                                        let args = parts.get(2).unwrap_or(&"");

                                        let mut clients_lock = clients_clone.lock().await;
                                        let mut history_lock = message_history_clone.lock().await;

                                        if action == "ENTERS" {
                                            if clients_lock.insert(id.to_string()) {
                                                client_id = id.to_string();
                                                println!("ID '{}' ajouté à la liste.", id);
                                            } else {
                                                println!("ID '{}' est déjà dans la liste.", id);
                                            }
                                        } else {
                                            let valid_actions_lock = valid_actions_clone.lock().await;
                                            if !valid_actions_lock.contains(action) {
                                                println!("Client misbehaved (Action '{}' non reconnue)", action);
                                                break 'message_loop;
                                            }
                                            if !clients_lock.contains(id) {
                                                println!("Client misbehaved (ID '{}' non enregistré)", id);
                                                break 'message_loop;
                                            }
                                            println!("ID '{}' a effectué l'action '{}' avec args '{}'", id, action, args);
                                        }

                                        history_lock.push_back(text.clone());
                                        if history_lock.len() > 100 {
                                            history_lock.pop_front();
                                        }

                                        let _ = tx_clone.send(text);
                                    }
                                },
                                Some(Err(_)) => {
                                    println!("Erreur de lecture du websocket");
                                    break 'message_loop;
                                },
                                None => {
                                    println!("Client déconnecté");
                                    break 'message_loop;
                                }
                            }
                        },
                        broadcast_msg = rx.recv() => {
                            match broadcast_msg {
                                Ok(msg) => {
                                    if ws_sender.send(Message::Text(msg)).await.is_err() {
                                        println!("Erreur d'envoi au websocket");
                                        break 'message_loop;
                                    }
                                },
                                Err(_) => {
                                    println!("Erreur de réception du canal broadcast");
                                    break 'message_loop;
                                }
                            }
                        }
                    }
                }

                if !client_id.is_empty() {
                    println!("Client '{}' déconnecté.", client_id);
                    
                    let leaves_msg = format!("{} LEAVES", client_id);
                    
                    {
                        let mut history_lock = message_history_clone.lock().await;
                        history_lock.push_back(leaves_msg.clone());
                    }
                    
                    {
                        let mut clients_lock = clients_clone.lock().await;
                        clients_lock.remove(&client_id);
                    }
                    
                    let _ = tx_clone.send(leaves_msg.clone());
                    
                    println!("Message '{}' envoyé à tous les clients.", leaves_msg);
                }
            }
        });
    }
}