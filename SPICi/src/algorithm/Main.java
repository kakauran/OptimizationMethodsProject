package algorithm;
import java.util.*;


public class Main {
	public static void main(String[] args) {
	
			int edges[][] = {{1,11},{11,10},{11,9},{9,6},{9,7},{9,12},{9,3},{9,8},{6,3},{8,12},{8,5},{3,4},{3,2},{4,2},  {9,10}};
			double weights[] = {1,     1,      1,   0.2,  0.7,   0.5,   0.9,  1,   0.4,   1,    0.6,   1,   0.6,  0.4,    0.7};
			int nVertices = 12;
			int nEdges = edges.length;
			HashMap<Integer, Double> degreeQ   = new HashMap<Integer, Double>();
			HashMap<Integer, Double> adjacents = new HashMap<Integer, Double>();
			HashMap<Integer, Double> canidateQ = new HashMap<Integer, Double>();
			HashMap<Integer, Double> clusterS  = new HashMap<Integer, Double>();
			HashMap<Integer, Double> tried     = new HashMap<Integer, Double>();
			double sThreshold = 0.5;
			double dThreshold = 0.5;
			
			//initialize degreeQ to be V
			for(int i=0; i<nVertices; i++) {
				degreeQ.put(i, Calculators.degree(i, weights, edges));
			}
			
			while(!degreeQ.isEmpty()) {
				//clear set S
				tried.clear();
				clusterS.clear();
				adjacents.clear();
				//seed1 = max value from degreeQ
				int seed1 = Calculators.max(degreeQ);
				System.out.println("Seed-One   " + seed1);
				adjacents = Calculators.adjacents(degreeQ, seed1, edges);
				//seed2 = max value from seed1's adjacent vertices
				
				HashMap<Integer, Double> binned = new HashMap<Integer, Double>();
				binned = Binning.binThese(adjacents, edges, weights, seed1);
				
				int seed2 = Calculators.max(binned);
				System.out.println("Seed-Two   " + seed2);
				if(seed2==-1) {
					//if no adjacent vertices S=seed1
					clusterS.put(seed1, degreeQ.get(seed1));
					System.out.println("Cluster     " + clusterS.keySet() );
				}else {
					clusterS.put(seed1, degreeQ.get(seed1));
					clusterS.put(seed2, degreeQ.get(seed2));
					do {
						for(int j: clusterS.keySet()) {
							for(int i=0; i < edges.length; i++) {
								if((edges[i][0]-1 == j)&&!tried.containsKey(edges[i][1]-1)&&!clusterS.containsKey(edges[i][1]-1)&&degreeQ.containsKey(edges[i][1]-1)) {
									canidateQ.put(edges[i][1]-1,Calculators.support(edges[i][1]-1, clusterS,weights,edges));
								}if((edges[i][1]-1 == j)&&!tried.containsKey(edges[i][0]-1)&&!clusterS.containsKey(edges[i][0]-1)&&degreeQ.containsKey(edges[i][0]-1)) {
									canidateQ.put(edges[i][0]-1,Calculators.support(edges[i][0]-1, clusterS,weights,edges));
								}
							}
						}
					if(!canidateQ.isEmpty()) {	
						//System.out.println("Canidates      " + canidateQ.keySet() );
						int maxSup = Calculators.max(canidateQ);
						//System.out.println("Highest support =  " + maxSup);
						//System.out.println("Support = " + canidateQ.get(maxSup));
						HashMap<Integer, Double> temp  = new HashMap<Integer, Double>();
						temp.putAll(clusterS);
						temp.put(maxSup,canidateQ.get(maxSup));
						
						if((canidateQ.get(maxSup)>=(sThreshold*clusterS.size()*Calculators.density(clusterS,weights,edges))
								&&Calculators.density(temp,weights,edges)>dThreshold)) {
							clusterS.put(maxSup,canidateQ.get(maxSup));
						}
						temp.clear();
						canidateQ.remove(maxSup);
						tried.put(maxSup, 0.0);
						}
					}while(!canidateQ.isEmpty());
					System.out.println("Cluster     " + clusterS.keySet() );
				}
				for(int key: clusterS.keySet()) {
					degreeQ.remove(key);
				}
			}
		}
	}

