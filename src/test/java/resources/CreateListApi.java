package resources;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateListApi {
	Response res;
	
	public Response createList(String key, String token, String boardId, String newList) {
		 res = given()
				 .queryParam("key", key)
				 .queryParam("token", token)
				 .queryParam("name", newList)
			 	  .contentType(ContentType.JSON)
				  .when()
				  .post("/boards/" + boardId);	
			
		return res;
	}
	
	public int getStatusCode() {
		return res.getStatusCode();
	}
	
	public JSONObject getJSONObject() {
		return new JSONObject(res.asString());
	}
	
	public String getListID() {
		JSONObject obj = getJSONObject();
		return obj.getString("id");
	}
	
	public String getListName() {
		JSONObject obj = getJSONObject();
		return obj.getString("name");
	}
	
	public String getBoardId() {
		JSONObject obj = getJSONObject();
		return obj.getString("idBoard");
	}
}
