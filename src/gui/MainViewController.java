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

	// Atributos dos �tens de menu ----------------------------------------
	// Itens do menu de cadastro
	@FXML
	private MenuItem menuItemCadastroEmpresa;
	@FXML
	private MenuItem menuItemCadastroFilial;
	@FXML
	private MenuItem menuItemCadastroDepartamento;
	
	// Itens do menu de usu�rios
	@FXML
	private MenuItem menuItemFuncionariosIncluir;
	@FXML
	private MenuItem menuItemFuncionariosConfigurar;
	@FXML
	private MenuItem menuItemFuncionariosExcluir;
	
	// Itens do menu de produtos
	@FXML
	private MenuItem menuItemProdutoIncuir;
	@FXML
	private MenuItem menuItemProdutoEditar;
	@FXML
	private MenuItem menuItemProdutoExcluir;
	
	// Itens do menu ajuda
	
	@FXML
	private MenuItem menuItemAjudaSobre;
	@FXML
	private MenuItem menuItemAjudaSuporte;
	
	// M�todos e ou controle de eventos ----------------------------------------
	
	
	// MENU CADASTROS
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
		// Nessa chamada de m�todo h� uma A��O DE INICIALIZA��O COMO PARAMETRO
		loadView("/gui/DepartamentoList.fxml", (DepartamentoListControle controle)->{
			controle.setDepartamentoService (new DepartamentoService());
			controle.updateTableView();
			
		});
	}
	
	
	// MENU PRODUTOS
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
	
	
	// MENU FUNCIONARIOS
	@FXML
	public void onMenuItemFuncionariosIncluir() {
		loadView("/gui/FuncionariosList.fxml", (FuncionariosListControle controle)->{
			controle.setFuncionariosService (new FuncionariosService());
			controle.updateTableView();
			
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
	
	
	// MENUS AJUDA
	
	@FXML
	public void onMenuItemAjudaSobre() {
		loadView("/gui/AjudaSobre.fxml", x->{});
	}
	@FXML
	public void onMenuItemAjudaSuporte() {
		System.out.println("onMenuItemAjudaSuporte");
	}
	
	// M�todos Inicialize da interface INICIALIZEBLE ----------------------------------------
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}
	
	// M�todo para abrir uma tela por um chamado de metodo
	// Parametriza��o do CONSUMER <T> passado ao m�todo loadView para que a chamada do m�todo aceite um construtor com inicializador como parametro.
	
	private synchronized <T> void loadView(String absolutName, Consumer<T> metodoInicializaca) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Main.getMainScene();// *1
			VBox mainVbox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();// *2
			
			Node mainMenu = mainVbox.getChildren().get(0);// Guarda uma referencia para o menu, pegando o primeiro o "children" na posi��o 0 
			mainVbox.getChildren().clear(); // aqui eu limpo todos os filhos do meu mainVbox
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());
			/* 
			 * 1
			 *  Instancia uma "SCENE" da janela principal (MAIN)
			 *  Isso foi poss�vel ap�s declarar um ATRIBUTO mainScene da classe Scenee construir o m�todo "getMainScene"
			 *
			 * 2
			 * Pegar uma referencia do VBOX da JANELA PRINCIPAL (mainScene instanciado na classe Main), criando uma vari�vel da classe VBOX onde ele 
			 * recebe o m�todo getRoot (que pega o PRIMEIRO elemento da View "<ScrollPane") sendo necess�rio o casting do SCROLLPANE e casting do VBox. 
			 */
			
			// --- inicializando (inserindo) os dados no VIEW (tabela do Departamento)
			// --- As duas linhas abaixo executam a a��o passada como m�todo na chamada da fun��o loadView
			T controle = loader.getController();
			metodoInicializaca.accept(controle);
			
		}
		catch (IOException e){
			Alerts.showAlert("IOException error ", "Error at loader View ", e.getMessage(), AlertType.ERROR);
		}
	}
}
