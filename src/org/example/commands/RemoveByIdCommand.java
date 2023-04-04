package org.example.commands;

import org.example.exceptions.WrongArgumentException;
import org.example.utility.CollectionManager;
import org.example.exceptions.EmptyCollectionException;
import org.example.exceptions.TicketNotFoundException;
import org.example.models.Ticket;

/**
 * Command 'remove_by_id'. Saves the collection to a file.
 */
public class RemoveByIdCommand extends AbstractCommand{
    CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager){
        super("remove_by_id","delete an item from the collection by its id");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'remove_by_id' command.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongArgumentException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
            int id = Integer.parseInt(argument);
            Ticket ticketToremove = collectionManager.getById(id);
            if (ticketToremove == null) throw new TicketNotFoundException();
            collectionManager.removeFromCollection(ticketToremove);
            System.out.println("Ticket is deleted");

        } catch (WrongArgumentException exception) {
            System.out.println("Used: '" + getName() + "'");
        } catch (EmptyCollectionException exception) {
            System.out.println("Collection is empty");
        } catch (NumberFormatException exception) {
            System.out.println("ID has to be a number");
        } catch (TicketNotFoundException exception) {
            System.out.println("Ticket with this ID doesn't exist");
        }
        return false;
    }

}
//перепроверить корректность работы