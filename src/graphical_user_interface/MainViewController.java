package graphical_user_interface;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem munuItemSeller;
	
	@FXML
	private MenuItem munuItemDepartment;
	
	@FXML
	private MenuItem munuItemAbout;	
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("Init");
	}
	@FXML
	public void onMenuItemDepartmentAction() {
		System.out.println("Init");
	}
	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("Init");
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

	
}
