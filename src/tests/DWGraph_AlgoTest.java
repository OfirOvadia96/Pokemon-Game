package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import api.DWGrpah_Algo;
import api.DWGrpah_DS;
import api.NodeData;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.node_data;

class DWGraph_AlgoTest {

	

	    @Test
	    void init_GetGraph() {
	    	directed_weighted_graph g = new DWGrpah_DS();

	    	NodeData s = new NodeData(1);
			NodeData d = new NodeData(2);
			NodeData f = new NodeData(0);
			NodeData a = new NodeData(3);
	        g.addNode(s);
	        g.addNode(d);
	        g.addNode(f);
	        g.addNode(d);
	        g.addNode(a);
	        g.connect(0,0,1);
	        g.connect(0,1,1);
	        g.connect(0,2,1);
	        g.connect(0,3,1);
	        g.connect(0,3,1);

	        dw_graph_algorithms graph= new DWGrpah_Algo ();
	        graph.init(g);
	        assertEquals(4, graph.getGraph().nodeSize());
	        assertEquals(3, graph.getGraph().edgeSize());
	        assertEquals(1 , graph.getGraph().getEdge(0, 1).getWeight());
	        assertEquals(null ,graph.getGraph().getEdge(0, 0));
	    }
	    
	    @Test
	    void copy() {
	    	directed_weighted_graph g = new DWGrpah_DS();
	    	NodeData s = new NodeData(1);
			NodeData d = new NodeData(2);
			NodeData f = new NodeData(0);
			NodeData a = new NodeData(3);
	        g.addNode(s);
	        g.addNode(d);
	        g.addNode(f);
	        g.addNode(a);
	        g.connect(0,1,1.0);
	        g.connect(0,2,1.0);
	        g.connect(0,3,1.0);

	        dw_graph_algorithms ga= new DWGrpah_Algo();
	        ga.init(g);
	        directed_weighted_graph g2= ga.copy();
	        
	        NodeData v = new NodeData(4);
	        g.addNode(v);
	        g2.removeNode(0);

	        assertNotEquals(g.nodeSize(),g2.nodeSize());
	        assertNotEquals(g.edgeSize(),g2.edgeSize());
	        assertNotEquals(g,g2);
	    }
	    
	    
	    @Test
	    void isConnected() {

	    	directed_weighted_graph g = new DWGrpah_DS();
	        int n = 100000;

	        for(int i = 0; i < n; i++) {
	        	NodeData a = new NodeData(i);
	        	g.addNode(a);
	        }
	        for(int i = 2; i < n; i++) {
	        	g.connect(0, i, 1);
	        	g.connect(i, 0, 1);
	        }
	        
	        dw_graph_algorithms ga= new DWGrpah_Algo();
	        ga.init(g);
	        

	        
	        assertEquals(false, ga.isConnected());
        
	        // Connect the missing edge
	        g.connect(0, 1, 1);
	        g.connect(1, 0, 1);
	        ga.init(g);

	        assertEquals(true, ga.isConnected());
	    }
	    
	    
	    @Test
	    void shortestPathDist() {
	    	directed_weighted_graph g = new DWGrpah_DS();
	        int n = 10;

	        for(int i = 0; i < n; i++) {
	        NodeData v = new NodeData(i);
	        	g.addNode(v);
	        }

	        // 0 -> 1 -> 2 -> 3 -> 6 -> 5
	        // weights: [2,4,5,3,1] = 15
	        g.connect(0,1,2);
	        g.connect(1,2,4);
	        g.connect(2,3,5);
	        g.connect(3,6,3);
	        g.connect(6,5,1);
	        
	        // 0 -> 4 -> 7 -> 8 -> 9 -> 1
	        //  : weights: [3,1,5,1,4] = 14
	        g.connect(0,4,3);
	        g.connect(4,7,1);
	        g.connect(7,8,5);
	        g.connect(8,9,1);
	        g.connect(9,1,4);

	        dw_graph_algorithms mygraph = new DWGrpah_Algo();
	        mygraph.init(g);
	        double weights1 = mygraph.shortestPathDist(0,5);
	        assertEquals(15,weights1);

	        double weights = mygraph.shortestPathDist(0,1);
	        assertEquals(2,weights);
	    }
	    
	    
	    @Test
	    void shortestPath() {
	    	directed_weighted_graph g = new DWGrpah_DS();
	        
	        int n =10;

	        for(int i = 0; i < n; i++) {
	        NodeData v = new NodeData(i);
	        	g.addNode(v);
	        }

	        // weights: [2,4,5,3,1] = 14
	        g.connect(0,1,2);
	        g.connect(1,2,4);
	        g.connect(2,3,5);
	        g.connect(3,6,3);
	 

	        //  : weights: [3,1,6,1,4] = 14
	        g.connect(0,4,3);
	        g.connect(4,7,1);
	        g.connect(7,8,5);
	        g.connect(8,9,1);
	        g.connect(9,5,4);

	        // long way 
	        g.connect(6,5,1);
	        
	        dw_graph_algorithms mygraph = new DWGrpah_Algo();
	        int[] pathA = {0,1,2,3,6};
	        int[] pathB = {0,4,7,8,9,5};

	        mygraph.init(g);
	        List<node_data> listA =  mygraph.shortestPath(0,6);
	      
	        for(int i = 0; i < listA.size(); i++) {
	        	assertEquals(pathA[i],listA.get(i).getKey());
	        }
	        
	        List<node_data> listB =  mygraph.shortestPath(0,5);
	
	        for(int i = 0; i < listB.size(); i++) assertEquals(pathB[i],listB.get(i).getKey());
	    }
	
	    
	    @Test
	    void save_load() {
	    	//save
	    	directed_weighted_graph g0 = DWGrpah_DSTest.graph_creator(10,30,1);
	        dw_graph_algorithms ag0 = new DWGrpah_Algo();
	        ag0.init(g0);
	        String str = "g0.json";
	        ag0.save(str);

	        String str3 = "g3.json";
	        ag0.save(str3);
	        
	        //load
	        directed_weighted_graph g1 = DWGrpah_DSTest.graph_creator(10,30,1);
	        ag0.load(str);
	        assertEquals(g0,g1);
	        g0.removeNode(0);
	        assertNotEquals(g0,g1);
	        
	        //load
	        directed_weighted_graph g2 = DWGrpah_DSTest.graph_creator(10,30,1);
	        ag0.load(str);
	        assertEquals(g1,g2);

	        
	        directed_weighted_graph g3 = DWGrpah_DSTest.graph_creator(10,30,1);
	        ag0.load(str3);
	        assertEquals(g1,g3);
	    }
	}