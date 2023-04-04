package org.example.utility;

import org.example.exceptions.*;
import org.example.models.Coordinates;
import org.example.models.Country;
import org.example.models.Person;
import org.example.models.TicketType;
import org.example.Main;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static java.time.LocalDateTime.parse;


/**
 * Ask from user or file ticket fields.
 */
public class OrganizationAsker {
    private final Float min_x = (float) -534;
    private final long min_discount = 0;
    private final long max_discount = 100;
    private final int min_price = 0;


    private boolean fileMode;
    private UserIO userIO;

    public OrganizationAsker(UserIO userIO) {
        fileMode = false;
        this.userIO = userIO;
    }


    /**
     * asks about ticket name
     *
     * @return new name
     */
    public String askName() throws IncorrectScriptException {
        String name;

        while (true) {
            try {
                Console.println("Введите имя:");
                Console.print(Main.clack);
                name = userIO.readline();
                if (fileMode) Console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Имя не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (MustBeNotEmptyException exception) {
                Console.printerror("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return name;
    }

    /**
     * asks coordinates of labwork
     *
     * @return new coordinates
     */
    public Coordinates askCoordinates() throws IncorrectScriptException {
        Float x;
        float y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }

    /**
     * asks about date of person.
     *
     * @return new birthday
     */

    public LocalDateTime askDate() throws IncorrectScriptException {
        LocalDateTime birthday;
        String strDate;
        while (true) {
            try {
                Console.println("Введите дату рождения человека:");
                Console.print(Main.clack);
                strDate = userIO.readline();
                if (fileMode) Console.println(strDate);
                birthday = parse(strDate);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Дата рождения не распознана");
                if (fileMode) throw new IncorrectScriptException();

            } catch (java.time.DateTimeException exception) {
                Console.printerror("Дата рождения должна быть представлена в формате yyyy-MM-ddTHH:mm:ss");
                if (fileMode) throw new IncorrectScriptException();

            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");

                System.exit(0);
            }
        }
        return birthday;
    }

    /**
     * asks y of coordinates
     *
     * @return new y
     */
    public float askY() throws IncorrectScriptException {
        String strY;
        float y;
        while (true) {
            try {
                Console.println("Введите координату Y:");
                Console.print(Main.clack);
                strY = userIO.readline();
                if (fileMode) Console.println(strY);
                y = Float.parseFloat(strY);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата Y не распознана!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * asks about x of coordinates
     *
     * @return new x
     */
    public Float askX() throws IncorrectScriptException {
        String strX;
        Float x;
        while (true) {
            try {
                Console.println("Введите координату X > " + min_x + ":");
                Console.print(Main.clack);
                strX = userIO.readline();
                if (fileMode) Console.println(strX);
                x = Float.parseFloat(strX);
                if (x < min_x) throw new NotDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата X не распознана!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NotDeclaredLimitsException exception) {
                Console.printerror("Координата X не может быть меньше " + min_x + "!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Координата X должна быть представлена числом!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }


    /**
     * asks about type of ticket
     *
     * @return new type
     */
    public TicketType askTicketType() throws IncorrectScriptException {
        String strTicketType;
        TicketType type;
        while (true){
            try {
                Console.println("Список категорий билетов: " + TicketType.nameList());
                Console.println("Введите категорию билета:");

                Console.print(Main.clack);
                strTicketType = userIO.readline();
                if (fileMode) Console.println(strTicketType);
                type = TicketType.valueOf(strTicketType.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Тип билета не распознан!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("Типа билета нет в списке!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return type;
    }

    /**
     * asks about price of a ticket
     *
     * @return new price
     */
    public int askPrice() throws IncorrectScriptException {
        String strPrice;
        int price;
        while (true) {
            try {
                Console.println("Введите цену");
                Console.print(Main.clack);
                strPrice = userIO.readline();
                if (fileMode) Console.println(strPrice);
                price = Integer.parseInt(strPrice);
                if (price < min_price) throw new NotDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Такой цены не может существовать");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Цены считаются цифрами!(желательно арабскими)");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }catch (NotDeclaredLimitsException exception){
                Console.println("Цена должна быть больше нуля");
                if (fileMode) throw new IncorrectScriptException();
            }
        }
        return price;

    }

    /**
     * asks about discount of ticket
     *
     * @return new discount
     */
    public long askDiscount() throws IncorrectScriptException {
        String strDiscount;
        long dicsount;
        while (true) {
            try {
                Console.println("Введите скидку");
                Console.print(Main.clack);
                strDiscount = userIO.readline();
                if (fileMode) Console.println(strDiscount);
                dicsount = Long.parseLong(strDiscount);
                if (dicsount > max_discount) throw new NotDeclaredLimitsException();
                if (min_discount > dicsount) throw new NotDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Скидку не дали");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Скидка должна быть представлена числом");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotDeclaredLimitsException exception) {
                Console.println("Скидка должна быть больше нуля и меньше ста");
                if (fileMode) throw new IncorrectScriptException();
            }

        }
        return dicsount;

    }

    /**
     * asks about refund(true or false)
     *
     * @return new refund
     */
    public Boolean askRefund() throws IncorrectScriptException {
        String strRefund;
        Boolean refund;
        while (true) {
            try {
                Console.println("Есть ли возврат денег на билет? Введите true/false");
                Console.print(Main.clack);
                strRefund = userIO.readline();
                if (fileMode) Console.println(strRefund);
                refund = Boolean.parseBoolean(strRefund);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Возврата денег нет");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Возврат денег либо может быть, либо нет. Введите конкретный ответ.");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return refund;
    }

    /**
     * asks for person in ticket
     *
     * @return new Person
     */
    public Person askPerson() throws IncorrectScriptException{
        LocalDateTime birthday;
        Float height;
        Float weight;
        Country nationality;

        birthday = askDate();
        height = askHeight();
        weight = askWeight();
        nationality = askNationality();
        return  new Person(birthday, height, weight, nationality);
    }

    /**
     * asks about height of person
     *
     * @return new height
     */
    public Float askHeight() throws IncorrectScriptException {
        String strHeight;
        Float height;
        while (true) {
            try {
                Console.println("Введите показатели роста");
                Console.print(Main.clack);
                strHeight = userIO.readline();
                if (fileMode) Console.println(strHeight);
                height = Float.parseFloat(strHeight);
                if (height > 300) throw new NotDeclaredLimitsException();
                break;

            }catch (NotDeclaredLimitsException exception){
                Console.println("Рост человека должен быть меньше чем 3 метра");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NoSuchElementException exception) {
                Console.printerror("Рост не распознан");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Рост должен быть представлен числом!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return height;
    }

    /**
     * asks about weight of person
     *
     * @return new weight
     */
    public Float askWeight() throws IncorrectScriptException {
        String strWeight;
        Float weight;
        while (true) {
                try {
                    Console.println("Введите показатели веса");
                    Console.print(Main.clack);
                    strWeight = userIO.readline();
                    if (fileMode) Console.println(strWeight);
                    weight = Float.parseFloat(strWeight);

                    if (weight > 120) throw new NotDeclaredLimitsException();
                    break;

                }catch (NotDeclaredLimitsException exception){
                    Console.println("Люди с таким весом не могут купить билеты, для них не хватит места!!");
                    if (fileMode) throw new IncorrectScriptException();
                } catch (NoSuchElementException exception) {
                    Console.printerror("Вес не распознан");
                    if (fileMode) throw new IncorrectScriptException();
                } catch (NumberFormatException exception) {
                    Console.printerror("Вес должен быть представлен числом!");
                    if (fileMode) throw new IncorrectScriptException();
                } catch (NullPointerException | IllegalStateException exception) {
                    Console.printerror("Непредвиденная ошибка!");
                    System.exit(0);
                }
            }
        return weight;
    }

    /**
     * asks about nationality of person
     *
     * @return new nationality
     */
    public Country askNationality () throws IncorrectScriptException {
        Country nationality;
        String strNationality;
        while (true) {
            try {
                Console.println("Список доступных национальностей человека - " + Country.nameList());
                Console.println("Введите национальность человека:");
                Console.print(Main.clack);
                strNationality = userIO.readline();
                if (fileMode) Console.println(strNationality);
                nationality = Country.valueOf(strNationality.toUpperCase());
                break;

            } catch (NoSuchElementException exception) {
                Console.printerror("Национальность не распознана");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("Страны нет в списке");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }

        }
        return nationality;
    }

    /**
     * manages questions for update command
     *
     * @return answer
     */
    public boolean askQuestion(String question) throws IncorrectScriptException {
        String finalQuestion = question + " (yes/no):";
        String answer;
        while (true) {
            try {
                Console.println(finalQuestion);
                Console.print(Main.clack);
                answer = userIO.readline();
                if (fileMode) Console.println(answer);
                if (!answer.equals("yes") && !answer.equals("no")) throw new NotDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Ответ не распознан!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NotDeclaredLimitsException exception) {
                Console.printerror("Ответ должен быть представлен знаками 'yes' или 'no'!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return (answer.equals("+")) ? true : false;
    }

        @Override
        public String toString () {
            return "Organisation Asker is helper class for asking things";
        }
}


