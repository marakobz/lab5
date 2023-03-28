package commands;

import exceptions.WrongAmountOfElementsException;
import models.Ticket;
import utility.CollectionManager;
import utility.Console;
import java.util.PriorityQueue;

/**
 * This is command 'show'. Prints all elements of collection.
 */
public class ShowCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "show all collection's elements");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'show' command.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.println(collectionManager.toString());
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Used: '" + getName() + "'");
        }
        return false;

    }
}
