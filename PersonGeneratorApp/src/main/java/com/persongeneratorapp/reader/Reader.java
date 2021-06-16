package com.persongeneratorapp.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public interface Reader {

    default List<String> readFile(String fileString) {
        List<String> list = new ArrayList<>();
        try {
            File file = new File(fileString);

            Scanner nameReader = new Scanner(file);
            while (nameReader.hasNextLine()) {
                String data = nameReader.nextLine();
                String[] split = data.split(",");
                list.addAll(Arrays.asList(split));
            }

            nameReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return list;
    }
}
