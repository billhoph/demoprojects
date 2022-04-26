package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.*;
public interface PatientRepository extends JpaRepository<Patient, Long> {
  List<Patient> findByVaccinated(boolean vaccinated);
  List<Patient> findByPatientNameContaining(String patientName);
}