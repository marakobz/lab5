package commands;

import utility.CollectionManager;
import utility.Console;

/**
 * Command 'head'. Saves the collection to a file.
 */
public class HeadCommand extends AbstractCommand {
    CollectionManager collectionManager;

    public HeadCommand(CollectionManager collectionManager) {
        super("head", " show first element of collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'head' command.
     */
    @Override
    public boolean execute(String argument) {
            if (!argument.isEmpty()) {
                Console.println("Use: '" + getName() + "'");
                return false;
            }

            if (collectionManager.getCollection().isEmpty()) {
                Console.println("Collection is empty");
            } else {
                Console.println(collectionManager.getFirst());
            }
            return true;
        }
}
