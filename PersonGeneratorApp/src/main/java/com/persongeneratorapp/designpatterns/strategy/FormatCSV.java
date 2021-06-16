package com.persongeneratorapp.designpatterns.strategy;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.persongeneratorapp.model.Person;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FormatCSV implements Strategy {

    @Override
    public void writeData(Set<Person> persons) {
        String csv = "file.csv";
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(csv));

            String header = "Given Name; Family name; Age; Age type";
            writer.writeNext(header.toString().split("; "));

            Set<Person> persons2 = new HashSet<>(persons);

            for (Iterator<Person> it = persons2.iterator(); it.hasNext(); ) {
                Person f = it.next();
                writer.writeNext(f.toString().split(" "));
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readData() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("file.csv"), ',', '"', 1);

            System.err.println("CSV FILE: ");
            List<String[]> allRows = reader.readAll();
            for (String[] row : allRows) {
                System.out.println(Arrays.toString(row));
            }
            System.out.println("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
