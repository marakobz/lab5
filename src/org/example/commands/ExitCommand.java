package org.example.commands;

import org.example.exceptions.WrongAmountOfElementsException;
import org.example.utility.CollectionManager;
import org.example.utility.Console;

import java.io.IOException;


/**
 * Command 'exit'. Saves the collection to a file.
 */
public class ExitCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public ExitCommand(CollectionManager collectionManager){
        super("exit", "end the program (without saving to a file)");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'exit' command.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            //Console.println("Страдания программы завершены");
            collectionManager.exit();

        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
