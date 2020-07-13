/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller.login;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.cloud.DropBoxAccount;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * FXML Controller class
 */
public class DropBoxUIController implements Initializable {

    // === variables === //
    @FXML
    public Button acceptB;
    @FXML
    public Button cancelB;
	@FXML
	public WebView web;

	/**
	 * Network enabled accounts.
	 */
	private DropBoxAccount dropBoxAccount;

	/**
	 * The login controller.
	 */
	private LoginUIController loginController;

	/**
	 * The stage for this controller.
	 */
	private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dropBoxAccount = new DropBoxAccount();
        // hide webview scrollbars whenever they appear.
        web.getChildrenUnmodifiable().addListener((ListChangeListener<javafx.scene.Node>) change -> {
            Set<javafx.scene.Node> deadSeaScrolls = web.lookupAll(".scroll-bar");
            for (javafx.scene.Node scroll : deadSeaScrolls) scroll.setVisible(false);
        });
        //Reset the user agent. WebView does not work properly otherwise.
        web.getEngine().setUserAgent(null);
        web.getEngine().load(dropBoxAccount.getAuthorizeURL());
    }

	@FXML
	public void cancelEventFired(ActionEvent evt){
		stage.hide();
        web.getEngine().load(null);
		loginController.showStage();
	}

    @FXML
    public void acceptEventFired(ActionEvent evt){
        Document doc = web.getEngine().getDocument();
        NodeList nodeList = doc.getElementsByTagName("INPUT");
        boolean found = false;
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            NamedNodeMap map = node.getAttributes();
            Node node2 = map.getNamedItem("data-token");
            if(node2 != null){
                String code = node2.getNodeValue();
                if (!dropBoxAccount.setAuthorizeCode(code))
                    PopupManager.getPopupManager().showError("Authentication failed.");
                else {
                    stage.hide();
                    KeyboardingMaster.getInstance().startDropboxSync(dropBoxAccount, true);
                }
                found = true;
                break;
            }
        }
        if (!found) PopupManager.getPopupManager().showError("No Access Token Found. Did you click Sign In?");
        else {
            stage.hide();
            web.getEngine().load(null);
        }
    }

    public void setLoginController(LoginUIController loginController){
            this.loginController = loginController;
    }

    public void setStage(Stage stage){
            this.stage = stage;
    }
}
