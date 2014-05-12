package Containers;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class SecondBoot {

public static String PROPERTIES_FILE = "Ressources/propertiesSecond.properties";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		Profile p = null;
		try {
			p = new ProfileImpl(PROPERTIES_FILE);
			ContainerController container = rt.createAgentContainer(p);
			AgentController serveur = container.createNewAgent("serveur",
					"Agents.ServeurAgent", null);
			serveur.start();
			AgentController liaison = container.createNewAgent("liaison",
					"Agents.LiaisonAgent", null);
			liaison.start();
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

}
