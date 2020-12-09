package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ex2.DWGrpah_DS;
import ex2.NodeData;
import ex2.directed_weighted_graph;
import ex2.edge_data;
import ex2.node_data;



class DWGrpah_DSTest {
	
	directed_weighted_graph g;
	private static Random _rnd = null;

	public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
		directed_weighted_graph g = new DWGrpah_DS();
		_rnd = new Random(seed);
		for(int i=0;i<v_size;i++) {
			NodeData n = new NodeData(i);
			g.addNode(n);
		}
		int[] nodes = nodes(g);
		while(g.edgeSize() < e_size) {
			int a = nextRnd(0,v_size);
			int b = nextRnd(0,v_size);
			int i = nodes[a];
			int j = nodes[b];
			double w = _rnd.nextDouble();
			g.connect(i,j, w);

		}
		return g;
	}
	
	private static int nextRnd(int min, int max) {
		double v = nextRnd(0.0+min, (double)max);
		int ans = (int)v;
		return ans;
	}
	
	private static double nextRnd(double min, double max) {
		double d = _rnd.nextDouble();
		double dx = max-min;
		double ans = d*dx+min;
		return ans;
	}
	
	private static int[] nodes(directed_weighted_graph g) {
		int size = g.nodeSize();
		Collection<node_data> V = g.getV();
		node_data[] nodes = new node_data[size];
		V.toArray(nodes); // O(n) operation
		int[] ans = new int[size];
		for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
		Arrays.sort(ans);
		return ans;
	}

	@BeforeEach
	public void declareGraph() {
		g = new DWGrpah_DS();
	}

	@Test
	void nodeSize() {
		directed_weighted_graph g = new DWGrpah_DS();
		int  n=10;
		for (int i = 0; i < n; i++) {
			NodeData s = new NodeData(i);
			g.addNode(s);
		}

		for (int i = 0; i < n; i++) {
			g.removeNode(i);
		}

		int s = g.nodeSize();
		assertEquals(0,s);
	}
	@Test
	void edgeSize() {
		directed_weighted_graph g = new DWGrpah_DS();
		int n =5;
		for (int i = 0; i < n; i++) {
			NodeData s = new NodeData(i);
			g.addNode(s);
		}
		g.connect(0,0,1);
		g.connect(0,1,1);
		g.connect(0,2,1);
		g.connect(0,3,1);
		g.connect(0,3,1);
		g.connect(0,4,1);
		g.connect(1,4,1);

		assertEquals(5, g.edgeSize());
		g.connect(0,1,2);
		assertEquals(5, g.edgeSize());
	}
	
	@Test
	void MCSize() {
		directed_weighted_graph g = new DWGrpah_DS();
		NodeData s = new NodeData(1);
		NodeData d = new NodeData(2);
		g.addNode(d);
		g.addNode(s);
		assertEquals(2, g.getMC());
		
		g.connect(1 ,2 , 1);
		g.connect(1 ,2 ,10);
		g.connect(0, 10, 100);
		g.removeEdge(1, 2);
		g.removeNode(2);
		g.removeNode(1);
		assertEquals(7, g.getMC());
		
		
	}

	@Test
	void addNode() {
		directed_weighted_graph g = new DWGrpah_DS();
		NodeData s = new NodeData(1);
		g.addNode(s);
		node_data n= g.getNode(1);
		assertEquals(1, n.getKey());
	}
	
	@Test
	void getEdge() {
		directed_weighted_graph g = new DWGrpah_DS();
		NodeData a = new NodeData(100);
		NodeData b = new NodeData(20);
		NodeData c = new NodeData(30);
		g.addNode(a);
		g.addNode(b);
		g.addNode(c);
		g.connect(20, 30, 10);
		g.connect(20, 100, 20);
		double w1 = g.getEdge(20, 30).getWeight();
		double w2 = g.getEdge(20, 100).getWeight();
		assertEquals(10 , w1);
		assertEquals(20 , w2);
		g.removeEdge(20, 30);
		assertEquals(null , g.getEdge(20, 30));
		
	}
	@Test()
	void getNode() {
		directed_weighted_graph g = new DWGrpah_DS();
		NodeData s = new NodeData(1);
		NodeData d = new NodeData(2);
		g.addNode(s);
		g.addNode(d);
		assertNotEquals(null, g.getNode(2));
	}

	@Test
	void connect() {
		directed_weighted_graph g = new DWGrpah_DS();
		int n =4;
		for (int i = 0; i < n; i++) {
			NodeData s = new NodeData(i);
			g.addNode(s);
		}
		g.connect(0,0,1.0);
		g.connect(0,1,1.0);
		g.connect(0,2,1.0);
		g.connect(2, 0, 1);
		g.connect(0,3,1.0);
		
		assertEquals(1.0, g.getEdge(0, 1).getWeight());

		g.removeEdge(0,1);
		assertEquals(null, g.getEdge(0, 1));
		assertEquals(null, g.getEdge(2, 1));
		double w = g.getEdge(2,0).getWeight();
		assertEquals(1,w);

		g.connect(0,2,2);
		g.connect(2, 0, 2);
		w = g.getEdge(2,0).getWeight();
		assertEquals(2,w);
	}

	@Test
	void getV() {
		directed_weighted_graph g = new DWGrpah_DS();
		int n =4;
		for (int i = 0; i < n; i++) {
			NodeData s = new NodeData(i);
			g.addNode(s);
		}
		g.connect(0,1,1);
		g.connect(0,2,2);
		g.connect(0,3,3);
		g.connect(0,1,1);
		Collection<node_data> vertex =  new LinkedList<>();;
		vertex.add(g.getNode(0));
		vertex.add(g.getNode(1));
		vertex.add(g.getNode(2));
		vertex.add(g.getNode(3));


		assertEquals(vertex.size(), g.getV().size());

		vertex.remove(g.getNode(0));

		g.removeNode(0);

		assertEquals(vertex.size(), g.getV().size());
	}
	
//	@Test
//	void getE() {
//		directed_weighted_graph g = new DWGrpah_DS();
//		int n =4;
//		for (int i = 0; i < n; i++) {
//			NodeData s = new NodeData(i);
//			g.addNode(s);
//		}
//		g.connect(1, 2, 1);
//		g.connect(1, 3, 1);
//		g.connect(0, 1, 2);
//		g.connect(0, 3, 3);
//		Collection<edge_data> vertex =  new LinkedList<>();
//		vertex.add((edge_data) g.getNode(0));
//		vertex.add((edge_data) g.getNode(1));
//		vertex.add((edge_data) g.getNode(2));
//		vertex.add((edge_data) g.getNode(3));
//       assertEquals(vertex.size(), g.getE(g.edgeSize()));
//       vertex.remove(0);
//       
//	}
	
	@Test
	void removeNode() {
		directed_weighted_graph g = new DWGrpah_DS();
		int n =4;
		for (int i = 0; i < n; i++) {
			NodeData s = new NodeData(i);
			g.addNode(s);
		}
		
		g.connect(0,1,1);
		g.connect(0,2,1);
		g.connect(0,3,1);

		g.removeNode(4);
		assertEquals(4,g.nodeSize());

		g.removeNode(3);
		g.removeNode(3);
		assertEquals(3,g.nodeSize());

		g.removeNode(0);
		assertEquals(null , g.getEdge(1, 0));
		assertEquals(0,g.edgeSize());
		assertEquals(2,g.nodeSize());
	}
	@Test
	void removeEdge() {

		directed_weighted_graph g = new DWGrpah_DS();
		int n =5;
		for (int i = 0; i < n; i++) {
			NodeData s = new NodeData(i);
			g.addNode(s);
		}
		g.connect(0,1,1);
		g.connect(0,2,2);
		g.connect(0,3,3);
		g.connect(0, 4, 1);

		g.removeEdge(0,3);
		g.removeEdge(0, 4);
		g.removeEdge(0,1);
		g.removeEdge(1,2);
		g.removeEdge(1,0);
		g.removeEdge(3,0);
		g.removeEdge(4, 1);
		assertEquals( null,g.getEdge(3, 0));
		assertEquals( null,g.getEdge(0, 3));
		assertEquals(1, g.edgeSize());
	}


}



