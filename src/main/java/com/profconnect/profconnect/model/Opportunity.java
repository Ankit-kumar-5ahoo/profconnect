package com.profconnect.profconnect.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String fieldOfStudy;
    private String type;
    private String duration;
    private String prerequisites;
    private String pdfUrl;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    @JsonBackReference
    private Professor professor;
}
