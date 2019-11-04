package com.maly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.maly.dto.Emp;

@Repository
public interface EmpDao extends PagingAndSortingRepository<Emp, Long> {
	
	 @Query(value="SELECT *  FROM Emp where first_name like %:searchKey% or last_name like %:searchKey2% or eid  like %:searchKey3% ",nativeQuery=true)
	 List<Emp> getEmployeesBySearch(@Param("searchKey") String searchKey,@Param("searchKey2") String searchKey2,@Param("searchKey3") String searchKey3);

}
