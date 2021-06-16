package com.persongeneratorapp.designpatterns.strategy;

import com.persongeneratorapp.model.Person;

import java.util.Set;

public interface Strategy {

    void writeData(Set<Person> persons);

    void readData();
}
