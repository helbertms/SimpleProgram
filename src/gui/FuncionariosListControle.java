package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListeners;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entites.Funcionarios;
import model.service.DepartamentoService;
import model.service.FuncionariosService;

public class FuncionariosListControle implements Initializable, DataChangeListeners {

	// Dependencia da classe FuncionariosService...
	private FuncionariosService service;

	@FXML
	private TableView<Funcionarios> tableViewFuncionarios;
	@FXML
	private TableColumn<Funcionarios, Integer> tableColumnId;
	@FXML
	private TableColumn<Funcionarios, String> tableColumnNome;
	@FXML
	private TableColumn<Funcionarios, String> tableColumnEmail;
	@FXML
	private TableColumn<Funcionarios, Date> tableColumnBirthDate;
	@FXML
	private TableColumn<Funcionarios, Double> tableColumnSalario;
	@FXML
	private TableColumn<Funcionarios, Funcionarios> tableColumnEDIT;
	@FXML
	private TableColumn<Funcionarios, Funcionarios> tableColumnREMOVE;
	@FXML
	private Button btNovo;
	

	private ObservableList<Funcionarios> obsList;

	public void onBtNovoAction(ActionEvent event) {
		Funcionarios obj = new Funcionarios();
		Stage parentStage = Utils.currentStage(event);
		createFuncionariosForm(obj, "/gui/FuncionariosForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	public void setFuncionariosService(FuncionariosService service) {
		this.service = service;
	}

	private void initializeNodes() {

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id")); 
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		tableColumnSalario.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDouble(tableColumnSalario, 2);
		
		Stage stage = (Stage) Main.getMainScene().getWindow(); // código para que a tableView acompanhe o tamanho da tela
		tableViewFuncionarios.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço está nulo!");
		}

		List<Funcionarios> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewFuncionarios.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createFuncionariosForm(Funcionarios obj, String absolutName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
			Pane pane = loader.load();

			FuncionariosFormControl controller = loader.getController();
			controller.setFuncionarios(obj);
			controller.setServices(new FuncionariosService(), new DepartamentoService());
			controller.carregarDepartamento();//  <--- lista os departamentos no COMBOBOX
			controller.subscribleDataChangeListeners(this);
			controller.updateFuncionarios();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Funcionarios");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("Error", "Error in metod creat departament form", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
		// método que atualiza os dados da tabela quando houver mudança
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Funcionarios, Funcionarios>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Funcionarios obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createFuncionariosForm(obj, "/gui/FuncionariosForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Funcionarios, Funcionarios>() {
			private final Button button = new Button("del");

			@Override
			protected void updateItem(Funcionarios obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeFuncionarios(obj));
			}
		});
	}
	
	
	private void removeFuncionarios(Funcionarios obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
}
