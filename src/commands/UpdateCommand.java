package commands;

import utility.CollectionManager;
import utility.OrganizationAsker;
import exceptions.*;
import models.Coordinates;
import models.Person;
import models.Ticket;
import models.TicketType;
import java.time.LocalDate;

/**
 * This is command 'update'. Refreshes an element of collection which id equals given one.
 */
public class UpdateCommand extends AbstractCommand{
    CollectionManager collectionManager;
    OrganizationAsker organizationAsker;

    public UpdateCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker){
        super("update", " ID - reload the value of the collection item");
        this.collectionManager = collectionManager;
        this.organizationAsker = organizationAsker;
    }

    /**
     * Execute of 'update' command.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();

            var id = Integer.parseInt(argument);
            var oldTicket = collectionManager.getById(id);
            if (oldTicket == null) throw new TicketNotFoundException();

            String name = oldTicket.getName();
            Coordinates coordinates = oldTicket.getCoordinates();
            LocalDate creationDate = oldTicket.getCreationDate();
            int price = Integer.parseInt(argument);
            oldTicket.getPrice();
            long discount = Long.parseLong(argument);
            oldTicket.getDiscount();
            Boolean refundable = Boolean.getBoolean(argument);
            oldTicket.getRefundable();
            TicketType type = oldTicket.getType();
            Person person = oldTicket.getPerson();

            collectionManager.removeFromCollection(oldTicket);
            if (organizationAsker.askQuestion("Изменить имя билетика?")) name = organizationAsker.askName();
            if (organizationAsker.askQuestion("Изменить координаты?")) coordinates = organizationAsker.askCoordinates();
            if (organizationAsker.askQuestion("Изменить цену?")) price = organizationAsker.askPrice();
            if (organizationAsker.askQuestion("Изменить скидку?")) discount = organizationAsker.askDiscount();
            if (organizationAsker.askQuestion("Изменить сумму вычета?")) refundable = organizationAsker.askRefund();
            if (organizationAsker.askQuestion("Изменить тип билета?")) type = organizationAsker.askTicketType();
            if (organizationAsker.askQuestion("Изменить человека?")) person = organizationAsker.askPerson();

            collectionManager.addToCollection(new Ticket(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    price,
                    discount,
                    refundable,
                    type,
                    person
            ));
            System.out.println("Ticket is changed");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Used: '" + getName() + "'");
        } catch (EmptyCollectionException exception) {
            System.out.println("Collection is empty");
        } catch (NumberFormatException exception) {
            System.out.println("ID has to be a number");
        } catch (TicketNotFoundException exception) {
            System.out.println("Ticket with this ID doesn't exist");
            return false;
        } catch (IncorrectScriptException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
