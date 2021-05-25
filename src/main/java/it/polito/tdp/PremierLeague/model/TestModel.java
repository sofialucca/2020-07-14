package it.polito.tdp.PremierLeague.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		m.creaGrafo();
		
//		System.out.println(m.getMigliori(null));
//		System.out.println(m.getPeggiori(null));
		m.setSim(10, 6);
		System.out.println(m.getMediaReporter());
		System.out.println(m.getPartiteCritiche());
	}

}
