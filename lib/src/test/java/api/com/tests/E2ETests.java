package api.com.tests;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import api.com.core.BaseTest;
import api.com.core.Resources;
import api.com.pojo.CreateOrderReqPayload;
import api.com.pojo.CreateOrderResPayload;
import api.com.pojo.LoginResPayload;
import api.com.pojo.OrdersPojo;
import api.com.util.ExtentReport;
import api.com.util.Helper;
import api.com.util.LoginReqPayload;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class E2ETests extends BaseTest {
	String serverAdd;
	String resource;
	String endPoint;
	private String accessToken;
	private String id;
	private String productID;
	private CreateOrderReqPayload OrderDetails;
	private OrdersPojo orders;
	private List<OrdersPojo> lst;
	private List<String> orderId;
	private CreateOrderResPayload orderRes;
	
	
	
	@Test(description = "Login to app and fetch the access token", groups = "RegressionSuite")
	public void loginApp() throws FileNotFoundException, IOException {
		ExtentReport.extentReport.startTest("loginApp", "Login to app and fetch the access token");
		serverAdd = Helper.configReader("baseUrl");
		resource = Resources.loginAPI.getResource();
		endPoint = serverAdd + resource;
		Response res = given()
						.log().all()
						.header("Content-type", "application/json")
						.body(IOUtils.toString(new FileInputStream(LoginReqPayload.readLoginReq()), "UTF-8"))
						.when()
						.post(endPoint);

		Assert.assertEquals(res.statusCode(), 200);
		System.out.println(res.body().asString());
		LoginResPayload resBody = res.body().as(LoginResPayload.class);
		accessToken = res.path("token");
		System.out.println(accessToken);
		id = resBody.getUserId();
		// System.out.println(id);

	}

	@Test(description = "verify to create a product in app", dependsOnMethods = "loginApp", groups = "RegressionSuite")
	public void createProduct() throws IOException {
		ExtentReport.extentReport.startTest("createProduct", "Create a product and validate status code");
		String prodApi = Resources.createProductAPI.getResource();
		// serverAdd = Helper.configReader("baseUrl");
		String productRes = serverAdd + prodApi;
		System.out.println(productRes);
		Response res = given()
						.log().all()
						.header("Authorization", accessToken)
						.param("productName", "NikeShoes")
						.param("productAddedBy", id).param("productCategory", "fashion").param("productSubCategory", "shoes")
						.param("productPrice", "1000").param("productDescription", "Just Do It").param("productFor", "Men")
						.multiPart("productImage", new File("C:\\Users\\Lenovo\\Desktop\\Nike_Shoes.jpg"))
						.when()
						.post(productRes);

		Assert.assertEquals(res.statusCode(), 201);
		System.out.println(res.body().asString());
		productID = res.path("productId");

	}

	@Test(description = "Verify to Create order for existing product", dependsOnMethods = "createProduct", groups = "RegressionSuite")
	public void createOrder() {
		ExtentReport.extentReport.startTest("createOrder", "Create an order and validate status code");
		OrderDetails = new CreateOrderReqPayload();
		String createOrder = Resources.createOrderAPI.getResource();
		String orderEndPoint = serverAdd + createOrder;
		System.out.println(orderEndPoint);

		orders = new OrdersPojo();
		orders.setCountry("India");
		orders.setProductOrderedId(productID);

		lst = new ArrayList();
		lst.add(orders);

		OrderDetails.setOrders(lst);
		

		Response res = given()
						.log().all()
						.header("Content-Type", "application/json")
						.header("Authorization", accessToken)
						.body(OrderDetails)
						.when()
						.post(orderEndPoint);
		//System.out.println(res.body().asString());
		orderRes = res.body().as(CreateOrderResPayload.class);
		System.out.println(orderRes);
		orderId = orderRes.getOrders();
		System.out.println(orderId);
		Assert.assertEquals(res.statusCode(), 201);

	}
	
	@Test(description = "Verify to get order details", dependsOnMethods = "createOrder", groups = {"RegressionSuite", "SanitySuite"})
	public void getOrderdetails() {
		ExtentReport.extentReport.startTest("getOrderDetails", "Retreive order details and validate response body");
		String getOrderDetails = Resources.viewOrderAPI.getResource();
		String getOrderEndPoint = serverAdd + getOrderDetails;
		Response res = given()
						.header("Authorization", accessToken)
						.queryParam("id", orderId)
						.when()
						.get(getOrderEndPoint);
		String orderId  = res.path("data._id");
		String orderBy = res.path("data.orderBy");
		System.out.println("The order Id from get order details is " + orderId);
		Assert.assertEquals(orderBy, "Johnson.kudumula09@gmail.com");
		
	}
	
	
	@Test(description = "Delete product details", dependsOnMethods = "createOrder", groups = "RegressionSuite")
	public void deleteOrder() {
		ExtentReport.extentReport.startTest("deleteOrder", "delete order and validate response code");
		String delete = Resources.deleteOrderAPI.getResource();
		String deleteEndPoint = serverAdd + delete;
		Response res = given()
						.header("Authorization", accessToken)
						.pathParam("productId", productID)
						.when()
						.delete(deleteEndPoint);
		System.out.println(res.body().asString());
		Assert.assertEquals(res.statusCode(), 200);
		
	}
	
	

}
