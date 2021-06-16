package com.persongeneratorapp.designpatterns.observer;

import com.persongeneratorapp.model.Person;

public abstract class Observer {

    protected Person person;

    public abstract Person update();
}
