package com.it_academyproject.exceptions;

public class UserNotEnabled extends Exception
{
    private String username;
    private String message;

    //Constructor
    public UserNotEnabled( String username )
    {
        super( "The user " + username + " is not enabled." );
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
