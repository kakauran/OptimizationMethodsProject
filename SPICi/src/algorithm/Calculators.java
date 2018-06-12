package algorithm;

import java.util.HashMap;
import java.util.List;

public class Calculators {
	
	public static double density(HashMap <Integer, Double> cluster, List<Double> weights, List<Integer[]> edges, int nEdges) {
		double weight = 0;
		for(int x: cluster.keySet()) {
			for(int y: cluster.keySet()) {
				for(int z=0; z < nEdges; z++) {
					Integer[] edge = edges.get(z);
					if(edge[0] == x&&edge[1] == y) {
						weight+=weights.get(z);
					}
				}
			}
		}
		double density = weight / (cluster.size()*(cluster.size()-1)/2);
		return density;
	}
	
	public static double support(int vertex, HashMap <Integer, Double> cluster, List<Double> weights, List<Integer[]> edges, int nEdges) {
		double support = 0;
		for(int i: cluster.keySet()) {
			for(int j=0; j < nEdges; j++) {
				Integer[] edge = edges.get(j);
				if((edge[0] == vertex||edge[1] == vertex)&&(edge[0] == i||edge[1] == i)) {
					support+=weights.get(j);
				}
			}
		}
		return support;
	}
	
	public static double degree(int vertex, List<Double> weights, List<Integer[]> edges, int nEdges) {
		double degree=0;
		for(int i=0; i < nEdges; i++) {
			Integer[] edge = edges.get(i);
			if(edge[0] == vertex || edge[1] == vertex) {
				degree+=weights.get(i);
			}
		}
		return degree;
	}
	
	public static int max (HashMap<Integer, Double> cluster ) {
		if(cluster.isEmpty()) {
			return -1;
		}
			return cluster.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
	}
	
	public static HashMap<Integer, Double> adjacents(HashMap<Integer, Double> degreeQ, int vertex, List<Integer[]> edges, int nEdges){
		HashMap<Integer, Double> adjacents = new HashMap<Integer, Double>();
		for(int i=0; i < nEdges; i++) {
			Integer[] edge = edges.get(i);
			if(degreeQ.containsKey(edge[1])&&(edge[0] == vertex)) {
				adjacents.put(edge[1],degreeQ.get(edge[1]));
			}
			if(degreeQ.containsKey(edge[0])&&(edge[1] == vertex)) {
				adjacents.put(edge[0],degreeQ.get(edge[0]));
			}
		}
		return adjacents;
	}
}
