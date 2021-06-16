package com.persongeneratorapp.binary;

import com.persongeneratorapp.model.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Set;

public class Serialization {

    public void serialization(Set<Person> persons) {
        try {
            FileOutputStream file = new FileOutputStream("file.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);

            for (Person person : persons) {
                Person p = new Person();
                p.setGivenName(person.getGivenName());
                p.setFamilyName(person.getFamilyName());
                p.setAge(person.getAge());
                p.setAgeType(person.getAgeType());

                out.writeObject(p);
            }

            out.flush();
            out.close();
            file.close();

        } catch (IOException ex) {
            //System.out.println("IOException is caught: can't write in file.ser");
        }
    }
}
