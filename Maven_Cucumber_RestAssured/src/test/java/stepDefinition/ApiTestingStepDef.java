package stepDefinition;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.testng.Assert;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import runner.BeforeStep;
import utils.BaseTest;
import APIHelper.*;

public class ApiTestingStepDef {

	public static String access_token ;
	public static String orderId;
	public Response resp;
	
	
	@Before
	public void beforestep()
	{
		BaseTest.setup(); 
	}
	
	@Given("I want to get access token from PayPal api")
	public void i_want_to_get_access_token_from_PayPal_api()
	{
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("in first function");
		 access_token = given().param("grant_type", "client_credentials")
								.auth().preemptive()
								.basic(BaseTest.client_id,BaseTest.secret)
								.post("/v1/oauth2/token")
								.jsonPath()
								.get("access_token").toString();
		
		System.out.println("Access Token"+access_token);
				 
   }

	@When("I set currency code as {string} & value as {string} and hit the api")
	public void i_set_currency_code_and_value(String currencyCode, String currencyValue) 
	{
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("in second function"+currencyCode);
		System.out.println("in second function"+currencyValue);
		
		ArrayList<PurchaseUnits> list = new ArrayList<PurchaseUnits>();
		list.add(new PurchaseUnits(currencyCode,currencyValue));
		Orders order = new Orders("CAPTURE",list);
		
		resp=given().contentType(ContentType.JSON)
				.auth().oauth2(access_token)	
				.body(order)
				.post("/v2/checkout/orders");
		
		orderId=resp.jsonPath().get("id").toString();	
	}

	
	@ Then("I verify the status as CREATED")
	public void i_verify_the_status_as_created()
	{
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("in third function");
		Assert.assertEquals(resp.jsonPath().get("status").toString(),"CREATED");
		
	}

	
	@When("I get order from the paypal api")
	public void i_get_order_from_the_paypal_api(){
	    // Write code here that turns the phrase above into concrete actions
		resp=given().contentType(ContentType.JSON)
				.auth().oauth2(access_token)
				.get("/v2/checkout/orders"+"/"+orderId);
		
	}

	@When("I verify the status code as {string}")
	public void i_verify_the_status_code_as(String statusCode) 
	{
	    // Write code here that turns the phrase above into concrete actions
		Assert.assertEquals(resp.getStatusCode(), statusCode);
	}
	
}
