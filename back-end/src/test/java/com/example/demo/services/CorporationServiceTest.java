package com.example.demo.services;

import com.example.demo.controllers.CorporationController;
import com.example.demo.entities.ConferenceRoomEntity;
import com.example.demo.entities.CorporationEntity;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ConferenceRoomRepository;
import com.example.demo.repository.CorporationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest()
public class CorporationServiceTest {
    @Container
    public static PostgreSQLContainer container  = new PostgreSQLContainer()
            .withUsername("matt")
            .withPassword("test")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }
    @Autowired
    private CorporationRepository corporationRepository;

    @Autowired
    private CorporationService corporationService;

    @Autowired
    private CorporationController corporationController;

    @Autowired
    private ConferenceRoomService conferenceRoomService;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;

    @AfterEach
    public void cleanUp(){
        bookingRepository.deleteAll();
        conferenceRoomRepository.deleteAll();
        corporationRepository.deleteAll();
    }



    @Test
    public void testGetAllCorporations() {

        List<CorporationEntity> list = corporationRepository.findAll();
        assertEquals(0,  list.size());
    }

    @Test
    public void testAddCorporation(){
        CorporationEntity corporationEntity = new CorporationEntity();
        corporationEntity.setName("Test");
        corporationController.addCorporation(corporationEntity);
        List<CorporationEntity> list = corporationRepository.findAll();
        assertEquals(1, list.size());
    }

    @Test
    public void shouldUserAddCorporationWithNameTooShort(){
        CorporationEntity corporationEntity = new CorporationEntity();
        corporationEntity.setName("te");
        //corporationController.addCorporation(corporationEntity);
        assertThrows(IllegalArgumentException.class, () -> {
            corporationService.createCorporation(corporationEntity);
        });
    }

    @Test
    public void shouldUserAddCorporationWithExistingName(){
        CorporationEntity corporationEntity = new CorporationEntity();
        corporationEntity.setName("Test exiting name");
        corporationController.addCorporation(corporationEntity);
        assertThrows(IllegalArgumentException.class, () -> {
            corporationService.createCorporation(corporationEntity);
        });
    }

    @Test
    public void shouldUserAddCorporationWithExistingNameDifferentCase(){
        CorporationEntity corporationEntity = new CorporationEntity();
        corporationEntity.setName("Test different case");
        corporationController.addCorporation(corporationEntity);
        corporationEntity.setName("test different case");
        assertThrows(IllegalArgumentException.class, () -> {
            corporationService.createCorporation(corporationEntity);
        });
    }


    @Test
    public void shouldUserNeedCorporationIDThatDontExist(){
        long corporationID = 11111;
        assertThrows(IllegalArgumentException.class, () -> {
            corporationService.getCorporationById(corporationID);
        });
    }

    @Test
    public void shouldDeletingAllCorporationsWork(){
        corporationRepository.deleteAll();
        List<CorporationEntity> list = corporationRepository.findAll();
        assertEquals(0, list.size());
    }

    @Test
    @Transactional
    public void shouldDeletingCorporationsWithConferenceRoomsWork(){
        CorporationEntity corporationEntity = new CorporationEntity();
        corporationEntity.setName("CorpWithRoom");
        corporationController.addCorporation(corporationEntity);
        List<CorporationEntity> list = corporationRepository.findAll();
        ConferenceRoomEntity conferenceRoomEntity = new ConferenceRoomEntity();
        conferenceRoomEntity.setName("RoomWithCorp");
        for (CorporationEntity corporationEntity1 : list) {
            conferenceRoomEntity.setCorporationEntity(corporationEntity1);
            conferenceRoomService.createRoom(conferenceRoomEntity, corporationEntity1.getId());
        }
        List<CorporationEntity> list2 = corporationRepository.findAll();
        for (CorporationEntity corporationEntity1 : list2) {
            corporationService.deleteCorporation(corporationEntity1.getId());

        }
        System.out.println(corporationService.getAllCorporations());
        assertEquals(0, corporationRepository.findAll().size());


        }
    }


