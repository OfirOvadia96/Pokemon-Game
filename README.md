<h1  align="center"> PokemonGame: </h1>

**A game played on an arena where the "Pokemon Catchers" agents try to catch as many Pokemon as possible by the end of the given time.
Each player who enters the game enters his ID and the level at which he wants to play, then the game will run and the screen will show the points accumulated by the agents, and the remaining time.
The arena - built of a directed and weighted graph.**


***The project is divided into several interfaces and departments that implement them:***


<h3>Interfaces: </h3>

* node_data

* geo_location

* edge_location

* edge_data

* game_service

* directed_weighted_graph

* dw_graph_algorithms



<h3> The classes: </h3>


* **NodeData:**

A class that implements the node_data interface which displays a vertex and its properties.
In its ability: to know its location, to know its weight if necessary with the help of the algorithm found in the DWGrpah_Algo class.

* **GeoLocation:**

A class that implements the geo_location interface that allows vertices to know their location and calculates distances within it.

* **EdgeData:**

A class that implements the edge_data interface that displays a rib and its properties.

* **DWGrpah_DS:**

A class that implements the directed_weighted_graph interface which displays a directed and weighted graph,
The data structure on which it is based is HasMap, which holds 3 of this structure: the vertices, the source ribs, the target edges.

*****Department capabilities:*****

Accessing a particular vertex.
 accessing a particular side.
 adding a vertex.
 adding a side (connecting vertices).
 accessing neighbors of a particular vertex.
 deleting a vertex.
 deleting a side.
 copying the graph data to another graph.
 And in addition counts the number of vertices and sides and changes made in the graph.
 
* **DWGrpah_Algo :**

 A class that implements the dw_graph_algorithms interface which expands the DWGrpah_DS graph in other capabilities.
 
*****Department capabilities:*****

Checking the graph binding,
 calculating the shortest path between two vertices and returning the length,
 calculating the shortest path between 2 vertices and returning it,
 writing the object to a text file and loading the graph object from a text file,
 In addition it contains an auxiliary function in which an algorithm based on the dijkstra algorithm supports the other capabilities.

* **algoJsonDeserializer:**

Auxiliary class for writing the graph object to a text file and loading the graph object from a text file.

* **Arena:**

The arena in which the game takes place.

* **Point3D :**

Enables positioning capability.

* **Range :**

This class represents a simple 1D range of shape [min,max]

* **Range2D :**

This class represents a 2D Range, composed from two 1D Ranges.

* **Range2Range :**

This class represents a simple world 2 frame conversion (both ways).

* **The_Agent :**

A class representing the agents who walk across the graph and capture the Pokemon.

* **Pokemon:**

A department that represents the goals that agents need to achieve.

* **myFrame:**

A class that inherits from the JFrame class, which shows the user (the player) the entire game - the graph, the agents, the goals (the Pokemon), the total number of steps of the agents, time, score, and the final score
The class also contains a MyPanel terminal object.

* **MyPanel:**

Auxiliary class that serves as a Panel on which are drawn the figures of the Agents Pokemon and the entire graph.


* **Ex2:**

The department ran the game plan,
Consolidates all the data from all the required departments, initializes the game and runs it until the selected stage chosen by the player is completed.





