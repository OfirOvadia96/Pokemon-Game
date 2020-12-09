package ex2;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class algoJsonDeserializer implements JsonDeserializer<DWGrpah_Algo>
{

	@Override
	public DWGrpah_Algo deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException 
	{

		JsonObject jsonObject = json.getAsJsonObject();
		System.out.println(jsonObject);
		JsonObject graphObject = jsonObject.get("graph").getAsJsonObject();
		

		HashMap<Integer,node_data> nodes = new HashMap<Integer, node_data>();
		
		// Src -> Dest -> edge_data
		HashMap<Integer,HashMap<Integer,edge_data>> edges = new HashMap<Integer,HashMap<Integer,edge_data>>();

		// Dest -> Src -> edge_data
		HashMap<Integer,HashMap<Integer,edge_data>> destEdges = new HashMap<Integer,HashMap<Integer,edge_data>>();

		JsonObject nodesJsonObj = graphObject.get("nodes").getAsJsonObject();
		JsonObject edgesJsonObj = graphObject.get("edges").getAsJsonObject();
		JsonObject destEdgesJsonObj = graphObject.get("destEdges").getAsJsonObject();
		int edgesSize = graphObject.get("edgesSize").getAsInt();
		int mcSize = graphObject.get("mcSize").getAsInt();
		
		for (Entry<String, JsonElement> set : nodesJsonObj.entrySet())
		{

			String hashKey = set.getKey(); //the key of the hashmap 
			JsonElement jsonValueElement = set.getValue(); //the value of the hashmap as json element

			JsonElement jsonKey = jsonValueElement.getAsJsonObject().get("key");
			JsonElement jsonWeight = jsonValueElement.getAsJsonObject().get("weight");
			JsonElement jsonInfo = jsonValueElement.getAsJsonObject().get("info");			
			JsonElement jsonTag = jsonValueElement.getAsJsonObject().get("tag");
			JsonElement jsonLocation = jsonValueElement.getAsJsonObject().get("location");
			JsonElement x = jsonLocation.getAsJsonObject().get("x");
			JsonElement y = jsonLocation.getAsJsonObject().get("y");
			JsonElement z = jsonLocation.getAsJsonObject().get("z");

			GeoLocation location = new GeoLocation(x.getAsDouble(),y.getAsDouble(),z.getAsDouble());

			NodeData node = new NodeData(jsonKey.getAsInt(),
					jsonInfo.getAsString(),
					jsonWeight.getAsDouble(),
					jsonTag.getAsInt(),
					location) ;
			nodes.put(node.getKey(),node);
			HashMap<Integer, edge_data> neighborsSrc = new HashMap<Integer, edge_data>();
			HashMap<Integer, edge_data> neighborsDest = new HashMap<Integer, edge_data>();
			edges.put(node.getKey(),  neighborsSrc );
			destEdges.put(node.getKey(),  neighborsDest );
		}
		
		for(Entry<String, JsonElement> jsonEdge : edgesJsonObj.entrySet()) {
			Integer src = Integer.parseInt(jsonEdge.getKey()); //the key of the hashmap 
			JsonElement jsonValueElement = jsonEdge.getValue();

			HashMap<Integer, edge_data> neighborsSrc = edges.get(src);

			for(Entry<String, JsonElement> jsonValueEdgeEntrySet : jsonValueElement.getAsJsonObject().entrySet()) {
				Integer dest = Integer.parseInt(jsonEdge.getKey()); //the key of the hashmap 

				JsonElement jsonValueEdge = jsonValueEdgeEntrySet.getValue();
				JsonElement jsonSrc = jsonValueEdge.getAsJsonObject().get("src");
				JsonElement jsonDest = jsonValueEdge.getAsJsonObject().get("dest");
				JsonElement jsonWeight = jsonValueEdge.getAsJsonObject().get("weight");
				JsonElement jsonInfo = jsonValueEdge.getAsJsonObject().get("info");
				JsonElement jsonTag = jsonValueEdge.getAsJsonObject().get("tag");
				EdgeData edge = new EdgeData (jsonSrc.getAsInt(),
						jsonDest.getAsInt(),
						jsonWeight.getAsDouble(),
						jsonInfo.getAsString(),
						jsonTag.getAsInt());

				neighborsSrc.put(dest, edge);
			}
			edges.put(src,  neighborsSrc);
		}
		
		for(Entry<String, JsonElement> jsonEdge : destEdgesJsonObj.entrySet()) {
			Integer src = Integer.parseInt(jsonEdge.getKey()); //the key of the hashmap 
			JsonElement jsonValueElement = jsonEdge.getValue();
			
			HashMap<Integer, edge_data> neighborsDest = edges.get(src);

			for(Entry<String, JsonElement> jsonValueEdgeEntrySet : jsonValueElement.getAsJsonObject().entrySet()) {
				Integer dest = Integer.parseInt(jsonEdge.getKey()); //the key of the hashmap 
				JsonElement jsonValueEdge = jsonValueEdgeEntrySet.getValue();
				JsonElement jsonSrc = jsonValueEdge.getAsJsonObject().get("src");
				JsonElement jsonDest = jsonValueEdge.getAsJsonObject().get("dest");
				JsonElement jsonWeight = jsonValueEdge.getAsJsonObject().get("weight");
				JsonElement jsonInfo = jsonValueEdge.getAsJsonObject().get("info");
				JsonElement jsonTag = jsonValueEdge.getAsJsonObject().get("tag");
				EdgeData edge = new EdgeData (jsonSrc.getAsInt(),
						jsonDest.getAsInt(),
						jsonWeight.getAsDouble(),
						jsonInfo.getAsString(),
						jsonTag.getAsInt());

				neighborsDest.put(dest, edge);

			}
			destEdges.put(src,  neighborsDest);
		}
		
		DWGrpah_Algo algo = new DWGrpah_Algo();
		DWGrpah_DS ds = new DWGrpah_DS(nodes,edges,destEdges, edgesSize,mcSize);
		
		algo.init(ds);
		
		return algo;
	}

}
