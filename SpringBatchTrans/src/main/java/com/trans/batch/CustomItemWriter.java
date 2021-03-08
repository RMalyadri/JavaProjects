package com.trans.batch;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.trans.bean.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomItemWriter implements ItemWriter<Person> {

	@Autowired
	private DataSource datasouce;
	
	@Override
	public void write(List<? extends Person> persons) throws Exception {
		log.info("writing records"+datasouce.getConnection());
		persons.forEach(per -> log.info("{},{},{}",per.getFirstName(),per.getLastName(),per.getUpdateStatus()));
		
	}
}