package com.employee.restapp.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;

import com.employee.restapp.model.*;
import com.employee.restapp.repository.EmployeeRepository;

//@Service
public class EmployeeService {
	ArrayList<EmployeeModel> list = new ArrayList<>();
	
	@Autowired
	public EmployeeRepository employeeRepository;
	
	public ArrayList<EmployeeModel> getAllEmployeeList()
	{
		return list;
	}
	public EmployeeModel getEmployee(String employeeId)
	{
		//EmployeeModel employee = list.stream().filter(c -> c.getId() == employeeId).findAny().get();
		Iterator<EmployeeModel> itr = list.iterator();
		EmployeeModel emp;
		EmployeeModel emp2 = null;
		while(itr.hasNext()){
			emp = itr.next();
			if(emp.getId().equals(employeeId)){
				emp2 = emp;
				return emp;
			}
		}
		return emp2;
	}
	public void addEmployee(EmployeeModel topic)
	{
		list.add(topic);
	}
	public void updateEmployee(EmployeeModel employee,String id)
	{
		//EmployeeModel oldEmployee = list.stream().filter(c -> c.getId() == id).findAny().get();
		Iterator<EmployeeModel> itr = list.iterator();
		EmployeeModel emp;
		//EmployeeModel emp2 = null;
		while(itr.hasNext()){
			emp = itr.next();
			if(emp.getId().equals(id)){
				itr.remove();
				list.add(employee);
				break;
				
			}
		}
		//list.remove(oldEmployee);
        //list.add(employee);
	}
	public String deleteEmployee(String id)
	{
		Iterator<EmployeeModel> itr = list.iterator();
		while(itr.hasNext()){
			EmployeeModel emp = itr.next();
			if(emp.getId().equals(id)){
				itr.remove();
				break;
			}
		}
		return("Employee with ID "+id+" has been deleted from the list");
		
	}
	public void deleteAll()
	{
		list.clear();
		//return("Employee records deleted. List is Empty");
	}

}
