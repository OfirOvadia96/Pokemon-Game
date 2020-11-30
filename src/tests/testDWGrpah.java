package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class mytestDWGrpah {
	


	directed_weighted_graph g;
		    private static Random _rnd = null;
		
		  public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
			  directed_weighted_graph g = new DWGrpah_DS();
		        _rnd = new Random(seed);
		        for(int i=0;i<v_size;i++) {
		        	 node_data n = new NodeData();
		            g.addNode(n);
		        }
		        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
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
		    /**
		     * Simple method for returning an array with all the node_data of the graph,
		     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
		     * @param g
		     * @return
		     */
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
		        	g.addNode(i);
					
				}
		        
		        for (int i = 0; i < n; i++) {
		        	g.removeNode(i);
		        }
		        int s = g.nodeSize();
		        assertEquals(0,s);

		    }
		    @Test
		    void edgeSize() {
		    	directed_weighted_graph g = new WGraph_DS();
		        int n =4;
		        for (int i = 0; i < n; i++) {
		        	 g.addNode(i);
				}
		        g.connect(0,0,1);
		        g.connect(0,1,1);
		        g.connect(0,2,1);
		        g.connect(0,3,1);
		        g.connect(0,3,1);
		        g.connect(0,4,1);
		        g.connect(1,4,1);
		    
		        assertEquals(3, g.edgeSize());

		        g.connect(0,1,2);
		        assertEquals(3, g.edgeSize());
		    }

		    @Test
		    void hasEdge(){
		        for (int i = 0; i <6 ; i++) {
		            g.addNode(i);
		            
		        }
		        
		    
		        g.connect(0,5,1);
		        g.connect(0,1,3);
		        assertTrue(g.hasEdge(0,1)&&g.hasEdge(0,5));
		        assertFalse(g.hasEdge(0,2)); // no connection- return false
		        assertFalse(g.hasEdge(0,4)); // node 4 dont exist in the graph- return false
		    }
		    @Test
		    void addNode() {
		    	directed_weighted_graph g = new DWGrpah_DS();
		        g.addNode(1);
		        node_data n= g.getNode(1);
		        assertEquals(1, n.getKey());
		    }
		    
		    
		    @Test
		    void connect2() {
		    	directed_weighted_graph g = new DWGrpah_DS();
		        int n =4;
		        for (int i = 0; i < n; i++) {
		        	 g.addNode(i);
				}
		        g.connect(0,0,1.0);
		        g.connect(0,1,1.0);
		        g.connect(0,2,1.0);
		        g.connect(0,3,1.0);

		        assertTrue(g.hasEdge(0, 1));

		        g.removeEdge(0,1);
		        assertFalse(g.hasEdge(0, 1));
		        assertFalse(g.hasEdge(2, 1));

		        double w = g.getEdge(2,0);
		        assertEquals(1,w);

		        g.connect(0,2,2);
		        w = g.getEdge(2,0);
		        assertEquals(2,w);
		    }
		    @Test
		    void connect() {
		    	directed_weighted_graph g = new DWGrpah_DS();
		        int n =4;
		        for (int i = 0; i < n; i++) {
		        	 g.addNode(i);
				}
		        g.connect(0,1,1);
		        g.connect(0,2,2);
		        g.connect(0,3,3);
		        g.removeEdge(0,1);
		        assertFalse(g.hasEdge(1,0));
		        g.removeEdge(2,1);
		        g.connect(0,1,1);
		        double w = g.getEdge(1,0);
		        assertEquals(w,1);
		    }
			  @Test
			    void getV() {
				  directed_weighted_graph g = new DWGrpah_DS();
			        int n =4;
			        for (int i = 0; i < n; i++) {
			        	 g.addNode(i);
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
			   @Test
			    void removeNode() {
				   directed_weighted_graph g = new DWGrpah_DS();
			        int n =4;
			        for (int i = 0; i < n; i++) {
			        	 g.addNode(i);
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
			        assertFalse(g.hasEdge(1,0));

			        assertEquals(0,g.edgeSize());
			        assertEquals(2,g.nodeSize());
			    }
			   @Test
			    void removeEdge() {
			    
				   directed_weighted_graph g = new DWGrpah_DS();
			        int n =4;
			        for (int i = 0; i < n; i++) {
			        	 g.addNode(i);
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
			        assertFalse(g.hasEdge(3,0));
			        assertFalse(g.hasEdge(0,3));
			        
			        assertEquals(1, g.edgeSize());
			        assertEquals(-1, g.getEdge(0,3));
			    }
			  

	}
}
