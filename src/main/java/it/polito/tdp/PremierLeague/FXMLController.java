/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.SquadraDifferenza;
import it.polito.tdp.PremierLeague.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {

    	txtResult.clear();
    	Team t = cmbSquadra.getValue();
    	if(t == null) {
    		txtResult.appendText("ERRORE: selezionare una squadra");
    		return;
    	}
    	
    	List<SquadraDifferenza> migliori = model.getMigliori(t);
    	List<SquadraDifferenza> peggiori = model.getPeggiori(t);
    	
    	this.txtResult.appendText("SQUADRE MIGLIORI:\n");
    	for(SquadraDifferenza sd: migliori) {
    		this.txtResult.appendText(sd.toString() + "\n");
    	}
    	
    	this.txtResult.appendText("\nSQUADRE PEGGIORI:\n");
    	for(SquadraDifferenza sd : peggiori) {
    		this.txtResult.appendText(sd.toString() + "\n");
    	}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	this.txtResult.clear();
    	
    	model.creaGrafo();
    	cmbSquadra.getItems().setAll(model.getSquadre());
    	
    	this.txtResult.appendText("Grafo creato\n");
    	this.txtResult.appendText("# VERTICI: "+model.getSizeVertex() + "\n");
    	this.txtResult.appendText("# ARCHI: " + model.getSizeEdge());
    	this.btnClassifica.setDisable(false);
    	this.cmbSquadra.setDisable(false);
    	this.txtN.setDisable(false);
    	this.txtX.setDisable(false);
    	this.btnSimula.setDisable(false);
    	this.btnCreaGrafo.setDisable(true);
    }

    @FXML
    void doSimula(ActionEvent event) {

    	txtResult.clear();
    	
    	if(!isValid()) {
    		return;
    	}
    	
    	int X = Integer.parseInt(txtX.getText());
    	int N = Integer.parseInt(txtN.getText());
    	
    	model.setSim(N, X);
    	
    	txtResult.appendText("NUMERO DI REPORTER MEDI PER PARTITA: " + String.format("%.5g%n",model.getMediaReporter()));
    	txtResult.appendText("\nNUMERO DI PARTITE CON MENO DI " + X + " REPORTER: " + model.getPartiteCritiche());
    }

    private boolean isValid() {
		String N = this.txtN.getText();
		String X = this.txtX.getText();
		boolean check = true;
		
		if(N == null) {
			txtResult.appendText("ERRORE: inserire un valore nel campo N\n");
			check = false;
		}else {
			try {
				Integer.parseInt(N);
			}catch(NumberFormatException nfe) {
				txtResult.appendText("ERRORE: il valore di N deve essere un numero intero\n");
				check = false;
			}
		}
		
		if( X ==  null) {
			txtResult.appendText("ERRORE: inserire un valore nel campo X\n");
			check = false;
		}else {
			try {
				Integer.parseInt(X);
			}catch(NumberFormatException nfe) {
				txtResult.appendText("ERRORE: il valore di X deve essere un numero intero\n");
				check = false;
			}
		}
		return check;
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
