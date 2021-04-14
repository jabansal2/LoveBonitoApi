package Utilities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.util.JSON;

public class JSONMethods {

	public List<JSONObject> getArrayOfObjects(String response){
		List<JSONObject> list = new ArrayList<JSONObject>();		
		JSONArray jsonArr = new JSONArray(response);
		for(int i=0; i<jsonArr.length(); i++) {
			list.add(jsonArr.getJSONObject(i));
		}
		return list;		
	}
}
