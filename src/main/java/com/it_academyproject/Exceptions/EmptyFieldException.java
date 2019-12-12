package com.it_academyproject.Exceptions;


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
}