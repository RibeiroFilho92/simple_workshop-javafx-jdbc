package graphical_user_interface;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import JDBC.DBException;
import graphical_user_interface.listeners.DataChangeListener;
import graphical_user_interface.util.Alerts;
import graphical_user_interface.util.Constraints;
import graphical_user_interface.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.DepartmentService;
import model.services.SellerService;

public class SellerFormController implements Initializable {

	private SellerService ss;

	private DepartmentService ds;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	private Seller entity;
	@FXML
	private TextField txtID;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtEmail;
	@FXML
	private DatePicker dpBirthDate;
	@FXML
	private TextField txtBaseSalary;
	@FXML
	private ComboBox<Department> comboBoxDepartment;
	@FXML
	private Label labelErrorName;
	@FXML
	private Label labelErrorEmail;
	@FXML
	private Label labelErrorBirthDate;
	@FXML
	private Label labelErrorBaseSalary;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	@FXML
	private ObservableList<Department> obsList;

	public void setSeller(Seller entity) {
		this.entity = entity;
	}

	public void setServices(SellerService ss, DepartmentService ds) {
		this.ss = ss;
		this.ds = ds;
	}

	public void subscribeDataChangeListener(DataChangeListener dcl) {
		dataChangeListeners.add(dcl);
	}

	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("The value of entity is null");
		}
		if (ss == null) {
			throw new IllegalStateException("The value of departmentservise(ds) is null");
		}
		try {
			entity = getFormData();
			ss.saveOrUpdate(entity);
			noritfyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DBException e) {
			Alerts.showAlert("Error sanving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void noritfyDataChangeListeners() {

		for (DataChangeListener dcl : dataChangeListeners) {
			dcl.onDataChange();
		}
	}

	private Seller getFormData() {

		Seller dp = new Seller();
		ValidationException exception = new ValidationException("Error on validation, please verify");

		dp.setID(Utils.tryParseToInt(txtID.getText()));
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Empty fild, please, insert a valid name.");
		}
		dp.setName(txtName.getText());
		
		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addError("email", "Empty fild, please, insert a valid email.");
		}
		dp.setEmail(txtEmail.getText());
		
		if (dpBirthDate.getValue() == null) {
			exception.addError("birthdate", "Empty fild, please, insert a valid date.");
		}
		else {
			Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
			dp.setBirthdate(Date.from(instant));
		}
		
		if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")) {
			exception.addError("baseSalary", "Empty fild, please, insert a valid salary.");
		}
		dp.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));
		
		dp.setDp(comboBoxDepartment.getValue());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return dp;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtID);
		Constraints.setTextFieldMaxLength(txtName, 30);
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		
		initializeComboBoxDepartment();
	}

	public void updateSeller() {
		if (entity == null) {
			throw new IllegalStateException("The value of entity is null");
		}
		txtID.setText(String.valueOf(entity.getID()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
		if (entity.getBirthdate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthdate().toInstant(), ZoneId.systemDefault()));
		}
		if (entity.getDp() == null) {
			comboBoxDepartment.getSelectionModel().selectFirst();
		}
		comboBoxDepartment.setValue(entity.getDp());
	}

	public void loadAssociatedObjects() {
		if (ds == null) {
			throw new IllegalStateException("Null Department Service");
		}
		List<Department> list = ds.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxDepartment.setItems(obsList);
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		labelErrorName.setText(fields.contains("name") ? errors.get("name"): "");
	
		labelErrorEmail.setText(fields.contains("email") ? errors.get("email"): "");
	
		labelErrorBaseSalary.setText(fields.contains("baseSalary") ? errors.get("baseSalary"): "");

		labelErrorBirthDate.setText(fields.contains("birthdate") ? errors.get("birthdate"): "");

	}

	private void initializeComboBoxDepartment() {
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
			@Override
			protected void updateItem(Department item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxDepartment.setCellFactory(factory);
		comboBoxDepartment.setButtonCell(factory.call(null));
	}
}
