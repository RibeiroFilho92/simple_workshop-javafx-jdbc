package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentService {

	public List<Department> findAll() {
		List<Department> list = new ArrayList<>(); //Mock, for now
		list.add(new Department(1, "Games"));
		list.add(new Department(2, "Computers"));
		list.add(new Department(3, "Action figures"));
		return list;
	}
	
}
