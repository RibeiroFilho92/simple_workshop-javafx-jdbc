package graphical_user_interface;

import java.net.URL;
import java.util.ResourceBundle;

import graphical_user_interface.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable {
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
	public void onBtSaveAction() {
		System.out.println("save");
	}
	@FXML
	public void onBtCancelAction() {
		System.out.println("Cancel");
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		initialixaNodes();
	}
	
	private void initialixaNodes() {
		Constraints.setTextFieldInteger(txtID);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

}
