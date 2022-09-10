package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.entity.Student;
import peaksoft.service.CompanyService;
import peaksoft.service.CourseService;
import peaksoft.service.StudentService;

import javax.management.modelmbean.ModelMBeanOperationInfo;

@Controller
public class StudentController {

    private final StudentService studentService;
    private final CompanyService companyService;
    private final CourseService courseService;

    @Autowired
    public StudentController(StudentService service, CompanyService companyService, CourseService courseService) {
        this.studentService = service;
        this.companyService = companyService;
        this.courseService = courseService;
    }

    @GetMapping("/allStudents/{companyId}")
    public String getAllStudents(@PathVariable("companyId")Long companyId,
                                 Model model,@ModelAttribute("course")Course course){
        model.addAttribute("students",studentService.getAllStudentByCompanyId(companyId));
        model.addAttribute("companyId",companyId);
        Company company = companyService.getById(companyId);
        model.addAttribute("courses",courseService.getAllCoursesById(companyId));
        return "/students/mainStudentPage";
    }

    @PostMapping("/{companyId}/{studentId}/assign")
    private String assign(@PathVariable("studentId")Long id,
                          @PathVariable("companyId")Long companyId,
                          @ModelAttribute("course") Course course) {
        studentService.assignStudentToCourse(course.getId(),id);
        return "redirect:/allStudents/ "+ companyId;
    }

    @GetMapping("/newStudent/{compId}")
    public String addStudent(Model model,@PathVariable("compId")Long compId){
        model.addAttribute("newStudent",new Student());
        model.addAttribute("compId",compId);
        return "/students/newStudent";
    }


    @PostMapping("/saveStudent/{compId}")
    public String saveStudent(@PathVariable("compId")Long compId,
                              @ModelAttribute("newStudent")Student student){
        studentService.saveStudentByCompanyId(compId,student);
        return "redirect:/allStudents/ "+compId;
    }

    @GetMapping("/editStudent/{studentId}")
    private String updateStudent(@PathVariable("studentId")Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student",student);
        model.addAttribute( "companyId",student.getCompany().getId());
        return "students/updateStudent";
    }

    @PostMapping("/{studentId}/{compId}/updateStudent")
    public String editStudent(@PathVariable("compId") Long id,
                                    @PathVariable("studentId")Long studentId,
                                    @ModelAttribute("student") Student student) {
        studentService.updateStudentById(studentId,student);
        return "redirect:/allStudents/ "+id;
    }

    @RequestMapping("/deleteStudent/{studentId}/{id}")
    public String deleteStudent(@PathVariable("id")Long id,
                                @PathVariable("studentId")Long studentId){
        studentService.deleteStudentById(studentId);
        return "redirect:/allStudents/ "+id;
    }
}

