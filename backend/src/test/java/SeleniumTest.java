import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver","./src/main/resources/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/");
    }

    // Al momento de ejecutar todos los test se cierra el driver del navegador Web
    @AfterAll
    public static void tearDownDriver(){
        if (driver != null) {
            driver.quit();
        }
    }
    // Cuando se realiza un test se debe esperar 5 segundos.
    // Para ver el resultado y dejar que los datos se actualicen
    @AfterEach
    public void afterSleep(){
        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException ie) {
        }
    }

    @Test
    @Order(1)
    public void pruebaLoginFail(){
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("SoyUnCorreoErrado@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la pagina inicial, ya que no existe usuario.
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(2)
    public void pruebaLoginVacio(){
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
    }

    @Test
    @Order(3)
    public void pruebaLoginExito(){
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("juan_a@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la página de creación de cuenta
        String expected = "http://localhost:3000/main";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
}