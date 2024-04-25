package org.enset.hospital.repository;

import org.enset.hospital.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository()

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findById(Long Id);
    Page<Patient> findByNomContains(String mc, Pageable pageable);
    @Query("select p from Patient p where p.nom like:s")
    Page<Patient>search(@Param("s") String mc, Pageable pageable);
}
