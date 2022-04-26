package com.example.demo.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "patients")
public class Patient {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "patientName")
  private String patientName;
  @Column(name = "patientid")
  private String patientid;
  @Column(name = "vaccinated")
  private boolean vaccinated;
  public Patient() {
  }
  public Patient(String patientName, String patientid, boolean vaccinated) {
    this.patientName = patientName;
    this.patientid = patientid;
    this.vaccinated = vaccinated;
  }
  public long getId() {
    return id;
  }
  public String getPatientName() {
    return patientName;
  }
  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }
  public String getDescription() {
    return patientid;
  }
  public void setDescription(String patientid) {
    this.patientid = patientid;
  }
  public boolean isVaccinated() {
    return vaccinated;
  }
  public void setVaccinated(boolean isVaccinated) {
    this.vaccinated = isVaccinated;
  }
  @Override
  public String toString() {
    return "Patient [id=" + id + ", patientName=" + patientName + ", desc=" + patientid + ", vaccinated=" + vaccinated + "]";
  }
}