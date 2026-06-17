package com.testautomation.apitesting.tests;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.testautomation.apitesting.utils.FileNameConstants;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;

public class TestRestAssured {
	

	@Test
	public void getAllBookings() {
//		Response response=
//			JsonPath j = 
//			Response r=
			ResponseBodyExtractionOptions b = given()
				.contentType(ContentType.JSON)
				.baseUri(FileNameConstants.BASE_URI+"/booking")
			.when()
				.get()
			.then()
				.assertThat()
				.statusCode(200)
//				.body("[1].bookingid",Matchers.equalTo(4))
				.extract()
				.body();
			System.out.println(b.asPrettyString());
	
	}
}
