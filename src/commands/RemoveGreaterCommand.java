package commands;

import utility.*;
import exceptions.*;
import models.Ticket;

/**
 * This is command 'remove_greater_key'. Remove elements which have key that is more than given.
 */
public class RemoveGreaterCommand extends AbstractCommand implements ICommand {
    CollectionManager collectionManager;
    OrganizationAsker organizationAsker;

    public RemoveGreaterCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker){
        super("remove_greater", "remove all items from the collection that exceed the specified");
        this.collectionManager = collectionManager;
        this.organizationAsker = organizationAsker;
    }

    /**
     * Execute of 'remove_greater' command.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
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
            Ticket ticketColl = collectionManager.getByValue(ticket);
            if (ticketColl == null) throw new TicketNotFoundException();
            collectionManager.removeGreater(ticketColl);
            Console.println("Билеты исчезли");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Used: '" + getName() + "'");
        } catch (EmptyCollectionException exception) {
            Console.printerror("Collection is empty");
        } catch (TicketNotFoundException exception) {
            Console.printerror("There is no ticket like that");
        } catch (IncorrectScriptException exception) {}
        return false;
    }
}
