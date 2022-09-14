package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import pdv.application.Program;
import pdv.model.entities.Cliente;
import pdv.model.entities.Item;
import pdv.model.entities.Vendas;
import pdv.model.entities.enums.VendaStatus;
import pdv.model.services.ClienteService;
import pdv.model.services.VendasService;

public class ClientVendasRelatorioController implements Initializable, DataChangeListener {

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

	@FXML
	private Button btnExcel;

	private ObservableList<Vendas> obsList;

	private ObservableList<VendaStatus> obsListStatus;

	public void setService(VendasService service) {
		this.service = service;
	}

	@FXML
	public void onBtnPesquisarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);

		createDialogForm("/gui/PesquisarClientVendaList.fxml", parentStage);
	}

	@FXML
	public void onBtnExcelAction() {

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("Relatório de Vendas");

		HSSFRow header = sheet.createRow(0);

		header.createCell(0).setCellValue("ID Vendas");
		header.createCell(1).setCellValue("Nome Cliente");
		header.createCell(2).setCellValue("Total da Venda (R$)");
		header.createCell(3).setCellValue("Dia da Venda (formato americano)");
		header.createCell(4).setCellValue("Situação da Venda");

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);

		sheet.setColumnWidth(1, 256 * 25);

		try {
			int i = 1;

			for (Vendas lista : obsList) {
				HSSFRow row = sheet.createRow(i);
				
				row.createCell(0).setCellValue(lista.getId());
				
				if(lista.getCliente() == null) {
					row.createCell(1).setCellValue("N/I");
				}else {
					row.createCell(1).setCellValue(lista.getCliente().getName());
				}
				
				if(lista.getTotalVenda() == null) {
					row.createCell(2).setCellValue("N/I");
				}else {
					row.createCell(2).setCellValue(lista.getTotalVenda());
				}
				
				row.createCell(3).setCellValue(lista.getData().toString());
				row.createCell(4).setCellValue(lista.getVendaStatus().toString());
				i++;
			}

			try {

				//FileOutputStream fileOut = new FileOutputStream("Relatorio.xls");
				
				File desktopDir = new File(System.getProperty("user.home"), "Desktop");
				System.out.println(desktopDir.getPath() + " " + desktopDir.exists());
				
				FileOutputStream fileOut =  new FileOutputStream(new File(desktopDir, "Relatorio.xls"));
				
				try {
					workbook.write(fileOut);
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {

				e.printStackTrace();

			}
		} catch (NullPointerException e) {
			Alerts.showAlert("Relatório Vazio", "Relatório está vazio, sendo assim não é possivel gerar o excel", e.getMessage(), AlertType.ERROR);
		}
		
		Alerts.showAlert("Sucesso ao transferir Relatório", "O Relatório foi gerado com sucesso e está na sua area de trabalho (Desktop)!!", null, AlertType.INFORMATION);

	}

	@FXML
	public void onBtnRelatorioAction() {

		try {
			LocalDate dInicial;
			LocalDate dFinal;

			Instant instant1;
			Instant instant2;

			if (dataInicial.getValue() != null && dataFinal.getValue() != null
					&& comboBoxStatus.getSelectionModel().getSelectedIndex() == -1 && cliente == null) {

				dInicial = dataInicial.getValue();
				dFinal = dataFinal.getValue();

				instant1 = dInicial.atStartOfDay(ZoneId.systemDefault()).toInstant();
				instant2 = dFinal.atStartOfDay(ZoneId.systemDefault()).toInstant();

				List<Vendas> list = service.findByRelatorio(instant1, instant2);

				obsList = FXCollections.observableArrayList(list);
				tableViewVendas.setItems(obsList);
				tableViewVendas.refresh();
				System.out.println("primeiro IF");

			} else if (dataInicial.getValue() != null && dataFinal.getValue() != null
					&& comboBoxStatus.getSelectionModel().getSelectedIndex() != -1 && cliente == null) {

				dInicial = dataInicial.getValue();
				dFinal = dataFinal.getValue();

				instant1 = dInicial.atStartOfDay(ZoneId.systemDefault()).toInstant();
				instant2 = dFinal.atStartOfDay(ZoneId.systemDefault()).toInstant();

				List<Vendas> list = service.findByRelatorio(instant1, instant2,
						comboBoxStatus.getSelectionModel().getSelectedIndex() + 1);

				obsList = FXCollections.observableArrayList(list);
				tableViewVendas.setItems(obsList);
				tableViewVendas.refresh();
				System.out.println("segundo IF");

			} else if (dataInicial.getValue() != null && dataFinal.getValue() != null && cliente != null
					&& comboBoxStatus.getSelectionModel().getSelectedIndex() != -1) {

				dInicial = dataInicial.getValue();
				dFinal = dataFinal.getValue();

				instant1 = dInicial.atStartOfDay(ZoneId.systemDefault()).toInstant();
				instant2 = dFinal.atStartOfDay(ZoneId.systemDefault()).toInstant();

				List<Vendas> list = service.findByRelatorio(cliente.getId(), instant1, instant2,
						comboBoxStatus.getSelectionModel().getSelectedIndex() + 1);

				obsList = FXCollections.observableArrayList(list);
				tableViewVendas.setItems(obsList);
				tableViewVendas.refresh();
				System.out.println("terceiro IF");

			} else if (dataInicial.getValue() != null && dataFinal.getValue() != null && cliente != null
					&& comboBoxStatus.getSelectionModel().getSelectedIndex() == -1) {

				dInicial = dataInicial.getValue();
				dFinal = dataFinal.getValue();

				instant1 = dInicial.atStartOfDay(ZoneId.systemDefault()).toInstant();
				instant2 = dFinal.atStartOfDay(ZoneId.systemDefault()).toInstant();

				List<Vendas> list = service.findByRelatorio(cliente.getId(), instant1, instant2);

				obsList = FXCollections.observableArrayList(list);
				tableViewVendas.setItems(obsList);
				tableViewVendas.refresh();
				System.out.println("quarto IF");

			} else if (cliente != null && dataInicial.getValue() == null && dataFinal.getValue() == null
					&& comboBoxStatus.getSelectionModel().getSelectedIndex() == -1) {
				List<Vendas> list = service.findByRelatorio(cliente.getId());

				obsList = FXCollections.observableArrayList(list);
				tableViewVendas.setItems(obsList);
				tableViewVendas.refresh();
				System.out.println("quinto IF");
			} else if (cliente == null && dataInicial.getValue() == null && dataFinal.getValue() == null
					&& comboBoxStatus.getSelectionModel().getSelectedIndex() == -1) {
				List<Vendas> list = service.findAll();

				obsList = FXCollections.observableArrayList(list);
				tableViewVendas.setItems(obsList);
				tableViewVendas.refresh();
				System.out.println("sexto IF");
			}

			cliente = null;
			txtNomeCliente.setText("");
			dataFinal.setValue(null);
			dataInicial.setValue(null);
			comboBoxStatus.getSelectionModel().select(-1);
		} catch (NullPointerException e) {
			Alerts.showAlert("Erro ao buscar relatório", null, e.getMessage(), AlertType.ERROR);
		} catch (RuntimeException e) {
			Alerts.showAlert("Erro ao buscar relatório", null, e.getMessage(), AlertType.ERROR);
		}

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

		Stage stage = (Stage) Program.getMainScene().getWindow();
		tableViewVendas.prefHeightProperty().bind(stage.heightProperty());

		initializeComboBoxDepartment();

	}

	public void loadAssociatedObjects() {
		List<VendaStatus> list = new ArrayList<>();
		list.add(VendaStatus.AGUARDANDO_PAGAMENTO);
		list.add(VendaStatus.PAGO);
		list.add(VendaStatus.CANCELADO);
		obsListStatus = FXCollections.observableArrayList(list);
		comboBoxStatus.setItems(obsListStatus);
	}

	@FXML
	public void pegandoItemPorVendas(KeyEvent event) {
		if (event.getCode() == KeyCode.F1) {
			int i = tableViewVendas.getSelectionModel().getSelectedIndex();

			Vendas v = (Vendas) tableViewVendas.getItems().get(i);
			System.out.println(v.getId());
		}
	}

	private void initializeComboBoxDepartment() {
		Callback<ListView<VendaStatus>, ListCell<VendaStatus>> factory = lv -> new ListCell<VendaStatus>() {
			@Override
			protected void updateItem(VendaStatus item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : String.valueOf(item.name()));
			}
		};
		comboBoxStatus.setCellFactory(factory);
		comboBoxStatus.setButtonCell(factory.call(null));
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
