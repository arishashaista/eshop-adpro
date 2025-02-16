package id.ac.ui.cs.advprog.eshop;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class EditProductFunctionalTest {

    @LocalServerPort
    private Integer port;

    private WebDriver driver;
    private String productId;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);

        createProduct("Initial Product", 50);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void createProduct(String name, int quantity) {
        driver.get(String.format("http://localhost:%d/product/create", port));

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys(name);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys(String.valueOf(quantity));

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
    }

    @Test
    void testEditProduct_Success() {
        driver.get(String.format("http://localhost:%d/product/list", port));

        WebElement editButton = driver.findElement(By.linkText("Edit"));
        editButton.click();

        assertEquals("Edit Product", driver.getTitle());

        WebElement nameInput = driver.findElement(By.id("productName"));
        nameInput.clear();
        nameInput.sendKeys("Updated Product Name");

        WebElement quantityInput = driver.findElement(By.id("productQuantity"));
        quantityInput.clear();
        quantityInput.sendKeys("75");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        assertTrue(driver.getCurrentUrl().contains("/product/list"));

        WebElement productTable = driver.findElement(By.tagName("tbody"));
        String pageContent = productTable.getText();
        assertTrue(pageContent.contains("Updated Product Name"));
        assertTrue(pageContent.contains("75"));
    }

    @Test
    void testEditProduct_EmptyName() {
        driver.get(String.format("http://localhost:%d/product/list", port));
        WebElement editButton = driver.findElement(By.linkText("Edit"));
        editButton.click();

        WebElement nameInput = driver.findElement(By.id("productName"));
        nameInput.clear();

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        assertEquals("Edit Product", driver.getTitle());
    }

    @Test
    void testEditProduct_CancelButton() {
        driver.get(String.format("http://localhost:%d/product/list", port));
        WebElement editButton = driver.findElement(By.linkText("Edit"));
        editButton.click();

        WebElement cancelButton = driver.findElement(By.linkText("Cancel"));
        cancelButton.click();

        assertTrue(driver.getCurrentUrl().contains("/product/list"));

        WebElement productTable = driver.findElement(By.tagName("tbody"));
        String pageContent = productTable.getText();
        assertTrue(pageContent.contains("Initial Product"));
        assertTrue(pageContent.contains("50"));
    }

    @Test
    void testEditProduct_NavigationFromList() {
        driver.get(String.format("http://localhost:%d/product/list", port));

        WebElement editButton = driver.findElement(By.linkText("Edit"));
        assertTrue(editButton.isDisplayed());
        assertTrue(editButton.isEnabled());

        editButton.click();

        assertEquals("Edit Product", driver.getTitle());
    }
}