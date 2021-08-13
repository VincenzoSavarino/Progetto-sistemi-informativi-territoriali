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
	
	public Layer readUserLayerChoice(PlugInContext context) { //return il layer selezionato
		MultiInputDialog mid = new MultiInputDialog( context.getWorkbenchFrame(), this.getName(), true );
		String _nomelayer = "Scegli il layer da elaborare";
		mid.addLayerComboBox( _nomelayer, null, context.getLayerManager());
		mid.setVisible( true );
		if( mid.wasOKPressed() == false ) return null;
		// se arrivo qua, l'utente ha cliccato ok:
		String layername = mid.getText( _nomelayer );
		//System.out.println( "readUserLayerChoice= "+layername );
		Layer layerSelezionato = context.getLayerManager().getLayer(layername);
		return layerSelezionato;
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
