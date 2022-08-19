package gui;

import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pdv.model.entities.Vendas;
import pdv.model.entities.enums.VendaStatus;

public class ClientVendasRelatorioController implements Initializable{
	
	@FXML
	private TextField txtNomeCliente;
	
	@FXML
	private DatePicker dataInicial;
	
	@FXML
	private DatePicker dataFinal;
	
	@FXML
	private ComboBox<VendaStatus> comboBoxStatus;
	
	@FXML
	private TableView<Vendas> tableViewVendas;
	
	@FXML
	private TableColumn<Vendas, Integer> tableColumnIdVendas;
	
	@FXML
	private TableColumn<Vendas, String> tableColumnNomeCliente;
	
	@FXML
	private TableColumn<Vendas, Double> tableColumnTotalVendas;
	
	@FXML
	private TableColumn<Vendas, Instant> tableColumnDiaVendas;
	
	@FXML
	private TableColumn<Vendas, String> tableColumnSituacao;
	
	@FXML
	private Button btnPesquisar;
	
	@FXML
	private Label lbResultadoPesquisa;
	
	@FXML
	private Button btnRelatorio;
	
	@FXML
	public void onBtnPesquisarAction() {
		System.out.println("pesquisando...");
	}
	
	@FXML
	public void onBtnRelatorioAction() {
		System.out.println("relatorio....");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
}
