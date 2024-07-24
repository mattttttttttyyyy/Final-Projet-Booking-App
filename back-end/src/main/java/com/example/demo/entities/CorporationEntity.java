package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Getter @Setter  @NoArgsConstructor
@Table(name = "corporation")
public class CorporationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private Long id;


    @OneToMany(mappedBy = "corporationEntity", cascade = {CascadeType.REMOVE})
    private List<ConferenceRoomEntity> conferenceRoomEntityList;
    @Length(min = 2, max = 20, message = "Name has to be between 2 and 20 characters")
    private String name;

}
