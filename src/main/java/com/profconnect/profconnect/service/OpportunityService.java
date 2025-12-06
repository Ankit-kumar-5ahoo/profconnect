package com.profconnect.profconnect.service;
import com.profconnect.profconnect.model.Opportunity;
import com.profconnect.profconnect.model.User;
import com.profconnect.profconnect.repository.OpportunityRepository;
import com.profconnect.profconnect.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final ProfessorRepository professorRepository;


    public Opportunity createOpportunity(Opportunity opportunity, Long professorId) {
        var professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor not found"));
        opportunity.setProfessor(professor);
        return opportunityRepository.save(opportunity);
    }


    public Opportunity createOpportunity(Opportunity opportunity, User user) {

        switch (user.getRole()) {

            case "PROFESSOR" -> {
                opportunity.setProfessor((Professor) user);
                return opportunityRepository.save(opportunity);
            }

            case "STUDENT" -> {
                throw new RuntimeException("Students cannot create opportunities");
            }

            default -> throw new RuntimeException("Unknown role");
        }
    }

    public List<Opportunity> getAllOpportunities() {
        return opportunityRepository.findAll();
    }

    public Opportunity getOpportunityById(Long id) {
        return opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));
    }

    public Opportunity updateOpportunity(Long id, Opportunity updated) {
        Opportunity opp = getOpportunityById(id);
        opp.setTitle(updated.getTitle());
        opp.setDescription(updated.getDescription());
        opp.setFieldOfStudy(updated.getFieldOfStudy());
        opp.setType(updated.getType());
        opp.setDuration(updated.getDuration());
        opp.setPrerequisites(updated.getPrerequisites());
        opp.setPdfUrl(updated.getPdfUrl());
        return opportunityRepository.save(opp);
    }

    public void deleteOpportunity(Long id) {
        opportunityRepository.deleteById(id);
    }

    public List<Opportunity> getOpportunitiesByProfessor(Long profId) {
        return opportunityRepository.findByProfessorId(profId);
    }
}
