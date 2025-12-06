package com.profconnect.profconnect.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professor extends User {

    private String department;

    @Column(length = 500)
    private String bio;

    private String specialization;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Opportunity> opportunities;

    @Override
    public String getRole() {
        return "PROFESSOR";
    }


}
