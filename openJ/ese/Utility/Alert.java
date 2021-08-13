package Utility;

import javax.swing.JOptionPane;

import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

public class Alert extends AbstractPlugIn{
	
	public void pannello(double msg,String Title,PlugInContext arg0) {
		JOptionPane.showMessageDialog( arg0.getWorkbenchFrame(), 
				msg,
				Title,
				JOptionPane.INFORMATION_MESSAGE);
		
	}

	
	

}
