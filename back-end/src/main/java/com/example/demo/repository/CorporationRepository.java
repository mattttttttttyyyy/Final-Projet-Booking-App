package com.example.demo.repository;

import com.example.demo.entities.ConferenceRoomEntity;
import com.example.demo.entities.CorporationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CorporationRepository extends JpaRepository<CorporationEntity, Long> {


    ConferenceRoomEntity findByName(String name);
}
