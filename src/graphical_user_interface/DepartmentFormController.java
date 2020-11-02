package graphical_user_interface;

import java.net.URL;
import java.util.ResourceBundle;

import JDBC.DBException;
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
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {
	
	private DepartmentService ds;
	
	private Department entity;
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
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void setDepartmentService(DepartmentService ds) {
		this.ds = ds;
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
			Utils.currenteStage(event).close();
		}
		catch (DBException e) {
			Alerts.showAlert("Error sanving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Department getFormData() {
		
		Department dp = new Department();
		dp.setID(Utils.tryParseToInt(txtID.getText()));
		dp.setName(txtName.getText());
		
		return dp;
	}
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currenteStage(event).close();
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initialixaNodes();
	}
	
	private void initialixaNodes() {
		Constraints.setTextFieldInteger(txtID);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateDepartment() {
		if (entity == null) {
			throw new IllegalStateException("The value of entity is null");
		}
		txtID.setText(String.valueOf(entity.getID()));
		txtName.setText(entity.getName());
	}

}
