package algorithm;
import gurobi.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		
		try {
			GRBEnv env = new GRBEnv("spici.log");
			GRBModel model = new GRBModel(env);
			
			model.set(GRB.StringAttr.ModelName, "SPICi");
	
			
			int edges[][] = {{1,11},{11,10},{11,9},{9,6},{9,7},{9,12},
					         {9,3},{9,8},{6,3},{8,12},{8,5},{3,4},{3,2},{4,2}};
			double weights[] = {1,1,1,0.2,0.7,0.5,1,1,0.4,1,0.6,1,1,0.4};
			int nVertices = 12;
			int nEdges = edges.length;
			HashMap<Integer, Double> degreeQ   = new HashMap<Integer, Double>();
			HashMap<Integer, Double> adjacents = new HashMap<Integer, Double>();
			HashMap<Integer, Double> canidateQ = new HashMap<Integer, Double>();
			HashMap<Integer, Double> clusterS  = new HashMap<Integer, Double>();
			double sThreshold = 0.5;
			double dThreshold = 0.5;
			
			double x[][] = new double[nVertices][nVertices];
			for(int i=0; i<nVertices; i++) {
				for(int j=0; j<nVertices; j++) {
					int flag =0;
					for(int k=0; k<nEdges; k++) {
						if(edges[k][0]-1==i&&edges[k][1]-1==j) {
							x[i][j] = weights[k];
							flag = 1;
							//System.out.println("x[" + i + "]["+ j +"]"+ "     " + weights[k] );
						}else if(edges[k][0]-1==j&&edges[k][1]-1==i) {
							flag = 1;
							x[i][j] = weights[k];
							//System.out.println("x[" + i + "]["+ j +"]" + "     " + weights[k] );
						}
					}
					if(flag == 0) {
						x[i][j] = 0.0;
						//System.out.println("x[" + i + "]["+ j +"]" + "     " + 0 );
					}
				}
			}
			
			//initialize degreeQ to be V
			for(int i=0; i<nVertices; i++) {
				double temp = 0;
				for(int j=0; j<nVertices; j++) {
					temp += x[i][j];
				}
				degreeQ.put(i, temp);
			}
			
			while(!degreeQ.isEmpty()) {
				//clear set S
				clusterS.clear();
				adjacents.clear();
				//seed1 = max value from degreeQ
				int seed1 = degreeQ.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
				System.out.println("Seed 1   " + seed1);
				for(int i=0; i<nVertices; i++) {
					if(x[seed1][i]>0) {
						adjacents.put(i,degreeQ.get(i));
					}
				}
				//seed2 = max value from seed1's adjacent vertices
				System.out.println("Adjacent Vertices     " + adjacents.size());
				int seed2 = adjacents.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
				System.out.println("Seed 2   " + seed2);
				if(seed2==-1) {
					//if no adjacent vertices S=seed1
					clusterS.put(seed1, degreeQ.get(seed1));
					System.out.println("Cluster     " + seed1 );
				}else {
					clusterS.put(seed1, degreeQ.get(seed1));
					clusterS.put(seed2, degreeQ.get(seed2));
					System.out.println("Cluster     " + seed1 + "  " + seed2 );
				}
				degreeQ.remove(seed1);
				degreeQ.remove(seed2);
			}

			
			

			
			
		} catch (GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
			}
		}
	}
