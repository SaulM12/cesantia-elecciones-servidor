package com.cesantia.elections.services;

import com.cesantia.elections.entities.ExecutiveDirectorNominee;
import com.cesantia.elections.repositories.ExecutiveDirectorNomineeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExecutiveDirectorNomineeService {
    @Autowired
    private ExecutiveDirectorNomineeRepository nomineeRepository;

    public List<ExecutiveDirectorNominee> getAllNominees() {
        return nomineeRepository.findAll();
    }

    public ExecutiveDirectorNominee createNominee(ExecutiveDirectorNominee nominee) {
        return nomineeRepository.save(nominee);
    }

    public ExecutiveDirectorNominee updateNominee(Long id, ExecutiveDirectorNominee nomineeDetails) {
        ExecutiveDirectorNominee nominee = nomineeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nominee not found"));
        nominee.setCi(nomineeDetails.getCi());
        nominee.setNames(nomineeDetails.getNames());
        nominee.setLastName(nomineeDetails.getLastName());
        nominee.setSecondLastName(nomineeDetails.getSecondLastName());
        nominee.setElectionName(nomineeDetails.getElectionName());
        nominee.setPeriod(nomineeDetails.getPeriod());
        return nomineeRepository.save(nominee);
    }

    public ExecutiveDirectorNominee updateNomineeImage(Long id, byte[] image) {
        System.out.println(image);
        ExecutiveDirectorNominee nominee = nomineeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nominee not found with id: " + id));
        nominee.setImage(image);
        return nomineeRepository.save(nominee);
    }
}
