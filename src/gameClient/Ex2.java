package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.util.*;

public class Ex2 implements Runnable {
	
	private static Arena The_Arena;
	private static int UserID;
	private static myFrame The_Frame;
	protected static int numberOfMoves = 0;
	protected static Thread user = new Thread(new Ex2());
	private static int Level_number;
	private static directed_weighted_graph graph;
	private static final dw_graph_algorithms graph_algo = new DWGrpah_Algo();
	protected static HashMap<Integer, Integer> target;
	protected static int time;


	public static void main(String[] a) {
		login();
		user.run();
	}


	//Log in to the Game (insert ID and Level for the game)
	private static void login() {
		for(int i = 4; i > 0; i--) { 
			int j = i-1;  //number of tries
			try {
				String ID = JOptionPane.showInputDialog("Insert ID: ", "Your ID");
				UserID = (int)Long.parseLong(ID);

				if (Level_number <= -1 || Level_number >= 24 || UserID < 100000000 || UserID > 999999999)
					throw new Exception();
			}

			catch (Exception e) {
				JOptionPane.showMessageDialog(new myFrame(), "You have " + j + " more tries : \n" + "please insert currect ID (with 9 nubmers) :", "Error! - Invalid input !", JOptionPane.ERROR_MESSAGE);
				continue;
			}
			break;
		}
		for(int i = 4; i > 0; i--) { 
			int j = i-1; //number of tries
			try {
				String Level = JOptionPane.showInputDialog("Insert level number", "0-23");
				Level_number = Integer.parseInt(Level);
				if (Level_number <= -1 || Level_number >= 24)
					throw new Exception();
			}
			catch (Exception e) {
				if(j > 0) {
					JOptionPane.showMessageDialog(new myFrame(), "You have " + j + " more tries : \n please insert number between 0-23", "Error! - Invalid input !", JOptionPane.ERROR_MESSAGE);
				}
				if(j == 0) {	
					JOptionPane.showMessageDialog(new myFrame(), " no more options \n The game will start level 0..." , "Error! - Invalid input !", JOptionPane.ERROR_MESSAGE);
					Level_number = 0;
				}
				continue;
			}
			break;
		}
	}

	/*
	 * The main function that runs the game that takes data from all other classes including the init function 
	 * And of course take data from "myFrame" and "myPanel" for the purpose of seeing the game design
	 * In addtion at any set time uses the "move" function to run the agents to their target "pokemon"
	 */
	@Override
	public void run() {
		game_service game = Game_Server_Ex2.getServer(Level_number);
		loadGraph(game.getGraph());
		init(game);
		game.startGame();
		The_Frame.setTitle("Pokemons Game ! ,  Current Level: " + Level_number);
		while (game.isRunning()) {
			numberOfMoves++;
			time = 100;
			move(game);
			try {
				The_Frame.getPanel().setMoves(numberOfMoves);
				The_Frame.repaint();
				Thread.sleep(time);
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
		String resultsOfDetails = game.toString();
		System.out.println(resultsOfDetails);
	}

	
	private void loadGraph(String str) { // function that enable to load the graph from the server (use Gson) in Json format
		try {
			GsonBuilder builder = new GsonBuilder().registerTypeAdapter(directed_weighted_graph.class, new algoJsonDeserializer());
			Gson gson = builder.create();
			graph = gson.fromJson(str, directed_weighted_graph.class);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * A function that initializes the game at the beginning of the run 
	 * And allows all data to be taken from the server and other classes
	 */
	private void init(game_service game) {
		target = new HashMap<Integer , Integer>();
		String AllPokemons = game.getPokemons();
		
		The_Arena = new Arena();
		The_Arena.setGraph(graph);
		The_Arena.setPokemons(Arena.getPokemons(AllPokemons));
		The_Arena.setGame(game);
	
		The_Frame = new myFrame();
		The_Frame.setSize(1050, 750);
		The_Frame.InitFrame(The_Arena);
		The_Frame.setVisible(true);
		String info = game.toString();
		
		try {
			JSONObject rows = new JSONObject(info);
			JSONObject str = rows.getJSONObject("GameServer");
			int num = str.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			Arena.getPokemons(game.getPokemons());
			LinkedList<Integer> strongestOne = The_Arena.getBestValue();
			int i = 0;
			while ( i < num) {
				boolean To_continue = false;
			
				
				Iterator<Integer> iterator =  strongestOne.iterator();
				
				while (iterator.hasNext()) {
					Integer kValue = iterator.next();
					if (!To_continue) {
						int pos = kValue;
					
						
						if (!target.containsValue(pos)) {
							game.addAgent(pos);
							target.put(i, pos);
							To_continue = true;
						}
					}
				}
				i++;
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * A function that completes the algorithm where to proceed in order to achieve the goal (Pokemon) in the shortest time in order to earn as many points as possible
	 * Of course this function is aided by other auxiliary functions
	 */
	private static void move(game_service game) {
		String details = game.move();
		List<The_Agent> log = Arena.getAgents(details, graph);
		The_Arena.setAgents(log);
		String strPoke = game.getPokemons();
		List<Pokemon> PokemonList = Arena.getPokemons(strPoke);
		The_Arena.setPokemons(PokemonList);
		graph_algo.init(graph);
		double grade = 0.0;
		
		Iterator<The_Agent> iterator =  log.iterator();
		
		while (iterator.hasNext()) {
			The_Agent ag = iterator.next();
			
			grade = grade + (int) ag.getPoints();
			if (ag.getSrcNode() == target.get(ag.getID())) {
				target.put(ag.getID(), -1);
			}
			game.chooseNextEdge(ag.getID(), nextchoose(ag, PokemonList));
			System.out.println("Agent: " + ag.getID() + ",  His points: " + ag.getPoints() + " , Go to node: " + nextchoose(ag, PokemonList));
		}
		
		The_Frame.getPanel().setGrade(grade);
	}
	
	
	
	//A function that contains the algorithm itself means that it decides where the next target of the agent will be - that is, to which vertex its face goes
	private static int nextchoose(The_Agent bond, List<Pokemon> pokemons) {
		PriorityQueue<Pokemon> closestOne = new PriorityQueue<>(new ComparatorDist());
		
		Iterator<Pokemon> iterator =  pokemons.iterator();

		while (iterator.hasNext()) {
			Pokemon p = iterator.next();
			
			if (!target.containsValue(p.getEdges().getDest()) || target.get(bond.getID()) == p.getEdges().getDest()) {
				double distance = graph_algo.shortestPathDist(bond.getSrcNode(), p.getEdges().getDest());
				p.setDistance(distance);
				closestOne.add(p);
			}
		}
		
		ArrayList<node_data> The_path = null;
	
		if (!closestOne.isEmpty()) {
			Pokemon targetPokemon = closestOne.poll();
			target.put(bond.getID(), targetPokemon.getEdges().getDest());
			if(The_Arena.computeStuckProblem(bond,targetPokemon) >= 8) {
				time = 25;
			}
			if (bond.getSrcNode() == targetPokemon.getEdges().getDest()) {
				return targetPokemon.getEdges().getSrc();
			}
			else {
				The_path = new ArrayList<>(graph_algo.shortestPath(bond.getSrcNode(), targetPokemon.getEdges().getDest()));
			}
		}
		
		if (The_path== null || The_path.isEmpty() ) {
			LinkedList<edge_data> edgeData = new LinkedList<>(graph.getE(bond.getSrcNode()));
			return edgeData.getFirst().getDest();
		}
		
		return The_path.get(1).getKey();
	}

	
	public static class ComparatorDist implements Comparator<Pokemon> {
		
		@Override
		public int compare(Pokemon o1, Pokemon o2) {
			int ans = Double.compare(o1.getDistance(), o2.getDistance());
			return ans;
		}
	}

}
