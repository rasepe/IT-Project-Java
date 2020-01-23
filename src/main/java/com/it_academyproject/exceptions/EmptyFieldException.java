package com.it_academyproject.exceptions;


public class EmptyFieldException extends Exception
{
    private String fieldName;
    private String message;

    //Constructor
    public EmptyFieldException( String fieldName )
    {
        super( "The field " + fieldName + " is cannot be empty" );
        this.fieldName = fieldName;
    }
    //Constructor
    public EmptyFieldException( String fieldName , String type)
    {
        super( "The field " + fieldName + " is " + type );
        this.fieldName = fieldName;
    }
}