package com.persongeneratorapp.binary;

import com.persongeneratorapp.model.Person;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Deserialization {

    public List<Person> deserialization() {
        List<Person> personsDeserialization=new ArrayList<Person>();
        File file = new File("file.ser");
        if (file.exists()) {
            Person person = null;
            try {
                FileInputStream fis = new FileInputStream("file.ser");
                ObjectInputStream in = new ObjectInputStream(fis);

                while (true) {
                    try {
                        person = (Person) in.readObject();
                        personsDeserialization.add(person);
                    } catch (EOFException e) {
                        break;
                    }
                }

                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return personsDeserialization;
    }
}
