package com.example.demo.services;

import com.example.demo.entities.ConferenceRoomEntity;
import com.example.demo.entities.CorporationEntity;
import com.example.demo.repository.ConferenceRoomRepository;
import com.example.demo.repository.CorporationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorporationService {
    @Autowired
    CorporationRepository corporationRepository;

    @Autowired
    ConferenceRoomRepository conferenceRoomRepository;

    public CorporationEntity corporationNameChecker(CorporationEntity corporationEntity){
        List<CorporationEntity> corporations = corporationRepository.findAll();
        for (CorporationEntity c : corporations) {
            if (c.getName().equalsIgnoreCase(corporationEntity.getName())) {
                throw new IllegalArgumentException("Corporation name already exists");
            }
        }
        if (corporationEntity.getName().length() < 3) {
            throw new IllegalArgumentException("Corporation name too short");
        }
        if (corporationEntity.getName().length() > 20) {
            throw new IllegalArgumentException("Corporation name too long");
        }
        return corporationEntity;
    }

    public Long createCorporation(CorporationEntity corporation) {
        return corporationRepository.save(corporationNameChecker(corporation)).getId();
    }

    public List<CorporationEntity> getAllCorporations() {
        return corporationRepository.findAll();
    }

    public CorporationEntity getCorporationById(long id) {
        if (!corporationRepository.existsById(id)) {
            throw new IllegalArgumentException("Corporation does not exist");
        } else {
            return corporationRepository.findById(id).get();
        }
    }

    public int getCorporationByName(String name) {
        List<CorporationEntity> corporations = corporationRepository.findAll();
        for (CorporationEntity c : corporations) {
            if (c.getName().equals(name)) {
                return Math.toIntExact(c.getId());
            }
        }
        return 1;


    }

    @Transactional
    public void deleteCorporation(long id) {
        List<Long> roomIds = conferenceRoomRepository.findByCorporationEntity_Id(id)
                .stream()
                .map(ConferenceRoomEntity::getId)
                .collect(Collectors.toList());

        conferenceRoomRepository.deleteAllById(roomIds);
        corporationRepository.deleteById(id);
    }


    public void updateCorporation(long id, CorporationEntity corporation) {
            corporationRepository.save(corporationNameChecker(corporation));
    }
}