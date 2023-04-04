package org.example.models;


public enum Country{
    RUSSIA,
    UNITED_KINGDOM,
    USA,
    FRANCE,
    SPAIN;

    public static String nameList() {
        String nameList = "";
        for (Country nationality: values()) {
            nameList += nationality.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
