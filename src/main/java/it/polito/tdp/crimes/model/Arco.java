package it.polito.tdp.crimes.model;

public class Arco {
	
	private String r1;
	private String r2;
	private Integer peso; //distretti
	public Arco(String r1, String r2, Integer peso) {
		super();
		this.r1 = r1;
		this.r2 = r2;
		this.peso = peso;
	}
	public String getR1() {
		return r1;
	}
	public void setR1(String r1) {
		this.r1 = r1;
	}
	public String getR2() {
		return r2;
	}
	public void setR2(String r2) {
		this.r2 = r2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	public String toString() {
		return r1+" - "+r2+" ( "+peso+" )";
	}

}
