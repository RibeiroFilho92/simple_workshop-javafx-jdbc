package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

	void insert(Department dp);
	void update(Department dp);
	void deleteByID(Integer ID);
	Department findByID(Integer ID);
	List<Department> findAll();
	
}