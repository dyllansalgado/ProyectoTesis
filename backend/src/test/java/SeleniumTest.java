import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
            Thread.sleep(2000);
        }
        catch(InterruptedException ie) {
        }
    }
    @Test
    @Order(1)
    public void pruebaRegister(){
        driver.get("http://localhost:3000/registrarse");
        driver.navigate().refresh();
        // Bajamos con el scroll para ver todo el form.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.findElement(By.xpath("//input[@placeholder='Nombre de Usuario']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@placeholder='Apellido de Usuario']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("admin@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("1");
        // Se selecciona el tema como servicio al cliente
        Select rol = new Select(driver.findElement(By.name("id_rol")));
        rol.selectByValue("1");
        try{
            Thread.sleep(3500);
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("SoyUnCorreoErrado@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        try{
            Thread.sleep(3000);
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.findElement(By.xpath("//input[@placeholder='Nombre de Usuario']")).sendKeys("pepito");
        driver.findElement(By.xpath("//input[@placeholder='Apellido de Usuario']")).sendKeys("perez");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("admin@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        try{
            Thread.sleep(3000);
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
    }

    @Test
    @Order(6)
    public void pruebaLoginExito(){
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("admin@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la página de creación de cuenta
        String expected = "http://localhost:3000/main";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(7)
    public void pruebaCrearProyecto(){
        driver.get("http://localhost:3000/main");
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
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
            Thread.sleep(3000);
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
    }

    @Test
    @Order(9)
    public void pruebaCerrarSesion(){
        driver.navigate().refresh();
        // Se hace click en cerrar sesion
        WebElement button = driver.findElement(By.id("cerrarSesion"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button2 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button2.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    @Test
    @Order(10)
    public void pruebaLoginExito2(){
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("juan_a@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la página de main
        String expected = "http://localhost:3000/main";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    @Test
    @Order(11)
    public void pruebaCrearIngresarAProyectoFail(){
        driver.get("http://localhost:3000/main");
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click crear proyecto
        WebElement button = driver.findElement(By.id("verMasProyecto"));
        button.click();
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("contrasenafail");
        WebElement button2 = driver.findElement(By.id("botonRegistrarProyecto"));
        button2.click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'La contraseña del proyecto es incorrecta')]"));
        Assertions.assertNotNull(message);
    }

    @Test
    @Order(12)
    public void pruebaCrearIngresarAProyecto(){
        driver.get("http://localhost:3000/main");
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click crear proyecto
        WebElement button = driver.findElement(By.id("verMasProyecto"));
        button.click();
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("1234");
        WebElement button2 = driver.findElement(By.id("botonRegistrarProyecto"));
        button2.click();
        // Se verifica la redirección a la página de main.
        String expected = "http://localhost:3000/main/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(13)
    public void pruebaCerrarSesion2(){
        driver.navigate().refresh();
        // Se hace click en cerrar sesion
        WebElement button = driver.findElement(By.id("cerrarSesion"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button2 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button2.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    @Test
    @Order(14)
    public void pruebaLoginExito3(){
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("admin@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la página de creación de cuenta
        String expected = "http://localhost:3000/main";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    @Test
    @Order(15)
    public void editarProyectoExito(){
        driver.navigate().refresh();
        // Se hace click en mis proyectos
        WebElement button = driver.findElement(By.id("misProyectos"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editar proyecto
        WebElement button2 = driver.findElement(By.id("editarProyecto"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button3 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de proyecto nuevo']")).sendKeys("edit con selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un objetivo nuevo']")).sendKeys("obj edit con selenium");
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("25-12-2022");
        WebElement button4 = driver.findElement(By.id("editarProyecto"));
        button4.click();
        // Se verifica la redirección a la página de mis proyectos
        String expected = "http://localhost:3000/misProyectos";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }


    @Test
    @Order(16)
    public void editarProyectoFail(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editar proyecto
        WebElement button = driver.findElement(By.id("editarProyecto"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button2 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de proyecto nuevo']")).sendKeys("edit con selenium");
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("25-12-2022");
        WebElement button3 = driver.findElement(By.id("editarProyecto"));
        button3.click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
    }

    @Test
    @Order(17)
    public void ingresarAProyecto(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ok
        WebElement button = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button2 = driver.findElement(By.id("volver"));
        button2.click();
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button3 = driver.findElement(By.id("ingresarAproyecto"));
        button3.click();
        // Se verifica la redirección a la página de ingresarAProyecto
        String expected = "http://localhost:3000/ingresarAProyecto/2";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(18)
    public void CrearReunion(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear reunion
        WebElement button = driver.findElement(By.id("crearReunion"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("25-12-2022");
        WebElement button2 = driver.findElement(By.id("nombreProyecto"));
        button2.click();
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un lugar de reunión']")).sendKeys("En selenium");
        // Se hace click en crear reunion
        WebElement button3 = driver.findElement(By.id("reunionCrear"));
        button3.click();
        // Se verifica la redirección a la página de ingresarAProyecto
        String expected = "http://localhost:3000/ingresarAProyecto/2";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(19)
    public void CrearReunionFail(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear reunion
        WebElement button = driver.findElement(By.id("crearReunion"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("25-12-2022");
        WebElement button2 = driver.findElement(By.id("nombreProyecto"));
        button2.click();
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear reunion
        WebElement button3 = driver.findElement(By.id("reunionCrear"));
        button3.click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
    }

    //AHORA DEBERIA HACER PRUEBA DE VOLVER Y EDITAR REUNION.



}