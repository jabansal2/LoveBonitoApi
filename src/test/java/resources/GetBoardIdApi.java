package resources;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Utilities.JSONMethods;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetBoardIdApi {

	Response res;
	JSONMethods jsonMethods = new JSONMethods();
	List<JSONObject> list = new ArrayList<JSONObject>();
	public Response getBoardIds(String key, String token) {
		 res = given()
				 .queryParam("fields", "name,url")
				 .queryParam("key", key)
				 .queryParam("token", token)
			 	  .contentType(ContentType.JSON)
				  .when()
				  .get("/member/me/boards");
			
		return res;
	}
	
	public int getStatusCode(Response res) {
		return res.getStatusCode();
	}
	
	public List<JSONObject> getArrayOfObjects(Response res) {
		list = jsonMethods.getArrayOfObjects(res.asString());
		return list;
	}
	
	public int sizeOfArrayFromGetResponse(List<JSONObject> list) {
		return list.size();
	}
	
	public String getBoardId(List<JSONObject> list) {
		return list.get(0).getString("id");
	}
	
	public String getBoardName(List<JSONObject> list) {
		return list.get(0).getString("name");
	}
}
