package com.example.sfsalaries.Gson;

import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GsonExample {
    public static void main(String[] args) {

        var gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        var personList = new ArrayList<Person>();
        personList.add(new Person("John Doe", 30));
        personList.add(new Person("Jane Doe", 25));
        personList.add(new Person("Mike Ross", 35));

        try (FileWriter writer = new FileWriter("C:\\Users\\dar31\\OneDrive\\Pulpit\\Demos\\SfSalaries\\src\\main\\java\\com\\example\\sfsalaries\\Gson\\people.json")) {
            gson.toJson(personList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Pomyslnie zapisano people.json!");
    }
}
