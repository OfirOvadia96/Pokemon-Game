package gameClient;

import api.*;
import gameClient.*;
import gameClient.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * This class use JPanel class
 * On this class we will draw the given game graph at the stage the user has chosen to play
 */

public class MyPanel extends JPanel {
	protected Arena Game_Format;
	protected Range2Range The_range;
	protected int s = 7;
	protected int moves = 0;
	protected double grade = 0;


	public MyPanel(Arena ar) { //constructor
		Game_Format = ar;
	}


	public void paint(Graphics graph) {
		resize();
		DrawGraph((Graphics2D) graph);
		DrawAgents((Graphics2D) graph);
		DrawPokemons((Graphics2D) graph);
		
		double seconds = TimeUnit.MILLISECONDS.toSeconds(Game_Format.getGame().timeToEnd());
		graph.setColor(Color.orange);
		graph.drawString("Grade: " + grade, 800, 70);
		graph.drawString("Moves: " + moves, 800, 110);
		graph.drawString("TimeOut: 00:" + seconds, 800, 90);
	}
	
	public void setMoves(int move) {
		moves = move;
	}


	public void setGrade(double newGrade) {
		grade = newGrade;
	}


	private void resize() {
		directed_weighted_graph graph = Game_Format.getGraph();
		Range y = new Range(this.getHeight() - 60, 160);
		Range x = new Range(110, this.getWidth() - 115);
		Range2D frame = new Range2D(x, y);
		The_range = Arena.w2f(graph, frame);
	}


	private void DrawPokemons(Graphics2D graphics) {
		List<Pokemon> PokemonList = Game_Format.getPokemons();
		if (PokemonList != null) {

			for (Pokemon current : PokemonList) {
				Point3D pos3D = current.getLocation();
				Color color = Color.RED;

				if (current.getType() < 0) {
					color = Color.YELLOW;
				}

				if (pos3D != null) {
					geo_location loc = this.The_range.world2frame(pos3D);
					graphics.setColor(color);
					graphics.drawString("pokemon", (int) loc.x() - 12, (int) loc.y() - 60);
					graphics.setColor(Color.WHITE);
					graphics.drawString("ValuationPoints:" + current.getValue(), (int) loc.x() - 18, (int) loc.y() - 39);
					
				}
			}
		}
	}
	


	private void DrawAgents(Graphics2D g) {
		List<The_Agent> AgentList = Game_Format.getAgents();
	
		int i = 0;	
		while (i < AgentList.size()) {
			
			if(AgentList != null) {
				geo_location agent_location = AgentList.get(i).getLocation();
				
				if (agent_location != null) {
					geo_location locfor = this.The_range.world2frame(agent_location);
					g.setColor(Color.BLUE);
					g.setFont(new Font("sizeType", Font.CENTER_BASELINE, 30));
					g.drawString("Agent", (int) locfor.x() -60, (int) locfor.y() - 40);
					g.setFont(new Font("sizeType", Font.CENTER_BASELINE, 15));
					g.setFont(new Font("sizeType", Font.CENTER_BASELINE, 20));
				}
				i++;
			}
		}
	}


	protected void DrawGraph(Graphics2D g) {
		directed_weighted_graph graph = Game_Format.getGraph();
	
		for (node_data Node : graph.getV()) {
			DrawNode(Node, g);
		}
		
		for (node_data Node : graph.getV()) {
			
			for (edge_data edge : graph.getE(Node.getKey())) {
				DrawEdge(edge, g);
			}
		}
	}


	protected void DrawNode(node_data node, Graphics2D graphic) {
		graphic.setColor(new Color(73, 155, 84));
		geo_location loc = node.getLocation();
		geo_location fp = this.The_range.world2frame(loc);
		graphic.setFont(new Font("sizeType", Font.CENTER_BASELINE, 20));
		graphic.setColor(Color.BLACK);
		graphic.drawString(" " + node.getKey(), (int) fp.x() - 14, (int) fp.y() - 34);
	}


	protected void DrawEdge(edge_data edge, Graphics2D graphic) {
		directed_weighted_graph Graph = Game_Format.getGraph();
	
		geo_location o1 = Graph.getNode(edge.getDest()).getLocation();
		geo_location o3 = this.The_range.world2frame(o1);
		geo_location o2 = Graph.getNode(edge.getSrc()).getLocation();
		geo_location o4 = this.The_range.world2frame(o2);
		
		graphic.setColor(new Color(30, 120, 65));
		Arrow(graphic, (int) o4.x(), (int) o4.y(), (int) o3.x(), (int) o3.y());
	}


	protected void Arrow(Graphics graphic, int x1, int y1, int x2, int y2) {
		Graphics2D graphic2D = (Graphics2D) graphic.create();
		//calculate dist formola
		double distx = x2 - x1; 
		double disty = y2 - y1;
		double angle = Math.atan2(disty, distx);
		double len = (distx * distx + disty * disty);
		int ans = (int)Math.sqrt(len);
		
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		graphic2D.transform(at);
		graphic2D.setStroke(new BasicStroke(3));
		graphic2D.drawLine(0, 0, ans, 0);
		graphic2D.fillPolygon(new int[]{ans - 10, ans - 8 - 22, ans - 8 - 22, ans - 10}, new int[]{0, -8, 8, 0}, 4);
	}

}