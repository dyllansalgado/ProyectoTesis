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

    /**
     * 
     */
    /**@Test
    @Order(1)
    public void pruebaRegister(){
        driver.get("http://localhost:3000/registrarse");
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='Nombre de Usuario']")).sendKeys("pepito");
        driver.findElement(By.xpath("//input[@placeholder='Apellido de Usuario']")).sendKeys("perez");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("SoyUnCorreoNuevo@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        // Se selecciona el tema como servicio al cliente
        Select rol = new Select(driver.findElement(By.name("id_rol")));
        rol.selectByValue("2");
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la pagina inicial, ya que se ha creado
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    @Test
    @Order(2)
    public void pruebaRegisterFail(){
        driver.get("http://localhost:3000/registrarse");
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("SoyUnCorreoErrado@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
    }

    @Test
    @Order(3)
    public void pruebaRegisterCorreoUsado(){
        driver.get("http://localhost:3000/registrarse");
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='Nombre de Usuario']")).sendKeys("pepito");
        driver.findElement(By.xpath("//input[@placeholder='Apellido de Usuario']")).sendKeys("perez");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("SoyUnCorreoNuevo@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        try{
            Thread.sleep(2500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'El correo se encuentra utilizado')]"));
        Assertions.assertNotNull(message);
    }
    @Test
    @Order(4)
    public void pruebaLoginFail(){
        driver.get("http://localhost:3000/");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("SoyUnCorreoErrado@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la pagina inicial, ya que no existe usuario.
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(5)
    public void pruebaLoginVacio(){
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
    }*/

    @Test
    @Order(6)
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

    /**@Test
    @Order(7)
    public void pruebaCrearProyecto(){
        driver.get("http://localhost:3000/main");
        driver.navigate().refresh();
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click crear proyecto
        WebElement button = driver.findElement(By.id("crearProyecto"));
        button.click();
        driver.findElement(By.xpath("//input[@placeholder='Proyecto....']")).sendKeys("Soy un proyecto de selenium");
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("24-12-2022");
        driver.findElement(By.xpath("//input[@placeholder='Objetivos del proyecto']")).sendKeys("Soy un objetivo del proyecto de selenium");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("1234");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la página de main.
        String expected = "http://localhost:3000/main/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    @Test
    @Order(8)
    public void pruebaCrearProyectoFail(){
        driver.get("http://localhost:3000/main");
        driver.navigate().refresh();
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click crear proyecto
        WebElement button = driver.findElement(By.id("crearProyecto"));
        button.click();
        driver.findElement(By.xpath("//input[@placeholder='Proyecto....']")).sendKeys("Soy un proyecto de selenium");
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("24-12-2022");
        driver.findElement(By.xpath("//input[@placeholder='Objetivos del proyecto']")).sendKeys("Soy un objetivo del proyecto de selenium");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
    }*/
}