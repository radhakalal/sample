package baseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.relevantcodes.extentreports.LogStatus;

import apiBuilders.ConvertObjectIntoJSON;
import apiBuilders.DifferetTypeOfBodyofSovLocation;
import apiConfigs.APIPath;
import apiVerifications.APIVerification;
import baseTest.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.PostBody;
import utils.ExtentReportListner;
import utils.ExtentReporterNG;

@Listeners(ExtentReportListner.class)
public class Sov_location_Data extends BaseTest {

	DifferetTypeOfBodyofSovLocation typebody = new DifferetTypeOfBodyofSovLocation();
	ConvertObjectIntoJSON convertjson = new ConvertObjectIntoJSON();
	String currentDirectory = System.getProperty("user.dir");

	// SOV data uploaded successfully. 201
	@Test(priority = 1)
	public void validePathWithValideData()  {

		
		System.out.println("file  "+currentDirectory);
		System.out.println( new File("./src/test/resources/Sovdata.xlsx"));
		
		test.log(LogStatus.INFO, "My test is starting.....");
		Response resp=given()
	    .contentType("multipart/form-data")
	    .multiPart("product_line","testing")
	    .multiPart("file", new File("./src/test/resources/Sovdata.xlsx"))
	    .post(APIPath.apiPath.POST_VALID_PATH);

		resp.then().body("message",equalTo("SOV data uploaded successfully."));
		test.log(LogStatus.PASS, "SOV data uploaded successfully.");
		Assert.assertEquals(resp.getStatusCode(),201);
		test.log(LogStatus.PASS, "Successfully validated status code expected 201  and Actual status code is :: " + resp.getStatusCode());

		
		test.log(LogStatus.INFO, "My Test Has been ended ");

	}

	// body is empty. 400 Should not Be Empty
	@Test(priority = 3)
	public void emptyBody() {

		test.log(LogStatus.INFO, "My test is starting.....");
		Response resp=given()
	    .contentType("multipart/form-data")
	    .multiPart("product_line","")
	    .multiPart("file", "")
	    .post(APIPath.apiPath.POST_VALID_PATH);

		Assert.assertEquals(resp.getStatusCode(),400);
		test.log(LogStatus.PASS, "Successfully validated status code expected 400  and Actual status code is :: " + resp.getStatusCode());

		resp.then().body("message",equalTo("Body Should Not bee Empty "));
		test.log(LogStatus.PASS, "Body Should Not bee Empty.");
		test.log(LogStatus.INFO, "My Test Has been ended ");

	}

	// Invalid file formate 400
	@Test(priority = 4)
	public void inValideFileType() throws JsonProcessingException {

		test.log(LogStatus.INFO, "My test is starting.....");
		Response resp=given()
	    .contentType("multipart/form-data")
	    .multiPart("product_line","testing")
	    .multiPart("file", new File("./src/test/resources/statuscode.txt"))
	    .post(APIPath.apiPath.POST_VALID_PATH);

		resp.then().body("message",equalTo("Invalide File Type."));
		test.log(LogStatus.PASS, "Invalide File Type");
		Assert.assertEquals(resp.getStatusCode(),400);
		test.log(LogStatus.PASS, "Successfully validated status code expected 400  and Actual status code is :: " + resp.getStatusCode());

		test.log(LogStatus.INFO, "My Test Has been ended ");

	}

	@Test(priority = 5)
	public void datatypecheckingForProductLine() throws JsonProcessingException {

		test.log(LogStatus.INFO, "My test is starting.....");
		Response resp=given()
	    .contentType("multipart/form-data")
	    .multiPart("product_line",123)
	    .multiPart("file", new File("./src/test/resources/Sovdata.xlsx"))
	    .post(APIPath.apiPath.POST_VALID_PATH);

		Assert.assertEquals(resp.getStatusCode(),400);
		test.log(LogStatus.PASS, "Successfully validated status code expected 400  and Actual status code is :: " + resp.getStatusCode());

		resp.then().body("message",equalTo("Product_line Should bee in Sting Formate"));
		test.log(LogStatus.PASS, "Product_line Should bee in Sting Formate");
		test.log(LogStatus.INFO, "My Test Has been ended ");

	}

	// Oops some error occured, please try again. 500
	@Test(priority = 6)
	public void internalServerError() throws JsonProcessingException {

		
		test.log(LogStatus.INFO, "My test is starting.....");
		Response resp=given()
	    .contentType("multipart/form-data")
	    .multiPart("product_line","testing")
	    .multiPart("file", new File("./src/test/resources/Sovdata.xlsx"))
	    .post(APIPath.apiPath.POST_VALID_PATH);

		Assert.assertEquals(resp.getStatusCode(),500);
		test.log(LogStatus.PASS, "Successfully validated status code expected 500  and Actual status code is :: " + resp.getStatusCode());

		resp.then().body("message",equalTo("Oops some error occured, please try again."));
		test.log(LogStatus.FAIL, "Oops some error occured, please try again.");
		test.log(LogStatus.INFO, "My Test Has been ended ");


	}
	
	@Test(priority = 7)
	public void contentType()   {

		
		test.log(LogStatus.INFO, "My test is starting.....");
		Response resp=given()
	    .contentType(ContentType.JSON)
	    .multiPart("product_line","testing")
	    .multiPart("file", new File("./src/test/resources/Sovdata.xlsx"))
	    .post(APIPath.apiPath.POST_VALID_PATH);

		Assert.assertEquals(resp.getStatusCode(),415);
		test.log(LogStatus.PASS, "Successfully validated status code expected 415  and Actual status code is :: " + resp.getStatusCode());

		resp.then().body("message",equalTo("Unsupport Media Type"));
		test.log(LogStatus.FAIL, "Unsupport Media Type");
		test.log(LogStatus.INFO, "My Test Has been ended ");


	}

	// 204 Record not found
	@Test(priority = 8)
	public void recordNotFound() throws JsonProcessingException {

	}
}