package com.example.demo.repository;

import com.example.demo.entities.ConferenceRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoomEntity, Long> {


    List<ConferenceRoomEntity> findByCorporationEntity_Id(long corporateId);
}
