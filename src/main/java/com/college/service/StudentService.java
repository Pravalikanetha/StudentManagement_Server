package com.college.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college.exception.StudentAlreadyExistsException;
import com.college.exception.StudentNotFoundException;
import com.college.model.Student;
import com.college.repository.StudentRepository;


@Service
public class StudentService implements IStudentService{

	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public List<Student> getStudents() {
		return studentRepository.findAll();
	}
	
	@Override
	public Student addStudent(Student student) {
		if(studentAlreadyExists(student.getEmail())) {
			throw new StudentAlreadyExistsException(student.getEmail()+"Already Exist");
		}
		return studentRepository.save(student);
	}
	
	private boolean studentAlreadyExists(String email) {
		return studentRepository.findByEmail(email).isPresent();
	}
	
	@Override
	public Student updateStudent(Student student, Long id) {
		return studentRepository.findById(id).map(st->{
			st.setFirstName(student.getFirstName());
			st.setLastname(student.getLastname());
			st.setEmail(student.getEmail());
			st.setDepartment(student.getDepartment());
			return studentRepository.save(st);
		}).orElseThrow(() -> new StudentNotFoundException("Sorry, this student doesn't exist"));
	}

	@Override
	public Student getStudentById(Long id) {
		
		return studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException("Sorry, no student not found with id : " +id));
	}

	@Override
	public void deletestudent(Long id) {
		if(!studentRepository.existsById(id)) {
			throw new StudentNotFoundException("Sorry, no student not found with id : " +id);
		}
		studentRepository.deleteById(id);
	}

}