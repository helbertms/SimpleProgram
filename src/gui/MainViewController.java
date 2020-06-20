package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
	
	
	
	// Métodos Inicialize da interface INICIALIZEBLE ----------------------------------------
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
		
	}

}
