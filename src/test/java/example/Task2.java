package example;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class Task2 {
	private static final String url = "https://dummyjson.com";
	    @Test
    public void testSearchProducts() {
        
        Response response = given()
        	.queryParam("q", "phone")
            .when()
            .get(url + "/products/search");
        

        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Status Code: " + response.getStatusCode());
        

        Assert.assertFalse(response.asString().isEmpty());
        System.out.println("Response is not empty: " + !response.asString().isEmpty());
        System.out.println("____________________________________");
        

        List<Integer> ids = response.jsonPath().getList("products.id");
        List<String> titles = response.jsonPath().getList("products.title");
        List<String> categories = response.jsonPath().getList("products.category");
        
        Assert.assertTrue(ids.size() > 0);
        Assert.assertEquals(ids.size(), titles.size());
        Assert.assertEquals(ids.size(), categories.size());
        
        for (int i = 0; i < ids.size(); i++) {
        	Assert.assertTrue(ids.get(i) > 0);
            Assert.assertNotNull(titles.get(i));
            Assert.assertNotNull(categories.get(i));
            
        }
        
    }
}
