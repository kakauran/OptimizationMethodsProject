package algorithm;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class Main {
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		List<Integer[]> edges = new ArrayList<Integer[]>();
		List<Double> weights = new ArrayList<Double>();
		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		
		try {
			edges = Graph.readEdgesFromFile("resources/biogrid.9606");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
			try {
				weights = Graph.readWeightFromFile("resources/biogrid.9606");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
			Integer highest = Math.max(edges.stream().max((a, b) -> a[0] > b[0] ? 1 : -1 ).get()[0],edges.stream().max((a, b) -> a[1] > b[1] ? 1 : -1 ).get()[1]);
			
			int nVertices = highest;
			int nEdges = edges.size();
			
			
			//Choose initial capacity high enough to minimize frequency of rehashing
			HashMap<Integer, Double> degreeQ   = new HashMap<Integer, Double>();
			
			double sThreshold = 0.5;
			double dThreshold = 0.5;
			
			//initialize degreeQ to be V
			for(int i=0; i<nEdges; i++) {
				Integer[] edge = edges.get(i);
				degreeQ.put(edge[0], Calculators.degree(edge[0], weights, edges, nEdges));
				degreeQ.put(edge[1], Calculators.degree(edge[1], weights, edges, nEdges));
			}
			

			while(!degreeQ.isEmpty()) {
				//clear set S
				HashMap<Integer, Double> tried = new HashMap<Integer, Double>();
				HashMap<Integer, Double> adjacents = new HashMap<Integer, Double>();
				HashMap<Integer, Double> canidateQ = new HashMap<Integer, Double>();
				HashMap<Integer, Double> clusterS  = new HashMap<Integer, Double>();
				
				//seed1 = max value from degreeQ
				int seed1 = Calculators.max(degreeQ);
				System.out.println("Seed-One   " + seed1);
				adjacents = Calculators.adjacents(degreeQ, seed1, edges, nEdges);
				//seed2 = max value from seed1's adjacent vertices
				
				int seed2 = Calculators.max(Binning.binThese(adjacents, weights, edges, seed1, nEdges));
				System.out.println("Seed-Two   " + seed2);
				if(seed2==-1) {
					//if no adjacent vertices S=seed1
					clusterS.put(seed1, degreeQ.get(seed1));
					writer.write(clusterS.keySet().toString() + "\n");
					System.out.println("Cluster     " + clusterS.keySet() );
				}else {
					clusterS.put(seed1, degreeQ.get(seed1));
					clusterS.put(seed2, degreeQ.get(seed2));
					do {
						for(int j: clusterS.keySet()) {
							for(int i=0; i < nEdges; i++) {
								Integer[] edge = edges.get(i);
								if((edge[0] == j)&&!tried.containsKey(edge[1])&&!clusterS.containsKey(edge[1])&&degreeQ.containsKey(edge[1])) {
									canidateQ.put(edge[1],Calculators.support(edge[1], clusterS,weights,edges,nEdges));
								}if((edge[1] == j)&&!tried.containsKey(edge[0])&&!clusterS.containsKey(edge[0])&&degreeQ.containsKey(edge[0])) {
									canidateQ.put(edge[0],Calculators.support(edge[0], clusterS,weights,edges, nEdges));
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
						
						if((canidateQ.get(maxSup)>=(sThreshold*clusterS.size()*Calculators.density(clusterS,weights,edges,nEdges))
								&&Calculators.density(temp,weights,edges,nEdges)>dThreshold)) {
							clusterS.put(maxSup,canidateQ.get(maxSup));
						}
						temp.clear();
						canidateQ.remove(maxSup);
						tried.put(maxSup, 0.0);
						}
					}while(!canidateQ.isEmpty());
					
						writer.write(clusterS.keySet().toString() + "\n");
						System.out.println("Cluster     " + clusterS.keySet() );
				}
				
				for(int key: clusterS.keySet()) {
					degreeQ.remove(key);
				}
			}
			writer.close();
		}
	}

