package com.maly.dao;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.maly.config.AppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class EmpDaoTest {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EmpDao empDao;

	@Test
	public void testGetEmployeesBySearch() {
		System.out.println(empDao + " " + entityManager);
	}
}
