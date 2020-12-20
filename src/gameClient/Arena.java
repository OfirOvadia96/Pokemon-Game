package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Arena {

	static HashMap<Integer, HashMap<edge_data, Integer>> HashArea;
	public static double EPS1 = 0.001;
	public static double EPS2 = EPS1 * EPS1;
	private static directed_weighted_graph The_Graph;
	private List<The_Agent> Agents;
	private List<Pokemon> Pokemons;
	private static game_service game_Server;
	static HashMap<Integer, LinkedList<Pokemon>> edges;
	static PriorityQueue<Integer> Edge;


	public Arena() {
		HashArea = new HashMap<>();
	}


	public void setPokemons(List<Pokemon> pokemon) {
		Pokemons = pokemon;
	}


	public void setAgents(List<The_Agent> a) {
		Agents = a;
	}


	public void setGraph(directed_weighted_graph g) {
		The_Graph = g;
	}


	public List<The_Agent> getAgents() {
		return Agents;
	}


	public List<Pokemon> getPokemons() {
		return Pokemons;
	}


	public directed_weighted_graph getGraph() {
		return The_Graph;
	}


	public void setGame(game_service game) {
		game_Server = game;
	}


	public game_service getGame() {
		return game_Server;
	}


	public static List<The_Agent> getAgents(String str, directed_weighted_graph Graph) {
		ArrayList<The_Agent> agents = new ArrayList<>();

		try {
			JSONObject status = new JSONObject(str);
			JSONArray JSONArray = status.getJSONArray("Agents");

			int i = 0;
			while ( i < JSONArray.length()) {
				The_Agent one_Agent = new The_Agent(Graph, i);
				one_Agent.update(JSONArray.get(i).toString());
				agents.add(one_Agent);
				i++;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return agents;
	}


	public static ArrayList<Pokemon> getPokemons(String current) {
		ArrayList<Pokemon> contain_Pokemons = new ArrayList<>();
		edges = new HashMap<>();
		Edge = new PriorityQueue<>(new ComparatorValue());

		try {
			JSONObject pokemons_status = new JSONObject(current);
			JSONArray current_pokemons = pokemons_status.getJSONArray("Pokemons");

			for (node_data node : The_Graph.getV()) {
				edges.put(node.getKey(), new LinkedList<>());
			}
			int i = 0;
			while ( i < current_pokemons.length()) {
				JSONObject pokemon = current_pokemons.getJSONObject(i);

				JSONObject pk = pokemon.getJSONObject("Pokemon");
				int type = pk.getInt("type");
				double v = pk.getDouble("value");
				String p = pk.getString("pos");
				Pokemon f = new Pokemon(new Point3D(p), type, v, null);
				updateEdge(f, The_Graph);
				contain_Pokemons.add(f);
				edges.get(f.getEdges().getDest()).add(f);
				i++;
			}

			for (Pokemon p : contain_Pokemons) {
				Edge.add(p.getEdges().getDest());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return contain_Pokemons;
	}

	public static class ComparatorValue implements java.util.Comparator<Integer> {
		@Override
		public int compare(Integer o, Integer p) {
			return Integer.compare(valueOfEdge(p), valueOfEdge(o));
		}
	}


	public static boolean ifon(geo_location p, edge_data e, int type, directed_weighted_graph g) {
		int src = g.getNode(e.getSrc()).getKey();
		int dest = g.getNode(e.getDest()).getKey();
		if (type < 0 && dest > src) {
			return false;
		}
		if (type > 0 && src > dest) {
			return false;
		}
		geo_location srcLocation = g.getNode(src).getLocation();
		geo_location destLocation = g.getNode(dest).getLocation();
		boolean ans = false;
		double dist = srcLocation.distance(destLocation);
		double d1 = srcLocation.distance(p) + p.distance(destLocation);
		if (dist > d1 - EPS2) {
			ans = true;
		}
		return ans;
	}

	public static void updateEdge(Pokemon pk, directed_weighted_graph graph) {

		for (node_data runner : graph.getV()) {

			for (edge_data edge : graph.getE(runner.getKey())) {
				boolean ans = ifon(pk.getLocation(), edge, pk.getType(), graph);

				if (ans) {
					pk.setEdges(edge);
				}
			}
		}
	}

	public static Range2Range w2f(directed_weighted_graph g, Range2D frame) { 
		Range2Range toRange = new Range2Range(GraphRange(g), frame);
		return toRange;
	}


	public static Range2D GraphRange(directed_weighted_graph g) {
		boolean one = true;
		double x0 = 0, x1 = 0;
		double y0 = 0, y1 = 0;

		for (node_data runner : g.getV()) {
			geo_location locate = runner.getLocation();
			
			if (one) {
				x0 = locate.x();
				x1 = x0;
				y0 = locate.y();
				y1 = y0;
				one = false;
			}
			else {
				if (locate.x() < x0) x0 = locate.x();
				if (locate.x() > x1) x1 = locate.x();
				if (locate.y() < y0) y0 = locate.y();
				if (locate.y() > y1) y1 = locate.y();
			}
		}
		Range xRange = new Range(x0, x1);
		Range yRange = new Range(y0, y1);
		Range2D The_Range =  new Range2D(xRange, yRange);
		
		return The_Range;
	}


	public static int valueOfEdge(int k) {
		int v = 0;
		
		for (Pokemon pokemon : edges.get(k)) {
			v += pokemon.getValue();
		}
		return v;
	}


	public LinkedList<Integer> getBestValue() {
		return new LinkedList<>(Edge);
	}


	public LinkedList<Pokemon> getPokemonsOnEdge(int key) {
		return edges.getOrDefault(key, null);
	}


	public int computeStuckProblem(The_Agent catches, Pokemon p) {
		if (!HashArea.containsKey(catches.getID()) || !HashArea.get(catches.getID()).containsKey(p.getEdges())) {
			HashArea.put(catches.getID(), new HashMap<>());
			HashArea.get(catches.getID()).put(p.getEdges(), 1);
			return 1;
		}
		
		else {
			int FinalOne = HashArea.get(catches.getID()).get(p.getEdges());
			FinalOne++;
			HashArea.get(catches.getID()).put(p.getEdges(), FinalOne);
			return FinalOne;
		}
	}

}
