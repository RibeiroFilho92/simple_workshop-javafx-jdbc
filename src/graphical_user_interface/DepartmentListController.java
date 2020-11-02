package graphical_user_interface;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

public class DepartmentListController implements Initializable {
	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private TableColumn<Department, Integer> tableColumnID;
	@FXML
	private TableColumn<Department, String> tableColumnName;
	@FXML
	private Button btnNew;
	
	@FXML
	public void onBtnNewAction() {
		System.out.println("Button");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
		
	}

	
}
