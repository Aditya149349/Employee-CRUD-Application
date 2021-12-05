package com.employee.restapp.service.implement;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.restapp.model.EmployeeModel;
import com.employee.restapp.repository.EmployeeRepository;
import com.employee.restapp.service.EmployeeServiceInt;

@Service
public class EmployeeServiceImplement implements EmployeeServiceInt {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public ArrayList<EmployeeModel> getAllEmployeeList() {
		
		return (ArrayList<EmployeeModel>) employeeRepository.findAll();
	}

	@Override
	public EmployeeModel getEmployee(String employeeId){
		// TODO Auto-generated method stub
		return (EmployeeModel)employeeRepository.findById(employeeId).orElse(null);
	}

	@Override
	public void addEmployee(EmployeeModel topic) {
		
		employeeRepository.save(topic);

	}

	@Override
	public void updateEmployee(EmployeeModel employee) {
		
		employeeRepository.save(employee);

	}

	@Override
	public void deleteEmployee(String id) throws Exception {
		// TODO Auto-generated method stub
		EmployeeModel deletedUser = null;
		try {
			deletedUser = employeeRepository.findById(id).orElse(null);
			if(deletedUser == null) {
				throw new Exception("Employee not available");
			}
			else {
				employeeRepository.deleteById(id);
			}
		}catch(Exception ex) {
			throw ex;
		}
		
	}

	@Override
	public void deleteAll() {
		employeeRepository.deleteAll();

	}

}
