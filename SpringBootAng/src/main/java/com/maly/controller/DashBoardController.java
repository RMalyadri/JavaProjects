package com.maly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maly.dto.Emp;
import com.maly.dto.ResponseDto;
import com.maly.service.EmpService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dash")
@Slf4j
public class DashBoardController {
	@Autowired
	private EmpService empService;

	@GetMapping(value = "/all", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Page<Emp> getEmpInfo(@RequestParam("page") int page,@RequestParam("size") int size) throws InterruptedException {
		log.info("pagination start index:{}",page);
		Page<Emp> empPagination =  empService.getEmployees(PageRequest.of(page, size,Sort.by("eid").ascending()));
		log.info("pagination details:{}",empPagination);
		return empPagination;
	}

	@PostMapping(value = "/saveEmp", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseDto saveEmp(@RequestBody Emp emp){
		log.info("emp:{}", emp);
		return empService.saveEmp(emp);

	}

	@GetMapping(value="/searchEmp/{searchKey}",produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Emp> getEmpList(@PathVariable("searchKey") String searchKey) {
		log.info("search key:{}",searchKey);
		List<Emp> empList = empService.getEmployeesBySearch(searchKey);
		log.info("search result:{}",empList);
		return empList;
	}
	
	@PostMapping(value = "/deleteEmp/{empIds}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseDto deleteEmp(@PathVariable("empIds") List<Long> empIds) {
		log.info("deleted employee id's:{}", empIds);
		return empService.deleteEmp(empIds);
	}
	@PostMapping(value = "/editEmp/{empId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Emp getEmp(@PathVariable("empId") Long empId) {
		Emp emp =  empService.getEmployee(empId).get();
		log.info("available employee information:{}",emp);
		return emp;
	}
	 
}
