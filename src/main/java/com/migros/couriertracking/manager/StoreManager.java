package com.migros.couriertracking.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StoreManager implements CourierListener {
    private static Logger logger = LoggerFactory.getLogger(StoreManager.class);

    public StoreManager() {
        Manager.addListener(this);
    }


    @Override
    public void notify(String courierId) {
        logger.info("Courier number {} entered the store" , courierId);
    }
}
