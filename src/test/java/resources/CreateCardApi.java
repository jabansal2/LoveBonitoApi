package resources;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateCardApi {
	Response res;
	
	public Response createCard(String key, String token, String idList, String cardName) {
		 res = given()
				 .queryParam("key", key)
				 .queryParam("token", token)
				 .queryParam("idList", idList)
				 .queryParam("name", cardName)
			 	  .contentType(ContentType.JSON)
				  .when()
				  .post("/cards");
			
		return res;
	}
	
	public int getStatusCode() {
		return res.getStatusCode();
	}
	
	public JSONObject getJSONObject() {
		return new JSONObject(res.asString());
	}
	
	public String getCardID() {
		JSONObject obj = getJSONObject();
		return obj.getString("id");
	}
	
	public String getCardName() {
		JSONObject obj = getJSONObject();
		return obj.getString("name");
	}
	
	public String getListId() {
		JSONObject obj = getJSONObject();
		return obj.getString("idList");
	}
	
	public String getBoardId() {
		JSONObject obj = getJSONObject();
		return obj.getString("idBoard");
	}
	
}
