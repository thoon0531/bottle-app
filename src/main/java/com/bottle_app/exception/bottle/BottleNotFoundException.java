package com.bottle_app.exception.bottle;

public class BottleNotFoundException extends RuntimeException{

    public BottleNotFoundException(String message){
        super(message);
    }
}
