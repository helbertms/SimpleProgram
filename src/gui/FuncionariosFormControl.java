package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListeners;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entites.Departamento;
import model.entites.Funcionarios;
import model.exception.ValidationException;
import model.service.DepartamentoService;
import model.service.FuncionariosService;

public class FuncionariosFormControl implements Initializable {

	private Funcionarios funcionarios;
	private DepartamentoService departamentoService;
	private FuncionariosService service;

	private List<DataChangeListeners> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtEmail;
	@FXML
	private DatePicker dpBirthDate;
	@FXML
	private TextField txtBaseSalary;
	@FXML
	private ComboBox<Departamento> cbDepartamento;
	@FXML
	private Label labelError;
	@FXML
	private Label labelErrorEmail;
	@FXML
	private Label labelBirthDate;
	@FXML
	private Label labelBaseSalary;
	@FXML
	private Label labelSucess;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btFechar;

	private ObservableList<Departamento> obsLista;

	public void subscribleDataChangeListeners(DataChangeListeners listner) {
		dataChangeListeners.add(listner);
	}

	public void setFuncionarios(Funcionarios funcionarios) {
		this.funcionarios = funcionarios;
	}

	public void setServices(FuncionariosService service, DepartamentoService departamentoService) {
		this.service = service;
		this.departamentoService = departamentoService;
	}

	public void onSalvarAction() {

		if (funcionarios == null) {
			throw new IllegalStateException("funcionarios was null!");
		}
		if (service == null) {
			throw new IllegalStateException("serviço was null!");
		}
		try {
			funcionarios = getFormDate();
			service.saveOrUpdate(funcionarios);
			notifyChangeListeners();

		} catch (ValidationException e) {
			setErrorMessage(e.getErros());

		} catch (DbException e) {
			Alerts.showAlert("Error", "Erro ao salvar objeto - Insert button in class FuncionariosFormControl",
					e.getMessage(), AlertType.ERROR);
		}

		txtNome.clear();
		txtEmail.clear();
		txtBaseSalary.clear();

	}

	// envia um "alerta" quando houver mudança nos objetos contidos
	private void notifyChangeListeners() {
		for (DataChangeListeners listeners : dataChangeListeners) {
			listeners.onDataChanged();
		}

	}

	private Funcionarios getFormDate() {

		Funcionarios obj = new Funcionarios();
		ValidationException exception = new ValidationException("Validation exception! ");
		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("name", "Nome não pode ser nulo! ");
		}
		obj.setName(txtNome.getText());
		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addError("Email", "Email não pode ser nulo! ");
		}
		obj.setEmail(txtEmail.getText());
		if (exception.getErros().size() > 0) {
			throw exception;
		}

		return obj;
	}

	public void onFecharAction(ActionEvent event) {
		Utils.currentStage(event).close();
		;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNode();
	}

	private void initializeNode() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 50);
		Constraints.setTextFieldMaxLength(txtEmail, 50);
		Constraints.setTextFieldDouble(txtBaseSalary);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		initializeComboBoxDepartamento();
	}

	public void updateFuncionarios() {
		if (funcionarios == null) {
			throw new IllegalStateException("Funcionarios está nulo!");
		}
		txtId.setText(String.valueOf(funcionarios.getId()));
		txtNome.setText(funcionarios.getName());
		txtEmail.setText(funcionarios.getEmail());
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f", funcionarios.getBaseSalary()));
		if (funcionarios.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(funcionarios.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
		if (funcionarios.getDepartment() == null) {
			cbDepartamento.getSelectionModel().selectFirst();;
		}
		else {
		cbDepartamento.setValue(funcionarios.getDepartment());
		}
	}

	public void carregarDepartamento() {
		List<Departamento> lista = departamentoService.findAll();
		obsLista = FXCollections.observableArrayList(lista);
		cbDepartamento.setItems(obsLista);
	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("nome")) {
			labelError.setText(errors.get("nome"));
		}
		if (fields.contains("filial")) {
			labelErrorEmail.setText(errors.get("email"));
		}
	}

	private void initializeComboBoxDepartamento() {
		Callback<ListView<Departamento>, ListCell<Departamento>> factory = lv -> new ListCell<Departamento>() {
			@Override
			protected void updateItem(Departamento item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		cbDepartamento.setCellFactory(factory);
		cbDepartamento.setButtonCell(factory.call(null));
	}

}
