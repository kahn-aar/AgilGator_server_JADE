package behaviours;

import jade.core.behaviours.OneShotBehaviour;

public class BDDLunchUpdateRequestBehaviour extends OneShotBehaviour  {
	
	/**
	* Behaviour lançant une requête sur la base de données, qui n'attend pas de 
	* résultats en retour, uniquement un message de succès ou de fail
	*/
	private static final long serialVersionUID = 1L;
		private String conversationId;
		private String query;
		
		public BDDLunchUpdateRequestBehaviour(String conversationId, String query) {
			this.conversationId = conversationId;
			this.query = query;
		}
		
		@Override
		public void action() {
			System.out.println("BDD reçu = " + query);
		}
}

