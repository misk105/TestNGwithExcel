package loginTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;


import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;

    
    String chromePath = "C:\\Users\\2022\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    String url        = "https://certwcs.frontgate.com/?aka_bypass=5C73514EE7A609054D81DE61DD9CA3D6";
    String excelPath  = "src/test/resources/TestData.xlsx";
    
    private final By accountButtonAfterLogin = By.cssSelector("button.c-button.t-header__my-account.t-header__fades-in.is--tertiary-btn");
    private final By welcomeText = By.xpath("//*[contains(@class,'welcome')]");

    @BeforeMethod
    public void setUp() throws Exception {
        ExcelUtile.openFile(excelPath, "Sheet1");
        System.setProperty("webdriver.chrome.driver", ConfigReader.getChromePath());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(ConfigReader.getAppUrl());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testLogin() throws Exception {

        
        String email    = ExcelUtile.read(1, 1); 
        String password = ExcelUtile.read(1, 2); 

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        
        wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button.c-button.t-header__my-account"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Sign In / Register"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("input#email"))).sendKeys(email);

        driver.findElement(By.cssSelector("input#password")).sendKeys(password);

        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button.login-button.btn.btn-primary")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);

        
        boolean loggedIn = false;
        try {
        	var accountBtn = wait.until(
        	            ExpectedConditions.visibilityOfElementLocated(accountButtonAfterLogin)
        	        );
        	 new Actions(driver).moveToElement(accountBtn).perform();
        	  wait.until(
        	                ExpectedConditions.visibilityOfElementLocated(welcomeText)
        	            ).isDisplayed();
            loggedIn = true;
        } catch (Exception e) {
            loggedIn = false;
        }

        
        ExcelUtile.write(loggedIn ? "Pass" : "Fail", 1, 3); 
    }
}