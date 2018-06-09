package algorithm;

import java.util.HashMap;

public class Calculators {
	public static double density(HashMap <Integer, Double> cluster, double[] weights, int[][] edges) {
		double weight = 0;
		for(int x: cluster.keySet()) {
			for(int y: cluster.keySet()) {
				for(int z=0; z < edges.length; z++) {
					
					if(edges[z][0]-1 == x&&edges[z][1]-1 == y) {
						weight+=weights[z];
					}
				}
			}
		}
		double density = weight / (cluster.size()*(cluster.size()-1)/2);
		System.out.println("Density    " + weight);
		return density;
	}
	public static double support(int vertex, HashMap <Integer, Double> cluster,double[] weights, int[][] edges) {
		double support = 0;
		for(int i: cluster.keySet()) {
			for(int j=0; j < edges.length; j++) {
				if((edges[j][0]-1 == vertex||edges[j][1]-1 == vertex)&&(edges[j][0]-1 == i||edges[j][1]-1 == i)) {
					support+=weights[j];
				}
			}
		}
		return support;
	}
	public static double degree(int vertex, double[] weights, int[][] edges) {
		double degree=0;
		for(int i=0; i < edges.length; i++) {
			if(edges[i][0]-1 == vertex || edges[i][1]-1 == vertex) {
				degree+=weights[i];
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
	public static HashMap<Integer, Double> adjacents(HashMap<Integer, Double> degreeQ, int vertex, int[][] edges){
		HashMap<Integer, Double> adjacents = new HashMap<Integer, Double>();
		for(int i=0; i < edges.length; i++) {
			if(degreeQ.containsKey(edges[i][1]-1)&&(edges[i][0]-1 == vertex)) {
				adjacents.put(edges[i][1]-1,degreeQ.get(edges[i][1]-1));
			}
			if(degreeQ.containsKey(edges[i][0]-1)&&(edges[i][1]-1 == vertex)) {
				adjacents.put(edges[i][0]-1,degreeQ.get(edges[i][0]-1));
			}
		}
		return adjacents;
	}
}
