package com.it_academyproject.Domains;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.tools.View;

import javax.persistence.*;
import java.util.List;

@Entity
public class Seat
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int rowNumber;
    private int colNumber;

    @OneToMany
    private List<MyAppUser> myAppUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColNumber() {
        return colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public List<MyAppUser> getMyAppUser() {
        return myAppUser;
    }

    public void setMyAppUser(List<MyAppUser> myAppUser) {
        this.myAppUser = myAppUser;
    }
}
