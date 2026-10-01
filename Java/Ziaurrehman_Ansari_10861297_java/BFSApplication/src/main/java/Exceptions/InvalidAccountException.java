package Exceptions;

/*
Author : 
Date: 
Project : 
*/
public class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(){
        super("Invalid Account number");
    }
}
