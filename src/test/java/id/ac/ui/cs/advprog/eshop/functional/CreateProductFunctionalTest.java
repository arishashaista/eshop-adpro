package id.ac.ui.cs.advprog.eshop.functional;

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
class CreateProductFunctionalTest {

    @LocalServerPort
    private Integer port;

    private WebDriver driver;

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
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testCreateProduct_Success() {
        driver.get(String.format("http://localhost:%d/product/create", port));

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Sampo Cap Bambang");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("100");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        assertEquals("ADV Shop", driver.getTitle());

        WebElement productTable = driver.findElement(By.tagName("tbody"));
        String pageContent = productTable.getText();
        assertTrue(pageContent.contains("Sampo Cap Bambang"));
        assertTrue(pageContent.contains("100"));
    }

    @Test
    void testCreateProduct_WithEmptyName() {
        driver.get(String.format("http://localhost:%d/product/create", port));

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("100");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        assertEquals("ADV Shop", driver.getTitle());
    }

    @Test
    void testCreateProduct_InvalidQuantity() {
        driver.get(String.format("http://localhost:%d/product/create", port));

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Sampo Cap Bambang");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("abc"); // Invalid quantity

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

    }

    @Test
    void testCreateProductButtonFromList() {
        driver.get(String.format("http://localhost:%d/product/list", port));

        WebElement createButton = driver.findElement(By.linkText("Create Product"));
        createButton.click();

        assertEquals("Create New Product", driver.getTitle());
    }
}