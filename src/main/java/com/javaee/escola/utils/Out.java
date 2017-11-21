package com.javaee.escola.utils;

public class Out<T>{

    private T s;

    public void set(T value){
        s =  value;
    }
    public T get(){
        return s;
    }

    public Out() {
    }
}
