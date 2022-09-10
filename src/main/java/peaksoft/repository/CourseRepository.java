package peaksoft.repository;

import peaksoft.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    @Query("select c from Course c where c.company.id=:id")
    List<Course> getAllByCompanyId(Long id);
}
