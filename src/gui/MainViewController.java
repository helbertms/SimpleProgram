package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.service.DepartamentoService;
import model.service.FuncionariosService;

public class MainViewController implements Initializable{

	// Atributos dos ítens de menu ----------------------------------------
	
	@FXML
	private MenuItem menuItemCadastroEmpresa;
	@FXML
	private MenuItem menuItemCadastroFilial;
	@FXML
	private MenuItem menuItemCadastroDepartamento;
	
	
	@FXML
	private MenuItem menuItemFuncionariosIncluir;
	@FXML
	private MenuItem menuItemFuncionariosConfigurar;
	@FXML
	private MenuItem menuItemFuncionariosExcluir;
	
	
	@FXML
	private MenuItem menuItemProdutoIncuir;
	@FXML
	private MenuItem menuItemProdutoEditar;
	@FXML
	private MenuItem menuItemProdutoExcluir;
	
		
	@FXML
	private MenuItem menuItemAjudaSobre;
	@FXML
	private MenuItem menuItemAjudaSuporte;
	
	// Métodos e ou controle de eventos ----------------------------------------
	
	
	@FXML
	public void onMenuItemCadastroEmpresaAction() {
		System.out.println("onMenuItemCadastroEmpresaAction");
	}
	@FXML
	public void onMenuItemCadastroFilialAction() {
		System.out.println("onMenuItemCadastroFilialAction");
	}
	@FXML
	public void onMenuItemCadastroDepartamentoAction() {
		loadView("/gui/DepartamentoList.fxml", (DepartamentoListControle controle)->{
			controle.setDepartamentoService (new DepartamentoService());
			controle.updateTableView();
			// Nessa chamada de método há uma AÇÃO DE INICIALIZAÇÃO COMO PARAMETRO	
		});
	}
	
	
	@FXML
	public void onMmenuItemProdutoIncuir() {
		System.out.println("onProdAdd");
	}
	@FXML
	public void onMenuItemProdutoEditar() {
		System.out.println("onProdConf");
	}
	@FXML
	public void onMenuItemProdutoExcluir() {
		System.out.println("onProdDel");
	}
	
	
	@FXML
	public void onMenuItemFuncionariosIncluir() {
		loadView("/gui/FuncionariosList.fxml", (FuncionariosListControle controle)->{
			controle.setFuncionariosService (new FuncionariosService());
			controle.updateTableView();
			// Nessa chamada de método há uma AÇÃO DE INICIALIZAÇÃO COMO PARAMETRO
		});
	}
	@FXML
	public void onMenuItemFuncionariosConfigurar() {
		System.out.println("onUserConf");
	}
	@FXML
	public void onMenuItemFuncionariosExcluir() {
		System.out.println("onUserDel");
	}
	
	
	@FXML
	public void onMenuItemAjudaSobre() {
		loadView("/gui/AjudaSobre.fxml", x->{});
	}
	@FXML
	public void onMenuItemAjudaSuporte() {
		System.out.println("onMenuItemAjudaSuporte");
	}
	
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}
	
	// Método para abrir uma tela por um chamado de metodo
	// Parametrização do CONSUMER <T> passado ao método loadView para que a chamada do método aceite um construtor com inicializador como parametro.
	
	private synchronized <T> void loadView(String absolutName, Consumer<T> metodoInicializaca) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Main.getMainScene();// -1-
			VBox mainVbox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();// -2-
			
			Node mainMenu = mainVbox.getChildren().get(0);// Guarda uma referencia para o menu, pegando o primeiro o "children" na posição 0 
			mainVbox.getChildren().clear(); // aqui eu limpo todos os filhos do meu mainVbox
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());
			
			
			// --- inicializando (inserindo) os dados no VIEW (tabela do Departamento)
			// --- As duas linhas abaixo executam a ação passada como método na chamada da função loadView
			T controle = loader.getController();
			metodoInicializaca.accept(controle);
			
		}
		catch (IOException e){
			Alerts.showAlert("IOException error ", "Error at loader View ", e.getMessage(), AlertType.ERROR);
		}
	}
}

/* 
 * Anotação -1-
 *  Instancia uma "SCENE" da janela principal (MAIN)
 *  Isso foi possível após declarar um ATRIBUTO mainScene da classe Scenee construir o método "getMainScene"
 *
 * Anotação -2-
 * Pegar uma referencia do VBOX da JANELA PRINCIPAL (mainScene instanciado na classe Main), criando uma variável da classe VBOX onde ele 
 * recebe o método getRoot (que pega o PRIMEIRO elemento da View "<ScrollPane") sendo necessário o casting do SCROLLPANE e casting do VBox. 
 */
