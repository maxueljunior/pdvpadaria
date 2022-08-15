package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pdv.model.entities.Cliente;

public class ClientListController implements Initializable{
	
	@FXML
	private TableView<Cliente> tableViewCliente;
	
	@FXML
	private TableColumn<Cliente, Long> tableColumnId;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnNome;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnTelefone;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtTelefone;

	@FXML
	private Button btnSalvar;
	
	@FXML
	private Button btnAtualizar;
	
	@FXML
	private Button btnDeletar;
	
	@FXML
	public void btnSalvarAction() {
		System.out.println("Salvar");
	}
	
	@FXML
	public void btnAtualizarAction() {
		System.out.println("Atualizar");
	}
	
	@FXML
	public void btnDeletarAction() {
		System.out.println("Deletar");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
}
