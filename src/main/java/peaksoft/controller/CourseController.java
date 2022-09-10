package peaksoft.controller;

import peaksoft.entity.Course;
import peaksoft.entity.Instructor;
import peaksoft.service.CompanyService;
import peaksoft.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.service.InstructorService;


@Controller
@RequestMapping
public class CourseController {

    private final CourseService courseService;
    private final InstructorService instructorService;
    private final CompanyService companyService;

    @Autowired
    public CourseController(CourseService courseService, InstructorService instructorService, CompanyService companyService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.companyService = companyService;
    }


    @GetMapping("/allCourses/{id}")
    public String allCourses(Model model, @PathVariable("id")Long id,
                             @ModelAttribute("instructor") Instructor instructor){
        model.addAttribute("courses",courseService.getAllCoursesById(id));
        model.addAttribute("companyId",id);
        model.addAttribute("instructors",instructorService.getAllInstructorsByCompanyId(id));
        return "/courses/mainCoursePage";
    }

    @GetMapping("/newCourse/{id}")
    public String addCourse(Model model, @PathVariable("id") Long id) {
        model.addAttribute("newCourse", new Course());
        model.addAttribute("id", id);
        return "/courses/newCoursePage";
    }

    @PostMapping("/{id}/saveCourse")
    public String saveCourse(@ModelAttribute("newCourse") Course course,
                             @PathVariable("id") Long companyId) {
        courseService.saveCourseByCompanyId(companyId,course);
        return "redirect:/allCourses/ " + companyId;
    }


    @GetMapping("/editCourse/{id}/{companyId}")
    public String editCourse(Model model, @PathVariable("id") Long id,@PathVariable("companyId") Long companyId) {
        model.addAttribute("course", courseService.getCourseById(id));
        return "/courses/updateCourse";
    }

    @PostMapping("/{id}/{companyId}/updateCourse")
    public String updateCourse(@PathVariable("id") Long id,
                               @PathVariable("companyId") Long companyId,
                               @ModelAttribute Course course) {
        courseService.updateCourseById(id, course);
        return "redirect:/allCourses/ " + companyId;
    }

    @RequestMapping("/deleteCourse/{courseId}/{compId}")
    public String deleteCourse(@PathVariable("courseId") Long courseId,
                               @PathVariable("compId")Long compId) {
        courseService.deleteCourseById(courseId);
        return "redirect:/allCourses/ "+compId;
    }

    @PostMapping("/{courseId}/{companyId}/assignInstructor")
    public String assignInstructorToCourse(@PathVariable("courseId")Long courseId,
                                           @PathVariable("companyId")Long companyId,
                                           @ModelAttribute("instructor")Instructor instructor){
        instructorService.assignInstructorToCourse(instructor.getId(),courseId);
        return "redirect:/allCourses/ "+companyId;
    }
}