package TestScripts;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import Utilities.JSONMethods;
import Utilities.ReadTestDataFromExcel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import resources.CreateBoardApi;
import resources.CreateCardApi;
import resources.CreateListApi;
import resources.GetBoardIdApi;
import resources.GetCardApi;
import resources.GetListApi;

public class TestCases {

	GetBoardIdApi getBoardId = new GetBoardIdApi();
	CreateBoardApi createBoardApi = new CreateBoardApi();
	GetListApi getListApi = new GetListApi();
	CreateListApi createListApi = new CreateListApi();
	GetCardApi getCardApi = new GetCardApi();
	CreateCardApi createCardApi = new CreateCardApi();
	ReadTestDataFromExcel readData = new ReadTestDataFromExcel();
	JSONMethods jsonMethods = new JSONMethods();
	

	
	@BeforeTest
	public void setup() {
		RestAssured.baseURI = "https://api.trello.com/1";
	}
	
	@DataProvider(name = "data-provider")
	public Object[][] checkexcelData() throws FilloException {
		Object [][] testdata = readData.testFillo();
		return testdata;
		
	}
	
	@Test(dataProvider = "data-provider")
	public void testscenarios(String key, String token, String boardName, String listname, String card) {
		System.out.println("key = " + key);
		System.out.println("Token = " + token);
	}
	
	
	@Test(dataProvider = "data-provider")
	public void test1(String key, String token, String boardName, String listname, String cardName) {
		List<String> listOfBoards = new ArrayList<String>();
		List<String> listofLists = new ArrayList<String>();
		List<String> listOfCards = new ArrayList<String>();
		Response res = getBoardId.getBoardIds(key, token);
		System.out.println("res = " + res.asString());
		List<JSONObject> boardsList = new ArrayList<JSONObject>();
		boardsList = jsonMethods.getArrayOfObjects(res.asString());		
		System.out.println("boardslist = " + boardsList);
		
		//Check if the list size is zero, create the board
		
		if(boardsList.size() == 0) {
			//create board
			Response res1 = createBoardApi.createBoard(key, token, boardName);
			System.out.println("createBoardApi = " +res1.asString());
			assertEquals(createBoardApi.getStatusCode(), 200, "Status code for createBoardAPi is not as expected");
			JSONObject obj = new JSONObject(res1.asString());
			listOfBoards.add(obj.getString("name"));
			listOfBoards.add(obj.getString("id"));
			
			Response res2 = getBoardId.getBoardIds(key, token);
			System.out.println("getBoardIds = " + res2.asString());
			boardsList = jsonMethods.getArrayOfObjects(res2.asString());
			assertEquals(getBoardId.getStatusCode(res2), 200, "Status code for GetBoardID API is not as expected");
			assertEquals(getBoardId.getBoardName(boardsList), listOfBoards.get(0), "The Board name is not matching");
			
		}
		else {
			listOfBoards.add(boardsList.get(0).getString("name"));
			listOfBoards.add(boardsList.get(0).getString("id"));
		}
		
		System.out.println("board = " + listOfBoards);
		
		/**************************List Starts*****************************/
		Response res3 = getListApi.getListsInBoard(key, token, getBoardId.getBoardId(boardsList));
		System.out.println("GetListApi1 = " + res3.asString());
		
		List<JSONObject> listsList = new ArrayList<JSONObject>();
		listsList = jsonMethods.getArrayOfObjects(res3.asString());
		if(listsList.size() == 0) {
			Response res4 = createListApi.createList(key, token, boardName, listname);
			System.out.println("createListApi = " + res4.asString());
			JSONObject obj = new JSONObject(res4.asString());			
			assertEquals(createListApi.getStatusCode(), 200, "Status code mismatch");			
			listofLists.add(obj.getString("id"));
			listofLists.add(obj.getString("name"));
			listofLists.add(obj.getString("idBoard"));		
			
			Response res5 = getListApi.getListsInBoard(key, token, getBoardId.getBoardId(boardsList));
			System.out.println("GetListApi = " + res5.asString());
			assertEquals(getListApi.getStatusCode(res5), 200, "Status code didn't match for getListApi");
			listsList = jsonMethods.getArrayOfObjects(res5.asString());
			assertEquals(getListApi.getListId(listsList) , listofLists.get(0), "list id created is not matching the list id in get request");
			assertEquals(getListApi.getListName(listsList), listofLists.get(1), "list name created is not matching the list name in get request");
			assertEquals(getListApi.getBoardId(listsList), listofLists.get(2), "board Id created is not matching the board id in get request");
		}
		else {
			
			listofLists.add(listsList.get(0).getString("id"));
			listofLists.add(listsList.get(0).getString("name"));
			listofLists.add(listsList.get(0).getString("idBoard"));
		}
		
		/********************List Ends**************************/		
		/********************Card Begins************************/
		Response res6 = getCardApi.getCardsInList(key, token, getListApi.getListId(listsList));
		System.out.println("getCardApi = " + res6.asString());
		List<JSONObject> cardsList = new ArrayList<JSONObject>();
		cardsList = jsonMethods.getArrayOfObjects(res6.asString());
		System.out.println("GetCardApi = " + res6.asString());
		
			Response res7 = createCardApi.createCard(key, token, getListApi.getListId(listsList), cardName);
			System.out.println("createCardApi = " + res7.asString());
			assertEquals(createCardApi.getStatusCode(), 200, "Status code did not match for creating new card");
			JSONObject obj = new JSONObject(res7.asString());
			listOfCards.add(obj.getString("id"));
			listOfCards.add(obj.getString("name"));
			listOfCards.add(obj.getString("idList"));
			listOfCards.add(obj.getString("idBoard"));
			System.out.println("listOfCards = " + listOfCards);
			
			Response res8 = getCardApi.getCardsInList(key, token, getListApi.getListId(listsList));
			System.out.println("GetCardApi = " + res8.asString());
			assertEquals(getCardApi.getStatusCode(), 200, "Status codes didn't match");
			cardsList = jsonMethods.getArrayOfObjects(res8.asString());
			int size = cardsList.size();
			if(cardsList.size() == 1) {
				assertEquals(getCardApi.getCardId(cardsList), listOfCards.get(0), "Card not created successfully as cards id didn't match");
				assertEquals(getCardApi.getCardName(cardsList), listOfCards.get(1), "Card not created as card names mismatched");
				assertEquals(getCardApi.getListId(cardsList), listOfCards.get(2), "Card not created as list ids didn't match");
				assertEquals(getCardApi.getBoardId(cardsList), listOfCards.get(3), "card not created as board ids didn't match");
			}
			else {
				assertEquals(getCardApi.getLastCardId(cardsList, size), listOfCards.get(0), "Card not created successfully as cards id didn't match");
				assertEquals(getCardApi.getLastCardName(cardsList, size), listOfCards.get(1), "Card not created as card names mismatched");
				assertEquals(getCardApi.getLastListId(cardsList, size), listOfCards.get(2), "Card not created as list ids didn't match");
				assertEquals(getCardApi.getLastBoardId(cardsList, size), listOfCards.get(3), "card not created as board ids didn't match");
			}
		
		/********************Card Ends**************************/
	}
	


}
