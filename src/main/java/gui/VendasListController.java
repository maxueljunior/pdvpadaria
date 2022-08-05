package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pdv.model.entities.Item;
import pdv.model.entities.VendaItem;
import pdv.model.entities.Vendas;
import pdv.model.services.VendaItemService;
import pdv.model.services.VendasService;

public class VendasListController implements Initializable, DataChangeListener {

	private VendasService service;

	private VendaItemService serviceItem;

	private Vendas vendas = new Vendas();

	private Double soma = 0.00;

	@FXML
	private TableView<VendaItem> tableViewVendaItem;

	@FXML
	private TableColumn<VendaItem, Integer> tableColumnId;

	@FXML
	private TableColumn<Item, String> tableColumnDescri;

	@FXML
	private TableColumn<VendaItem, Double> tableColumnPreco;

	@FXML
	private TableColumn<VendaItem, Integer> tableColumnQnt;

	@FXML
	private TableColumn<VendaItem, Double> tableColumnTotal;

	@FXML
	private Label lbVenda;

	@FXML
	private Label lbTotalVenda;

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

	private ObservableList<VendaItem> obsList;

	private List<VendaItem> list = new ArrayList<>();

	public void setService(VendasService service) {
		this.service = service;
	}

	public void setServiceItem(VendaItemService serviceItem) {
		this.serviceItem = serviceItem;
	}

	public void btnAddItemAction() {

		VendaItem venda = new VendaItem();
		venda.setItem(serviceItem.findItemById(Utils.tryParseToInt(txtIdProduto.getText())));
		venda.setVendas(vendas);
		venda.setQntPedido(Utils.tryParseToInt(txtQuantidade.getText()));
		venda.setPreco(Utils.tryParseToDouble(txtPreco.getText()));
		venda.setTotal(Utils.tryParseToDouble(txtPreco.getText()) * venda.getQntPedido());
		venda.setDescricao(txtDescricao.getText());
		serviceItem.saveOrUpdate(venda);
		updateTableView(venda);
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

	private void initializeNodes() {

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("item"));
		tableColumnDescri.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
		tableColumnQnt.setCellValueFactory(new PropertyValueFactory<>("qntPedido"));
		tableColumnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

	}

	@Override
	public void onDataChanged() {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	public void updateTableView(VendaItem obj) {
		list.add(obj);
		obsList = FXCollections.observableArrayList(list);
		tableViewVendaItem.setItems(obsList);

		String aux = String.valueOf(obj.getQntPedido());
		Double quantidade = Utils.tryParseToDouble(aux);
		soma = soma + (obj.getPreco() * quantidade);
		lbTotalVenda.setText("R$ " + String.valueOf(soma));
	}

	public void initializeVendas() {
		if (service == null) {
			throw new IllegalStateException("SERVIÇO NULLO");
		}
		if (serviceItem == null) {
			throw new IllegalStateException("SERVIÇO NULLO");
		}
		service.saveOrUpdate(vendas);
		lbVenda.setText("Nº da Venda: " + String.valueOf(vendas.getId()));
		lbTotalVenda.setText("R$ 0.00");
	}
}
