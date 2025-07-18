package util;

import java.util.List;

import interpreter.Command;
import main.Player;

/**
 * The Message class represents a message sent by a player.
 * It contains the message content, author, rank, associated command, and arguments.
 */
public class Message {

    private String content;
    private int rank;
    private Player author;
    private Command command;
    private List<String> args;

    /**
     * Constructs a Message object.
     *
     * @param message The content of the message.
     * @param author The player who sent the message.
     * @param rank The rank of the message.
     * @param command The command associated with the message.
     * @param args The list of arguments associated with the command.
     */
    public Message(String message, Player author, int rank, Command command, List<String> args) {
        this.content = message;
        this.author = author;
        this.rank = rank;
        this.command = command;
        this.args = args;
    }

    /**
     * Gets the content of the message.
     *
     * @return The message content.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Gets the rank of the message.
     *
     * @return The message rank.
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Gets the author of the message.
     *
     * @return The player who sent the message.
     */
    public Player getAuthor() {
        return this.author;
    }

    /**
     * Sets the author of the message.
     *
     * @param author The player to set as the author.
     */
    public void setAuthor(Player author) {
        this.author = author;
    }

    /**
     * Gets the command associated with the message.
     *
     * @return The command.
     */
    public Command getCommand() {
        return this.command;
    }

    /**
     * Gets the list of arguments associated with the command.
     *
     * @return The list of arguments.
     */
    public List<String> getArgs() {
        return this.args;
    }

}
