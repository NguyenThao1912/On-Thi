package com.example.nguyenhuuthao;

import java.io.Serializable;

public class    Contact_NguyenHuuThao implements Serializable {
    private int id ;
    private String name;
    private String phonenumber;

    public Contact_NguyenHuuThao() {
    }

    public Contact_NguyenHuuThao(int id, String name, String phonenumber) {
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
