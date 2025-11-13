package com.profconnect.profconnect.controller;

import com.profconnect.profconnect.config.JwtService;
import com.profconnect.profconnect.model.Student;
import com.profconnect.profconnect.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final JwtService jwtService;  // ✅ ADDED: inject JWT service

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.registerStudent(student));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Student student) {

        // ✔ Your exact logic
        Student loggedIn = studentService.loginStudent(student.getEmail(), student.getPassword());
        String token = jwtService.generateToken(loggedIn.getEmail());

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
}
