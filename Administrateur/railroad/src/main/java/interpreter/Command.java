package interpreter;

import util.Message;

/*
 * Abstract represetation of a command associated with a message
 */
public abstract class Command {

    /**
     * Number of args needed in the associated message
     * -1 : infinite number (at least 1)
     * n : finite number
     */
    protected int args;

    /**
     * Constructor of the class without number of args specification (default 0)
     */
    public Command() {
        this.args = 0;
    }

    /**
     * Constructor of the class with number of args
     * @param args, the number of arguments needed
     */
    public Command(int args) {
        this.args = args;
    }

    /**
     * Getter of the number of args needed
     * @return the number of args needed
     */
    public int getArgs() {
        return this.args;
    }

    /**
     * Check if the number of args in the message is correc
     * @param message, the associated message
     * @return true if the message is correct, false if not
     */
    public boolean checkArgs(Message message) {
        if(this.args == -1) {
            return message.getArgs().size() >= 1;
        } else if (this.args == 0) {
            return message.getArgs().size() == 0;
        } else {
            return message.getArgs().size() == this.args;
        }
    }

    /**
     * Abstract method to define the behavior of this command
     * @param message, the associated message
     * @return true if the execution doesn't encounter any problem, false if not
     */
    public abstract boolean execute(Message message);
    
}