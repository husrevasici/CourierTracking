package com.migros.couriertracking.manager;

public abstract class Command<T> {

    public abstract void execute(T object);
}
