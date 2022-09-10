package peaksoft.repository;

import peaksoft.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Long> {

    @Query("select i from Instructor i where i.company.id=:id")
    List<Instructor> getAllByCompanyId(Long id);

}
