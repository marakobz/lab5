package commands;

import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;

/**
 * Command 'clear'. Saves the collection to a file.
 */
public class ClearCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
     super("clear", "clear collection");
     this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'clear' command.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.clearCollection();
            Console.println("Коллекция очищена!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        }
        return false;
    }

}