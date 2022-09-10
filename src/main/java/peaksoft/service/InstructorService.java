package peaksoft.service;

import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.entity.Instructor;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final CompanyRepository companyRepository;
    private final CourseRepository courseRepository;

    public void saveInstructorByCompanyId(Long id, Instructor instructor) {
        Company company = companyRepository.getById(id);
        company.addInstructor(instructor);
        instructor.setCompany(company);
        instructorRepository.save(instructor);
    }

    public void deleteInstructorById(Long id) {
        Instructor instructor = instructorRepository.getById(id);
        instructor.setCompany(null);
        for (Course course: instructor.getCourses()) {
            course.setInstructors(null);
        }
        instructorRepository.delete(instructor);
    }

    public void updateInstructor(Long id,Instructor instructor){
        Instructor instructor1 = instructorRepository.getReferenceById(id);
        instructor1.setFirstName(instructor.getFirstName());
        instructor1.setLastName(instructor.getLastName());
        instructor1.setPhoneNumber(instructor.getPhoneNumber());
        instructor1.setEmail(instructor.getEmail());
        instructor1.setSpecialization(instructor.getSpecialization());
        instructorRepository.save(instructor1);
    }

    public Instructor  getInstructorById(Long id){
       return instructorRepository.findById(id).get();
    }

    public List<Instructor> getAllInstructorsByCompanyId(Long companyId) {
        return instructorRepository.getAllByCompanyId(companyId);
    }

    public void assignInstructorToCourse(Long insId, Long courseId) {
        Instructor instructor = instructorRepository.getById(insId);
        Course course = courseRepository.getById(courseId);
        instructor.addCourse(course);
        course.addInstructor(instructor);
        instructorRepository.save(instructor);
        courseRepository.save(course);
    }
}
