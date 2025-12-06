package com.profconnect.profconnect.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
public class Student extends User {

    private String department;
    private String year;

    @Override
    public String getRole() {
        return "STUDENT";
    }

}
