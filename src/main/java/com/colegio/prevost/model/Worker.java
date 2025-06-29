package com.colegio.prevost.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "worker_profiles")
@Data
@NoArgsConstructor
public class Worker {

    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "hiring_date")
    private LocalDate hiringDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    private Integer mobileNumber;

    public Worker(User user, LocalDate hiringDate, LocalDate terminationDate, Integer mobileNumber) {
        this.user = user;
        this.hiringDate = hiringDate;
        this.terminationDate = terminationDate;
        this.mobileNumber = mobileNumber;
    }

}
