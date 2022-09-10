package peaksoft.repository;

import org.springframework.data.jpa.repository.Query;
import peaksoft.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("select t from Task t where t.lesson.id=:id")
    List<Task> getAllByLessonId(Long id);


}
