package model.services;

import java.util.List;

import model.dao.DAOFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {

	private DepartmentDao dao = DAOFactory.createDepartmentDao();
	
	public List<Department> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Department dp) {
		if (dp.getID() == null) {
			dao.insert(dp);
		}
		else {
			dao.update(dp);
		}
	}
	
	public void remover(Department dp) {
		dao.deleteByID(dp.getID());
	}
}
