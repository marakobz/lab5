package org.example.commands;

import org.example.exceptions.WrongAmountOfElementsException;
import org.example.models.Ticket;
import org.example.utility.CollectionManager;
import org.example.utility.Console;
import java.util.Iterator;


/**
 * Command 'average_of_discount'. Saves the collection to a file.
 */
public class AverageOfDiscountCommand extends AbstractCommand {
    CollectionManager collectionManager;

    public AverageOfDiscountCommand(CollectionManager collectionManager) {
        super("average_of_discount", "print the average value of the discount field for all items in the collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'average_of_discount' command.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            Iterator<Ticket> disc = collectionManager.getCollection().iterator();
            int sum = 0;
            int count = 0;
            while (disc.hasNext()) {
                Ticket ticket = disc.next();
                sum += ticket.getDiscount();
                count += 1;
                }
                Console.println("The average \"discount\" is: " + sum / count);
                return false;

        } catch (WrongAmountOfElementsException e) {
            throw new RuntimeException(e);
        }
    }
}
