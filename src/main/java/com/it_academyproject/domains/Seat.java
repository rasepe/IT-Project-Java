package com.it_academyproject.domains;

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
    private int classRoom;


    
    
    public Seat() {
		
	}

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
 

    public int getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(int classRoom) {
        this.classRoom = classRoom;
    }
}
