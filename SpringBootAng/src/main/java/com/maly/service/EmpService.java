package com.maly.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.maly.dao.EmpDao;
import com.maly.dto.Emp;
import com.maly.dto.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmpService {
	@Autowired
	private EmpDao empDao;
	@Autowired
    RestTemplate restTemplate;
	
	@Value("${springdata.url}")
	String springDataUrl;
	

	public ResponseDto saveEmp(Emp emp) {
		ResponseDto responseDto = new ResponseDto();
		try {
			empDao.save(emp);
			responseDto.setMessage("data is saved successfully");
			responseDto.setStatus(true);
			
		} catch (Exception exp) {
			responseDto.setMessage("Exception Occurred. Please contact administrator");
			responseDto.setStatus(false);
		}
		log.info("end of saveEmp()");
		return responseDto;
	}

	@SuppressWarnings("unchecked")
	public Page<Emp> getEmployees(Pageable pageable) {
		return empDao.findAll(pageable);
		/*List<Emp> list = new ArrayList<>();
		 //empDao.findAll(pageable).iterator().forEachRemaining(list::add);
		 List<Emp> empList = restTemplate.getForObject(springDataUrl,List.class);
		//log.info("emplist from data:{}",empList);
		//list.addAll(empList);
		return list;*/
	}
	
	public List<Emp> getEmployeesBySearch(String searchKey) {
		return empDao.getEmployeesBySearch(searchKey,searchKey,searchKey);
	}
	
	public ResponseDto deleteEmp(List<Long> empIds) {
		ResponseDto responseDto = new ResponseDto();
		try {
			empIds.forEach(empId -> empDao.deleteById(empId));
			responseDto.setMessage("record/records are deleted successfully");
			responseDto.setStatus(true);
			
		} catch (Exception exp) {
			responseDto.setMessage("Exception Occurred. Please contact administrator");
			responseDto.setStatus(false);
		}
		log.info("end of deleteEmp()");
		return responseDto;
	}
	public Optional<Emp> getEmployee(Long empId) {
		return empDao.findById(empId);
	}
	@Async("threadPoolTaskExecutor")
	public void saveDumbRecords() {
        log.info("asynchrounus method is started");
		for(int i=1;i<=100;i++) {
			String appendValue =  "malyadri"+i;
			Emp empTemp = new Emp();
			empTemp.setFirstName(appendValue);
			empTemp.setLastName("rachali"+i);
			empTemp.setGender("M");
		    saveEmp(empTemp);
		}
	}

}
