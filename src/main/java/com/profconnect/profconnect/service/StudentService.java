package com.profconnect.profconnect.service;

import com.profconnect.profconnect.model.Student;
import com.profconnect.profconnect.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;


    public Student registerStudent(Student student) {

        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        student.setPassword(passwordEncoder.encode(student.getPassword()));

        return studentRepository.save(student);
    }


    public Student loginStudent(String email, String rawPassword) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(rawPassword, student.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return student;
    }


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student updateStudent(Long id, Student updated) {
        Student s = getStudentById(id);

        s.setName(updated.getName());


        return studentRepository.save(s);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
