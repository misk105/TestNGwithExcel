package example;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Task1 {
	private static final String url = "https://jsonplaceholder.typicode.com/todos/";
    @Test
    public void testTodosAPI() {
        
        for (int id = 1; id <= 6; id++) {
        	
            System.out.println("ID: "+id);
            
            Response response = given()
                    .when()
                    .get(url + id);
            
            Assert.assertEquals(response.getStatusCode(), 200);
            System.out.println("Status Code: " + response.getStatusCode());


            Assert.assertFalse(response.asString().isEmpty());
            System.out.println("Response is not empty: " + !response.asString().isEmpty());

            
            int userId = response.jsonPath().getInt("userId");
            int returnedId = response.jsonPath().getInt("id");
            String title = response.jsonPath().getString("title");

            Assert.assertEquals(returnedId, id);
            Assert.assertTrue(userId > 0);
            Assert.assertNotNull(title);

            
        }
    }
}