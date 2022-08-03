package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pdv.model.entities.Item;
import pdv.model.entities.Vendas;
import pdv.model.services.VendaItemService;
import pdv.model.services.VendasService;

public class VendasListController implements Initializable, DataChangeListener {

	private VendasService service;

	private VendaItemService serviceItem;

	@FXML
	private Label lbVenda;

	@FXML
	private TextField txtIdProduto;

	@FXML
	private TextField txtDescricao;

	@FXML
	private TextField txtPreco;

	@FXML
	private TextField txtQuantidade;

	@FXML
	private TextField txtValorTotal;

	@FXML
	private Button btnAdicionarItem;

	@FXML
	private Button btnSair;

	@FXML
	private Button btnConcluir;

	@FXML
	private Button btnPesquisar;

	public void setService(VendasService service) {
		this.service = service;
	}

	public void setServiceItem(VendaItemService serviceItem) {
		this.serviceItem = serviceItem;
	}

	public void btnAddItemAction() {
		System.out.println("Adicionando..");
	}

	public void btnConcluir() {
		System.out.println("Concluindo..");
	}

	public void btnSair() {
		System.out.println("Saindo..");
	}

	public void btnPesquisar() {
		System.out.println("Pesquisando..");
	}

	public void EnterIdProduto(KeyEvent event) {

		if (event.getCode() == KeyCode.ENTER) {
			Item i = new Item();

			i = serviceItem.findItemById(Utils.tryParseToInt(txtIdProduto.getText()));
			if (i == null) {
				Alerts.showAlert("Codigo Produto não encontrado!", null, "Codigo não encontrado", AlertType.ERROR);
			} else {
				txtDescricao.setText(i.getName());
				txtPreco.setText(String.valueOf(i.getPreco()));
				txtQuantidade.setText("1");
			}
		}
	}

	@Override
	public void onDataChanged() {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("SERVIÇO NULLO");
		}
		if (serviceItem == null) {
			throw new IllegalStateException("SERVIÇO NULLO");
		}
		Vendas venda = new Vendas();
		service.saveOrUpdate(venda);
		lbVenda.setText("Nº da Venda: " + String.valueOf(venda.getId()));
	}
}
