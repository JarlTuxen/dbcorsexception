package dk.kea.dbcorsexception.controller;

import dk.kea.dbcorsexception.model.Student;
import dk.kea.dbcorsexception.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/students")
public class StudentController {

    private StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Student>> findAll(){

        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        //ResponseEntity builder - først status OK/200 - så til sidst body
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    //HTTP GET (/students/{id}) - findById
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> findById(@PathVariable Long id){
        //hent student fra repository
        Optional<Student> optionalStudent = studentRepository.findById(id);
        //er student fundet?
        if (optionalStudent.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optionalStudent);
        }
        else{
            //not found 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //HTTP Post (/students) - create
    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student){

        //hvis id sat, så returner BAD_REQUEST
        if (student.getId()!=0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        //opret ny student i JPA
        Student newStudent = studentRepository.save(student);

        //location header: /students/{id}
        String location = "/students/" + newStudent.getId();

        //HTTPStatus Created 201
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", location)
                .body(newStudent);
    }

    //HTTP Put (/students/{id}) - update
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Student student){
        //findes den studerende?
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            //er path id og student object id identiske? ellers returner BAD_REQUEST
            if (id.equals(student.getId())){
                //stud findes så opdater
                //student.setId(id);
                studentRepository.save(student);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            else{
                //forskel på path id og student object id
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //HTTP Delete (/students/{id}) - delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            studentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            //not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
