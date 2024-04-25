package org.enset.hospital.service;
import org.enset.hospital.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHospitalService {
    void AddPatient(Patient patient);
    void DeletePatient(Long id);
    void UpdatePatient(Long id, Patient patient);
    Page<Patient> listPatients(int page, int size);
    Patient patient(Long id);
    Page<Patient>findPatients(String nom,Pageable pageable);
    Page<Patient> findPatientsByMc(String mc, Pageable pageable);

}
