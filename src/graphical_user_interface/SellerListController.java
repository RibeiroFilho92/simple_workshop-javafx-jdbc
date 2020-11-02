package graphical_user_interface;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import JDBC.DBIntegrityException;
import application.Main;
import graphical_user_interface.listeners.DataChangeListener;
import graphical_user_interface.util.Alerts;
import graphical_user_interface.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {
	
	private SellerService service;
	@FXML
	private TableView<Seller> tableViewSeller;
	@FXML
	private TableColumn<Seller, Integer> tableColumnID;
	@FXML
	private TableColumn<Seller, String> tableColumnEmail;
	@FXML
	private TableColumn<Seller, Date> tableColumnBirthDate;
	@FXML
	private TableColumn<Seller, Double> tableColumnBaseSalary;
	@FXML
	private TableColumn<Seller, String> tableColumnName;
	@FXML
	private TableColumn<Seller, Seller> tableColumnEdit;
	@FXML
	private TableColumn<Seller, Seller> tableColumnRemove;
	@FXML
	private Button btnNew;
	
	private ObservableList<Seller> obsList;
	
	@FXML
	public void onBtnNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Seller dp = new Seller();
		createDialogForm(dp, "/graphical_user_interface/SellerForm.fxml", parentStage);
	}
	
	public void setSellerService(SellerService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
		
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Null service");
		}
		List<Seller> list = service.findAll();
		obsList = FXCollections.observableArrayList(list); 
		tableViewSeller.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}
	
	private void createDialogForm(Seller dp, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			SellerFormController controller = loader.getController();
			controller.setSeller(dp);
			controller.setSellerService(new SellerService());
			controller.subscribeDataChangeListener(this);
			controller.updateSeller();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Insert Seller information");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		catch (IOException e) {
			Alerts.showAlert("IOException", "Error", e.getMessage(), AlertType.ERROR);
		}
	}
	
	@Override
	public void onDataChange() {
		updateTableView();
	}
	
	private void initEditButtons() {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("Edit");
				@Override
				protected void updateItem(Seller dp, boolean empty) {
					super.updateItem(dp, empty);
					if (dp == null) {
						setGraphic(null);
						return;
					}
				setGraphic(button);
				button.setOnAction(
				event -> createDialogForm(dp, "/graphical_user_interface/SellerForm.fxml", Utils.currentStage(event)));
				}
			});
		}
		
	private void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("Remove");
				@Override
				protected void updateItem(Seller dp, boolean empty) {
					super.updateItem(dp, empty);
					if (dp == null) {
						setGraphic(null);
						return;
					}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(dp));
				}

			});
		}
	
	private void removeEntity(Seller dp) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Do you really wanna delete it?");
		
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Null service");
			}
			try {
				service.remover(dp);
				updateTableView();
			}
			catch (DBIntegrityException e) {
				Alerts.showAlert("Error removing Seller", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
}
