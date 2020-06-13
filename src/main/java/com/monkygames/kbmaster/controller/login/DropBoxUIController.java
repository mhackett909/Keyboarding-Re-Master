/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.login;

import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.account.DropBoxAccount;
import com.monkygames.kbmaster.util.thread.DropboxSyncTask;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
	private DropBoxAccount cloudAccount;

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
		// open for testing html to parse it
		//KeyboardingMaster.gotoWeb(cloudAccount.getAuthorizeURL());
		// so that stages can hide without being terminated.
		//Platform.setImplicitExit(false);
    }

	public void initializeWeb(){
		// setup dropbox
		cloudAccount = new DropBoxAccount();
	   // hide webview scrollbars whenever they appear.
		final WebView webb = web;
		webb.getChildrenUnmodifiable().addListener(new ListChangeListener<javafx.scene.Node>() {
		  @Override public void onChanged(Change<? extends javafx.scene.Node> change) {
			Set<javafx.scene.Node> deadSeaScrolls = webb.lookupAll(".scroll-bar");
			for (javafx.scene.Node scroll : deadSeaScrolls) {
			  scroll.setVisible(false);
			}
		  }
		});

		web.getEngine().load(cloudAccount.getAuthorizeURL());

	}
	@FXML
	public void cancelEventFired(ActionEvent evt){
		stage.hide();
		loginController.showStage();
	}

    @FXML
    public void acceptEventFired(ActionEvent evt){
        Document doc = web.getEngine().getDocument();
        /*
        try {
            printDocument(web.getEngine().getDocument(),System.out);
        } catch (Exception ex) { }
        */
        NodeList nodeList = doc.getElementsByTagName("INPUT");
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            NamedNodeMap map = node.getAttributes();
            Node node2 = map.getNamedItem("data-token");
            if(node2 != null){
                String code = node2.getNodeValue();
                cloudAccount.setAuthorizeCode(code);
                stage.hide();
                // create a task
                KeyboardingMaster kbmaster = KeyboardingMaster.getInstance();
                kbmaster.startDropboxSync(cloudAccount,true);
                //loginController.showDeviceMenuFromLogin(cloudAccount, true);
                // TODO if not found, show pop error
                break;
            }
        }

    }

    public void setLoginController(LoginUIController loginController){
            this.loginController = loginController;
    }

    public void setStage(Stage stage){
            this.stage = stage;
    }

    /**
     * Prints a dom document to screen.
     * @param doc
     * @param out
     * @throws IOException
     * @throws TransformerException 
     */
    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc),
            new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }
}
