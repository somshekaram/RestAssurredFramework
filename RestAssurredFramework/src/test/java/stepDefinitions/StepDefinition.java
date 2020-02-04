package stepDefinitions;
import static io.restassured.RestAssured.given;



import java.io.IOException;

import static org.junit.Assert.*;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class StepDefinition extends Utils{
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	 Response response;
	 static String place_id;
	 TestDataBuild data=new TestDataBuild();

	 @Given("Add place payload with {string} {string} {string}")
	 public void add_place_payload_with(String name , String language, String address) throws IOException {
//		AddPlace p=new AddPlace();
//		p.setAccuracy(50);
//		p.setAddress("29, side layout, cohen 09");
//		p.setLanguage("French-IN");
//		p.setPhone_number("(+91) 983 893 3937");
//		p.setName("Frontline house");
//		p.setWebsite("http://google.com");
//		List<String> mylist=new ArrayList();
//		mylist.add("shoe park");
//		mylist.add("shop");
//		p.setTypes(mylist);
//		Location l=new Location();
//		l.setLat(-38.383494);
//		l.setLng(33.427362);
//		
//		p.setLocation(l);
//	
		
		
		//APIResources.valueOf(addPlceAPI);
		reqSpec=given().spec(requestSpecification())
				.body(data.addPlacePayload(name,language, address));
		
//	    response=reqSpec.when().post("/maps/api/place/add/json").
//	    then().assertThat().statusCode(200).extract().response();
//	    String responseString=response.asString();
	}

	 @When("user calls {string} with {string} http request")
	 public void user_calls_with_http_request(String resource, String method) {
		 
		 //constructor will be called with valueOf resource
		
		APIResources  apiResource=APIResources.valueOf(resource);
		System.out.println(apiResource.getResource());
		resSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST"))
	     response=reqSpec.when().post(apiResource.getResource());
		else if(method.equalsIgnoreCase("GET"))
			response=reqSpec.when().get(apiResource.getResource());
	    	//	. then().spec(resSpec).extract().response();
	    String responseString=response.asString();
	    //System.out.println(responseString);
	}

	@Then("the API call is sucess with status code {int}")
	public void the_API_call_is_sucess_with_status_code(Integer int1) {
	    assertEquals(response.getStatusCode(), 200);
	}

	@Then("{string} in response body is {string}")
	public void in_respoonse_body_is(String keyValue, String expectedValue) {
	   //	String responseString=response.asString();
	   // System.out.println(responseString);
	   // JsonPath jp=new JsonPath(responseString);
	    assertEquals(getJsonPath(response,keyValue),expectedValue);
	    
	}
  
	@Then("verify placeid created that map to {string} using {string}")
	public void verify_placeid_created_that_map_to_using(String expectedName, String resource) throws IOException {
	  //
		
		place_id=getJsonPath(response,"place_id" );
		System.out.println("Place_id"+place_id);
		reqSpec=given().spec(requestSpecification()).queryParam("place_id",place_id);
		user_calls_with_http_request(resource,"GET");
		String actualName=getJsonPath(response,"name");
		assertEquals(actualName,expectedName);
	}
	

@Given("DeletePlace payload")
public void deleteplace_payload() throws IOException {
   reqSpec= given().spec(requestSpecification()).body(data.delePlacePayload(place_id));
}


	
	
}
