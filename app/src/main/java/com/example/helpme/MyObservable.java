package com.example.helpme;

public interface MyObservable {
    void addObserver(MyObserver observer);
    void updateName(String newName);
}
