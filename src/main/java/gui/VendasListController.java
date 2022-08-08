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
import javafx.scene.input.MouseEvent;
import pdv.model.entities.Item;
import pdv.model.entities.VendaItem;
import pdv.model.entities.Vendas;
import pdv.model.services.VendaItemService;
import pdv.model.services.VendasService;

public class VendasListController implements Initializable {

	private VendasService service;

	private VendaItemService serviceItem;

	private Vendas vendas = new Vendas();

	private Double soma = 0.00;

	private Double somaAux = 0.00;

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

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnAtualizar;

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
		list.add(venda);
		updateTableView();

		String aux = String.valueOf(venda.getQntPedido());
		Double quantidade = Utils.tryParseToDouble(aux);
		soma = soma + (venda.getPreco() * quantidade);
		lbTotalVenda.setText("R$ " + String.valueOf(soma));
	}

	public void btnAtualizarAction() {

		VendaItem venda = new VendaItem();
		System.out.println(somaAux);

		venda.setItem(serviceItem.findItemById(Utils.tryParseToInt(txtIdProduto.getText())));
		venda.setVendas(vendas);
		venda.setQntPedido(Utils.tryParseToInt(txtQuantidade.getText()));
		venda.setPreco(Utils.tryParseToDouble(txtPreco.getText()));
		venda.setTotal(Utils.tryParseToDouble(txtPreco.getText()) * venda.getQntPedido());
		venda.setDescricao(txtDescricao.getText());

		String id = txtIdProduto.getText();
		Integer qnt = Utils.tryParseToInt(txtQuantidade.getText());
		String aux = String.valueOf(qnt);
		Double somaTotal = Utils.tryParseToDouble(aux) * Utils.tryParseToDouble(txtPreco.getText());

		if (somaTotal > somaAux) {
			soma = soma + (somaTotal - somaAux);
		} else if (somaTotal < somaAux) {
			soma = soma - (somaAux - somaTotal);
		} else if (somaTotal == somaAux) {
			Alerts.showAlert("Quantidade Igual", null, "A quantidade está igual a anterior!", AlertType.INFORMATION);
		}

		for (int i = 0; i < list.size(); i++) {
			if (id.equals(list.get(i).getItem().toString())) {
				serviceItem.saveOrUpdate(venda);
				list.get(i).setQntPedido(qnt);
				list.get(i).setTotal(somaTotal);
				updateTableView();
				tableViewVendaItem.refresh();
			}
		}
		lbTotalVenda.setText("R$ " + String.valueOf(soma));
	}

	public void btnDeleteAction() {

		VendaItem venda = new VendaItem();

		venda.setItem(serviceItem.findItemById(Utils.tryParseToInt(txtIdProduto.getText())));
		venda.setVendas(vendas);
		venda.setQntPedido(Utils.tryParseToInt(txtQuantidade.getText()));
		venda.setPreco(Utils.tryParseToDouble(txtPreco.getText()));
		venda.setTotal(Utils.tryParseToDouble(txtPreco.getText()) * venda.getQntPedido());
		venda.setDescricao(txtDescricao.getText());

		String id = txtIdProduto.getText();
		Integer qnt = Utils.tryParseToInt(txtQuantidade.getText());
		String aux = String.valueOf(qnt);
		
		for (int i = 0; i < list.size(); i++) {
			if (id.equals(list.get(i).getItem().toString())) {
				serviceItem.remove(venda, vendas);
				list.remove(i);
				updateTableView();
				tableViewVendaItem.refresh();
			}
		}
		
		soma = soma - somaAux;
		
		lbTotalVenda.setText("R$ " + String.valueOf(soma));

	}

	public void btnConcluirAction() {
		System.out.println("Concluindo..");
	}

	public void btnSairAction() {
		System.out.println("Saindo..");
	}

	public void btnPesquisarAction() {
		System.out.println("Pesquisando..");
	}

	public void itensVendasClicked(MouseEvent e) {

		int i = tableViewVendaItem.getSelectionModel().getSelectedIndex();

		somaAux = 0.00;

		VendaItem v = (VendaItem) tableViewVendaItem.getItems().get(i);

		txtIdProduto.setText(String.valueOf(v.getItem().getId()));
		txtDescricao.setText(v.getDescricao());
		txtPreco.setText(String.valueOf(v.getPreco()));
		txtQuantidade.setText(String.valueOf(v.getQntPedido()));

		somaAux = Utils.tryParseToDouble(txtPreco.getText()) * Utils.tryParseToInt(txtQuantidade.getText());

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
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	public void updateTableView() {
		obsList = FXCollections.observableArrayList(list);
		tableViewVendaItem.setItems(obsList);
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
