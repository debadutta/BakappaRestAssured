package com.testautomation.apitesting.tests;

// Grabs given(), when(), response(), etc.
import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.FileNameConstants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class TestPostAPIRequestUsingPojos extends BaseTest{
	
	@Test
	public void postAPIRequest() {
		BookingDates bookingDates= new BookingDates("2026-06-06", "2026-06-09");
		Booking booking = new Booking("Minakshi", "Singh", "French Dinner", 20000, false, bookingDates);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
//			System.out.println(requestBody);
			
			Booking bookingDetails = objectMapper.readValue(requestBody, Booking.class);
//			System.out.println(bookingDetails.getFirstname());
//			System.out.println(bookingDetails.getBookingdates().getCheckin());
			String jsonSchema = FileUtils.readFileToString(new File(FileNameConstants.JSON_SCHEMA),"UTF-8");
			System.out.println(jsonSchema);
			System.out.println("*************POST*************");
			 Response response = RestAssured
				.given()
					.contentType(ContentType.JSON)
					.body(requestBody)
					.baseUri("http://localhost:3001").log().all()
				.when()
					.post("booking")
				.then()
					.assertThat()
					.statusCode(200).log().all()
					.extract()
					.response();
			 int bookingId=response.path("bookingid");
			 System.out.println("*************GET*************");
			 	given()
			 		.contentType(ContentType.JSON)
			 		.baseUri("http://localhost:3001").log().all()
			 	.when()
			 		.get("/booking/{b_id}",bookingId)
			 	.then()
			 		.assertThat()
			 		.statusCode(200)
			 		.log().all()
//			 		.body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
			 		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("expectedjsonschema.txt"));
			 		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
