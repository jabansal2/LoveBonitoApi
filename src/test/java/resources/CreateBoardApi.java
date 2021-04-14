package resources;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBoardApi {
	
	Response res;
	
	public Response createBoard(String key, String token, String boardName) {
		 res = given()
				 .queryParam("key", key)
				 .queryParam("token", token)
				 .queryParam("name", boardName)
			 	  .contentType(ContentType.JSON)
				  .when()
				  .post("/boards/");
		 
		return res;
	}
	
	public int getStatusCode() {
		return res.getStatusCode();
	}
	
	public JSONObject getJSONObject() {
		return new JSONObject(res.asString());
	}
	
	public String getBoardID() {
		JSONObject obj = getJSONObject();
		return obj.getString("id");
	}
	
	public String getBoardName() {
		JSONObject obj = getJSONObject();
		return obj.getString("name");
	}
}
