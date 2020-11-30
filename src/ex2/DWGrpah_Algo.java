package ex2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;




public class DWGrpah_Algo implements dw_graph_algorithms{
	 private directed_weighted_graph graph;
	 
	 
		public void dijkstra(node_data src, directed_weighted_graph weightedGraph, Hashtable<Integer, Integer> predecessors, Hashtable<Integer, Double> tags)  {

			// run over vertexes and set all
			for ( node_data vertex : weightedGraph.getV()) {
				vertex.setInfo("white");
				vertex.setTag(Integer.MAX_VALUE);
				predecessors.put(vertex.getKey(),-1);
			}


			PriorityQueue<node_data> q = new PriorityQueue<>();
			src.setTag(0);
			q.add(src);

			while (!q.isEmpty()) {
				node_data vertex = q.poll();
				Collection<edge_data> neighbors =  weightedGraph.getE(vertex.getKey());

				for (edge_data neighbor : neighbors) {
					double w = neighbor.getWeight();

					if(neighbor.getInfo() == "white") {
						double sourceWeight = vertex.getTag() + w;

						if (sourceWeight < neighbor.getTag()) {
							tags.put(vertex.getKey(), sourceWeight);
							predecessors.put(neighbor.getDest(),vertex.getKey());
							q.add(weightedGraph.getNode(neighbor.getDest()));
						}
					}
				}
				vertex.setInfo("black");
			}
		}

	@Override
	public void init(directed_weighted_graph g) {
		graph=g;		
	}

	@Override
	public directed_weighted_graph getGraph() {
		// TODO Auto-generated method stub
		return graph;
	}

	@Override
	public directed_weighted_graph copy() {
		if(graph ==null) {
			return null;
		}
		return new DWGrpah_DS (graph);
	}

	@Override
	public boolean isConnected() {
		Hashtable<Integer, Integer> predecessors = new Hashtable<Integer, Integer>();
		Hashtable<Integer, Double> tag = new Hashtable<Integer, Double>();
		//copy graph
		directed_weighted_graph copy = copy();

		//get first node 
		Iterator<node_data> it = copy.getV().iterator();
		if (!it.hasNext()) return true;
		node_data src = it.next();

		// activate Dijkstra 
		dijkstra(src, copy, predecessors,tag);



		Iterator<node_data> iterator = copy.getV().iterator();

		// run over all vertexes and check if all the vertexes are equal to "black"
		while (iterator.hasNext()) {
			node_data node_info = iterator.next();
			if(node_info.getInfo() != "black") {
				return false;
			}
		}

		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		if (graph.getNode(src) == null || graph.getNode(dest) == null) 
			return -1;
		Hashtable<Integer, Integer> predecessors = new Hashtable<Integer, Integer>();
		Hashtable<Integer, Double> tag = new Hashtable<Integer, Double>();
		directed_weighted_graph copy = copy();
		dijkstra(copy.getNode(src),copy, predecessors,tag);
		return tag.get(dest);

	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		if(graph.getNode(src) == null || graph.getNode(dest) == null)  return null;
		Stack<node_data> list = new Stack<node_data>();

		Hashtable<Integer, Integer> predecessors = new Hashtable<Integer, Integer>();
		Hashtable<Integer, Double> tag = new Hashtable<Integer, Double>();
		directed_weighted_graph copy = copy();
		dijkstra(copy.getNode(src),copy, predecessors,tag);

		Integer perent = dest;

		while (perent != src && perent != -1) {
			node_data nodePerent =  copy.getNode(perent);
			list.add(nodePerent);
			perent = predecessors.get(perent);		
		}

		list.add(copy.getNode(src));
		Collections.reverse(list);
		return list;


	}

	@Override
	public boolean save(String file) {

		try {
			FileOutputStream fileStreamPrimitive  = new FileOutputStream(file);
			ObjectOutputStream fileStreamObject = new ObjectOutputStream(fileStreamPrimitive);

			fileStreamObject.writeObject(this.graph);

			fileStreamPrimitive.close();
			fileStreamObject.close();
		} catch (IOException Error) {
			Error.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public boolean load(String file) {
		try {
			FileInputStream fileStreamPrimitive = new FileInputStream(file);
			ObjectInputStream fileStreamObject = new ObjectInputStream(fileStreamPrimitive);

			DWGrpah_DS loadedFile = (DWGrpah_DS) fileStreamObject.readObject();
			
			fileStreamPrimitive.close();
			fileStreamObject.close();
			
			this.graph = loadedFile;
			
		} catch (IOException | ClassNotFoundException Error) {
			
			Error.printStackTrace();
			return false;
		}


		return false;
	}
}
