package com.persongeneratorapp.model;

import com.persongeneratorapp.designpatterns.observer.Observer;

public class Elder extends Observer {

    public Elder(Person person) {
        this.person = person;
        this.person.attach(this);
    }

    @Override
    public Person update() {
        person.setAgeType("Elder"); return person;
    }
}
