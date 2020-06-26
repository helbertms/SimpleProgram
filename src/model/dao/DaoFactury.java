package model.dao;

import db.DB;
import model.dao.Impl.DepartamentoDaoJDBC;
import model.dao.Impl.SellerDaoJDBC;

public class DaoFactury {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartamentoDao createDepartamentoDao() {
		return new DepartamentoDaoJDBC(DB.getConnection());
	}
}
