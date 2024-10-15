package com.college.service;

import java.util.List;

import com.college.model.Student;

public interface IStudentService {

	Student addStudent(Student student);
	
	List<Student> getStudents();
	
	Student updateStudent(Student student, Long id);
	
	Student getStudentById(Long id);
	
	void deletestudent(Long id);
}