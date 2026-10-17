package com.infyme.bookseat.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "seat_master")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatMaster {

    @Id
    private String seatId;

    private Integer floorNo;

    private String wing;

    private Boolean isAvailable;
}
