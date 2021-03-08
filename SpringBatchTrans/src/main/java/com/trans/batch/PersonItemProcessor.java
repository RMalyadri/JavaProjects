package com.trans.batch;

import org.springframework.batch.item.ItemProcessor;

import com.trans.bean.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

	@Override
	public Person process(Person person) throws Exception {
		if("Y".equals(person.getUpdateStatus())){
			return person;
		}
		return null;
	}

}