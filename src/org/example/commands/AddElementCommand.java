package org.example.commands;

import org.example.utility.CollectionManager;
import org.example.utility.Console;
import org.example.utility.OrganizationAsker;
import org.example.exceptions.IncorrectScriptException;
import org.example.exceptions.WrongAmountOfElementsException;
import org.example.models.Ticket;

/**
 * Command 'add_element'. Saves the collection to a file.
 */
public class AddElementCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    private OrganizationAsker organizationAsker;

    public AddElementCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker){
        super("add","add a new item to the collection");
        this.organizationAsker = organizationAsker;
        this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'add_element' command.
     */
    @Override
    public boolean execute(String argument){
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.addToCollection(new Ticket(
                    collectionManager.generateNextId(),
                    organizationAsker.askName(),
                    organizationAsker.askCoordinates(),
                    collectionManager.generateCreationDate(),
                    organizationAsker.askPrice(),
                    organizationAsker.askDiscount(),
                    organizationAsker.askRefund(),
                    organizationAsker.askTicketType(),
                    organizationAsker.askPerson()
            ));
            Console.println("Ticket is created");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Used: '" + getName() + "'");
        } catch (IncorrectScriptException exception) {
            throw new RuntimeException(exception);
        }
        return false;
    }
}
