package com.migros.couriertracking.manager;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private static final List<CourierListener> listeners=new ArrayList<>();


    public static void addListener(CourierListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(CourierListener listener) {
        listeners.remove(listener);
    }

    public static void entranceNotify(String id) {
        for (CourierListener listener : listeners) {
            listener.notify(id);
        }
    }
}
