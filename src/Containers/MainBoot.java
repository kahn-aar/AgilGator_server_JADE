package Containers;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

public class MainBoot {

	public static String PROPERTIES_FILE = "/AgilGator_server_JADE/Ressources";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		Profile p = null;
		try {
			p = new ProfileImpl(PROPERTIES_FILE);
			AgentContainer container = rt.createMainContainer(p);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

}
