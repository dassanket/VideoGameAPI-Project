package testases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class VideoGameApiTestCases {

	@Test(priority=1)
	public void test_getAllVideoGames()
	{
		given()
		  .when()
		    .get("http://localhost:8080/app/videogames")
		       .then()
		          .statusCode(200);
	} 
	
	@Test(priority=2)
	public void test_addNewVideoGames()
	{
		HashMap data = new HashMap();
		  data.put("id","28");
		  data.put("name","Subway");
		  data.put("releaseDate","2023-06-15T05:01:49.941Z");
		  data.put("reviewScore","6");
		  data.put("category","Race");
		  data.put("rating","Good");
		  
		  Response res =
				  given()
				       .contentType("application/json")
				       .body(data)
				  .when()
				       .post("http://localhost:8080/app/videogames")
		          .then()
		               .statusCode(200)
		               .log().body()   //to get the response body
		               .extract().response();
		  String jsonString = res.asString();
		  Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
	}
	@Test(priority=3)
	public void test_getVideoGame()
	{
		given()
		  .when()
		    .get("http://localhost:8080/app/videogames/28")
		       .then()
		          .statusCode(200)
		          .log().body()   //to get the response body
		          .body("videoGame.id",equalTo("28"))
		          .body("videoGame.name",equalTo("Subway"));
		          
	}
	@Test(priority=4)
	public void test_updateVideoGame()
	{
		HashMap data = new HashMap();
		  data.put("id","4");
		  data.put("name","Paceman");
		  data.put("releaseDate","1998-10-10T05:01:49.941Z");
		  data.put("reviewScore","93");
		  data.put("category","Adventure");
		  data.put("rating","5");
		  given()
		     .contentType("application/json")
		     .body(data)
			 .when()
			       .put("http://localhost:8080/app/videogames/4")
			       .then()
			       .statusCode(200)
			       .log().body()
			       .body("videoGame.id",equalTo("4"))
			       .body("videoGame.name",equalTo("Paceman"));
	}  
	@Test(priority=5)
	public void test_deleteVideoGame()
	{
		Response res =
				  given()
				  .when()
				       .delete("http://localhost:8080/app/videogames/28")
		          .then()
		               .statusCode(200)
		               .log().body()   //to get the response body
		               .extract().response();
		  String jsonString = res.asString();
		  Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
	}
}
