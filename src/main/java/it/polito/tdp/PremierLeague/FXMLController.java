/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacente;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	Double goal = null ;
    	try {
    		goal = Double.parseDouble(this.txtGoals.getText()) ;
    	}catch (NumberFormatException nfe) {
    		this.txtResult.setText("inserire un numero di Goal valido!\n") ;
    		return ;
    	}
    	this.model.creaGrafo(goal) ;
    	this.txtResult.setText(this.model.infoGrafo());
    }

    @FXML
    void doDreamTeam(ActionEvent event) {

    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	if(this.model.isCreateGRaf()) {
    		List<Player> best = this.model.getBest();
    		this.txtResult.appendText("\nIl numero di best é:" + best.size());
    		for(Player p : best ) {
    			this.txtResult.appendText("\nI battututi da :" + p.getName() + "sono: \n");
    			List<Adiacente> battuti = this.model.getBattutti(p);
    			for(Adiacente scarso : battuti) {
    				this.txtResult.appendText(scarso.stampaScarso());
    			}
    		}
    	}else {
    		this.txtResult.setText("CREARE UN GRAFO!");
    		return ;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
