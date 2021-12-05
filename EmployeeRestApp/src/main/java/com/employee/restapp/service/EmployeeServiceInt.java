package com.employee.restapp.service;

import java.util.ArrayList;

import com.employee.restapp.model.EmployeeModel;


public interface EmployeeServiceInt {
	
	public ArrayList<EmployeeModel> getAllEmployeeList();
	public EmployeeModel getEmployee(String employeeId);
	public void addEmployee(EmployeeModel topic);
	public void updateEmployee(EmployeeModel employee);
	public void deleteEmployee(String id) throws Exception;
	public void deleteAll();

}
