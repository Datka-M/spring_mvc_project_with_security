package peaksoft.service;

import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.entity.Instructor;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;


    public void saveCourseByCompanyId(Long id, Course course) {
        Company company = companyRepository.getById(id);
        company.addCourse(course);
        course.setCompany(company);
        courseRepository.save(course);
    }

    public void deleteCourseById(Long id){
        Course course = courseRepository.getById(id);
        course.setCompany(null);
        for (Instructor i: course.getInstructors()) {
            i.setCourses(null);
        }
        courseRepository.delete(course);
    }

    public Course getCourseById(Long id) {
        return courseRepository.getById(id);
    }

    public List<Course> getAllCoursesById(Long id) {
        return courseRepository.getAllByCompanyId(id);
    }

    public void updateCourseById(Long id, Course course) {
        Course course1 = courseRepository.getReferenceById(id);
        course1.setCourseName(course.getCourseName());
        course1.setDateOfStart(course.getDateOfStart());
        course1.setDuration(course.getDuration());
        course1.setImage(course.getImage());
        course1.setDescription(course.getDescription());
        courseRepository.save(course1);
    }
}
