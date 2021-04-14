package resources;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import Utilities.JSONMethods;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetCardApi {
	
	Response res;
	JSONMethods jsonMethods = new JSONMethods();
	List<JSONObject> list = new ArrayList<JSONObject>();
	public Response getCardsInList(String key, String token, String listId) {
		 res = given()
				 .queryParam("key", key)
				 .queryParam("token", token)
			 	  .contentType(ContentType.JSON)
				  .when()
				  .get("/lists/"+listId+"/cards");
			
		return res;
	}
	
	public int getStatusCode() {
		return res.getStatusCode();
	}
	
	public List<JSONObject> getArrayOfCardObjects(Response res) {
		list = jsonMethods.getArrayOfObjects(res.asString());
		return list;
	}
	
	public String getCardId(List<JSONObject> list) {
		return list.get(0).getString("id");
	}
	
	public String getCardName(List<JSONObject> list) {
		return list.get(0).getString("name");
	}
	
	public String getListId(List<JSONObject> list) {
		return list.get(0).getString("idList");
	}
	
	public String getBoardId(List<JSONObject> list) {
		return list.get(0).getString("idBoard");
	}
	
	//For multiple and existing cards
	
	public String getLastCardId(List<JSONObject> list, int n) {
		return list.get(n-1).getString("id");
	}
	
	public String getLastCardName(List<JSONObject> list, int n) {
		return list.get(n-1).getString("name");
	}
	
	public String getLastListId(List<JSONObject> list, int n) {
		return list.get(n-1).getString("idList");
	}
	
	public String getLastBoardId(List<JSONObject> list, int n) {
		return list.get(n-1).getString("idBoard");
	}
}
