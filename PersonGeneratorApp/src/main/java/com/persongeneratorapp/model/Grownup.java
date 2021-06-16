package com.persongeneratorapp.model;

import com.persongeneratorapp.designpatterns.observer.Observer;

public class Grownup extends Observer {

    public Grownup(Person person) {
        this.person = person;
        this.person.attach(this);
    }

    @Override
    public Person update() {
        person.setAgeType("Grownup");
        return person;
    }
}
