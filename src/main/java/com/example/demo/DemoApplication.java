package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository studentRepository, MongoTemplate mongoTemplate){
		return args -> {
			Address address = new Address("Uruguay","Canelones","15008");
			String email = "rkilleen0@mysql.com";
			Student student = new Student(
					"Retha",
					"Killeen",
					email,
					Gender.FEMALE,
					address,
					List.of("C++","Java"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

			// usingMongoTemplateAndQuery(studentRepository, mongoTemplate, email, student);

			studentRepository.findStudentByEmail(email).ifPresentOrElse(
					s -> {
						System.out.println("Already exist that student with te email " + email);
					}, () -> {

						System.out.println("New student " + student);
						studentRepository.insert(student);
					}

			);

		};
	}

	private void usingMongoTemplateAndQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, String email, Student student){
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if (students.isEmpty()) {
			System.out.println("New student " + student);
			studentRepository.insert(student);
		} else {
			System.out.println("Already exist that student with te email " + email);
		}

	}

}
