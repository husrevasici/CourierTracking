package com.migros.couriertracking.constant;

public class ApiUrls {

    private ApiUrls() {
        // private constructor
    }

    public static final String VERSION = "api/v1";
    public static final String COURIERS = VERSION + "/couriers";
    public static final String STORES = VERSION + "/stores";
    public static final String ADD_COURIER_LOCATION_PATH = "/{courierId}/location";
    public static final String COURIER_TOTAL_DISTANCE_PATH = "/getTotalDistance/{courierId}";

}
