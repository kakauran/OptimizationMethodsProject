package algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph {
	public static List<Integer[]> readEdgesFromFile(String filePath) throws IOException {
		BufferedReader abc;
		List<Integer[]> data = new ArrayList<Integer[]>();
		String s;
		try {
			abc = new BufferedReader(new FileReader(filePath));
			while((s=abc.readLine())!=null) {
				String[] parts = s.split("\\s+");
				Integer[] edge = {Integer.parseInt(parts[0]),Integer.parseInt(parts[1])};
			    data.add(edge);
			    //System.out.println(edge[0] + "   " + edge[1]);
			}
			
			 abc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	
	public static List<Double> readWeightFromFile(String filePath) throws IOException {
		BufferedReader abc;
		List<Double> data = new ArrayList<Double>();
		String s;
		try {
			abc = new BufferedReader(new FileReader(filePath));
			while((s=abc.readLine())!=null) {
				String[] parts = s.split("\\s+");
			    data.add(Double.parseDouble(parts[2]));
			}
			 abc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}
