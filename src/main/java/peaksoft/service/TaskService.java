package peaksoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Lesson;
import peaksoft.entity.Task;
import peaksoft.repository.LessonRepository;
import peaksoft.repository.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final LessonRepository lessonRepository;


    public void saveTaskByLessonById(Long id, Task task) {
        Lesson lesson = lessonRepository.getById(id);
        lesson.addTask(task);
        task.setLesson(lesson);
        taskRepository.save(task);
    }


    public void deleteTask(Long id) {
        Task task = taskRepository.getById(id);
        task.setLesson(null);
        taskRepository.delete(task);
    }


    public void updateTaskById(Long id, Task task) {
        Task task1 = taskRepository.getReferenceById(id);
        task1.setTaskName(task.getTaskName());
        task1.setTaskText(task.getTaskText());
        task1.setDeadLine(task.getDeadLine());
        taskRepository.save(task1);
    }

    public Task getTaskById(Long id) {
        return taskRepository.getById(id);
    }

    public List<Task> getAllTasksByLessonId(Long id) {
        return taskRepository.getAllByLessonId(id);
    }
}
