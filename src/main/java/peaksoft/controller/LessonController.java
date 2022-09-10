package peaksoft.controller;

import peaksoft.entity.Lesson;
import peaksoft.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.service.LessonService;


@Controller
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/allLessons/{courseId}")
    public String getAllLessons(Model model, @PathVariable("courseId")Long courseId){
        model.addAttribute("lessons",lessonService.getAllLessonsByCourseId(courseId));
        model.addAttribute("courseId",courseId);
        return "/lessons/mainLessonPage";
    }

    @GetMapping("/lesson/{id}")
    public String getLessonById(Model model,@PathVariable("id")Long id){
        model.addAttribute("lesson",lessonService.getLessonById(id));
        return "/lessons/mainLessonPage";
    }

    @GetMapping("/newLesson/{courseId}")
    public String addLesson(Model model,@PathVariable("courseId")Long courseId){
        model.addAttribute("newLesson",new Lesson());
        model.addAttribute("courseId",courseId);
        return "/lessons/newLesson";
    }

    @PostMapping("/saveLesson/{courseId}")
    public String saveLesson(@ModelAttribute("newLesson")Lesson lesson,
                             @PathVariable("courseId")Long courseId){
        lessonService.saveLessonByCourseId(courseId,lesson);
        return "redirect:/allLessons/ "+courseId;
    }

    @GetMapping("/editLesson/{id}")
    public String editLesson(Model model,@PathVariable("id")Long id){
        Lesson lesson = lessonService.getLessonById(id);
        model.addAttribute("lesson",lesson);
        model.addAttribute("courseId",lesson.getCourse().getId());
        return "/lessons/updateLesson";
    }

    @PostMapping("/{courseId}/{lessonId}/updateLesson")
    private String saveUpdateLesson(@PathVariable("courseId")Long courseId,
                                    @PathVariable("lessonId")Long lessonId,
                                    @ModelAttribute("lesson")Lesson lesson) {
        lessonService.updateLessonById(lessonId,lesson);
        return "redirect:/allLessons/ " + courseId;
    }

    @RequestMapping("/deleteLesson/{id}/{courseId}")
    public String deleteLesson(@PathVariable("id")Long id,
                               @PathVariable("courseId")Long courseId){
        lessonService.deleteLesson(id);
        return "redirect:/allLessons/ "+courseId;
    }



}
