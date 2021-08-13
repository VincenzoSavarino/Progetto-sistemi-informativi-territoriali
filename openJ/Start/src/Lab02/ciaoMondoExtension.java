package Lab02;

import com.vividsolutions.jump.workbench.plugin.Extension;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;


public class ciaoMondoExtension extends Extension{

	@Override
	public void configure(PlugInContext context) throws Exception {
		// TODO Auto-generated method stub
		new ciaoMondo().initialize(context);
		
	}

}
