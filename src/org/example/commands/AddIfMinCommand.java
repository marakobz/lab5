package org.example.commands;

import org.example.utility.CollectionManager;
import org.example.utility.Console;
import org.example.utility.OrganizationAsker;
import org.example.exceptions.IncorrectScriptException;
import org.example.exceptions.WrongAmountOfElementsException;
import org.example.models.Ticket;

/**
 * Command 'add_if_min'. Saves the collection to a file.
 */
public class AddIfMinCommand extends AbstractCommand {
    CollectionManager collectionManager;
    OrganizationAsker organizationAsker;

    public AddIfMinCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker) {
        super("add_min_element", "add a new item to the collection if its value is less than that of the smallest item in this collection");
        this.organizationAsker = organizationAsker;
        this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'add_if_min' command.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            var ticket = new Ticket(
                    collectionManager.generateNextId(),
                    organizationAsker.askName(),
                    organizationAsker.askCoordinates(),
                    collectionManager.generateCreationDate(),
                    organizationAsker.askPrice(),
                    organizationAsker.askDiscount(),
                    organizationAsker.askRefund(),
                    organizationAsker.askTicketType(),
                    organizationAsker.askPerson()
            );
            if (collectionManager.collectionSize() == 0 || ticket.compareTo(collectionManager.getFirst()) < 0) {
                collectionManager.addToCollection(ticket);
                Console.println("Ticket is added successfully");
                return true;
            } else
                Console.printerror("The value of the ticket is greater than the value of the smallest of the tickets");
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Used: '" + getName() + "'");
        } catch (IncorrectScriptException exception) {
        }
        return false;
    }
}
