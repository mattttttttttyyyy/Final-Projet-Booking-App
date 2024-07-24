package com.example.demo.services;

import com.example.demo.controllers.BookingController;
import com.example.demo.controllers.ConferenceRoomController;
import com.example.demo.controllers.CorporationController;
import com.example.demo.entities.BookingEntity;
import com.example.demo.entities.ConferenceRoomEntity;
import com.example.demo.entities.CorporationEntity;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ConferenceRoomRepository;
import com.example.demo.repository.CorporationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest()
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ConferenceRoomServiceTest {
    @Container
    public static PostgreSQLContainer<?> container  = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    )
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
    private  CorporationRepository corporationRepository;

    @Autowired
    private  CorporationService corporationService;

    @Autowired
    private  CorporationController corporationController;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private  ConferenceRoomService conferenceRoomService;

    @Autowired
    private BookingController bookingController;

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    private ConferenceRoomController conferenceRoomController;
    private  ConferenceRoomEntity testingRoom;

    private  CorporationEntity testCorporation;


    @BeforeEach
    public void corporationSetUp(){
        CorporationEntity corporationEntity = new CorporationEntity();
        corporationEntity.setName("Corporation One");
        corporationService.createCorporation(corporationEntity);
        testCorporation = corporationService.getCorporationById(corporationRepository.findAll().get(0).getId());
        ConferenceRoomEntity conferenceRoomEntity = new ConferenceRoomEntity();
        conferenceRoomEntity.setName("Conference Room One");
        conferenceRoomRepository.save(conferenceRoomEntity);
        List<ConferenceRoomEntity> conferenceRoomEntityList = conferenceRoomService.getAllRooms();
        testingRoom =  conferenceRoomEntityList.get(0);
        System.out.println(testingRoom.getId());
        System.out.println(testCorporation.getId());
    }
    @AfterEach
    public void tearDown(){
        conferenceRoomRepository.deleteAll();
        corporationRepository.deleteAll();
    }



    @Test
    public void testAllRooms() {
        List<ConferenceRoomEntity> conferenceRoomEntityList = conferenceRoomService.getAllRooms();
        assertEquals(1,  conferenceRoomEntityList.size());
    }
    private CorporationEntity getTestCorporation() {
        return corporationRepository.findAll().get(0);
    }

    @Test
    @Transactional
    public void shouldAddingConferenceRoomWork() {
        int sizeBefore = conferenceRoomRepository.findAll().size();
        CorporationEntity corporationEntity = getTestCorporation();
        ConferenceRoomEntity conferenceRoomEntity = new ConferenceRoomEntity();
        conferenceRoomEntity.setName("Conference To Add");
        conferenceRoomController.addConferenceRoom(conferenceRoomEntity, corporationEntity.getId());
        assertEquals(sizeBefore + 1, conferenceRoomRepository.findAll().size());
    }

    @Test
    @Transactional
    public void shouldUserTryToUseExistingName() {
        ConferenceRoomEntity conferenceRoomEntity1 = new ConferenceRoomEntity();
        conferenceRoomEntity1.setName("Testing");
        ConferenceRoomEntity conferenceRoomEntity2 = new ConferenceRoomEntity();
        conferenceRoomEntity2.setName("Testing");
        conferenceRoomController.addConferenceRoom(conferenceRoomEntity1, testCorporation.getId());
        assertThrows(
                IllegalArgumentException.class,
                () -> conferenceRoomController.addConferenceRoom(conferenceRoomEntity2, testCorporation.getId())
        );

    }

    @Test
    @Transactional
    public void shouldUserTryToChangeToExistingName(){
        ConferenceRoomEntity conferenceRoomEntity1 = new ConferenceRoomEntity();
        conferenceRoomEntity1.setName("Original Name");
        ConferenceRoomEntity conferenceRoomEntity2 = new ConferenceRoomEntity();
        conferenceRoomEntity2.setName("Existing Name");
        conferenceRoomController.addConferenceRoom(conferenceRoomEntity1, testCorporation.getId());
        conferenceRoomController.addConferenceRoom(conferenceRoomEntity2, testCorporation.getId());
        conferenceRoomEntity1.setName("Existing Name");
        assertThrows(
                IllegalArgumentException.class,
                () -> conferenceRoomController.updateRoom(conferenceRoomEntity1.getId(), conferenceRoomEntity1)
        );
    }

    @Test
    @Transactional
    public void shouldDeletingAllRoomsWork() {
        ConferenceRoomEntity conferenceRoom = new ConferenceRoomEntity();
        conferenceRoom.setName("Conference xyz");
        conferenceRoomController.addConferenceRoom(conferenceRoom, testCorporation.getId());
        conferenceRoomRepository.deleteAll();
        assertEquals(0, conferenceRoomRepository.findAll().size());
    }

    @Test
    @Transactional
    public void shouldDeletingRoomWithBookingWork(){
        ConferenceRoomEntity conferenceRoom = new ConferenceRoomEntity();
        conferenceRoom.setName("Conference xyz");
        conferenceRoomController.addConferenceRoom(conferenceRoom, testCorporation.getId());
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setStartTime(Timestamp.valueOf("2024-01-01 12:00:00"));
        bookingEntity.setEndTime(Timestamp.valueOf("2024-01-01 13:00:00"));
        bookingEntity.setConferenceRoomEntity(conferenceRoomRepository.findAll().get(0));
        System.out.println(conferenceRoomRepository.findAll());
        conferenceRoomRepository.deleteAll();
        assertEquals(0, conferenceRoomRepository.findAll().size());
    }

    }



