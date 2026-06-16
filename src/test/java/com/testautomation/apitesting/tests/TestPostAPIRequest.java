package com.testautomation.apitesting.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.FileNameConstants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

public class TestPostAPIRequest extends BaseTest {
	@Test
	public void createBooking() {
//		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		JSONObject booking = new JSONObject();
		JSONObject bookingDates = new JSONObject();

		booking.put("firstname", "Ratnakar");
		booking.put("lastname", "Singh");
		booking.put("totalprice", 100000);
		booking.put("depositpaid", true);
		booking.put("additionalneeds", "Breakfast,Dinner");
		booking.put("bookingdates", bookingDates);

		bookingDates.put("checkin", "2026-06-06");
		bookingDates.put("checkout", "2026-06-09");

		Response response = RestAssured
							.given()
								.contentType(ContentType.JSON)
								.body(booking.toString())
//								.body(booking) // Also works
								.baseUri(FileNameConstants.BASE_URI+"/booking")
//								.log().ifValidationFails()
							.when()
								.post()
							.then()
								.assertThat()
								.statusCode(200)
//				.log().ifValidationFails()
				.body("booking.firstname", Matchers.equalTo("Ratnakar"))
				.body("booking.firstname", Matchers.containsString("atna"))
				.body("booking.lastname", Matchers.equalTo("Singh"))
				.body("booking.totalprice", Matchers.equalTo(100000))
				.body("booking.totalprice", Matchers.greaterThan(99000))
				.body("booking.additionalneeds", Matchers.equalTo("Breakfast,Dinner"))
				.body("booking.bookingdates.checkin", Matchers.equalTo("2026-06-06"))
				.body("booking.bookingdates.checkout", Matchers.equalTo("2026-06-09")).extract().response();

		int bookingId = response.path("bookingid");
		
		RestAssured
			.given()
				.contentType(ContentType.JSON)
				.pathParam("id", bookingId)
				.baseUri(FileNameConstants.BASE_URI+"booking")
			.when()
				.get("{id}")
			.then()
				.assertThat()
				.statusCode(200)
				.body("firstname",Matchers.equalTo("Ratnakar"))
				.body("lastname",Matchers.equalTo("Singh"))
				.body("bookingdates.checkin",Matchers.equalTo("2026-06-06"))
				.body("bookingdates.checkout",Matchers.equalTo("2026-06-09"));
	}

}
