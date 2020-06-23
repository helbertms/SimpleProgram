package model.service;

import java.util.ArrayList;
import java.util.List;

import model.entites.Departamento;

public class DepartamentoService {
	
	public List<Departamento> findAll (){
		
		List<Departamento> list = new ArrayList<>();
		list.add(new Departamento(1, "Departamento 1", "Filial X"));
		list.add(new Departamento(2, "Departamento 2", "Filial X"));
		list.add(new Departamento(3, "Departamento 3", "Filial X"));
		list.add(new Departamento(4, "Departamento 4", "Filial X"));
		
		return list;
	}
	

}
