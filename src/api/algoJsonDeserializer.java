package api;
import java.lang.reflect.Type;
import com.google.gson.*;

//class That Deserializer every given detail from the structures of a JSON file.

public class algoJsonDeserializer implements JsonDeserializer<directed_weighted_graph> {

	public double[] separateString(String loc) {
		double[] array = new double[3];
		int j = 0;
		int i = 0;
		String strin = "";


		while(i < loc.length())
		{
			if(loc.charAt(i) != ',') {
				strin += loc.charAt(i);
			}

			else {
				double values = Double.parseDouble(strin);
				array[j] = values;
				strin = "";
				j++;
			}
			i++; 
		}

		return array;
	}

	@Override
	public directed_weighted_graph deserialize(JsonElement jsonElement, Type TheType, JsonDeserializationContext ConText) {
		try {
			directed_weighted_graph Graph = new DWGrpah_DS();
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			JsonArray AllEdges = jsonObject.get("Edges").getAsJsonArray();
			JsonArray AllNodes = jsonObject.get("Nodes").getAsJsonArray();


			for (JsonElement v : AllNodes) {
				JsonElement val = v.getAsJsonObject();
				String loc = val.getAsJsonObject().get("pos").getAsString();
				int id = val.getAsJsonObject().get("id").getAsInt();
				double[] geolocation = separateString(loc);
				double a = geolocation[0];
				double b = geolocation[1];
				double c = geolocation[2];
				geo_location location = new GeoLocation(a, b, c);
				node_data node = new NodeData(id, location);
				Graph.addNode(node);
			}


			for (JsonElement e : AllEdges) {
				JsonElement val = e.getAsJsonObject();
				int source = val.getAsJsonObject().get("src").getAsInt();
				int destination = val.getAsJsonObject().get("dest").getAsInt();
				double weight = val.getAsJsonObject().get("w").getAsDouble();
				Graph.connect(source, destination, weight);
			}

			return Graph;

		}

		catch (Exception j)
		{
			System.out.println("Json Exception ! ! !");
			return null;
		}
	}
}