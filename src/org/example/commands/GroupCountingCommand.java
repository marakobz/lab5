package org.example.commands;

import org.example.exceptions.WrongArgumentException;
import org.example.utility.CollectionManager;

/**
 * Command 'group_counting'. Saves the collection to a file.
 */
public class GroupCountingCommand extends AbstractCommand{
    CollectionManager collectionManager;

    public GroupCountingCommand(CollectionManager collectionManager){
        super("group_counting","group the elements of the collection by the value of the CreationDate field, output the number of elements in each group");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'group_counting' command.
     */
    @Override
    public boolean execute(String argument) {
    try {
        if (!argument.isEmpty()) {
            throw new WrongArgumentException();
        }
        collectionManager.groupCountingByCrDate();
    } catch (WrongArgumentException e) {
        System.out.println("Use: '" + getName() + "'");
    } catch (Exception e) {
        System.out.println("Mistake. Try again");
    }
        return false;
    }
}
