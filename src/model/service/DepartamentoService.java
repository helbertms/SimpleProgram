package model.service;

import java.util.List;

import model.dao.DaoFactury;
import model.dao.DepartamentoDao;
import model.entites.Departamento;

public class DepartamentoService {
	
	private DepartamentoDao dao = DaoFactury.createDepartamentoDao();
	
	public List<Departamento> findAll (){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Departamento obj) {
		if (obj.getId()== null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Departamento obj) {
		dao.deleteById(obj.getId());
	}
}
