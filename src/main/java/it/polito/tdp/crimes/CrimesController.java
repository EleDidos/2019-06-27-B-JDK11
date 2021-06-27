/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Arco;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Arco> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    
    private List <Arco> archiMax;
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	Integer mese;
    	String categoria;
    	
    	try {
    		mese=boxMese.getValue();
    		categoria=boxCategoria.getValue();
    		if(mese==null || categoria==null) {
    			txtResult.setText("Scegli un mese e una categoria");
    			return;
    		}
    	}catch(NumberFormatException nfe) {
    		txtResult.setText("Scegli un mese e una categoria");
    		return;
    	}catch(NullPointerException npe) {
    		txtResult.setText("Scegli un mese e una categoria");
    		return;
    	}
    	
    	model.creaGrafo(mese,categoria);
    	txtResult.appendText("Caratteristiche del grafo:\n#VERTICI = "+model.getNVertici()+"\n#ARCHI = "+model.getNArchi());
    	
    	txtResult.appendText("\n\nGli ARCHI con un peso maggiore della media sono:\n");
    	archiMax = model.getArchiMax();
    	for(Arco a: archiMax )
    		txtResult.appendText(a+"\n");
    	
    	boxArco.getItems().addAll(archiMax);
    }
    
    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	//CONTROLLA SE ESISTE GRAFO
    	if(model.getGraph()==null) {
    		txtResult.setText("Devi prima creare il grafo!");
    		return;
    	}
    	Arco scelto;
    	try {
    		scelto=boxArco.getValue();
    		
    		if(scelto==null) {
    			txtResult.setText("Scegli un arco");
    			return;
    		}
    	}catch(NullPointerException npe) {
    		txtResult.setText("Scegli un arco");
    		return;
    	}
    	String partenza = scelto.getR1();
    	String arrivo = scelto.getR2();
    	
    	List <String> cammino = model.trovaPercorso (partenza,arrivo);
    	txtResult.appendText("\n\nIl PERCORSO con il max n° di passi è:\n");
    	for(String s: cammino)
    		txtResult.appendText(s+"\n");
    }
    
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxCategoria.getItems().addAll(model.getCategories());
    	boxMese.getItems().addAll(model.getMonths());
    }
}
