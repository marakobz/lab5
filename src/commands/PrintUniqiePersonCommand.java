package commands;

import exceptions.WrongArgumentException;
import models.Person;
import models.Ticket;
import utility.CollectionManager;
import utility.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Command 'print_unique'. Saves the collection to a file.
 */
public class PrintUniqiePersonCommand extends AbstractCommand {
    CollectionManager collectionManager;

    public PrintUniqiePersonCommand(CollectionManager collectionManager) {
        super("print_unique_person", "output the unique values of the person field of all items in the collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'print_unique' command.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) {
                throw new WrongArgumentException();
            }

            List<Person> personSet = collectionManager.getCollection()
                    .stream()
                    .map(Ticket::getPerson)
                    .collect(Collectors.toList());
            for (Person person : personSet){
                Console.println(person);
            }
        } catch (WrongArgumentException e) {
            System.out.println("Некорректный аргумент. Используйте: '" + getName() + "'");
        } catch (Exception e) {
            System.out.println("Ошибка. Повторите ввод.");
        }
        return false;
    }
}
