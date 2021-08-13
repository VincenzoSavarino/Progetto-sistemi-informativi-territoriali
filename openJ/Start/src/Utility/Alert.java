package Utility;

import javax.swing.JOptionPane;

import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

public class Alert extends AbstractPlugIn{
	
	public void pannello(String string,String Title,PlugInContext arg0) {
		JOptionPane.showMessageDialog( arg0.getWorkbenchFrame(), 
				string,
				Title,
				JOptionPane.INFORMATION_MESSAGE);
		
	}

	
	

}
