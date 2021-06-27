package com.example.onthi_luyentap_2;

import java.io.Serializable;

public class Contact_NguyenHuuLuan implements  Comparable<Contact_NguyenHuuLuan>,Serializable {
    // TODO Câu 1
    private int id;
    private String name;
    private String phonenumber;

    public Contact_NguyenHuuLuan() {
    }

    public Contact_NguyenHuuLuan(int id, String name, String phonenumber) {
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

    public void setname(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String GetFirstName()
    {
        int lastspace = name.lastIndexOf(" ");
        String firstname = "";
        if (lastspace != -1){
            firstname = name.substring(lastspace+1);
        }
        else firstname = name;
        return firstname.toLowerCase();
    }

    @Override
    public int compareTo(Contact_NguyenHuuLuan o) {
        return GetFirstName().compareTo(o.GetFirstName());
    }

}
