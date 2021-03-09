package com.example.shanksvilla.model;

public class dbRef {
    private String name;
    private int age;
    private boolean isOccupied;
    private int confirmation;
    private String contactNo;
    private String accountUID;

    public dbRef() {
    }

    public dbRef(String name, int age, boolean isOccupied, int confirmation, String contactNo, String accountUID) {
        this.name = name;
        this.age = age;
        this.isOccupied = isOccupied;
        this.confirmation = confirmation;
        this.contactNo = contactNo;
        this.accountUID = accountUID;
    }
}
