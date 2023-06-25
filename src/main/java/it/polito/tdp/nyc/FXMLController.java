package it.polito.tdp.nyc;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.nyc.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="clPeso"
    private TableColumn<?, ?> clPeso; // Value injected by FXMLLoader

    @FXML // fx:id="clV1"
    private TableColumn<?, ?> clV1; // Value injected by FXMLLoader

    @FXML // fx:id="clV2"
    private TableColumn<?, ?> clV2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBorough"
    private ComboBox<String> cmbBorough; // Value injected by FXMLLoader

    @FXML // fx:id="tblArchi"
    private TableView<?> tblArchi; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtProb"
    private TextField txtProb; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAnalisiArchi(ActionEvent event) {
    	this.txtResult.clear();

		this.txtResult.appendText("Il peso medio è di : "+ this.model.pesoMedio()+"\n");
		this.txtResult.appendText("Vi sono in tutto :"+ this.model.analisiArchi().size()+ " archi che hanno peso maggiore del medio\n");
    	for (String s :this.model.analisiArchi()) {
    		this.txtResult.appendText(s);
    	}
    	

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String borough = this.cmbBorough.getValue();
    	if(borough== null) {
    		this.txtResult.setText("Si prega di inserire un borgo");
    		return;
    	}
    	this.model.creaGrafo(borough);
    	this.txtResult.clear();
    	this.txtResult.setText("Grafo creato correttamente\n");
    	this.txtResult.appendText("Vi sono in tutto: "+ this.model.getNVertices()+ " vertici\n");
    	this.txtResult.appendText("Vi sono in tutto: "+ this.model.getNEdges()+ " archi\n");
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	double prob = Double.parseDouble(this.txtProb.getText());
    	int duration= Integer.parseInt(this.txtDurata.getText());
    	
    	if(prob<0.2|| prob>0.9 ) {
    		if(duration==0) {
    		 this.txtResult.appendText("Inserire una probabilità compresa tra 0.2 e 0.9 e una durata maggiore di 0");
    		}else {
    		 this.txtResult.appendText("Inserire una probabilità compresa tra 0.2 e 0.9 ");
    		}
    	}else {
	    	List<String> lista = new ArrayList<>(this.model.simulazione(prob, duration));
	    	
	    	for(String s :lista) {
	    		this.txtResult.appendText(s+"\n");
    	}
    	}
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clPeso != null : "fx:id=\"clPeso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clV1 != null : "fx:id=\"clV1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clV2 != null : "fx:id=\"clV2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBorough != null : "fx:id=\"cmbBorough\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblArchi != null : "fx:id=\"tblArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtProb != null : "fx:id=\"txtProb\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbBorough.getItems().addAll(this.model.allBorough());
    	
    }

}
