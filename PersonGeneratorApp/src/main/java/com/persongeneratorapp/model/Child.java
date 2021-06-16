package com.persongeneratorapp.model;

import com.persongeneratorapp.designpatterns.observer.Observer;

public class Child extends Observer {

    public Child(Person person) {
        this.person = person;
        this.person.attach(this);
    }

    @Override
    public Person update() {
        person.setAgeType("Child");
        return person;
    }
}
