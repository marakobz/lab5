package org.example.commands;

import org.example.exceptions.WrongArgumentException;
import org.example.models.Person;
import org.example.models.Ticket;
import org.example.utility.CollectionManager;
import org.example.utility.Console;

import java.util.List;
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
