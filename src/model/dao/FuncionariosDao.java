package model.dao;

import java.util.List;

import model.entites.Departamento;
import model.entites.Funcionarios;

public interface FuncionariosDao {
	
	void insert (Funcionarios obj);
	void update (Funcionarios obj);
	void deleteById (Integer id);
	Funcionarios findById (Integer id);
	List<Funcionarios> findAll ();
	List<Funcionarios> findByDepartment (Departamento departmento);

}
