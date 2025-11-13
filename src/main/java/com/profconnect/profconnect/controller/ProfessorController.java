package com.profconnect.profconnect.controller;
import com.profconnect.profconnect.dto.LoginRequest;

import com.profconnect.profconnect.model.Professor;
import com.profconnect.profconnect.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    // ✅ Create Professor
    @PostMapping("/register")
    public ResponseEntity<Professor> registerProfessor(@RequestBody Professor professor) {
        Professor saved = professorService.registerProfessor(professor);
        return ResponseEntity.ok(saved);
    }
    // ✅ NEW: Login API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(professorService.login(request));
    }

    // ✅ Get all professors
    @GetMapping
    public ResponseEntity<List<Professor>> getAllProfessors() {
        return ResponseEntity.ok(professorService.getAllProfessors());
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.getProfessorById(id));
    }

    // ✅ Update
    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(@PathVariable Long id, @RequestBody Professor updated) {
        return ResponseEntity.ok(professorService.updateProfessor(id, updated));
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
}
