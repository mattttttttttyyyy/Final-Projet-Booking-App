package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Getter @Setter  @NoArgsConstructor
@Table(name = "conference_room")
public class ConferenceRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private Long id;

    @Length(min = 2, max = 20, message = "Name has to be between 2 and 20 characters")
    private String name;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "corporation_id", columnDefinition = "integer")
    private CorporationEntity corporationEntity;

    @OneToMany(mappedBy = "conferenceRoomEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<BookingEntity> bookingEntityList;

    int level;

    int sittingNumber;

    int standingNumber;

    @Override
    public String
    toString() {
        return "ConferenceRoomEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
