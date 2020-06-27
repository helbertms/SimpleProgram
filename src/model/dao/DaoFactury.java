package model.dao;

import db.DB;
import model.dao.Impl.DepartamentoDaoJDBC;
import model.dao.Impl.FuncionariosDaoJDBC;

public class DaoFactury {

	public static FuncionariosDao createFuncionariosDao() {
		return new FuncionariosDaoJDBC(DB.getConnection());
	}
	
	public static DepartamentoDao createDepartamentoDao() {
		return new DepartamentoDaoJDBC(DB.getConnection());
	}
}
