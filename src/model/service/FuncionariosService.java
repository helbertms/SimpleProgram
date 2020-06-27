package model.service;

import java.util.List;

import model.dao.DaoFactury;
import model.dao.FuncionariosDao;
import model.entites.Funcionarios;

public class FuncionariosService {

	private FuncionariosDao dao = DaoFactury.createFuncionariosDao();

	public List<Funcionarios> findAll() {
		return dao.findAll();
	}

	public void saveOrUpdate(Funcionarios obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}

	public void remove(Funcionarios obj) {
		dao.deleteById(obj.getId());
	}
}
