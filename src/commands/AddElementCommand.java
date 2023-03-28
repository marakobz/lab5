package commands;

import utility.CollectionManager;
import utility.Console;
import utility.OrganizationAsker;
import exceptions.IncorrectScriptException;
import exceptions.WrongAmountOfElementsException;
import models.Ticket;

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
