package com.clinicals.api.endpoints;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.beans.factory.annotation.Autowired;

import com.clinicals.api.dto.ClinicalDataRequest;
import com.clinicals.api.model.ClinicalData;
import com.clinicals.api.model.Patient;
import com.clinicals.api.repos.ClinicalDataRepo;
import com.clinicals.api.repos.PatientRepository;

@Path("/api")
@Consumes("application/json")
@Produces("application/json")
@CrossOriginResourceSharing(allowAllOrigins = true)
public class ClinicalService {
	
	@Autowired
	PatientRepository patientRepo;
	@Autowired
	ClinicalDataRepo clinicalRepo;
	
	@Path("/clinicals")
	@POST
	public ClinicalData createClinicalData(ClinicalDataRequest request) {
		
		Patient patient = patientRepo.findById(request.getPatientId()).get();
		ClinicalData clinicalData=new ClinicalData();
		clinicalData.setComponentName(request.getComponentName());
		clinicalData.setComponentValue(request.getComponentValue());
		clinicalData.setPatient(patient);
		return clinicalRepo.save(clinicalData);
	}
	
	
	
}
