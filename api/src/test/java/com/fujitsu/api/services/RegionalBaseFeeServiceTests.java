package com.fujitsu.api.services;

import com.fujitsu.api.dtos.RegionalBaseFeeDto;
import com.fujitsu.api.entities.RegionalBaseFee;
import com.fujitsu.api.exceptions.AppException;
import com.fujitsu.api.mappers.RegionalBaseFeeMapper;
import com.fujitsu.api.mappers.RegionalBaseFeeMapperImpl;
import com.fujitsu.api.mocks.RegionalBaseFeeMock;
import com.fujitsu.api.repositories.RegionalBaseFeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class RegionalBaseFeeServiceTests {

    @Mock
    private RegionalBaseFeeRepository repository;

    @Spy
    private RegionalBaseFeeMapper mapper = new RegionalBaseFeeMapperImpl();

    @InjectMocks
    private RegionalBaseFeeService service;

    @Test
    @DisplayName("Get RBF | Success")
    void getRbfSuccess() {
        RegionalBaseFee rbf = RegionalBaseFeeMock.getRegionalBaseFeeMock(1L);

        when(repository.existsByCity(rbf.getCity())).thenReturn(true);
        when(repository.findByCityAndVehicleType(rbf.getCity(), rbf.getVehicleType()))
                .thenReturn(Optional.of(rbf));

        RegionalBaseFee response = service.getRegionalBaseFee(rbf.getCity(), rbf.getVehicleType());

        assertThat(response.getCity()).isEqualTo(rbf.getCity());
        assertThat(response.getVehicleType()).isEqualTo(rbf.getVehicleType());
        assertThat(response.getFee()).isEqualTo(rbf.getFee());
    }

    @Test
    @DisplayName("Get RBF City not found | Failure")
    void getRbfCityNotFound() {
        RegionalBaseFee rbf = RegionalBaseFeeMock.getRegionalBaseFeeMock(1L);

        when(repository.existsByCity(rbf.getCity()))
                .thenReturn(false);

        AppException ex = assertThrows(AppException.class,
                () -> service.getRegionalBaseFee(rbf.getCity(), rbf.getVehicleType()));

        assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Get RBF Invalid Vehicle | Failure")
    void getRbfInvalidVehicle() {
        RegionalBaseFee rbf = RegionalBaseFeeMock.getRegionalBaseFeeMock(1L);

        when(repository.existsByCity(rbf.getCity())).thenReturn(true);
        when(repository.findByCityAndVehicleType(rbf.getCity(), rbf.getVehicleType()))
                .thenReturn(Optional.empty());

        AppException ex = assertThrows(AppException.class,
                () -> service.getRegionalBaseFee(rbf.getCity(), rbf.getVehicleType()));

        assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    @DisplayName("Update RBF | Success")
    void updateRBF() {
        RegionalBaseFee existing = RegionalBaseFeeMock.getRegionalBaseFeeMock(1L);
        RegionalBaseFeeDto toUpdate = new RegionalBaseFeeDto("Rakvere", "Car", 1d);

        when(repository.existsByCity(toUpdate.getCity())).thenReturn(true);
        when(repository.findByCityAndVehicleType(toUpdate.getCity(), toUpdate.getVehicleType()))
                .thenReturn(Optional.of(existing));

        RegionalBaseFeeDto updated = service.updateRBF(toUpdate);

        verify(repository, times(1)).save(existing);
        verify(mapper, times(1)).toDto(existing);

        assertThat(updated.getFee()).isEqualTo(toUpdate.getFee());
        assertThat(updated.getCity()).isEqualTo(existing.getCity());
        assertThat(updated.getVehicleType()).isEqualTo(existing.getVehicleType());
    }
}
