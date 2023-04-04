package org.example.utility;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.models.*;

import static java.time.LocalDateTime.parse;

/**
 * Operates the collection.
 */

public class CollectionManager {

    private PriorityQueue<Ticket> collection = new PriorityQueue<>();
    private CSVReader csvReader;
    public File save_file = new File("temp.txt");
    private Scanner scanner = new Scanner(System.in);




    public CollectionManager(CSVReader csvReader) throws IOException {
        this.csvReader = csvReader;


        loadCollection();

    }

    /**
     * @return The collection itself.
     */
    public PriorityQueue<Ticket> getCollection(){
        return collection;
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize(){
        return collection.size();
    }

    /**
     * @return The first element of the collection or null if collection is empty.
     */
   public Ticket getFirst(){
        if (collection.isEmpty()) return null;
        return collection.peek();
    }


    /**
     * @return A marine by his ID or null if marine isn't found.
     */
    public Ticket getById(int id) {
        for (Ticket element : collection) {
            if (element.getId() == id) return element;
        }
        return null;
    }

    /**
     * @param ticket A ticket which value will be found.
     * @return A marine by his value or null if marine isn't found.
     */
    public Ticket getByValue(Ticket ticket){
        for (Ticket tickett : collection){
            if (tickett.equals(ticket)) return tickett;
        }
        return null;
    }

    /**
     * Removes greater.
     */
    public void removeGreater(Ticket ticketToCompare) {
        collection.removeIf(ticket -> ticket.compareTo(ticketToCompare) > 0);
    }

    /**
     * Adds a new ticket to collection.
     * @param element A ticket to add.
     */
    public void addToCollection(Ticket element) {
        collection.add(element);
        saveToTemp(element);

    }



    /**
     * Removes a new ticket to collection.
     * @param element A marine to remove.
     */
    public void removeFromCollection(Ticket element) {
        collection.remove(element);
    }

    /**
     * Generates next ID. It will be (the bigger one + 1).
     * @return Next ID.
     */
    public int generateNextId() {

        HashSet<Integer> hashSetId = new HashSet<>();
        if (collection != null){
            for (Object i : collection) {
                hashSetId.add(((Ticket)i).getId());
            }
        }
        int id;
        do {
            id = new Random().nextInt(Integer.MAX_VALUE);;
        } while (!hashSetId.add(id));
        return id;
    }


    /**
     * Generates creation date.
     */
    public LocalDate generateCreationDate(){
        if (collection.isEmpty()) return LocalDate.now();
        return collection.peek().getCreationDate();
    }


    public void saveToTemp(Ticket ticket){
        try (PrintWriter writer = new PrintWriter(new FileWriter(save_file, true))) {
            writer.println(convertTicketToString(ticket)); // записываем в виде строки
        } catch (IOException e) {
            System.err.printf("An error occurred while writing to file '%s': %s%n", save_file.getName(), e.getMessage());
        }

    }
    /**
     * Saves the collection to file.
     */
    public void saveCollection() {

        csvReader.write(collection);

    }

    private String convertTicketToString(Ticket ticket){
        return String.join(",",
                String.valueOf(ticket.getId()),
                ticket.getName(),
                String.valueOf(ticket.getCoordinates().getX()),
                String.valueOf(ticket.getCoordinates().getY()),
                String.valueOf(ticket.getCreationDate()),
                String.valueOf(ticket.getPrice()),
                String.valueOf(ticket.getDiscount()),
                String.valueOf(ticket.getRefundable()),
                String.valueOf(ticket.getType()),
                String.valueOf(ticket.getPerson().getBirthday()),
                String.valueOf(ticket.getPerson().getHeight()),
                String.valueOf(ticket.getPerson().getWeight()),
                String.valueOf(ticket.getPerson().getNationality())

                );
    }

    private Ticket convertStringToTicket(String string) throws IllegalArgumentException {
        String[] fields = string.split(",");
        if (fields.length != 13) {
            throw new IllegalArgumentException("Invalid string format for ticket: " + string);
        }
        int id = Integer.parseInt(fields[0]);
        String name = fields[1];
        Float x = Float.parseFloat(fields[2]);
        float y = Float.parseFloat(fields[3]);
        Coordinates coordinates = new Coordinates(x, y);
        LocalDate creationDate = LocalDate.parse(fields[4]);

        int price = Integer.parseInt(fields[5]);
        Long discount = Long.parseLong(fields[6]);
        boolean refundable = Boolean.parseBoolean(fields[7]);

        TicketType type = TicketType.valueOf(fields[8]);

        LocalDateTime birthday = parse(fields[9]);
        Float height = Float.parseFloat(fields[10]);
        Float weight = Float.parseFloat(fields[11]);
        Country nationality = Country.valueOf(fields[12]);


        Person person = new Person(birthday, height, weight, nationality);
        return new Ticket(id, name, coordinates, creationDate, price, discount, refundable, type, person);
    }

    public void exit() throws IOException {
            System.out.print("There are unsaved changes. Do you want to save them? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            switch (response) {
                case "yes":
                    saveCollection();
                    break;
                case "no":
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }

            Console.println("Программа завершена");
            System.exit(0);

    }

    private void loadFromTempFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(save_file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Ticket ticket = convertStringToTicket(line);
                    collection.add(ticket);
                } catch (IllegalArgumentException e) {
                    Console.printerror("Incorrect format of data");
                }
            }

        } catch (IOException e) {
            System.err.printf("An error occurred while reading from file '%s': %s%n", save_file.getName(), e.getMessage());
        }

    }

    /**
     * Loads the collection from file.
     */
    public void loadCollection() throws IOException {
        if (!save_file.exists()){
            save_file.createNewFile();
        }
        collection = csvReader.readFromFile();
        Console.println("Data loaded successfully");
        if (save_file.exists()){
            FileReader fileReader = new FileReader(save_file);
            if (fileReader.read() == -1){
                Console.println("Temp file is empty");
            }else{
                askToLoad();
            }
        }

    }

    public void askToLoad(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to load unsaved data from temporary file? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("yes")) {
            loadFromTempFile();
            Console.println("Data is saved from temporary file");
        }else{
            Console.println("Do you want to delete temp file? (yes/no)");
            String answer = scanner.nextLine().trim().toLowerCase();
            if(answer.equals("yes")){
            save_file.delete(); // удаляем временный файл с несохраненными данными после загрузки
            Console.println("Temporary file deleted");
            }
        }
    }

    /**
     * Clears the collection.
     */
    public void clearCollection() {
        collection.clear();
    }

    /**
     * Counting group by its creation date
     */
    public void groupCountingByCrDate(){
        HashMap<LocalDate, TreeSet<Ticket>> groupMap = new HashMap<>();
        for (Ticket i : collection){
            if (groupMap.get((i).getCreationDate()) == null){
                TreeSet<Ticket> x = new TreeSet<>();
                x.add(i);
                groupMap.put((i).getCreationDate(), x);
            } else groupMap.get((i).getCreationDate()).add(i);
        }
        for (Map.Entry<LocalDate, TreeSet<Ticket>> entry : groupMap.entrySet()){
            Console.println("Элементы созданные " + entry.getKey() + " :\n");
            entry.getValue().forEach(CollectionManager::display);
        }
    }

    /**
     * Display the info about created ticket
     */
    static void display(Ticket ticket) {
        Console.println("ID билета:" + ticket.getId());
        Console.println("Имя билета:" + ticket.getName());
        Console.println("Дата создания билета:" + ticket.getCreationDate());
        Console.println("Координаты:" + ticket.getCoordinates());
        Console.println("Цена билета:" + ticket.getPrice());
        Console.println("Скидка на билет:" + ticket.getDiscount());
        Console.println("Возврат денег на билет:" + ticket.getRefundable());
        Console.println("Тип билета:" + ticket.getType());
        Console.println("Человек:" + ticket.getPerson());
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "There is nothing in collection";

        StringBuilder builder = new StringBuilder();
        for (Ticket ticket : collection) {
            builder.append(ticket);
            builder.append("\n");
        }
        return builder.toString();
    }
}
