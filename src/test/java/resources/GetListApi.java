package resources;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import Utilities.JSONMethods;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetListApi {

	Response res;
	JSONMethods jsonMethods = new JSONMethods();
	List<JSONObject> list = new ArrayList<JSONObject>();
	
	public Response getListsInBoard(String key, String token, String boardId) {
		 res = given()
				 .queryParam("key", key)
				 .queryParam("token", token)
			 	  .contentType(ContentType.JSON)
				  .when()
				  .get("/boards/"+boardId+"/lists");
		System.out.println("getList Response = " + res.asString());
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
	
	public String getListId(List<JSONObject> list) {
		return list.get(0).getString("id");
	}
	
	public String getListName(List<JSONObject> list) {
		return list.get(0).getString("name");
	}

	public String getBoardId(List<JSONObject> list) {
		return list.get(0).getString("idBoard");
	}
}
