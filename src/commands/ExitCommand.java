package commands;

import utility.CollectionManager;
import exceptions.WrongAmountOfElementsException;
import utility.Console;


/**
 * Command 'exit'. Saves the collection to a file.
 */
public class ExitCommand extends AbstractCommand{

    public ExitCommand(){
        super("exit", "end the program (without saving to a file)");
    }

    /**
     * Execute of 'exit' command.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.println("Страдания программы завершены");
            System.exit(0);

        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        }
        return true;
    }
}
