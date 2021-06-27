package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
							//id reato
	private SimpleWeightedGraph< String , DefaultWeightedEdge>graph;
	private List <String> vertici;
	private EventsDao dao;
	private List <String> best;
	private String partenza;
	private String arrivo;
	private int Lmax; //lunghezza massima del cammino
	
	public Model() {
		vertici= new ArrayList <String>();
		dao=new EventsDao();
	}
	
	public List <String> getCategories(){
		List <String> categorie = dao.listAllCategories();
		Collections.sort(categorie);
		return categorie;
	}
	
	public List <Integer> getMonths(){
		List <Integer> mesi = dao.listAllMonths();
		Collections.sort(mesi);
		return mesi;
	}
	
	public void creaGrafo(Integer mese,String cat) {
		graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		dao.loadAllVertici(vertici,mese,cat);
		Graphs.addAllVertices(graph, vertici);
		
		for(Arco a : dao.listArchi(vertici,cat,mese))
			Graphs.addEdge(graph,a.getR1(),a.getR2(),a.getPeso());
		
		
	}
	
	public Integer getNVertici() {
		return graph.vertexSet().size();
	}
	
	public Integer getNArchi() {
		return graph.edgeSet().size();
	}
	
	
	private double getAvgPesoArchi() {
		double tot=0;
		for(DefaultWeightedEdge e: graph.edgeSet())
			tot+= graph.getEdgeWeight(e);
		return tot/graph.edgeSet().size();
	}
	
	
	public List <Arco> getArchiMax(){
		double avg=this.getAvgPesoArchi();
		List <Arco> max = new ArrayList <Arco>();
		
		for(DefaultWeightedEdge e: graph.edgeSet())
			if(graph.getEdgeWeight(e)>avg)
				max.add(new Arco(graph.getEdgeSource(e),graph.getEdgeTarget(e),(int)graph.getEdgeWeight(e)));
		return max;
	}
	
	
	

	public SimpleWeightedGraph< String , DefaultWeightedEdge> getGraph() {
		return graph;
	}
	
	
	public List <String> trovaPercorso(String partenza, String arrivo){
		this.arrivo=arrivo;
		this.partenza=partenza;
		best=new ArrayList <String>();
		this.Lmax=0;
		List <String> parziale = new ArrayList <String>();
		
		parziale.add(partenza);
		cerca(parziale);
		return best;
	}
	
	
	private void cerca(List <String> parziale) {
		if(parziale.get(parziale.size()-1).equals(arrivo) && parziale.size()>this.Lmax) {
				best=new ArrayList <String> (parziale);
				Lmax=parziale.size();
			
			return; 
			//sia che ci sia un nuovo best, sia in caso contrario
			//faccio backtracking e torno al "remove" per tornare al liv
			//precedente
		}
		
		for(String s: Graphs.neighborListOf(graph, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				cerca(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}
	
	
	
}
