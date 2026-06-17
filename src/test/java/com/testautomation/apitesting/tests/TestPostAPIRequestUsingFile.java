package com.testautomation.apitesting.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.FileNameConstants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;

public class TestPostAPIRequestUsingFile extends BaseTest{
	@Test
	public void postAPIRequest() {
		File file=new File(FileNameConstants.POST_API_REQUEST_BODY);
		try {
			String requestBody = FileUtils.readFileToString(file,"UTF-8");
//			System.out.println(requestBody);
			Response response = RestAssured
				.given()
					.contentType(ContentType.JSON)
					.body(requestBody)
					.baseUri("http://localhost:3001/booking")
				.when()
					.post()
				.then()
					.assertThat()
					.statusCode(200)
					.extract()
					.response();
			JSONArray checkinArray = JsonPath.read(response.body().asString(),"$.booking..bookingdates..checkin");
			JSONArray checkoutArray = JsonPath.read(response.body().asString(),"$.booking..bookingdates..checkout");
			String checkinDate = (String) checkinArray.get(0);
	        String checkoutDate = (String) checkoutArray.get(0);
//			String checkinDate=response.path("booking.bookingdates.checkin");
			Assert.assertEquals(checkinDate, "2026-06-01");
//			String checkoutDate=response.path("booking.bookingdates.checkout");
			Assert.assertEquals(checkoutDate, "2026-06-05");
			int bookingId=response.path("bookingid");
			System.out.println("bookingId = "+bookingId);
			
			RestAssured
				.given()
					.contentType(ContentType.JSON)
					.baseUri(FileNameConstants.BASE_URI+"/booking")
				.when()
					.get("/{id}",bookingId)
				.then()
					.assertThat()
					.statusCode(200);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
