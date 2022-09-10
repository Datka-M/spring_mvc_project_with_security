package peaksoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.entity.Instructor;
import peaksoft.entity.Student;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final CourseRepository courseRepository;


    public void saveStudentByCompanyId(Long id, Student student) {
        Company company = companyRepository.getById(id);
        company.addStudent(student);
        student.setCompany(company);
        studentRepository.save(student);
    }

    public void deleteStudentById(Long id) {
        Student student = studentRepository.getById(id);
        student.setCompany(null);
        studentRepository.delete(student);
    }

    public void updateStudentById(Long id, Student student) {
        Student student1 = studentRepository.getReferenceById(id);
        student1.setFirstName(student.getFirstName());
        student1.setLastName(student.getLastName());
        student1.setPhoneNumber(student.getPhoneNumber());
        student1.setEmail(student.getEmail());
        student1.setStudyFormat(student.getStudyFormat());
        studentRepository.save(student1);
    }

    public List<Student> getAllStudentByCompanyId(Long companyId) {
        return studentRepository.getAllByCompanyId(companyId);
    }

    public Student getStudentById(Long id) {
        return studentRepository.getById(id);
    }


    public void assignStudentToCourse(Long courseId, Long studentId) {
        Course course = courseRepository.getById(courseId);
        Student student = studentRepository.getById(studentId);
        course.addStudent(student);
        student.setCourse(course);
        courseRepository.save(course);
        studentRepository.save(student);
    }
}
