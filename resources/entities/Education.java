package com.codecamp.prep.devbook.database.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean currentEducation;

    @NotNull
    private String school;

    @NotNull
    private String degree;

    @NotNull
    private String fieldofstudy;

    @NotNull
    private String fromDate;

    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private Profile profile;

    private String toDate;
    private String description;
}
