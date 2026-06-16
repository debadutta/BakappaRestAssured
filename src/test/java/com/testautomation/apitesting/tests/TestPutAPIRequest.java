package com.testautomation.apitesting.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.FileNameConstants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class TestPutAPIRequest extends BaseTest {
	@Test
	public void putAPIRequest() {
		
		try {
			File file1=new File(FileNameConstants.POST_API_REQUEST_BODY);
			String postAPIRequestBody = FileUtils.readFileToString(file1,"UTF-8");
			
			File file2=new File(FileNameConstants.TOKEN_REQUEST_BODY);
			String tokenAPIRequestBody=FileUtils.readFileToString(file2, "UTF-8");
			
			File file3=new File(FileNameConstants.PUT_API_REQUEST_BODY);
			String putAPIRequestBody=FileUtils.readFileToString(file3, "UTF-8");
			
			//POST API - Create Booking and capture booking Id
			Response response = RestAssured
				.given()
					.contentType(ContentType.JSON)
					.body(postAPIRequestBody)
					.baseUri("http://localhost:3001")
				.when()
					.post("/booking")
				.then()
					.assertThat()
					.statusCode(200)
				.extract()
					.response();
			
			int bookingId=response.path("bookingid");
			System.out.println(bookingId);
			
			//GET API - Get booking details
			RestAssured
				.given()
					.contentType(ContentType.JSON)
					.baseUri("http://localhost:3001")
				.when()
					.get("booking/{id}",bookingId)
				.then()
					.assertThat()
					.statusCode(200);
			
			//POST API - Generate Token
			Response tokenResponse = RestAssured
			.given()
				.contentType(ContentType.JSON)
				.body(tokenAPIRequestBody)
				.baseUri("http://localhost:3001")
			.when()
				.post("/auth")
			.then()
				.assertThat()
				.statusCode(200)
//				.log().ifValidationFails()
				.extract()
				.response();
				
//			System.out.println(tokenResponse.asPrettyString());
			String authToken = tokenResponse.path("token");
			System.out.println(authToken);
//			String authToken = JsonPath.read(tokenResponse.body().asString(),"$.token");
			
			//PUT API - Update booking
			Response putResponse = RestAssured
				.given()
					.contentType(ContentType.JSON)
					.body(putAPIRequestBody)
					.headers("Cookie","token="+authToken)
					.baseUri("http://localhost:3001")
				.when()
					.put("/booking/{b_id}",bookingId)
				.then()
					.assertThat()
					.statusCode(200)
					.body("firstname", equalTo("James"))
					.body("lastname", equalTo("Brown"))
					.body("bookingdates.checkin", equalTo("2018-01-01"))
					.extract().response();
			
				Assert.assertEquals(putResponse.path("bookingdates.checkout"), "2019-01-01");	
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
