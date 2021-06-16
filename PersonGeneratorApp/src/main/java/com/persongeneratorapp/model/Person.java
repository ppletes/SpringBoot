package com.persongeneratorapp.model;

import com.persongeneratorapp.designpatterns.observer.Observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable {

    private final List<Observer> observers = new ArrayList<Observer>();

    private String givenName;
    private String familyName;
    private String age;
    private String ageType;

    public Person() {
    }

    public Person(String givenName, String familyName, String age) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.age = age;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAgeType() {
        notifyAllObservers();
        return ageType;
    }

    public void setAgeType(String ageType) {
        //notifyAllObservers();
        this.ageType = ageType;
    }

    @Override
    public String toString() {
        return givenName + " " + familyName + " " + age + " " + ageType;
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
