package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.EmployeeDao;
import com.dto.EmployeeDTO;
import com.entity.EmployeeEntity;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeDao employeeDao;

	
	  //registration operation
	@Override
	public void registerEmp(EmployeeDTO employeeDTO) {
		
		EmployeeEntity employeeEntity=new EmployeeEntity();
		//hence dao layer using employeeEntity. It is not using employeeDTO
		//that's why we have to copy employeeDTO to employeeEntity
		
		BeanUtils.copyProperties(employeeDTO, employeeEntity);
		
		employeeDao.save(employeeEntity);
		
	}

	
	
	//login validation
	@Override
	public EmployeeDTO authenticate(String emailId, String password) {
		
		Optional<EmployeeEntity> optional=employeeDao.findByEmailIdAndPassword(emailId,password);
		
		EmployeeDTO employeeDTO=null;
		
		if(optional.isPresent()) {
			               //isPresent()--->If a value is present, returns true, otherwise false.
			
			EmployeeEntity employeeEntity=optional.get();
			                                      //get()--->if a value is present, returns the value, otherwise throws NoSuchElementException.
			
			employeeDTO=new EmployeeDTO();
			
			BeanUtils.copyProperties(employeeEntity,employeeDTO);
			
			return employeeDTO;	 //not_null
		}else {			
			
			return employeeDTO;	 //null	
		}
	}

   
	//fetch all the records 

	@Override
	public List<EmployeeDTO> findAllEmployees() {
		
		List<EmployeeEntity> employeeEntity=employeeDao.findAll();
		
		List<EmployeeDTO> employeeDTOList=new ArrayList<>();
		
		if(employeeEntity.size()>0) {
			
			for(EmployeeEntity        tempa:employeeEntity) {
				
				EmployeeDTO employeeDTO=new EmployeeDTO();
				BeanUtils.copyProperties(tempa,employeeDTO);
				
				employeeDTOList.add(employeeDTO);	
			}		
		}
	
		return employeeDTOList;
	}


	
	
	//delete operation

	@Override
	public void deleteEmp(int employeeId) {
		employeeDao.deleteById(employeeId);
		
	}


    //edit operation - part-1(we are trying to fetch the data)
	@Override
	public EmployeeDTO fetchRecordFromTb(int employeeId) {
		Optional<EmployeeEntity> optional=employeeDao.findById(employeeId);
			EmployeeDTO employeeDTO=null;
		
		if(optional.isPresent()) {
			               //isPresent()--->If a value is present, returns true, otherwise false.
			
			EmployeeEntity employeeEntity=optional.get();
			                                      //get()--->if a value is present, returns the value, otherwise throws NoSuchElementException.
			
			employeeDTO=new EmployeeDTO();
			
			BeanUtils.copyProperties(employeeEntity,employeeDTO);
			
			return employeeDTO;	 //not_null
		}else {			
			
			return employeeDTO;	 //null	
		}

	}



	@Override
	public void updateTable(EmployeeDTO employeeDTO) {
		EmployeeEntity employeeEntity=new EmployeeEntity();
		//hence dao layer using employeeEntity. It is not using employeeDTO
		//that's why we have to copy employeeDTO to employeeEntity
		
		BeanUtils.copyProperties(employeeDTO, employeeEntity);
		
		
		employeeDao.save(employeeEntity);   //save updated 5 data inside table
		
	}
	
	
	
	
}
