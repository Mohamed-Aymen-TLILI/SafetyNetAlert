package com.safetynet.project.service;

import com.safetynet.project.model.FireStation;
import com.safetynet.project.repository.FireStationRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class FireStationServiceTests {

    @Mock
    private FireStationRepository fireStationRepositoryMock;


    private FireStationService fireStationService;

    private FireStation fireStation;



    @BeforeEach
    private void  setUpEachTest() {
        MockitoAnnotations.initMocks(this);
        this.fireStationService = new FireStationService(this.fireStationRepositoryMock);
        fireStation = new FireStation();
        fireStation.setStation(1);
        fireStation.setAddress("myAddress");
    }


    @Test
    public void getAllFireStationsReturnNull()
    {
        when(fireStationRepositoryMock.findAll()).thenReturn(null);
        assertThat(fireStationService.getAllStations()).isNull();
    }

    @Test
    public void getAllFireStationsReturnEmptyList()
    {
        when(fireStationRepositoryMock.findAll()).thenReturn(new ArrayList<FireStation>());
        assertThat(fireStationService.getAllStations()).isEmpty();
    }

    @Test
    public void getAllFireStationsReturnList()
    {
        List<FireStation> fireStationList = new ArrayList<>();
        fireStationList.add(fireStation);
        when(fireStationRepositoryMock.findAll()).thenReturn(fireStationList);
        assertThat(fireStationService.getAllStations()).size().isEqualTo(1);
    }

    @Test
    public void getAllFireStationsWithException()
    {
        given(fireStationRepositoryMock.findAll()).willAnswer(invocation -> { throw new Exception();});
        assertThat(fireStationService.getAllStations()).isNull();
        verify(fireStationRepositoryMock, Mockito.times(1)).findAll();


    }
    @Test
    public void addFireStationWithNullFireStation()
    {
        when(fireStationRepositoryMock.save(null)).thenReturn(null);
        verify(fireStationRepositoryMock, Mockito.times(0)).save(any());
        assertThat(fireStationService.addFireStation(null)).isNull();
    }

    @Test
    public void addFireStationWithFireStation()
    {
        when(fireStationRepositoryMock.findFirstByAddressIgnoreCaseAndStation(any(String.class), any(Integer.class))).thenReturn(Optional.empty());
        when(fireStationRepositoryMock.save(fireStation)).thenReturn(fireStation);
        assertThat(fireStationService.addFireStation(fireStation)).isEqualTo(fireStation);
        verify(fireStationRepositoryMock, Mockito.times(1)).save(any());
    }

    @Test
    public void addFireStationWithExistingFireStation()
    {
        when(fireStationRepositoryMock.findFirstByAddressIgnoreCaseAndStation(any(String.class), any(Integer.class))).thenReturn(Optional.of(fireStation));

        assertThat(fireStationService.addFireStation(fireStation)).isEqualTo(null);
        verify(fireStationRepositoryMock, Mockito.times(0)).save(any());
    }

    @Test
    public void addFireStationWithException()
    {
        when(fireStationRepositoryMock.findFirstByAddressIgnoreCaseAndStation(any(String.class), any(Integer.class))).
                thenReturn(Optional.empty());
        given(fireStationRepositoryMock.save(fireStation)).willAnswer(invocation -> { throw new Exception();});

        assertThat(fireStationService.addFireStation(fireStation)).isNull();
        verify(fireStationRepositoryMock, Mockito.times(1)).save(fireStation);
    }

    @Test
    public void updateFireStationWithNullFireStation()
    {
        when(fireStationRepositoryMock.save(null)).thenReturn(null);
        verify(fireStationRepositoryMock, Mockito.times(0)).save(any());
        assertThat(fireStationService.updateFireStation(null)).isNull();
    }

    @Test
    public void updateFireStationWithStation()
    {
        List<FireStation> fireStationList = new ArrayList<>();
        fireStationList.add(fireStation);

        when(fireStationRepositoryMock.findDistinctByAddressIgnoreCase(any(String.class))).thenReturn(fireStationList);
        when(fireStationRepositoryMock.save(fireStation)).thenReturn(fireStation);
        assertThat(fireStationService.updateFireStation(fireStation)).isEqualTo(fireStation);
        verify(fireStationRepositoryMock, Mockito.times(1)).save(fireStation);
    }

    @Test
    public void updateFireStationWithNonExistingFireStation()
    {
        List<FireStation> fireStationList = new ArrayList<>();
        when(fireStationRepositoryMock.findDistinctByAddressIgnoreCase(any(String.class))).thenReturn(fireStationList);

        assertThat(fireStationService.updateFireStation(fireStation)).isEqualTo(null);
        verify(fireStationRepositoryMock, Mockito.times(0)).save(any());
    }

    @Test
    public void updateFireStationWithException()
    {
        List<FireStation> fireStationList = new ArrayList<>();
        fireStationList.add(fireStation);
        when(fireStationRepositoryMock.findDistinctByAddressIgnoreCase(any(String.class))).thenReturn(fireStationList);

        given(fireStationRepositoryMock.save(fireStation)).willAnswer(invocation -> { throw new Exception();});

        assertThat(fireStationService.updateFireStation(fireStation)).isNull();
        verify(fireStationRepositoryMock, Mockito.times(1)).save(fireStation);
    }

    @Test
    public void deleteFireStationByAddressWithNullFireStation()
    {
        List<FireStation> fireStationList = new ArrayList<>();
        when(fireStationRepositoryMock.findDistinctByAddressIgnoreCase(null)).thenReturn(fireStationList);

        verify(fireStationRepositoryMock, Mockito.times(0)).deleteByAddressIgnoreCase(any(String.class));
    }

    @Test
    public void deleteFireStationByAddressAndStationWithNullFireStation()
    {
        List<FireStation> fireStationList = new ArrayList<>();

        when(fireStationRepositoryMock.findFirstByAddressIgnoreCaseAndStation(null,null)).thenReturn(null);

        verify(fireStationRepositoryMock, Mockito.times(0)).deleteByAddressIgnoreCaseAndStation(any(String.class), any(Integer.class));
    }


    @Test
    public void deleteFireStationByAddressWithNonExistingFireStation()
    {
        when(fireStationRepositoryMock.findDistinctByAddressIgnoreCase(any(String.class))).thenReturn(new ArrayList<>());
        verify(fireStationRepositoryMock, Mockito.times(0)).deleteByAddressIgnoreCase(any(String.class));
    }

    @Test
    public void deleteFireStationByAddressAndStationWithNonExistingFireStation()
    {
        when(fireStationRepositoryMock.findFirstByAddressIgnoreCaseAndStation(any(String.class),any(Integer.class))).thenReturn(Optional.empty());
        verify(fireStationRepositoryMock, Mockito.times(0)).deleteByAddressIgnoreCaseAndStation(any(String.class),any(Integer.class));
    }

}
