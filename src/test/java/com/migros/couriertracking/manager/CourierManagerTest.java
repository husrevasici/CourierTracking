package com.migros.couriertracking.manager;

import com.migros.couriertracking.dto.CurierRequestDTO;
import com.migros.couriertracking.dto.ErrorCode;
import com.migros.couriertracking.dto.LocationDTO;
import com.migros.couriertracking.dto.LocationRequestDTO;
import com.migros.couriertracking.entity.Courier;
import com.migros.couriertracking.entity.CourierLocation;
import com.migros.couriertracking.exception.GenericException;
import com.migros.couriertracking.repository.dao.CourierDAO;
import com.migros.couriertracking.repository.dao.CourierLocationDAO;
import com.migros.couriertracking.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourierManagerTest {

    @InjectMocks
    private CourierManager courierManager;

    @Mock
    private CourierDAO courierDAO;

    @Mock
    private CourierLocationDAO courierLocationDAO;

    @Mock
    private CalculatorService calculatorService;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_CourierFound_ShouldSaveLocation() {

        Long courierId = 1L;
        var locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setCourierId(courierId);
        locationRequestDTO.setLocation(new LocationDTO(40.0, 30.0));

        var courier = new Courier();
        courier.setCourierId(courierId);
        when(courierDAO.getCourierById(courierId)).thenReturn(Optional.of(courier));
        courierManager.execute(locationRequestDTO);
        verify(courierLocationDAO, times(1)).saveCourierLocation(courier, 40.0, 30.0);
    }

    @Test
    void testExecute_CourierNotFound_ShouldThrowException() {
        var courierId = 1L;
        var locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setCourierId(courierId);

        when(courierDAO.getCourierById(courierId)).thenReturn(Optional.empty());
        var exception = assertThrows(GenericException.class, () -> courierManager.execute(locationRequestDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(ErrorCode.COURIER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testGetTotalTravelDistance_ShouldReturnTotalDistance() {
        // Arrange
        Long courierId = 1L;
        var locations = List.of(
                 new CourierLocation(1L, 40.0, 30.0, null, null),
                new CourierLocation(2L, 41.0, 31.0, null, null),
                new CourierLocation(3L, 42.0, 32.0, null, null)
        );
        when(courierLocationDAO.getCourierLocations(courierId)).thenReturn(locations);
        when(calculatorService.calculateDistance(40.0, 30.0, 41.0, 31.0)).thenReturn(100.0);
        when(calculatorService.calculateDistance(41.0, 31.0, 42.0, 32.0)).thenReturn(150.0);

        var totalDistance = courierManager.getTotalTravelDistance(courierId);
        assertEquals(250.0, totalDistance, 0.001);
    }

    @Test
    void testCreateCourier_ShouldSaveCourier() {
        var curierRequestDTO = new CurierRequestDTO();
        curierRequestDTO.setNameAndSurname("Test Courier");
        curierRequestDTO.setEmail("mha@gmail.com");
        curierRequestDTO.setPhoneNumber("1234567890");

        courierManager.createCourier(curierRequestDTO);

        verify(courierDAO, times(1)).courierSave(any(Courier.class));
    }
}