package Lab02;


import javax.swing.JOptionPane;

import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.MultiInputDialog;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

public class ciaoMondo extends AbstractPlugIn {
	
	@Override
	public void initialize(PlugInContext context) throws Exception {
		// TODO Auto-generated method stub Load
		FeatureInstaller featureInstaller = new FeatureInstaller(context.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
				this, //the plugin to execute on click
				new String[] {"Corso GIS", "Lab02"}, //menu path
				"Hello World Plugin", //name
				false, //checkbox
				null, //icon
				null ); //enable check
	}

	@Override
	public boolean execute(PlugInContext context) {
		// TODO Auto-generated method stub
		String nome="Vincenzo";
		System.out.println(nome);
		/*JOptionPane.showMessageDialog( arg1.getWorkbenchFrame(), 
				"Warning di prova!",
				"Attenzione",
				JOptionPane.WARNING_MESSAGE);*/
		
		MultiInputDialog mid = new MultiInputDialog( context.getWorkbenchFrame(), this.getName(), true );
		String _nomelayer = "Scegli il layer da elaborare";
		mid.addLayerComboBox( _nomelayer, null, context.getLayerManager() );
		mid.setVisible( true );
		if( mid.wasOKPressed() == false ) return false;
		// se arrivo qua, l'utente ha cliccato ok:
		String layername = mid.getText( _nomelayer );
		System.out.println( "Hai selezionato il layer: "+layername );
		return false;
	}
	
}
