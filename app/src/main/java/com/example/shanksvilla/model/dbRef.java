package com.example.shanksvilla.model;

public class dbRef {
    private String name;
    private int age;
    private int confirmation;
    private String contactNo;
    private String accountUID;

    public dbRef() {
    }

    public dbRef(String name, int age,  int confirmation, String contactNo, String accountUID) {
        this.name = name;
        this.age = age;
        this.confirmation = confirmation;
        this.contactNo = contactNo;
        this.accountUID = accountUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(int confirmation) {
        this.confirmation = confirmation;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAccountUID() {
        return accountUID;
    }

    public void setAccountUID(String accountUID) {
        this.accountUID = accountUID;
    }
}
