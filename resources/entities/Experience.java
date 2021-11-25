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
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean currentJob;

    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private Profile profile;

    @NotNull
    private String company;

    @NotNull
    private String title;
    private String location;

    @NotNull
    private String fromDate;

    private String toDate;
    private String description;
}
