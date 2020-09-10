package baseTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import io.restassured.RestAssured;
import utils.ExtentReportListner;

@Listeners(ExtentReportListner.class)
public class BaseTest extends ExtentReportListner{
	
	@BeforeClass
	public void baseTest() {
		 String baseUri="http://10.172.145.139:10000/v1/";
		//RestAssured.baseURI = FileandEnv.envAndFile().get("ServerUrl");
		// String baseUri="http://10.172.145.139:10000
		 RestAssured.baseURI=baseUri;
	}

}
