package com.migros.couriertracking.config;

import com.migros.couriertracking.entity.Store;
import com.migros.couriertracking.manager.CourierManager;
import com.migros.couriertracking.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreDataInitializer implements CommandLineRunner {

    private final StoreRepository storeRepository;

    private static Logger logger = LoggerFactory.getLogger(StoreDataInitializer.class);

    private static final String ADDING_STORE_MESSAGE = "Adding store: {}";

    public StoreDataInitializer(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Store> predefinedStores = List.of(
            new Store(null, "Ataşehir MMM Migros", 40.9923307, 29.1244229),
            new Store(null, "Novada MMM Migros", 40.986106, 29.1161293),
            new Store(null, "Beylikdüzü 5M Migros", 41.0066851, 28.6552262),
            new Store(null, "Ortaköy MMM Migros", 41.055783, 29.0210292),
            new Store(null, "Caddebostan MMM Migros", 40.9632463, 29.0630908)
        );

        for (Store store : predefinedStores) {
            storeRepository.findByName(store.getName())
                .orElseGet(() -> {
                    logger.info(ADDING_STORE_MESSAGE , store.getName());
                    storeRepository.save(store);
                    return null;
                });
        }
    }
}
