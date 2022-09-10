package peaksoft.service;

import peaksoft.entity.Course;
import peaksoft.entity.Lesson;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public void saveLessonByCourseId(Long id, Lesson lesson) {
        Course course = courseRepository.getById(id);
        course.addLesson(lesson);
        lesson.setCourse(course);
        lessonRepository.save(lesson);
    }


    public Lesson getLessonById(Long id) {
        return lessonRepository.getById(id);
    }


    public void deleteLesson(Long id) {
       Lesson lesson = lessonRepository.getById(id);
       lesson.setCourse(null);
       lessonRepository.delete(lesson);
    }


    public void updateLessonById(Long id, Lesson lesson) {
        Lesson lesson1 = lessonRepository.getReferenceById(id);
        lesson1.setLessonName(lesson.getLessonName());
        lessonRepository.save(lesson1);
    }


    public List<Lesson> getAllLessonsByCourseId(Long id) {
        return lessonRepository.getAllByCourseId(id);
    }
}
