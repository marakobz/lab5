package utility;

import java.io.*;

import java.time.LocalDate;
import java.util.*;

import models.Ticket;

/**
 * Operates the collection.
 */

public class CollectionManager {

    private PriorityQueue<Ticket> collection = new PriorityQueue<>();
    private CSVReader csvReader;


    public CollectionManager(CSVReader csvReader) throws IOException {
        this.csvReader = csvReader;

        loadCollection();
    }

    /**
     * @return The collecton itself.
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


    /**
     * Saves the collection to file.
     */
    public void saveCollection(){
        csvReader.write( collection);
    }

    /**
     * Loads the collection from file.
     */
    public void loadCollection(){

        collection = csvReader.readFromFile();
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
