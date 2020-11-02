package model.services;

import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {

	private SellerDao dao = DAOFactory.createSellerDao();
	
	public List<Seller> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Seller dp) {
		if (dp.getID() == null) {
			dao.insert(dp);
		}
		else {
			dao.update(dp);
		}
	}
	
	public void remover(Seller dp) {
		dao.deleteByID(dp.getID());
	}
}
