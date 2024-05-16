package fs.miaad.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import fs.miaad.entities.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // 2 methodes pour rechercher
    Page<Patient> findByNomContains(String keyword , Pageable pageable);
    @Query("select p from Patient p where p.nom like :x")
    Page<Patient> Chercher(@Param("x") String keyword , Pageable pageable);
}
