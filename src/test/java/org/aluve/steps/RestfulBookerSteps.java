package org.aluve.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RestfulBookerSteps {

    public final RequestSpecBuilder requestSpec;
    public String bookingId;
    public Response response;

    public RestfulBookerSteps() {
        requestSpec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com/");
    }
    @When("the user makes a booking with {string} {string} {string} {string} {string} {string} {string}")
    public void theUserMakesABookingWith(String name, String lastname, String totalprice, String depositpaid, String checkin , String checkout, String additionalneeds) {
        String request = "<booking>\n" +
                "    <firstname>"+name+"</firstname>\n" +
                "    <lastname>"+lastname+"</lastname>\n" +
                "    <totalprice>"+totalprice+"</totalprice>\n" +
                "    <depositpaid>"+depositpaid+"</depositpaid>\n" +
                "    <bookingdates>\n" +
                "        <checkin>"+checkin+"</checkin>\n" +
                "        <checkout>"+checkout+"</checkout>\n" +
                "    </bookingdates>\n" +
                "    <additionalneeds>"+additionalneeds+"</additionalneeds>\n" +
                "</booking>\n";
        EncoderConfig encoderconfig = new EncoderConfig();
        response = given().spec(requestSpec.build()).log().all().config(RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).contentType("text/xml").accept("application/xml").body(request).post("booking");
        }


    @Then("the booking is created successfully")
    public void theBookingIsCreatedSuccessfully() {
        response.then().log().all()
                .statusCode(200)
                .body("bookingid", notNullValue());
        bookingId = response.xmlPath().getString("bookingid");

    }

    @When("the user gets the booking using the id")
    public void theUserGetsTheBookingUsingTheId() {
        response = given().spec(requestSpec.build()).get("booking"+bookingId);
        }

    @Then("the details are correct")
    public void theDetailsAreCorrect() {
        String xmlResponse = response.getBody().asString();

        XmlPath xmlPath = new XmlPath(xmlResponse);
        String firstName = xmlPath.getString("booking.firstname");
        String lastName = xmlPath.getString("booking.lastname");

        assertEquals(firstName, "Jim");
        assertEquals(lastName, "Brown");

    }
}