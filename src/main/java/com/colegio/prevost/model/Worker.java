package com.colegio.prevost.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@Data
public class Worker extends User {

    @Column(name = "hiring_date")
    private LocalDate hiringDate;
    @Column(name = "termination_date")
    private LocalDate terminationDate;
}
