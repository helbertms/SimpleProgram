package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class MainViewController implements Initializable{

	// Atributos dos ítens de menu ----------------------------------------
	// Itens do menu de cadastro
	@FXML
	private MenuItem menuItemCadastroEmpresa;
	@FXML
	private MenuItem menuItemCadastroFilial;
	@FXML
	private MenuItem menuItemCadastroDepartamento;
	
	// Itens do menu de usuários
	@FXML
	private MenuItem menuItemUsuariosIncuir;
	@FXML
	private MenuItem menuItemUsuariosConfigurar;
	@FXML
	private MenuItem menuItemUsuariosExcluir;
	
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
	
	// Métodos e ou controle de eventos ----------------------------------------
	
	
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
		System.out.println("onMenuItemCadastroDepartamento");
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
	
	
	// MENU USUARIOS
	@FXML
	public void onMenuItemUsuariosIncuir() {
		System.out.println("onUserAdd");
	}
	@FXML
	public void onMenuItemUsuariosConfigurar() {
		System.out.println("onUserConf");
	}
	@FXML
	public void onMenuItemUsuariosExcluir() {
		System.out.println("onUserDel");
	}
	
	
	// MENUS AJUDA
	
	@FXML
	public void onMenuItemAjudaSobre() {
		loadView("/gui/AjudaSobre.fxml");
	}
	@FXML
	public void onMenuItemAjudaSuporte() {
		System.out.println("onMenuItemAjudaSuporte");
	}
	
	// Métodos Inicialize da interface INICIALIZEBLE ----------------------------------------
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
		
	}
	
	// Método para abrir uma tela por um chamado de metodo
	
	private synchronized void loadView(String absolutName) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Main.getMainScene();// *1
			VBox mainVbox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();// *2
			
			Node mainMenu = mainVbox.getChildren().get(0);// Guarda uma referencia para o menu, pegando o primeiro o "children" na posição 0 
			mainVbox.getChildren().clear(); // aqui eu limpo todos os filhos do meu mainVbox
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());
			
			/* 
			 * 1
			 *  Instancia uma "SCENE" da janela principal (MAIN)
			 *  Isso foi possível após declarar um ATRIBUTO mainScene da classe Scene
			 *  e construir o método "getMainScene"
			 */
			
			/* 
			 * 2
			 * Pegar uma referencia do VBOX da JANELA PRINCIPAL (mainScene instanciado na classe Main), criando uma variável da classe VBOX
			 * onde ele recebe o método getRoot (que pega o PRIMEIRO elemento da View "<ScrollPane") sendo necessário o casting do 
			 * SCROLLPANE e casting do VBox. 
			 */
			
		}
		catch (IOException e){
			Alerts.showAlert("IOException error ", "Error at loader View ", e.getMessage(), AlertType.ERROR);
		}
	}

}
