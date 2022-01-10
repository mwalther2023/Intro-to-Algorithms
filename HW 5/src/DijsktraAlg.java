//import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class DijsktraAlg {	  
	public static void main(String arg[]) { 
		  
        // Inputs for the nodes from file and create graph
    	ArrayList<Node> arr = new ArrayList<Node>();
		File file = new File("graph24-6.txt");
        try {
        	Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner wordScanner = new Scanner(line);

				String parent = wordScanner.next();
				Node pNode;

				if (Node.checkContains(arr, parent)) {
					pNode = arr.get(Node.elementPos(arr, parent));

				} else {
					pNode = new Node(parent);
					arr.add(pNode);
				}

				while (wordScanner.hasNext()) {
					String name = wordScanner.next();
					Double distance = wordScanner.nextDouble();
					if (Node.checkContains(arr, name)) {
						pNode.Branch.add(arr.get(Node.elementPos(arr, name)));
						pNode.Distance.add(distance);

					} else {
						Node child = new Node(name);
						arr.add(child);
						pNode.Branch.add(child);
						pNode.Distance.add(distance);
					}
				}
				wordScanner.close();
			}
			scan.close();
        }
        catch(IOException e) {
       	 e.printStackTrace();
        }

        // Run Dijkstra to calculate the shortest path from the source
        Dijkstra(arr, arr.get(0));
        
        // Print the shortest path to all the nodes from the source
        System.out.println("The shorted path from node :"); 
        for (int i = 0; i < arr.size(); i++) 
            System.out.println(arr.get(0).name + " to " + arr.get(i).name + " is " + arr.get(i).val); 
    } 
 

    public static void Dijkstra(ArrayList<Node> arr, Node start) {
		ArrayList<Node> S = new ArrayList<Node>();

		ArrayList<Node> Q = new ArrayList<Node>();
		for (int i = 0; i < arr.size(); i++) {
			Q.add(arr.get(i));
		}
		Q.get(Node.elementPos(Q, start.name)).val = 0.0;
		while (!Q.isEmpty()) {
			int pos = 0;
			Double min = Double.POSITIVE_INFINITY;
			for (int i = 0; i < Q.size(); i++) {
				if (Q.get(i).val < min) {
					pos = i;
				}
			}
			for (int i = 0; i < Q.get(pos).Branch.size(); i++) {
				Double temp = Q.get(pos).Distance.get(i) + Q.get(pos).val;
				if(temp < Q.get(pos).Branch.get(i).val){
					Q.get(pos).Branch.get(i).val = Q.get(pos).Distance.get(i) + Q.get(pos).val;
				}
			}
			S.add(arr.get(pos));
			Q.remove(pos);

		}
	}
	  
	    // Driver code 
	    
	private static class Node {

		public ArrayList<Node> Branch = new ArrayList<Node>();
		public String name;
		public ArrayList<Double> Distance= new ArrayList<Double>();
		public Double val = Double.POSITIVE_INFINITY;
	
		Node(String n) {
			this.name = n;
		}
	
		public static boolean checkContains(ArrayList<Node> arr, String node) {
			for (int i = 0; i < arr.size(); i++) {
				if (arr.get(i).name.equals(node)) {
					return true;
				}
			}
			return false;
		}
	
		public static int elementPos(ArrayList<Node> arr, String n) {
			int i = 0;
			while (!arr.get(i).name.equals(n)) {
				i++;
			}
			return i;
		}
	}
}
