package com.fujitsu.api.services;

import com.fujitsu.api.dtos.RegionalBaseFeeDto;
import com.fujitsu.api.entities.RegionalBaseFee;
import com.fujitsu.api.exceptions.AppException;
import com.fujitsu.api.mappers.RegionalBaseFeeMapper;
import com.fujitsu.api.repositories.RegionalBaseFeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RegionalBaseFeeService {

    private final RegionalBaseFeeRepository repository;
    private final RegionalBaseFeeMapper mapper;

    public RegionalBaseFee getRegionalBaseFee(String city, String vehicleType) {
        if (!repository.existsByCity(city)) {
            throw new AppException("No data available for " + city, HttpStatus.NOT_FOUND);
        }

        Optional<RegionalBaseFee> optionalRBF = repository.findByCityAndVehicleType(city, vehicleType);

        if (optionalRBF.isEmpty()) {
            throw new AppException(vehicleType + " not found in " + city, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return optionalRBF.get();
    }

    public RegionalBaseFeeDto updateRBF(RegionalBaseFeeDto rbfDto) {
        RegionalBaseFee existing = getRegionalBaseFee(rbfDto.getCity(), rbfDto.getVehicleType());
        existing.setFee(rbfDto.getFee());

        repository.save(existing);

        return mapper.toDto(existing);
    }

}
