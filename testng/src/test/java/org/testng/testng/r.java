package org.testng.testng;

import org.testng.annotations.*;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import org.testng.Assert;

public class r {
	int intRandParam = 0 + (int) (Math.random() * 1452222);
	double doubleRandParam = 0.0 + (int) (Math.random() * 233333.666);

	@DataProvider
	 public Object getData() {
		Object[] array = new Object[5];
		for (int i = 0; i < 5; i++) {
			array[i] = new Object();
			intRandParam = 0 + (int) (Math.random() * 1452222);
			array[i] = intRandParam;
		}
		return array;
	}

	@BeforeGroups(groups = "int")

	public void startInt() {
		System.out.println("Starting int tests ....");
	}

	@AfterGroups(groups = "int")

	public void finishInt() {
		System.out.println("Complete int tests ....");
	}

	@BeforeGroups(groups = "double")

	public void startDouble() {
		System.out.println("Starting double tests ....");
	}

	@AfterGroups(groups = "double")

	public void finishDouble() {
		System.out.println("Complete double tests ....");
	}

	@BeforeGroups(groups = "string")

	public void startString() {
		System.out.println("Starting string tests ....");
	}

	@AfterGroups(groups = "string")

	public void finishString() {
		System.out.println("Complete string tests ....");
	}

	@Test(groups = "int")
	public void testRandInt() {
		baseURI = "https://api.vk.com/api.php";
		Response response =

				given().contentType(ContentType.JSON)
						.queryParams("oauth", 1, "method", "apps.getRandomInt", "max", intRandParam).when().get().then()
						.statusCode(200).extract().response();

		int resp = (Integer) response.path("response");

		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertNotNull(response.path("response"));
		Assert.assertTrue(resp >= 0 && resp <= intRandParam);

	}

	@Test(groups = "double", dependsOnGroups = "int")
	public void testRandDouble() {

		baseURI = "https://api.vk.com/api.php";
		Response response =

				given().contentType(ContentType.JSON)
						.queryParams("oauth", 1, "method", "apps.getRandomInt", "max", doubleRandParam).when().get()
						.then().statusCode(200).extract().response();

		int resp = (Integer) response.path("response");

		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertNotNull(response.path("response"));
		Assert.assertTrue(resp >= 0 && resp <= doubleRandParam);
	}

	@Test(groups = "int")
	public void negativeTestRandInt() {
		baseURI = "https://api.vk.com/api.php";
		int negativeRandIntParam = intRandParam * (-1);
		Response response =

				given().contentType(ContentType.JSON)
						.queryParams("oauth", 1, "method", "apps.getRandomInt", "max", negativeRandIntParam).when()
						.get().then().statusCode(200).extract().response();

		String respValue = response.path("error.request_params.value[2]");
		String negativeRandIntParamToSTRING = Integer.toString(negativeRandIntParam);

		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertEquals(100, response.path("error.error_code"));
		Assert.assertNotNull(response.path("error.error_msg"));
		Assert.assertEquals(negativeRandIntParamToSTRING, respValue);
	}

	@Test(groups = "double", dependsOnGroups = "int")
	public void negativeTestRandDouble() {

		baseURI = "https://api.vk.com/api.php";
		String negativeDoubleParam = intRandParam + "," + intRandParam % 6;

		Response response =

				given().contentType(ContentType.JSON)
						.queryParams("oauth", 1, "method", "apps.getRandomInt", "max", negativeDoubleParam).when().get()
						.then().statusCode(200).extract().response();

		String respValue = response.path("error.request_params.value[2]");

		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertEquals(100, response.path("error.error_code"));
		Assert.assertNotNull(response.path("error.error_msg"));
		Assert.assertEquals(negativeDoubleParam, respValue);
	}

	@Test(groups = "string", dependsOnGroups = "double")
	public void testRandString() {

		StringBuffer randStringParam = new StringBuffer();
		for (int x = 0; x < 8; x++) {
			randStringParam.append((char) ((int) (Math.random() * 26) + 97));
		}

		baseURI = "https://api.vk.com/api.php";
		Response response =

				given().contentType(ContentType.JSON)
						.queryParams("oauth", 1, "method", "apps.getRandomInt", "max", randStringParam).when().get()
						.then().statusCode(200).extract().response();

		String respValue = response.path("error.request_params.value[2]");

		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertEquals(100, response.path("error.error_code"));
		Assert.assertNotNull(response.path("error.error_msg"));
		Assert.assertEquals(randStringParam.toString(), respValue);

	}

	@Test(groups = "int")
	public void testMaxInt() {

		baseURI = "https://api.vk.com/api.php";
		int maxIntParam = 2147483647;
		Response response =

				given().contentType(ContentType.JSON)
						.queryParams("oauth", 1, "method", "apps.getRandomInt", "max", maxIntParam).when().get().then()
						.statusCode(200).extract().response();

		int resp = (Integer) response.path("response");

		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertNotNull(response.path("response"));
		Assert.assertTrue(resp >= 0 && resp <= maxIntParam);
	}

	@Test(groups = "int")
	public void testMaxIntPlus1() {

		baseURI = "https://api.vk.com/api.php";
		int maxIntParam = 2147483647;


		int maxIntParamPlus1 = maxIntParam + 3;


		Response response =

				given().contentType(ContentType.JSON)
						.queryParams("oauth", 1, "method", "apps.getRandomInt", "max", maxIntParamPlus1).when().get()
						.then().statusCode(200).extract().response();

		String respValue = response.path("error.request_params.value[2]");
		String maxIntParamPlus1ToSTRING = Integer.toString(maxIntParamPlus1);

		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertEquals(100, response.path("error.error_code"));
		Assert.assertNotNull(response.path("error.error_msg"));
		Assert.assertEquals(maxIntParamPlus1ToSTRING, respValue);
	}

	@Test(groups = "int", dataProvider = "getData")
	public void testWithDataProvider(int params) {

		baseURI = "https://api.vk.com/api.php";
		Response response =

				given().contentType(ContentType.JSON)
						.queryParams("oauth", 1, "method", "apps.getRandomInt", "max", params).when().get().then()
						.statusCode(200).extract().response();

		int resp = (Integer) response.path("response");

		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertNotNull(response.path("response"));
		Assert.assertTrue(resp >= 0 && resp <= params);
		// System.out.println(response.asString());
		// System.out.println(response.asString());
		// System.out.println("Hello, Aleksey");
	}	
}
