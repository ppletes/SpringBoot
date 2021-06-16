package com.persongeneratorapp;

import com.persongeneratorapp.binary.Deserialization;
import com.persongeneratorapp.binary.Serialization;
import com.persongeneratorapp.designpatterns.strategy.Context;
import com.persongeneratorapp.designpatterns.strategy.FormatCSV;
import com.persongeneratorapp.designpatterns.strategy.FormatKeyValue;
import com.persongeneratorapp.designpatterns.strategy.FormatXML;
import com.persongeneratorapp.exception.PersonCombinationPoolExhaustedException;
import com.persongeneratorapp.model.Child;
import com.persongeneratorapp.model.Elder;
import com.persongeneratorapp.model.Grownup;
import com.persongeneratorapp.model.Person;
import com.persongeneratorapp.reader.AgeReader;
import com.persongeneratorapp.reader.FamilyNameReader;
import com.persongeneratorapp.reader.GivenNameReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class PersonGeneratorAppApplication {

    static Set<Person> persons;
    static Set<Person> personsSerialization = new HashSet<>();

    public static void main(String[] args) {
        //make mix persons set
        Set<Person> persons = getPersons();

        //read previous file if exists
        Deserialization deserialization = new Deserialization();
        List<Person> personsDeserialization = deserialization.deserialization();

        //serialize new data in .ser file
        serialization();

        SpringApplication.run(PersonGeneratorAppApplication.class, args);
    }

    public static Set<Person> getPersons() {
        persons = new HashSet<>();

        AgeReader age = new AgeReader();
        List<String> ageList = age.readFile("attribute_age.txt");

        FamilyNameReader familyName = new FamilyNameReader();
        List<String> familyList = familyName.readFile("attribute_familyName.txt");

        GivenNameReader givenName = new GivenNameReader();
        List<String> nameList = givenName.readFile("attribute_givenName.txt");

        int count = 0;

        for (int a = 0; a < ageList.size(); a++) {
            for (int f = 0; f < familyList.size(); f++) {
                for (int n = 0; n < nameList.size(); n++) {
                    if (count == 10) {
                        try {
                            throw new PersonCombinationPoolExhaustedException("Number of duplicates: " + count);
                        } catch (PersonCombinationPoolExhaustedException e) {
                            e.printStackTrace();
                        }
                    }
                    boolean add = persons.add(new Person(nameList.get(n), familyList.get(f), ageList.get(a)));
                    if (!add) {
                        count = count + 1;
                    }
                }
            }
        }

        return persons;
    }

    public static void fileCSV(Set<Person> persons) {
        Context context = new Context(new FormatCSV());
        context.executeStrategy(persons);
    }

    public static void fileXML(Set<Person> persons) {
        Context context = new Context(new FormatXML());
        context.executeStrategy(persons);
    }

    public static void fileKeyValue(Set<Person> persons) {
        Context context = new Context(new FormatKeyValue());
        context.executeStrategy(persons);
    }

    public static void serialization() {
        Serialization serialization = new Serialization();
        for (Person person : getPersons()) {
            if (Integer.parseInt(person.getAge()) < 21) {
                Child child = new Child(person);
                personsSerialization.add(child.update());
            } else if (Integer.parseInt(person.getAge()) > 20 && Integer.parseInt(person.getAge()) < 65) {
                Grownup grownup = new Grownup(person);
                personsSerialization.add(grownup.update());
            } else if (Integer.parseInt(person.getAge()) > 65) {
                Elder elder = new Elder(person);
                personsSerialization.add(elder.update());
            }
        }

        serialization.serialization(personsSerialization);

        fileCSV(personsSerialization);
        fileXML(personsSerialization);
        fileKeyValue(personsSerialization);
    }
}
