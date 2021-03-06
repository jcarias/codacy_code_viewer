package com.jcarias.codacy.github.helpers;

import com.jcarias.git.model.Person;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Map;

public class PersonParser implements Parser<Person, Map> {

	@Override
	public Person parse(Map sourceObject) {
		String name = (String) sourceObject.get("name");
		String email = (String) sourceObject.get("email");
		String dateString = (String) sourceObject.get("date");

		DateTimeFormatter formatter = ISODateTimeFormat.dateTimeNoMillis();
		DateTime date = formatter.parseDateTime(dateString);

		return new Person(name, email, date.toDate());
	}
}
