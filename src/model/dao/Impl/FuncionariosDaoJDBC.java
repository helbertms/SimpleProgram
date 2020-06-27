package model.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.FuncionariosDao;
import model.entites.Departamento;
import model.entites.Funcionarios;

public class FuncionariosDaoJDBC implements FuncionariosDao {

	private Connection conn;

	public FuncionariosDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Funcionarios obj) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO funcionarios " 
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES " 
					+ "(?, ?, ?, ?, ?) ", 
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {

				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Funcionarios obj) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE funcionarios "
					+"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());

			st.executeUpdate(); 
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM funcionarios WHERE Id = ?");
			
			st.setInt(1, id);
			st.executeUpdate();
			
		}
		catch (SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Funcionarios findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT funcionarios.*,department.Name as DepName " 
							+ "FROM funcionarios INNER JOIN department "
							+ "ON funcionarios.DepartmentId = department.Id " 
							+ "WHERE funcionarios.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Departamento dep = instantiateDepartment(rs);
				Funcionarios obj = instantiateFuncionarios(rs, dep);
				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	private Funcionarios instantiateFuncionarios(ResultSet rs, Departamento dep) throws SQLException {
		Funcionarios obj = new Funcionarios();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(new java.util.Date(rs.getTimestamp("BirthDate").getTime()));
		obj.setDepartment(dep);
		return obj;
	}

	private Departamento instantiateDepartment(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setNome(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Funcionarios> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
							"SELECT funcionarios.*,department.Name as DepName " 
							+ "FROM funcionarios INNER JOIN department "
							+ "ON funcionarios.DepartmentId = department.Id " 
							+ "ORDER BY Name");

			rs = st.executeQuery();

			List<Funcionarios> list = new ArrayList<Funcionarios>();
			Map<Integer, Departamento> map = new HashMap<>();

			while (rs.next()) {
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Funcionarios obj = instantiateFuncionarios(rs, dep);
				list.add(obj);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Funcionarios> findByDepartment(Departamento departmento) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
							"SELECT funcionarios.*,department.Name as DepName " 
							+ "FROM funcionarios INNER JOIN department "
							+ "ON funcionarios.DepartmentId = department.Id " 
							+ "WHERE DepartmentId = ? " 
							+ "ORDER BY Name");
			st.setInt(1, departmento.getId());
			rs = st.executeQuery();

			List<Funcionarios> list = new ArrayList<Funcionarios>();
			Map<Integer, Departamento> map = new HashMap<>();

			while (rs.next()) {
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Funcionarios obj = instantiateFuncionarios(rs, dep);
				list.add(obj);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

}
