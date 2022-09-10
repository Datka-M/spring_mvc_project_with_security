package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Task;
import peaksoft.service.TaskService;

@Controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/allTasks/{lessonId}")
    public String getAllTask(Model model, @PathVariable("lessonId")Long lessonId){
        model.addAttribute("tasks",taskService.getAllTasksByLessonId(lessonId));
        model.addAttribute("lessonId",lessonId);
        return "/tasks/mainTaskPage";
    }

    @GetMapping("/getTask/{id}")
    public String getTask(Model model,@PathVariable("id")Long id){
        model.addAttribute("justTask",taskService.getTaskById(id));
        return "/tasks/mainTaskPage";
    }

    @GetMapping("/newTask/{lesId}")
    public String addTask(@PathVariable("lesId")Long lesId, Model model){
        model.addAttribute("newTask",new Task());
        model.addAttribute("lesId",lesId);
        return "/tasks/newTask";
    }

    @PostMapping("/saveTask/{lesId}")
    public String saveTask(@PathVariable("lesId")Long lesId, @ModelAttribute("newTask")Task task){
        taskService.saveTaskByLessonById(lesId,task);
        return "redirect:/allTasks/ "+lesId;
    }

    @GetMapping("/editTask/{taskId}")
    public String editTask(@PathVariable("taskId")Long taskId,
                           Model model){
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task",task);
        model.addAttribute("lessonId",task.getLesson().getId());
        return "/tasks/updateTask";
    }

    @PostMapping("/{taskId}/{lessonId}/updateTask")
    public String updateTask(@PathVariable("lessonId")Long lessonId,
                             @PathVariable("taskId")Long taskId,
                             @ModelAttribute("task")Task task){
        taskService.updateTaskById(taskId,task);
        return "redirect:/allTasks/ "+lessonId;
    }

    @RequestMapping("/deleteTask/{id}/{lessonId}")
    public String deleteTask(@PathVariable("id")Long id,
                             @PathVariable("lessonId")Long lessonId){
        taskService.deleteTask(id);
        return "redirect:/allTasks/ "+lessonId;
    }
}
