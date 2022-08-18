package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VendaFormController implements Initializable{
	
	private Long numeroVendas;
	
	@FXML
	private Label lbCliente;
	
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
	
	public Long getNumeroVendas() {
		return numeroVendas;
	}

	public void setNumeroVendas(Long numeroVendas) {
		this.numeroVendas = numeroVendas;
	}

	@FXML
	public void onBtnPesquisarClienteAction() {
		System.out.println(this.numeroVendas);
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
	
	public void initializeFormVendas() {
		lbCliente.setText("Conclusão de Venda nº " + this.numeroVendas);
	}
	
}
