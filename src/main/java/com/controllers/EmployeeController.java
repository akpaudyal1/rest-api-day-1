package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.EmployeeDTO;
import com.service.EmployeeService;

//This line is commited by farid
//..........
@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping({ "/loginPage", "/" })
	public String callLoginPage() {
		return "login"; // /WEB-INF/pages/ login.jsp
	}

	@GetMapping("/register")
	public String getRegistrationPage() {
		return "registration"; // registration.jsp
	}

	@PostMapping("/registration")
	public String registerEmployee(@ModelAttribute EmployeeDTO employeeDTO, Model model) {
		// @ModelAttribute ---> for reading all data (all data means --5
		// data-->employeeId,employeename,salary,emailId,password)
		employeeService.registerEmp(employeeDTO); // here employeeDTO is having 5 data

		model.addAttribute("message", "You have registered yourself successfully");
		return "info"; // You have registered yourself successfully
		// return "registration";
	}

	@PostMapping("/loginValidate")
	public String authenticate(@RequestParam String emailId, @RequestParam String password, Model model) {
		// @RequestParam --->used for reading single data
		EmployeeDTO employeeDTO = employeeService.authenticate(emailId, password);

		if (employeeDTO != null) {
			// valid user

			model.addAttribute("employeeDTO", employeeDTO);
			// return "congratulation";
			return "congratulations";
		} else {
			// invalid user

			model.addAttribute("message", "Wrong credential--Re-Try please!!");
			return "login";
		}
	}

	// fetch all the records from table
	@GetMapping("/showEmployee")
	public String showAllEmployees(Model model) {
		List<EmployeeDTO> employeeDTOList = employeeService.findAllEmployees();

		model.addAttribute("employeeDTOList", employeeDTOList);
		return "showAll";
	}

	// delete operation

	@GetMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam int employeeId, Model model) {

		employeeService.deleteEmp(employeeId);

		model.addAttribute("message", "Record deleted successfully");
		// return "info";

		return "redirect:/showEmployee"; // after deleting a record, rest of the records will be shown
	}

	// Edit operation - step:1 (we want to get edit page and in that page respective
	// data has also to be shown)
	@GetMapping("/updateEmployee")
	public String getEditPage(@RequestParam int employeeId, Model model) {
		EmployeeDTO employeeDTO = employeeService.fetchRecordFromTb(employeeId);

		model.addAttribute("employeeDTO", employeeDTO);
		return "employeeUpdate";
	}

	// Edit operation - step:2 --> we want to update existing data
	// employeeUpdate
	@PostMapping("/employeeUpdate")
	public String updateInToTbl(@ModelAttribute EmployeeDTO employeeDTO, Model model) {
		employeeService.updateTable(employeeDTO);

		model.addAttribute("message", "Record Updated Successfully");
		// return "info"; //on this page it will show that record updated successfully

		return "redirect:/showEmployee"; // after updation, updated record will be shown with all records
	}

}
