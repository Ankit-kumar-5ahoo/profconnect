package com.profconnect.profconnect.service;

import com.profconnect.profconnect.config.JwtService;
import com.profconnect.profconnect.dto.LoginRequest;
import com.profconnect.profconnect.model.Professor;
import com.profconnect.profconnect.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    // 🔹 Register new professor with hashed password
    public Professor registerProfessor(Professor professor) {
        if (professorRepository.findByEmail(professor.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // ✅ Hash password before saving
        professor.setPassword(passwordEncoder.encode(professor.getPassword()));

        return professorRepository.save(professor);
    }

    // 🔹 Validate login credentials (used by AuthController)
    public Professor loginProfessor(String email, String rawPassword) {
        Professor professor = professorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(rawPassword, professor.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return professor;
    }

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public Professor getProfessorById(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found"));
    }

    public Professor updateProfessor(Long id, Professor updated) {
        Professor prof = getProfessorById(id);
        prof.setName(updated.getName());
        prof.setDepartment(updated.getDepartment());
        prof.setSpecialization(updated.getSpecialization());
        prof.setBio(updated.getBio());

        // ✅ If password is provided, hash new one
        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            prof.setPassword(passwordEncoder.encode(updated.getPassword()));
        }

        return professorRepository.save(prof);
    }

    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }
    public String login(LoginRequest request) {
        Professor professor = professorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), professor.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtService.generateToken(professor.getEmail());   // ✅ USE ONLY EMAIL
    }
    public Professor getByEmail(String email) {
        return professorRepository.findByEmail(email)
                .orElse(null);
    }


}
