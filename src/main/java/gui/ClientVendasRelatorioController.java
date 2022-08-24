package gui;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pdv.model.entities.Cliente;
import pdv.model.entities.Item;
import pdv.model.entities.Vendas;
import pdv.model.entities.enums.VendaStatus;
import pdv.model.services.ClienteService;
import pdv.model.services.VendasService;

public class ClientVendasRelatorioController implements Initializable, DataChangeListener{
	
	private VendasService service;
	
	private Cliente cliente;
	
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
	
	private ObservableList<Vendas> obsList;
	
	public void setService(VendasService service) {
		this.service = service;
	}
	
	@FXML
	public void onBtnPesquisarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		
		createDialogForm("/gui/PesquisarClientVendaList.fxml", parentStage);
	}
	
	@FXML
	public void onBtnRelatorioAction() {
		
		LocalDate dInicial = dataInicial.getValue();
		LocalDate dFinal = dataFinal.getValue();
		
		Instant instant1 = dInicial.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Instant instant2 = dFinal.atStartOfDay(ZoneId.systemDefault()).toInstant();
		
		List<Vendas> list = service.findByRelatorio(cliente.getId(), instant1, instant2);
		
		obsList = FXCollections.observableArrayList(list);
		tableViewVendas.setItems(obsList);
		tableViewVendas.refresh();
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço nullo");
		}

		List<Vendas> lista = service.findAll();
		obsList = FXCollections.observableArrayList(lista);
		tableViewVendas.setItems(obsList);
	}
	
	private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			PesquisarClientVendaListController controller = loader.getController();
			controller.setService(new ClienteService());
			controller.updateTableView();
			controller.subscribeDataChangeListener(this);
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Painel");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(true);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {

		tableColumnIdVendas.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNomeCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		tableColumnDiaVendas.setCellValueFactory(new PropertyValueFactory<>("data"));
		tableColumnTotalVendas.setCellValueFactory(new PropertyValueFactory<>("totalVenda"));
		tableColumnSituacao.setCellValueFactory(new PropertyValueFactory<>("vendaStatus"));

	}

	@Override
	public void onDataChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSelect(Item item) {
		
	}

	@Override
	public void onSelectCliente(Cliente cliente) {
		txtNomeCliente.setText(cliente.getName());
		this.cliente = cliente;
	}
}
