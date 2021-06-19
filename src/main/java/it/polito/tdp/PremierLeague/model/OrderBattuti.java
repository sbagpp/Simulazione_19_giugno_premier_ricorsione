package it.polito.tdp.PremierLeague.model;

import java.util.Comparator;

public class OrderBattuti implements Comparator<Adiacente> {

	@Override
	public int compare(Adiacente o1, Adiacente o2) {
		// TODO Auto-generated method stub
		return -o1.getPeso().compareTo(o2.getPeso());
	}

}
