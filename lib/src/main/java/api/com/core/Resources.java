package api.com.core;
//Enum is a special class which is used to maintain colection of constants or methods
public enum Resources{
	
	loginAPI("api/ecom/auth/login"),
	createProductAPI("api/ecom/product/add-product"),
	createOrderAPI("api/ecom/order/create-order"),
	deleteOrderAPI("api/ecom/product/delete-product/{productId}"),
	viewOrderAPI("api/ecom/order/get-orders-details");
	private String resource;
	
	Resources(String resource){
		this.resource = resource;
		
	}
	
	
	public String getResource() {
		
		return resource;
		
	}
	
	
}