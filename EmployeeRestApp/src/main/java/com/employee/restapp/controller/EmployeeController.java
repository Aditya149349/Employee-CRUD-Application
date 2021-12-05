package com.employee.restapp.controller;

import java.util.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.restapp.model.EmployeeModel;
import com.employee.restapp.service.*;
//import com.employee.restapp.model.*;

//the one with API
@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController {
	
	@Autowired
    private EmployeeServiceInt employeeServiceInt;

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	Logger logger= LoggerFactory.getLogger(EmployeeController.class);
	ObjectMapper mapper = new ObjectMapper();

	@Value("${cloud.aws.end-point.uri}")
	private String endPoint;

	public static final String CUSTOMER_DETAILS_SQSQUEUE_NAME = "${cloud.aws.end-point.uri}";
	//AWS Functions
	@PostMapping("/send_message")
	public EmployeeModel sendMessage(@RequestBody @Valid EmployeeModel employee) {

		try {
			//ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(employee);
			queueMessagingTemplate.send(endPoint, MessageBuilder.withPayload(jsonString).build());
			logger.info("Message sent successfully  " + jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

	@SqsListener(value = CUSTOMER_DETAILS_SQSQUEUE_NAME, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void receiveMessage(String stringJson) {

		try{
			logger.info("Message Received using SQS Listener " + stringJson);
			EmployeeModel queueEmployee = mapper.readValue(stringJson,EmployeeModel.class);
			employeeServiceInt.updateEmployee(queueEmployee);
			logger.info("Emplyee updated in the Database");

		} catch (Exception e){
			e.printStackTrace();
		}

		//System.out.println(stringJson);

	}


	// Before AWS Code
	@GetMapping(path = "/getall")
	public ResponseEntity<ArrayList<EmployeeModel>> results()
	{
		ArrayList<EmployeeModel> employees = null;
		employees = employeeServiceInt.getAllEmployeeList();
		return new ResponseEntity<ArrayList<EmployeeModel>>(employees,HttpStatus.OK);
		//return records;
	}
	@GetMapping(path = "getemployee/{empId}")
	public ResponseEntity<EmployeeModel> getEmployee(@NotBlank @PathVariable("empId") String empId) throws Exception
	{
		EmployeeModel employee = null;
		try
		{
			employee = employeeServiceInt.getEmployee(empId);
			if(employee == null) {
				throw new Exception("Employee not available");
			}
			else {
				return new ResponseEntity<EmployeeModel>(employee,HttpStatus.OK);
			}
		}
		catch(Exception ex){
			throw ex;
			
		}
		//return new ResponseEntity<EmployeeModel>(employee,HttpStatus.OK);
	}
	@PostMapping(path = "/addemployee")
	public String addEmployee(@RequestBody @Valid EmployeeModel employee)
	{
		employeeServiceInt.addEmployee(employee);
		return("Employee added");
	}
	@PutMapping(path = "update/{empId}")
	public String updateEmployee(@NotBlank @PathVariable("empId") String empId, @RequestBody @Valid EmployeeModel employee)
	{
		employeeServiceInt.updateEmployee(employee);
		return("Employee updated");
	}
	@DeleteMapping(path = "deleteemployee/{empId}")
	public String deleteEmployee(@NotBlank @PathVariable("empId") String empId) throws Exception
	{
		employeeServiceInt.deleteEmployee(empId);
		return("Employee "+empId+" deleted");
	}
	@DeleteMapping(path = "/deleteall")
	public String deleteAll()
	{
		employeeServiceInt.deleteAll();
		return("Employee records deleted. List is Empty");
	}
	

}
