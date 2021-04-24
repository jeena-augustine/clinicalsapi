package com.clinicals.api.endpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.beans.factory.annotation.Autowired;

import com.clinicals.api.model.ClinicalData;
import com.clinicals.api.model.Patient;
import com.clinicals.api.repos.PatientRepository;

@Path("/api")
@Consumes("application/json")
@Produces("application/json")
@CrossOriginResourceSharing(allowAllOrigins = true)
public class PatientService {

	@Autowired
	PatientRepository patientRepo;

	@Path("/patients")
	@POST
	public Patient createPatient(Patient patient) {
		return patientRepo.save(patient);
	}

	@GET
	@Path("/patient/{id}")
	public Patient getPatient(@PathParam("id") int id) {
		return patientRepo.findById(id).get();
	}

	@GET
	@Path("/patients")
	public List<Patient> getPatients() {
		return patientRepo.findAll();
	}

	@GET
	@Path("/patient/analyse/{id}")
	public Patient analysePatientBmi(@PathParam("id") int id) {

		Patient patient = patientRepo.findById(id).get();
		List<ClinicalData> clinicalDatas = new ArrayList<>(patient.getClinicalData());
		for (ClinicalData clinicalData : clinicalDatas) {

			if (clinicalData.getComponentName().equals("h/w")) {
				String component = clinicalData.getComponentValue();
				String[] split = component.split("/");
				String height = split[0];
				String weight = split[1];
				Float heightInMeters = Float.parseFloat(height) * 0.0254F;
				Float weghtInKg = Float.parseFloat(weight);
				Float bmi = weghtInKg / (heightInMeters * heightInMeters);
				ClinicalData clinicalData2 = new ClinicalData();
				clinicalData2.setComponentName("bmi");
				clinicalData2.setComponentValue(String.valueOf(bmi));
				patient.getClinicalData().add(clinicalData2);
			}

		}

		return patient;
	}

}
