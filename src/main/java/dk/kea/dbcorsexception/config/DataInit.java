package dk.kea.dbcorsexception.config;

import dk.kea.dbcorsexception.model.Student;
import dk.kea.dbcorsexception.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInit implements CommandLineRunner {

    private StudentRepository studentRepository;

    public DataInit(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        studentRepository.save(new Student("Asger", "Clausen"));
        studentRepository.save(new Student("Lars", "Mortensen"));
        studentRepository.save(new Student("Jarl", "Tuxen"));
    }
}
