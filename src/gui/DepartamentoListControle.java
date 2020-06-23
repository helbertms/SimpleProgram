package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entites.Departamento;
import model.service.DepartamentoService;

public class DepartamentoListControle implements Initializable{
	
	//Dependencia da classe DepartamentoService...
	private DepartamentoService service;

	@FXML
	private TableView<Departamento> tableViewDepartamento;
	@FXML
	private TableColumn<Departamento, Integer> tableColumnId;
	@FXML
	private TableColumn<Departamento, String> tableColumnNome;
	@FXML
	private TableColumn<Departamento, String> tableColumnFilial;
	@FXML
	private Button btNovo;
	
	private ObservableList<Departamento> obsList;
	
	
	
	public void onBtNovoAction() {
		System.out.println("onBtNovoAction");
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	public void setDepartamentoService (DepartamentoService service) {
		this.service = service;
	}


	private void initializeNodes() {
		
		//Código para iniciar de forma correta o "comportamento" das colunas.
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnFilial.setCellValueFactory(new PropertyValueFactory<>("filial"));
		//código para que a tableView acompanhe o tamanho da tela 
		Stage stage = (Stage)Main.getMainScene().getWindow();
		tableViewDepartamento.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço está nulo!");
		}
		
		List<Departamento> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartamento.setItems(obsList);
	}
}
