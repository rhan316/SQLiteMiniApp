package com.example.sfsalaries.Gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GsonDeserialize {
    public static void main(String[] args) {

        var gson = new Gson();
        var personListType = new TypeToken<List<Person>>() {}.getType();

        try (FileReader reader = new FileReader("people.json")) {

            List<Person> personList = gson.fromJson(reader, personListType);
            personList.forEach(e -> System.out.println(e.getName() + ": " + e.getAge()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
