package com.persongeneratorapp.designpatterns.strategy;

import com.persongeneratorapp.model.Person;

import java.util.Set;

public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(Set<Person> persons) {
        strategy.writeData(persons);
        strategy.readData();
    }
}
