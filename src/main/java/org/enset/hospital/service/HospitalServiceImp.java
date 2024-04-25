package org.enset.hospital.service;

import jakarta.transaction.Transactional;
import org.enset.hospital.entities.Patient;
import org.enset.hospital.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class HospitalServiceImp implements IHospitalService {
    private final PatientRepository patientRepository;

    public HospitalServiceImp(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void AddPatient(Patient patient) {
        patientRepository.save(patient);
    }
    @Override
    public void DeletePatient(Long id) {
        patientRepository.deleteById(id);
    }
    @Override
    public void UpdatePatient(Long id, Patient patient) {
        Patient patient1 = patientRepository.findById(id).get();
        patient1.setNom(patient1.getNom());
        patient1.setScore(patient.getScore());
        patient1.setMalade(patient.isMalade());
        patient1.setDateNaissance(patient1.getDateNaissance());
        patientRepository.save(patient);
    }
    @Override
    public Page<Patient> listPatients(int page, int size) {
        return patientRepository.findAll(PageRequest.of(page,size));
    }
    @Override
    public Patient patient(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Patient> findPatients(String nom, Pageable pageable) {
        return patientRepository.search(nom,pageable);
    }
    @Override
    public Page<Patient> findPatientsByMc(String mc,Pageable pageable) {
        return patientRepository.findByNomContains(mc,pageable);
    }

}
