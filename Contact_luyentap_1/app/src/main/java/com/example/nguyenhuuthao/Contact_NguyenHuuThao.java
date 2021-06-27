package com.example.nguyenhuuthao;

import java.io.Serializable;


public class    Contact_NguyenHuuThao implements Comparable<Contact_NguyenHuuThao>,Serializable {

    //TODO Câu 1
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

    // TODO Câu 3
    // 3.1 Lấy tên sinh viên
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
    // 3.2 So sánh tên sinh viên
    @Override
    public int compareTo(Contact_NguyenHuuThao o) {
        return GetFirstName().compareTo(o.GetFirstName());
    }

}
