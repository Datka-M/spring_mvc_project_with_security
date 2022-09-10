package peaksoft.repository;

import org.springframework.data.jpa.repository.Query;
import peaksoft.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("select s from Student s where s.company.id=:id")
    List<Student> getAllByCompanyId(Long id);
}
