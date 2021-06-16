package com.persongeneratorapp.designpatterns.strategy;

import com.persongeneratorapp.model.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FormatKeyValue implements Strategy {

    @Override
    public void writeData(Set<Person> persons) {
        try {
            File file = new File("file.txt");
            FileWriter myWriter = new FileWriter(file);

            Map<String, Set<String>> map = getMap(persons);
            for (String key : map.keySet()) {
                myWriter.write(key + "=" + map.get(key));
            }

            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void readData() {
        try {
            File file = new File("file.txt");
            Scanner nameReader = new Scanner(file);

            System.err.println("KEY=VALUE FILE: ");
            while (nameReader.hasNextLine()) {
                String data = nameReader.nextLine();
                System.out.println(data);
            }
            System.out.println("\n");

            nameReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Map<String, Set<String>> getMap(Set<Person> persons) {
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        for (Person person : persons) {
            if (!map.containsKey(person.getAgeType())) {
                map.put(person.getAgeType(), new HashSet<>());
            }
        }
        for (Person person : persons) {
            for (String key : map.keySet()) {
                if (key.equals(person.getAgeType())) {
                    map.get(key).add(person.getGivenName() + " " + person.getFamilyName() + " " + person.getAge());
                }
            }
        }

        return map;
    }
}
