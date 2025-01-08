package com.migros.couriertracking.service;

import com.migros.couriertracking.entity.Courier;
import com.migros.couriertracking.entity.CourierLocation;
import com.migros.couriertracking.entity.CourierMovementHistory;
import com.migros.couriertracking.repository.CourierLocationRepository;
import com.migros.couriertracking.repository.CourierMovementHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class CourierService {

    @Autowired
    private com.migros.couriertracking.repository.CourierRepository courierRepository;

    @Autowired
    CourierLocationRepository courierLocationRepository;
    @Autowired
    CourierMovementHistoryRepository courierMovementHistoryRepository;

    public void createLocation(String courierId, double lat, double lng) {
// TODO: validasyon ekle. controllerdan dto gelsin. dto içinde validasyon

        Courier courier = courierRepository.findCourierByCourierId(courierId);

        if (Objects.isNull(courier)) {
            new RuntimeException("Courier not found");
        }

        CourierLocation courierLocation = new CourierLocation();
        courierLocation.setLatitude(lat);
        courierLocation.setLongitude(lng);
        courierLocation.setTimestamp(LocalDateTime.now());
        courierLocation.setCourier(courier);
        courierLocationRepository.save(courierLocation);

        /* */
        CourierMovementHistory courierMovementHistory = courierMovementHistoryRepository.findByCourier_CourierId(courierId);
        if (courierMovementHistory.getCourier().getCourierId().equalsIgnoreCase(courierId)) {
            // courierMovementHistory.setStoreId(); // TODO: en yakın olduğu store'un'id'sini bulup eklicez.
            courierMovementHistoryRepository.updateCourierId(LocalDateTime.now(), null ,courierId);
        }else {
            CourierMovementHistory courierMovementHistory1 = new CourierMovementHistory();
            courierMovementHistory1.setDate(LocalDateTime.now());
            courierMovementHistory1.setCourier(courier);
            courierMovementHistory1.setStoreId(null);
            courierMovementHistoryRepository.save(courierMovementHistory1);
        }

        /*
        courierRepository.save(Courier.builder()
                .courierId(courierId)
                .courierLocations(List.of(CourierLocation.builder()
                        .courier(courier)
                        .longitude(lng)
                        .latitude(lat)
                        .timestamp(LocalDateTime.now())
                        .build()))
                .build()); */


        //checkStoreEntrance(courier, lat, lng);
    }
/*
    private void checkStoreEntrance(CourierDTO courier, double lat, double lng) {
        for (StoreDTO store : stores) {
            double distance = calculateDistance(lat, lng, store.getLat(), store.getLng());
            if (distance <= 100) { // 100 metre içinde
                // 4. Giriş kaydını kontrol et
                if (canLogEntrance(courier, store)) {
                    logEntrance(courier, store);
                }
            }
        }
    }

    private boolean canLogEntrance(CourierDTO courier, StoreDTO store) {
        // Kuryenin en son girişi kontrol et
        LocalDateTime lastEntranceTime = courier.getLastEntranceTime(store);
        if (lastEntranceTime == null || lastEntranceTime.isBefore(LocalDateTime.now().minusMinutes(1))) {
            return true; // Girişi kaydedebiliriz
        }
        return false; // Girişi kaydedemeyiz
    }

    private void logEntrance(CourierDTO courier, StoreDTO store) {
        // Girişi kaydet
        System.out.println("Courier " + courier.getCourierId() + " entered " + store.getName() + " at " + LocalDateTime.now());
        courier.setLastEntranceTime(store, LocalDateTime.now());
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        // Haversine formülü ile mesafe hesaplama
        final int R = 6371; // Dünya'nın yarıçapı (km)
        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // metre cinsine çevir
    }


 */
}

/*
Kuryenin Mevcut Konumunu Alma: computeIfAbsent metodu, eğer kurye daha önce kaydedilmemişse yeni bir Courier nesnesi oluşturur.

Yeni Konumu Ekleme: Yeni konum bilgileri (lat, lng, timestamp) Location nesnesi olarak oluşturulur ve kuryenin konum listesine eklenir.

Mağaza Girişini Kontrol Etme: checkStoreEntrance metodu, her mağaza için kuryenin mevcut konumunun 100 metre içinde olup olmadığını kontrol eder.

Giriş Kaydını Kontrol Etme: canLogEntrance metodu, kuryenin daha önce o mağazaya giriş yapıp yapmadığını ve son girişinin üzerinden 1 dakikadan fazla zaman geçmiş olup olmadığını kontrol eder.

Girişi Kaydetme: Eğer kuryenin girişi kaydedilebiliyorsa, logEntrance metodu çağrılır ve giriş kaydedilir.
 */
