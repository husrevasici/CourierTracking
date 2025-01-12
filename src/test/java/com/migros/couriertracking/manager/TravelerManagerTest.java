package com.migros.couriertracking.manager;

import com.migros.couriertracking.dto.ErrorCode;
import com.migros.couriertracking.dto.LocationDTO;
import com.migros.couriertracking.dto.LocationRequestDTO;
import com.migros.couriertracking.dto.StoreDTO;
import com.migros.couriertracking.entity.CourierMovementHistory;
import com.migros.couriertracking.entity.Store;
import com.migros.couriertracking.exception.GenericException;
import com.migros.couriertracking.mapper.StoreMapper;
import com.migros.couriertracking.repository.dao.CourierDAO;
import com.migros.couriertracking.repository.dao.CourierMovementHistoryDAO;
import com.migros.couriertracking.repository.dao.StoreDAO;
import com.migros.couriertracking.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TravelerManagerTest {

    @InjectMocks
    private TravelerManager travelerManager;

    @Mock
    private CourierDAO courierDAO;

    @Mock
    private CourierMovementHistoryDAO courierMovementHistoryDAO;

    @Mock
    private StoreDAO storeDAO;

    @Mock
    private CalculatorService calculatorService;

    @Mock
    private StoreMapper storeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindNearestStore_WithValidData_ShouldReturnNearestStore() {
        var latitude = 41.0;
        var longitude = 29.0;

        var store1 = new Store(1L, "Store1", 41.1, 29.1);
        var store2 = new Store(2L, "Store2", 41.2, 29.2);

        var stores = List.of(store1, store2);
        when(storeDAO.getAllStore()).thenReturn(stores);

        var storeDTO1 = new StoreDTO(1L, "Store1", 41.1, 29.1);
        var storeDTO2 = new StoreDTO(2L, "Store2", 41.2, 29.2);
        when(storeMapper.toDTOList(stores)).thenReturn(List.of(storeDTO1, storeDTO2));

        when(calculatorService.calculateDistance(anyDouble(), anyDouble(), eq(41.1), eq(29.1))).thenReturn(90.0);
        when(calculatorService.calculateDistance(anyDouble(), anyDouble(), eq(41.2), eq(29.2))).thenReturn(150.0);

        var nearestStore = travelerManager.findNearestStore(latitude, longitude);

        assertNotNull(nearestStore);
        assertEquals(storeDTO1.getId(), nearestStore.getId());
    }

    @Test
    void testFindNearestStore_NoStoresFound_ShouldReturnNull() {
        when(storeDAO.getAllStore()).thenReturn(List.of());
        var nearestStore = travelerManager.findNearestStore(41.0, 29.0);
        assertNull(nearestStore);
    }

    @Test
    void testValidateNearestStore_WithNoNearestStore_ShouldThrowException() {

        var locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setLocation(new LocationDTO(41.0, 29.0));

        when(storeDAO.getAllStore()).thenReturn(List.of());
        when(storeMapper.toDTOList(anyList())).thenReturn(List.of());
        GenericException exception = assertThrows(GenericException.class, () -> {
            travelerManager.validateNearestStore(locationRequestDTO);
        });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
        assertEquals(ErrorCode.NOT_AVAILABLE_LAST_MOVEMENT, exception.getErrorCode());
    }

    @Test
    void testIsAvailableLastMovement_LastMovementNotAvailable_ShouldReturnTrue() {
        Long courierId = 1L;
        when(courierMovementHistoryDAO.getCourierMovementHistory(courierId)).thenReturn(Optional.empty());
        var result = travelerManager.isAvailableLastMovement(courierId);
        assertTrue(result);
    }

    @Test
    void testIsAvailableLastMovement_MovementTooRecent_ShouldReturnFalse() {
        Long courierId = 1L;
        var courierMovement = new CourierMovementHistory();
        courierMovement.setDate(LocalDateTime.now());
        when(courierMovementHistoryDAO.getCourierMovementHistory(courierId))
                .thenReturn(Optional.of(courierMovement));

        boolean result = travelerManager.isAvailableLastMovement(courierId);
        assertFalse(result);
    }

    @Test
    void testProcessCourierMovement_WithEmptyHistory_ShouldCreateNewMovement() {
        Long courierId = 1L;
        var locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setCourierId(courierId);

        when(courierMovementHistoryDAO.getCourierMovementHistory(courierId)).thenReturn(Optional.empty());
        when(courierDAO.getCourierById(courierId)).thenReturn(Optional.of(mock()));

        travelerManager.processCourierMovement(locationRequestDTO, courierId, Optional.empty());

        verify(courierMovementHistoryDAO, times(1)).saveCourierMovementHistory(any());
    }

    @Test
    void testSaveCourierMovementToDatabase_ShouldSaveSuccessfully() {
        var nearestStore = new StoreDTO();
        nearestStore.setId(1L);
        var store = new Store();
        store.setId(1L);
        var courierMovementHistory = new CourierMovementHistory();
        when(storeMapper.toEntity(nearestStore)).thenReturn(store);
        travelerManager.saveCourierMovementToDatabase(courierMovementHistory, nearestStore);

        assertEquals(store.getId(), courierMovementHistory.getStoreId());
        verify(storeMapper, times(1)).toEntity(nearestStore);
        verify(courierMovementHistoryDAO, times(1)).saveCourierMovementHistory(courierMovementHistory);
    }

}