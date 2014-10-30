import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.MockGame;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.objective.Objective;
import br.uff.es2.war.network.WarServer;

public class WarServerLauncher {
    
    public static void main(String[] args) throws IOException {
//	World world = MockGame.createWorld();
//	Set<Territory> territories = world.getTerritories();
//	System.out.println(encodeTerritories(territories));
	
	if(args.length == 0)
	    args = new String[]{"55555"};
	int port = Integer.parseInt(args[0]);
	System.out.println("War Server");
	new Thread(new WarServer(port)).start();
    }
    
    private static JSONArray encodeTerritories(Collection<Territory> territories){
	JSONArray array = new JSONArray();
	Iterator<Territory> iterator = territories.iterator();
	for(int i = 0; i < territories.size(); i++)
	    array = array.put(i, encodeTerritory(iterator.next()));
	return array;
    }
    
    private static JSONObject encodeTerritory(Territory territory){
	JSONObject obj = new JSONObject();
	obj.put("name", territory.getName());
	obj.put("soldiers", territory.getSoldiers());
	obj.put("continent", territory.getContinent().getName());
	obj.put("owner", territory.getOwner().getColor());
	return obj;
    }
}
