package graphical_user_interface;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import JDBC.DBException;
import graphical_user_interface.listeners.DataChangeListener;
import graphical_user_interface.util.Alerts;
import graphical_user_interface.util.Constraints;
import graphical_user_interface.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {
	
	private SellerService ds;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	private Seller entity;
	@FXML
	private TextField txtID;
	@FXML
	private TextField txtName;
	@FXML
	private Label labelErrorName;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	@FXML
	
	public void setSeller(Seller entity) {
		this.entity = entity;
	}
	
	public void setSellerService(SellerService ds) {
		this.ds = ds;
	}
	
	public void subscribeDataChangeListener(DataChangeListener dcl) {
		dataChangeListeners.add(dcl);
	}
	
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("The value of entity is null");
		}
		if (ds == null) {
			throw new IllegalStateException("The value of departmentservise(ds) is null");
		}
		try {
			entity = getFormData();
			ds.saveOrUpdate(entity);
			noritfyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DBException e) {
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
			exception.addError("name", "Empty fild, please, insert valid data.");
		}
		dp.setName(txtName.getText());
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
		initialixaNodes();
	}
	
	private void initialixaNodes() {
		Constraints.setTextFieldInteger(txtID);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateSeller() {
		if (entity == null) {
			throw new IllegalStateException("The value of entity is null");
		}
		txtID.setText(String.valueOf(entity.getID()));
		txtName.setText(entity.getName());
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
		
	}

}
