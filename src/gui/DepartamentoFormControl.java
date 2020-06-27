package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListeners;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entites.Departamento;
import model.exception.ValidationException;
import model.service.DepartamentoService;

public class DepartamentoFormControl implements Initializable{
	
	
	private Departamento departamento;
	
	private DepartamentoService service;
	
	private List<DataChangeListeners> dataChangeListeners = new ArrayList<>();
	
	
	
	@FXML
	private TextField txtId;	
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtFilial;
	@FXML
	private Label labelStatus;
	@FXML 
	private Label labelSucess;
	@FXML
	private Button btInserir;
	@FXML
	private Button btFechar;
	
	
	
	public void subscribleDataChangeListeners(DataChangeListeners listner) {
		dataChangeListeners.add(listner);
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public void setDepartamentoService(DepartamentoService service) {
		this.service = service;
	}
	
	
	public void onInserirAction() {
		
		if (departamento == null) {
			throw new IllegalStateException("departamento was null!");
		}
		if (service == null) {
			throw new IllegalStateException("serviço was null!");
		}
		try {
			departamento = getFormDate();
			service.saveOrUpdate(departamento);
			notifyChangeListeners();
		
		}
		catch (ValidationException e){
			setErrorMessage(e.getErros());
			
		}
		catch(DbException e) {
			Alerts.showAlert("Error", "Erro ao salvar objeto - Insert button in class DepartamentoFormControl", e.getMessage(), AlertType.ERROR);
		}
		
		txtNome.clear();
		txtFilial.clear();
	}
	
	//envia um "alerta" quando houver mudança nos objetos contidos 
	private void notifyChangeListeners() {
		for (DataChangeListeners listeners : dataChangeListeners) {
			listeners.onDataChanged();
		}
		
	}

	private Departamento getFormDate() {
		
		Departamento obj = new Departamento();
		
		ValidationException exception = new ValidationException("Validation exception! ");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "Nome não pode ser nulo! ");
			}
			obj.setNome(txtNome.getText());
		if(txtFilial.getText() == null || txtFilial.getText().trim().equals("")) {
			exception.addError("filial", "Filial não pode ser nulo! ");
			} 
			obj.setFilial(txtFilial.getText());
		if (exception.getErros().size() > 0) {
			throw exception;
			}
		
		return obj;
	}

	
	
	public void onFecharAction (ActionEvent event) {
		Utils.currentStage(event).close();;
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNode();
	}
	
	private void initializeNode() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
		Constraints.setTextFieldMaxLength(txtFilial, 30);
	}
	
	
	public void updateDepartamento () {
		if (departamento == null) {
			throw new IllegalStateException("Departamento está nulo!");
		}
		txtId.setText(String.valueOf(departamento.getId()));
		txtNome.setText(departamento.getNome());
		txtFilial.setText(departamento.getFilial());
	}

	private void setErrorMessage (Map<String, String> errors) {
		Set<String> fields = errors.keySet(); 
		
		if (fields.contains("nome")) {
			labelStatus.setText(errors.get("nome"));
		}
		if (fields.contains("filial")) {
			labelStatus.setText(errors.get("filial"));
		}
	}
	
}
