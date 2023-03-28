package commands;

import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;


/**
 * Command 'save'. Saves the collection to a file.
 */
public class SaveCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save", "save collection to the file");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'save' command.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.saveCollection();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Used: '" + getName() + "'");
        }
        return false;
    }
}
