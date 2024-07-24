package com.example.demo.services;

import com.example.demo.entities.ConferenceRoomEntity;
import com.example.demo.repository.ConferenceRoomRepository;
import com.example.demo.repository.CorporationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConferenceRoomService {
    @Autowired
    ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    CorporationService corporationService;

    @Autowired
    CorporationRepository corporationRepository;

    public void createRoom(ConferenceRoomEntity conferenceRoom, final long corporate_id) {
        conferenceRoom = checkUniqueName(conferenceRoom);
        conferenceRoom.setCorporationEntity(corporationService.getCorporationById(corporate_id));
        conferenceRoomRepository.save(conferenceRoom);
    }

    public ConferenceRoomEntity checkUniqueName(final ConferenceRoomEntity conferenceRoomEntity) {
        if(conferenceRoomEntity.getLevel() > 10){
            throw new IllegalArgumentException("Level doesn't exist");
        }

        final List<ConferenceRoomEntity> rooms = conferenceRoomRepository.findAll();
        rooms.stream().filter(room -> room.getName().equalsIgnoreCase(conferenceRoomEntity.getName())).forEach(room -> {
            throw new IllegalArgumentException("Room name already exists");
        });
        return conferenceRoomEntity;
    }

    public List<ConferenceRoomEntity> getAllRooms() {
        return conferenceRoomRepository.findAll();
    }

    public List<ConferenceRoomEntity> getRoomsByCorpoId(final long corporateId) {
        if (!corporationRepository.existsById(corporateId)) {
            throw new IllegalArgumentException("Corporation does not exist");
        }
        return conferenceRoomRepository.findByCorporationEntity_Id(corporateId);
    }

    public void deleteRoom(final long roomId) {
        if (!conferenceRoomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Room does not exist");
        } else {
            conferenceRoomRepository.deleteById(roomId);
        }
    }

    public void updateRoom(final long roomId, ConferenceRoomEntity conferenceRoomEntity) {
        if (!conferenceRoomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Room does not exist");
        } else {
            conferenceRoomEntity = checkUniqueName(conferenceRoomEntity);
            final ConferenceRoomEntity room = conferenceRoomRepository.findById(roomId).get();
            room.setName(conferenceRoomEntity.getName());
            conferenceRoomRepository.save(room);
        }

    }

    public void deleteAllRoomsByCorporation(final long corporateId) {
        final List<ConferenceRoomEntity> rooms = conferenceRoomRepository.findByCorporationEntity_Id(corporateId);
        rooms.forEach(room -> conferenceRoomRepository.deleteById(room.getId()));
    }
}
