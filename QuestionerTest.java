package baseTest;

import static org.testng.Assert.assertEquals;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import apiConfigs.APIPath;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utils.ExtentReportListner;

import static org.hamcrest.Matchers.*;


@Listeners(ExtentReportListner.class)
public class QuestionerTest extends BaseTest {
/*
	 String baseUri="http://10.172.145.186:10000";
	 String validQueryParamWithData="?productGroupId=sharedandlayered";
	 String invalideQueryParamNodata="?productGroupId=sharedandlayere";
	 String invalideQuestionerResource="/v1/question";
	 String valideQuestionerResource="v1/questions"*/;
	 
	// String queryString= "http://10.172.145.186:10000/v1/questions?productGroupId=sharedandlayered";
	
	@Test(priority=1)
	public void valideResponseDataTest() {

		
		test.log(LogStatus.INFO, "My test is starting.....");
		Response resp = given().contentType(ContentType.JSON)
				        .queryParam(APIPath.apiPath.Key, APIPath.apiPath.ValideValue)
				        .when().get(APIPath.apiPath.GET_SINGLE_Questioner);
	
		Assert.assertEquals(resp.statusCode(),200);
		test.log(LogStatus.PASS, "Successfully validated status code expected 200  and Actual status code is :: " + resp.statusCode());
		resp.then().body("data", hasSize(greaterThan(0)));
		test.log(LogStatus.PASS,resp.prettyPrint());
		test.log(LogStatus.INFO, "Test End...");
		

	}

	@Test(priority=2)
	public void contentNotFoundTest() {

		
		test.log(LogStatus.INFO, "My test is starting.....");
		Response resp = given().contentType(ContentType.JSON)
				        .queryParam(APIPath.apiPath.Key, APIPath.apiPath.InValide_Value)
				        .when().get(APIPath.apiPath.GET_SINGLE_Questioner);
	
		resp.then().body("data",Matchers.hasSize(0));
		test.log(LogStatus.PASS,resp.body().prettyPrint() );
		Assert.assertEquals(resp.statusCode(),204);
		test.log(LogStatus.PASS, "Successfully validated status code expected 204  and Actual status code is :: " + resp.statusCode());
		
		test.log(LogStatus.INFO, "Test End...");
		

	}
	
	@Test(priority=3)
	public void internalServerTest() {

		 Response resp = given().contentType(ContentType.JSON)
				         .queryParam(APIPath.apiPath.Key, APIPath.apiPath.ValideValue)
				        .when().get(APIPath.apiPath.GET_SINGLE_Questioner);
		
		    Assert.assertEquals(resp.statusCode(),500);
			test.log(LogStatus.PASS, "Successfully validated status code expected 500  and Actual status code is :: " + resp.statusCode());
			resp.then().body("data",Matchers.hasSize(0));
			test.log(LogStatus.PASS,resp.body().prettyPrint());
			test.log(LogStatus.INFO, "Test End...");
			
		
	}
}