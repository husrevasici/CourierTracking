package com.migros.couriertracking.mapper;

import com.migros.couriertracking.dto.StoreDTO;
import com.migros.couriertracking.entity.Store;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    List<StoreDTO> toDTOList(List<Store> stores);
    StoreDTO toDTO(Store store);
    Store toEntity(StoreDTO storeDTO);
}