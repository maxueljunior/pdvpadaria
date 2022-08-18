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

public class PesquisarClientVendaListController implements Initializable{
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Button btnPesquisar;
	
	@FXML
	private TableView<Cliente> tableViewCliente;
	
	@FXML
	private TableColumn<Cliente, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnName;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnTelefone;
	
	@FXML
	private void onBtnPesquisarAction() {
		System.out.println("Pesquisando...");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}
}
