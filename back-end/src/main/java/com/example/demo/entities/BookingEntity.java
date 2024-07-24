package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter @Setter  @NoArgsConstructor
@Table(name = "booking")
public class BookingEntity {
    String uniqueId;
    java.sql.Timestamp startTime;
    java.sql.Timestamp endTime;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private Long id;
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "conference_room_id")
    private ConferenceRoomEntity conferenceRoomEntity;

}
