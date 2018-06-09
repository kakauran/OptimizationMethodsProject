package algorithm;

import java.util.HashMap;

public class Binning {
	public static HashMap<Integer, Double> binThese(HashMap<Integer, Double> adjacents, int[][] edges, double[] weights, int seed){
		
		HashMap<Integer, Double> bin1 = new HashMap<Integer, Double>(); //0.8<n<=1
		HashMap<Integer, Double> bin2 = new HashMap<Integer, Double>(); //0.6<n<=0.8
		HashMap<Integer, Double> bin3 = new HashMap<Integer, Double>(); //0.4<n<=0.6
		HashMap<Integer, Double> bin4 = new HashMap<Integer, Double>(); //0.2<n<=0.4
		HashMap<Integer, Double> bin5 = new HashMap<Integer, Double>(); //0<n<=0.2		
		
		for(int key: adjacents.keySet()) {
			for(int i=0; i<edges.length; i++) {
				if((edges[i][0]-1 == seed||edges[i][1]-1 == seed)&&(edges[i][0]-1 == key||edges[i][1]-1 == key)) {
					if(0.8<weights[i] && weights[i] <=1) {
						bin1.put(key, adjacents.get(key));
					}else if(0.6<weights[i] && weights[i] <=0.8) {
						bin2.put(key, adjacents.get(key));
					}else if(0.4<weights[i] && weights[i] <=0.6) {
						bin3.put(key, adjacents.get(key));
					}else if(0.2<weights[i] && weights[i] <=0.4) {
						bin4.put(key, adjacents.get(key));
					}else if(0<weights[i] && weights[i] <=0.2) {
						bin5.put(key, adjacents.get(key));
					}
				}
			}
		}
		
		if(!bin1.isEmpty()) {
			return bin1;
		}else if(!bin2.isEmpty()) {
			return bin2;
		}else if(!bin3.isEmpty()) {
			return bin3;
		}else if(!bin4.isEmpty()) {
			return bin4;
		}else if(!bin5.isEmpty()) {
			return bin5;
		}else {
			return bin1;
		}
		
	}

}
