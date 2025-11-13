package com.profconnect.profconnect.controller;

import com.profconnect.profconnect.model.Opportunity;
import com.profconnect.profconnect.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/opportunities")
@RequiredArgsConstructor
public class StudentOpportunityController {

    private final OpportunityService opportunityService;

    // ✅ Students can view all opportunities (NO AUTH REQUIRED)
    @GetMapping
    public ResponseEntity<List<Opportunity>> getAllOpportunities() {
        return ResponseEntity.ok(opportunityService.getAllOpportunities());
    }

    // ✅ Student can view one opportunity by ID (NO AUTH REQUIRED)
    @GetMapping("/{id}")
    public ResponseEntity<Opportunity> getOpportunityById(@PathVariable Long id) {
        return ResponseEntity.ok(opportunityService.getOpportunityById(id));
    }
}
