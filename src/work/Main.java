package work;

import utility.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Main application class. Creates all instances and runs the program.
 *
 * @author Кобзарь Мария P3115
 */

public class Main {
    public static final String clack = "*";

    public static void main(String[] args) throws IOException {


        if (args.length >= 1) {
            try {
                var userIO = new UserIO(new Scanner(System.in), new PrintWriter(System.out));
                var organizationAsker = new OrganizationAsker(userIO);
                var csvReader = new CSVReader(args[0]);
                var collectionManager = new CollectionManager(csvReader);

                var commandManager = new CommandManager(collectionManager, userIO, organizationAsker, args[0]);
                Console console = new Console(commandManager, userIO);
                console.start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
