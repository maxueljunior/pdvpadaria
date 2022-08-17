package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class VendaFormController implements Initializable{
	
	@FXML
	private Button btnPesquisarCliente;
	
	@FXML
	private Button btnVendaSemEmissao;
	
	@FXML
	private Button btnVendaComEmissao;
	
	@FXML
	private TextField txtNomeCliente;
	
	@FXML
	private TextField txtTelefoneCliente;
	
	@FXML
	private TextField txtCpfCliente;
	
	@FXML
	public void onBtnPesquisarClienteAction() {
		System.out.println("pesquisar cliente");
	}
	
	@FXML
	public void onBtnVendaSemEmissaoAction() {
		System.out.println("venda sem emissao de nfc-e");
	}
	
	@FXML
	public void onBtnVendaComEmissaoAction() {
		System.out.println("venda com emissão de nfc-e");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
}
