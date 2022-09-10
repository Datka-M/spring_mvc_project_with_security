package peaksoft.controller;

import peaksoft.entity.Instructor;
import peaksoft.service.CourseService;
import peaksoft.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class InstructorController {


    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("/allInstructors/{companyId}")
    private String getAllInstructors(@PathVariable("companyId") Long id, Model model) {
        model.addAttribute("instructors", instructorService.getAllInstructorsByCompanyId(id));
        model.addAttribute("companyId", id);
        return "instructors/mainInstructorPage";
    }


    @GetMapping("/newInstructor/{companyId}")
    private String newInstructor(@PathVariable("companyId") Long id, Model model) {
        model.addAttribute("newInstructor", new Instructor());
        model.addAttribute("companyId", id);
        return "instructors/newInstructor";
    }

    @PostMapping("{companyId}/saveInstructor")
    private String saveInstructor(@PathVariable("companyId") Long id,
                                  @ModelAttribute("newInstructor") Instructor instructor) {
        instructorService.saveInstructorByCompanyId(id, instructor);
        return "redirect:/allInstructors/ " + id;
    }

    @GetMapping("/editInstructor/{instructorId}")
    private String updateInstructor(@PathVariable("instructorId")Long instructorId,Model model) {
        Instructor instructor = instructorService.getInstructorById(instructorId);
        model.addAttribute("instructor",instructor);
        model.addAttribute("companyId",instructor.getCompany().getId());
        return "instructors/updateInstructor";
    }

    @PostMapping("/{companyId}/{instructorId}/updateInstructor")
    private String  saveUpdate(@PathVariable("companyId")Long companyId,
                               @PathVariable("instructorId")Long id,
                               @ModelAttribute("instructor")Instructor instructor) {
        instructorService.updateInstructor(id,instructor);
        return "redirect:/allInstructors/ "+ companyId;
    }

    @PostMapping("/deleteInstructor/{id}/{instructorId}")
    private String deleteInstructor(@PathVariable("id") Long id, @PathVariable("instructorId") Long instructorId) {
        instructorService.deleteInstructorById(instructorId);
        return "redirect:/allInstructors/ " + id;
    }
}
