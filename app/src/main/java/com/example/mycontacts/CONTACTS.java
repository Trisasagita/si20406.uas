package com.example.mycontacts;

public class CONTACTS {

    public String name;
    public String phone;
    public String email;
    public String photo;
    public String type;

    public CONTACTS(String name, String phone, String email, String photo, String type) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }

    public String getType() {
        return type;
    }
}
