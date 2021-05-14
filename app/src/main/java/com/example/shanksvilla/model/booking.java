package com.example.shanksvilla.model;

public class booking {
    private final String bookingId;
    private final String dateFrom;
    private final String dateTo;
    private final String UID;
    private final String name_of_person;
    private final String age;
    private final String mobile_number;
    private final String email;
    //status means booking completed or not...
    private final Integer status;
    private  final  String details;

    public booking(String bookingId, String dateFrom,
                   String dateTo, String UID, String name_of_person,
                   String age, String mobile_number, String email,
                   Integer status, String details) {
        this.bookingId = bookingId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.UID = UID;
        this.name_of_person = name_of_person;
        this.age = age;
        this.mobile_number = mobile_number;
        this.email = email;
        this.status = status;
        this.details = details;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getUID() {
        return UID;
    }

    public String getName_of_person() {
        return name_of_person;
    }

    public String getAge() {
        return age;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public Integer getStatus() {
        return status;
    }

    public  String getDetails(){
        return details;
    }
}
