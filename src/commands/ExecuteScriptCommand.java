package commands;

import exceptions.NoSuchCommandException;

import utility.UserIO;


/**
 * Command 'execute_script'. Saves the collection to a file.
 */
public class ExecuteScriptCommand extends AbstractCommand{
    private UserIO userIO;
    public ExecuteScriptCommand(UserIO userIO){
        super("execute_script", "read and execute the script from the specified file");
        this.userIO = userIO;
    }

    /**
     * Execute of 'execute_script' command.
     */
    @Override
    public boolean execute(String argument) {
        if (argument.isEmpty()) {
            throw new NoSuchCommandException();
        }
        userIO.startReadScript(argument);

        return false;
    }




}

