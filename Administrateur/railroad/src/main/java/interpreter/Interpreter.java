package interpreter;

import client.Client;
import main.Game;
import util.Message;
import util.Parser;

/**
 * Represents the translators of received messages 
 * This class use the singleton pattern
 */
public class Interpreter {

    /**
     * Instance of class
     */
    private static Interpreter instance = null;

    /**
     * Private constructor of the class
     */
    private Interpreter() {}

    /**
     * Static method to obtain the instance of the class
     * 
     * @return the associated instance
     */
    public static Interpreter getInstance() {
        if(instance == null) {
            instance = new Interpreter();
        }
        return instance;
    }

    /**
     * Function to translate messages into actions
     *
     * @param message, the received message
     */
    public void interpret(String message) throws CloneNotSupportedException {
        Message mes = Parser.parse(message);
        Command command = mes.getCommand();
        if(Game.getCurrentGame().getPlayerById(message.split(" ")[0]) == null) {
            command.execute(mes);
        } else if(mes.getAuthor().getGrantedCommands().contains(command.getClass())) {
            if(!command.checkArgs(mes)) {
                Client.getInstance().sendMessage("admin BLAMES " + mes.getRank() + " for args");
            } else if (!command.execute(mes)){
                Client.getInstance().sendMessage("admin BLAMES " + mes.getRank() + " for execution "+mes.getContent());

            }
        }
        Game.getCurrentGame().addMessage(mes);
    }
    
}