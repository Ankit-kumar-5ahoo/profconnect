package com.profconnect.profconnect.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profconnect.profconnect.model.Opportunity;
import com.profconnect.profconnect.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/professor/opportunities")
@RequiredArgsConstructor
public class OpportunityController {

    private final OpportunityService opportunityService;


    @PostMapping(
            value = "/create",
            consumes = { "multipart/form-data" }
    )
    public ResponseEntity<Opportunity> createOpportunity(
            @RequestPart("data") String data,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Opportunity opportunity = mapper.readValue(data, Opportunity.class);

        if (file != null && !file.isEmpty()) {
            if (file.getSize() > 10 * 1024 * 1024) { // 10MB limit
                return ResponseEntity.badRequest().build();
            }

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            new File(uploadDir).mkdirs();
            file.transferTo(new File(uploadDir + filename));

            opportunity.setPdfUrl("/uploads/" + filename);
        }


        Long professorId = null;
        if (opportunity.getProfessor() != null) {
            professorId = opportunity.getProfessor().getId();
        }

        if (professorId == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Opportunity saved = opportunityService.createOpportunity(opportunity, professorId);
        return ResponseEntity.ok(saved);
    }


    @GetMapping
    public ResponseEntity<List<Opportunity>> getAllOpportunities() {
        return ResponseEntity.ok(opportunityService.getAllOpportunities());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Opportunity> getOpportunityById(@PathVariable Long id) {
        return ResponseEntity.ok(opportunityService.getOpportunityById(id));
    }


    @PutMapping(
            value = "/update/{id}",
            consumes = { "multipart/form-data" }
    )
    public ResponseEntity<Opportunity> updateOpportunityWithFile(
            @PathVariable Long id,
            @RequestPart("data") String data,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Opportunity updated = mapper.readValue(data, Opportunity.class);


        if (file != null && !file.isEmpty()) {
            if (file.getSize() > 10 * 1024 * 1024) { // limit 10MB
                return ResponseEntity.badRequest().build();
            }

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            new File(uploadDir).mkdirs();

            file.transferTo(new File(uploadDir + filename));

            updated.setPdfUrl("/uploads/" + filename);
        }

        Opportunity saved = opportunityService.updateOpportunity(id, updated);
        return ResponseEntity.ok(saved);
    }

    // Delete oopurtunity
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable Long id) {
        opportunityService.deleteOpportunity(id);
        return ResponseEntity.noContent().build();
    }

    // Get Opportunities by Professor ID
    @GetMapping("/byProfessor/{profId}")
    public ResponseEntity<List<Opportunity>> getOpportunitiesByProfessor(@PathVariable Long profId) {
        return ResponseEntity.ok(opportunityService.getOpportunitiesByProfessor(profId));
    }
}
