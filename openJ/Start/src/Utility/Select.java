package Utility;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.MultiInputDialog;

public class Select extends AbstractPlugIn{
	
	public List<Layer> readUserLayerChoice(PlugInContext context) { //return il layer selezionato
		MultiInputDialog mid = new MultiInputDialog( context.getWorkbenchFrame(), this.getName(), true );
		String _rimessa = "Scegli il layer della rimessa";
		String _strade = "Scegli il layer delle strade";
		String _segnalazioni = "Scegli il layer delle segnalazioni";
		mid.addLayerComboBox( _rimessa, null, context.getLayerManager());
		mid.addLayerComboBox( _strade, null, context.getLayerManager());
		mid.addLayerComboBox( _segnalazioni, null, context.getLayerManager());
		mid.setVisible( true );
		if( mid.wasOKPressed() == false ) return null;
		// se arrivo qua, l'utente ha cliccato ok:
		String layer_rimessa = mid.getText( _rimessa );
		String layer_strade = mid.getText( _strade );
		String layer_segnalazioni = mid.getText( _segnalazioni );
		//System.out.println( "readUserLayerChoice= "+layername );
		List<Layer> layerSelezionati = new ArrayList<>();
		layerSelezionati.add(context.getLayerManager().getLayer(layer_rimessa));
		layerSelezionati.add(context.getLayerManager().getLayer(layer_strade));
		layerSelezionati.add(context.getLayerManager().getLayer(layer_segnalazioni));
		return layerSelezionati;
	}
	public List<Layer> readUserLayerChoice1(PlugInContext context) { //return il layer selezionato
		MultiInputDialog mid = new MultiInputDialog( context.getWorkbenchFrame(), this.getName(), true );
		String _rimessa = "Scegli il layer della rimessa";
		mid.addLayerComboBox( _rimessa, null, context.getLayerManager());

		mid.setVisible( true );
		if( mid.wasOKPressed() == false ) return null;
		// se arrivo qua, l'utente ha cliccato ok:
		String layer_rimessa = mid.getText( _rimessa );

		//System.out.println( "readUserLayerChoice= "+layername );
		List<Layer> layerSelezionati = new ArrayList<>();
		layerSelezionati.add(context.getLayerManager().getLayer(layer_rimessa));

		return layerSelezionati;
	}
	public String readUserTableChoice(DbConnect DbManager, PlugInContext context) {
		List<String> scelte=new ArrayList<String>();
		String query="select f_table_name from geometry_columns";
		
		try {
			ResultSet res = DbManager.MyExecute(DbManager.MyConnectStmt(), query);
			while(res.next()) {
				//System.out.println(res.getString(1)); //possib scelte
				scelte.add(res.getString(1));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String nome_tabella = (String)JOptionPane.showInputDialog(
				context.getWorkbenchFrame(),
				"Scegli la tabella",
				"PG Query Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null, scelte.toArray(), "");
		return nome_tabella;
	}
	
	
	
	
	

}
