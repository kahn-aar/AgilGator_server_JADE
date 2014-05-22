package behaviours;

import jade.core.behaviours.OneShotBehaviour;

public class BDDLunchUpdateRequestBehaviour extends OneShotBehaviour  {
	
	/**
	* Behaviour lan�ant une requ�te sur la base de donn�es, qui n'attend pas de 
	* r�sultats en retour, uniquement un message de succ�s ou de fail
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
			System.out.println("BDD re�u = " + query);
		}
}

