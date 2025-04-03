package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.EmployeeDTO;
import com.service.EmployeeService;

@RestController

public class RestController1 {

	@Autowired
	EmployeeService employeeService;

	// --------------Rest Api Operation-------------------

	@GetMapping("employees") // localhost:444/employees
	List<EmployeeDTO> fetchAllEmployees() {

		List<EmployeeDTO> employeeDTOList = employeeService.findAllEmployees();
		return employeeDTOList;

	}

	// --------------End of Rest Api Operation-------------


//find data of particular employee--  

	@GetMapping("/employees/{employeeId}") //localhost:444/employees/2
	
	EmployeeDTO fetchEmployee(@PathVariable int employeeId ){
	
		EmployeeDTO employee=employeeService.fetchRecordFromTb(employeeId);
		return employee;

  }
	
	//registration
	@PostMapping("/employees") 
	
	String registerEmployee(@RequestBody  EmployeeDTO employeeDTO) {
		
		employeeService.registerEmp(employeeDTO);
		return "registration done====";
	}
	
	
	//updation
		@PutMapping("/employees") 
		
		String updateEmployee(@RequestBody  EmployeeDTO employeeDTO) {
			
			employeeService.updateTable(employeeDTO);
			return "updation done====";
		}
	
		//delete
				@DeleteMapping("/employees/{employeeId}") ////localhost:444/employees/3
				
				String deleteEmployee(@PathVariable int employeeId) {
					
					employeeService.deleteEmp(employeeId);
					return "deletion done====";
				}
	
}
