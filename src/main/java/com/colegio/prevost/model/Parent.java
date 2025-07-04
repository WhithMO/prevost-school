package com.colegio.prevost.model;

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
@Table(name = "parent_profiles")
@Data
@NoArgsConstructor
public class Parent {

    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private Integer mobileNumber;

    public Parent(User user, Integer mobileNumber) {
        this.user = user;
        this.mobileNumber = mobileNumber;
    }

}
