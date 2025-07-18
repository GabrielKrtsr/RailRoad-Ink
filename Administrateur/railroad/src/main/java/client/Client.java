package client;

import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import interpreter.Interpreter;

/**
 * Represents a WebSocket client for connecting to the reflector.
 */
public class Client {

	/**
	 * Represents a static instance of the interpreter to translate messages.
	 */
	private static final Interpreter interpreter = Interpreter.getInstance();

	/**
	 * Represents the singleton instance of the client.
	 */
	private static Client instance = null;

	/**
	 * WebSocket client for managing the connection.
	 */
	private WebSocketClient client;

	/**
	 * Private constructor to enforce singleton pattern.
	 */
	private Client() {}

	/**
	 * Retrieves the singleton instance of the client.
	 *
	 * @return the singleton instance of the client.
	 */
	public static Client getInstance() {
		if (instance == null) {
			instance = new Client();
		}
		return instance;
	}

	/**
	 * Establishes a connection with the reflector.
	 *
	 * @throws InterruptedException if the connection process is interrupted.
	 */
	public void connect() throws InterruptedException {
		try {
			URI serverUri = new URI("ws://localhost:8000");

			this.client = new WebSocketClient(serverUri) {
				@Override
				public void onOpen(ServerHandshake handshakedata) {
					System.out.println("Connecté au serveur WebSocket !");
				}

				@Override
				public void onMessage(String message) {
					try {
						interpreter.interpret(message);
					} catch (CloneNotSupportedException e) {
						throw new RuntimeException(e);
					}
				}

				@Override
				public void onClose(int code, String reason, boolean remote) {
					System.out.println("\n\nConnexion fermée.");
					System.exit(0);
				}

				@Override
				public void onError(Exception ex) {
					System.err.println("Erreur : " + ex.getMessage());
				}
			};

			client.connectBlocking();
			Client.getInstance().sendMessage("admin ENTERS");
			Client.getInstance().sendMessage("admin ELECTS admin");
		} catch (URISyntaxException e) {
			System.err.println("URI invalide : " + e.getMessage());
		}
	}

	/**
	 * Sends a message to the WebSocket server.
	 *
	 * @param message The message to be sent.
	 */
	public void sendMessage(String message) {
		this.client.send(message);
	}
}
