package it.polito.tdp.PremierLeague.model;

public class Adiacente {
	private Player p1;
	private Player p2;
	private Double peso;
	public Adiacente(Player p1, Player p2, Double peso) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.peso = peso;
	}
	public Player getP1() {
		return p1;
	}
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	public Player getP2() {
		return p2;
	}
	public void setP2(Player p2) {
		this.p2 = p2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public String stampaScarso() {
		return this.p2.getName() + " minuti di differenza = "+ this.peso+"\n";
	}
	

}
