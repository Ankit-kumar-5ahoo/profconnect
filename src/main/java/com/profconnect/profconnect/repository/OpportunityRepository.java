package com.profconnect.profconnect.repository;

import com.profconnect.profconnect.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    List<Opportunity> findByProfessorId(Long professorId);
    List<Opportunity> findByFieldOfStudyContainingIgnoreCase(String keyword);
}

