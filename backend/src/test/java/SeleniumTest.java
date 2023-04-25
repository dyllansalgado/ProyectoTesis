import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver","./src/main/resources/chromedriver/chromedriver2.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
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
            Thread.sleep(3000);
        }
        catch(InterruptedException ie) {
        }
    }
    /*@Test
    @Order(1)
    public void pruebaRegister(){
        driver.get("http://localhost:3000/registrarse");
        driver.navigate().refresh();
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Bajamos con el scroll para ver todo el form.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.findElement(By.xpath("//input[@placeholder='Nombre de Usuario']")).sendKeys("usuario");
        driver.findElement(By.xpath("//input[@placeholder='Apellido de Usuario']")).sendKeys("usuario");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("usuario@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Contraseña']")).sendKeys("Contrasena_1");
        driver.findElement(By.xpath("//input[@placeholder='Repetir Contraseña']")).sendKeys("Contrasena_1");
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
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
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
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.findElement(By.xpath("//input[@placeholder='Nombre de Usuario']")).sendKeys("pepito");
        driver.findElement(By.xpath("//input[@placeholder='Apellido de Usuario']")).sendKeys("perez");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("usuario@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Contraseña']")).sendKeys("Contrasena_1");
        driver.findElement(By.xpath("//input[@placeholder='Repetir Contraseña']")).sendKeys("Contrasena_1");
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
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("dyllan_s@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
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
        driver.findElement(By.xpath("//input[@placeholder='Contraseña']")).sendKeys("Contrasena_proyecto");
        driver.findElement(By.xpath("//input[@placeholder='Repetir Contraseña']")).sendKeys("Contrasena_proyecto");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
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
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("usuario@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("Contrasena_1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la página de main
        String expected = "http://localhost:3000/main";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlToBe(expected));
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
        // Se hace click ver mas proyecto
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
    public void pruebaIngresarAProyecto(){
        driver.get("http://localhost:3000/main");
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click ver mas proyecto
        WebElement button = driver.findElement(By.id("verMasProyecto"));
        button.click();
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("Contrasena_proyecto");
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
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("dyllan_s@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
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
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ok
        WebElement button4 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //Se selecciona volver
        WebElement button5 = driver.findElement(By.id("volver"));
        button5.click();
    }

    @Test
    @Order(17)
    public void ingresarAProyecto(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button = driver.findElement(By.id("ingresarAproyecto"));
        button.click();
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
        WebElement hora = driver.findElement(By.id("hora_reunion"));
        hora.sendKeys("10:30");
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
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //Se selecciona volver
        WebElement button4 = driver.findElement(By.id("volver"));
        button4.click();
    }
    @Test
    @Order(20)
    public void EditarReunion(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editarReunion
        WebElement button = driver.findElement(By.id("editarReunion"));
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
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de lugar de reunion nuevo']")).sendKeys("edit selenium lugar de reunion");
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("30-12-2022");
        WebElement hora = driver.findElement(By.id("hora_reunion"));
        hora.sendKeys("12:30");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("editarReunion"));
        button3.click();
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se verifica la redirección a la página de ingresarAProyecto
        String expected = "http://localhost:3000/ingresarAProyecto/2";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(21)
    public void EditarReunionFail(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editarReunion
        WebElement button = driver.findElement(By.id("editarReunion"));
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
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("30-12-2022");
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("editarReunion"));
        button3.click();
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ok
        WebElement button4 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona volver
        WebElement button5 = driver.findElement(By.id("volver"));
        button5.click();
    }
    @Test
    @Order(22)
    public void ingresarReunion(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button = driver.findElement(By.id("ingresarReunion"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se verifica la redirección a la página de ingresarReunion
        String expected = "http://localhost:3000/ingresarReunion/2/2";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(23)
    public void crearTema(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear tema
        WebElement button = driver.findElement(By.id("crearTema"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Nombre de tema...']")).sendKeys("Soy un tema de selenium");
        driver.findElement(By.xpath("//input[@placeholder='Descripción']")).sendKeys("Soy descripcion selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona crear
        WebElement button2 = driver.findElement(By.id("crear"));
        button2.click();
        // Se verifica la redirección a la página de ingresarReunion
        String expected = "http://localhost:3000/ingresarReunion/2/2";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(24)
    public void crearTemaFail(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear tema
        WebElement button = driver.findElement(By.id("crearTema"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Nombre de tema...']")).sendKeys("Soy un tema de selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona crear
        WebElement button2 = driver.findElement(By.id("crear"));
        button2.click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar
        WebElement button3 = driver.findElement(By.className("btn-close"));
        button3.click();
    }

    @Test
    @Order(25)
    public void editarTema(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editar tema
        WebElement button = driver.findElement(By.id("editarTema"));
        button.click();
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
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de tema nuevo']")).sendKeys("Soy un nombre editado de selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una descripción de tema nuevo']")).sendKeys("Soy una descripcion editada de selenium");
        // Se hace click en editar tema
        WebElement button4 = driver.findElement(By.id("editarTema"));
        button4.click();
        // Se verifica la redirección a la página de ingresarReunion
        String expected = "http://localhost:3000/ingresarReunion/2/2";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(26)
    public void editarTemaFail(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editar tema
        WebElement button = driver.findElement(By.id("editarTema"));
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
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de tema nuevo']")).sendKeys("Soy un nombre editado de selenium");
        // Se hace click en editar tema
        WebElement button3 = driver.findElement(By.id("editarTema"));
        button3.click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ok
        WebElement button4 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona volver
        WebElement button5 = driver.findElement(By.id("volver"));
        button5.click();
    }
    
    @Test
    @Order(27)
    public void irAglosarios(){
        // Se hace click en ir a glosarios
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("irGlosario"));
        button.click();
        String expected = "http://localhost:3000/GlosarioReunion/2/2";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(28)
    public void crearGlosario(){
        // Se hace click en crear glosario
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("crearGlosario"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Nombre de glosario...']")).sendKeys("glosario selenium");
        driver.findElement(By.xpath("//input[@placeholder='Descripción']")).sendKeys("descripcion selenium");
        WebElement button2 = driver.findElement(By.id("crear"));
        button2.click();
        String expected = "http://localhost:3000/GlosarioReunion/2/2";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(29)
    public void crearGlosarioFail(){
        // Se hace click en crear glosarios
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("crearGlosario"));
        button.click();
        // Se hace click en ir a glosario
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Nombre de glosario...']")).sendKeys("glosario selenium");
        WebElement button2 = driver.findElement(By.id("crear"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        // Se hace click en ok
        WebElement button3 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar
        WebElement button4 = driver.findElement(By.className("btn-close"));
        button4.click();
    }

    @Test
    @Order(30)
    public void editarGlosario(){
        // Se hace click en editar glosario
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("editarGlosario"));
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
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de glosario nuevo']")).sendKeys("glosario edit selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una descripción de glosario nuevo']")).sendKeys("descripcion edit selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("editarGlosario"));
        button3.click();
        String expected = "http://localhost:3000/GlosarioReunion/2/2";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(31)
    public void editGlosarioFail(){
        // Se hace click en editar glosario
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("editarGlosario"));
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
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de glosario nuevo']")).sendKeys("glosario edit selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("editarGlosario"));
        button3.click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        // Se hace click en ok
        WebElement button4 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona volver
        WebElement button5 = driver.findElement(By.id("volver"));
        button5.click();
    }
    @Test
    @Order(32)
    public void ingresarAGlosario(){
        // Se hace click en ir a glosario
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("ingresarAglosario"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String expected = "http://localhost:3000/ingresarAGlosario/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(33)
    public void crearTermino(){
        // Se hace click en crear término
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("crearTermino"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Nombre de término...']")).sendKeys("Término creado con selenium");
        driver.findElement(By.xpath("//input[@placeholder='Descripción']")).sendKeys("Descripción término selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button2 = driver.findElement(By.id("crear"));
        button2.click();
        String expected = "http://localhost:3000/ingresarAGlosario/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(34)
    public void crearTerminoFail(){
        // Se hace click en crear término
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("crearTermino"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Nombre de término...']")).sendKeys("Término creado con selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button2 = driver.findElement(By.id("crear"));
        button2.click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar
        WebElement button3 = driver.findElement(By.className("btn-close"));
        button3.click();
    }
    @Test
    @Order(35)
    public void editarTermino(){
        // Se hace click en editar término
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("editarTermino"));
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
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de término nuevo']")).sendKeys("Nombre término editado con selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una descripción de término nuevo']")).sendKeys("Descripción término edito con selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("editarTermino"));
        button3.click();
        String expected = "http://localhost:3000/ingresarAGlosario/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(36)
    public void editarTerminoFail(){
        // Se hace click en editar término
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("editarTermino"));
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
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de término nuevo']")).sendKeys("Nombre término editado con selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("editarTermino"));
        button3.click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ok
        WebElement button4 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona volver
        WebElement button5 = driver.findElement(By.id("volver"));
        button5.click(); 
    }
    @Test
    @Order(37)
    public void descargarGlosarioSI(){
        // Se hace click en descargar
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("descargar"));
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
        String expected = "http://localhost:3000/ingresarAGlosario/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    @Test
    @Order(38)
    public void descargarGlosarioNO(){
        // Se hace click en descargar
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("descargar"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en no
        WebElement button2 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--cancel']"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String expected = "http://localhost:3000/ingresarAGlosario/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    @Test
    @Order(39)
    public void elimarTerminoNO(){
        // Se hace click en eliminar término
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("eliminarTermino"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en no
        WebElement button2 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--cancel']"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String expected = "http://localhost:3000/ingresarAGlosario/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(40)
    public void elimarTerminoSi(){
        // Se hace click en eliminar término
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("eliminarTermino"));
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
        String expected = "http://localhost:3000/ingresarAGlosario/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        // Se hace click en volver
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("volver"));
        button3.click();
    }

    @Test
    @Order(41)
    public void eliminarGlosarioNo(){
        // Se hace click en eliminar glosario
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("deleteGlosario"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en No
        WebElement button2 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--cancel']"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String expected = "http://localhost:3000/GlosarioReunion/2/2";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(42)
    public void eliminarGlosarioSi(){
        // Se hace click en eliminar glosario
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("deleteGlosario"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en Si
        WebElement button2 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String expected = "http://localhost:3000/GlosarioReunion/2/2";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        // Se hace click en volver
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("volver"));
        button3.click();
    }
    @Test
    @Order(43)
    public void ingresarTema(){
        driver.navigate().refresh();
        // Se hace click en ingresar tema
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("ingresarTema"));
        button.click();
        String expected = "http://localhost:3000/temaReunion/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    
    @Test
    @Order(44)
    public void crearPregunta(){
        driver.navigate().refresh();
        // Se hace click en crear pregunta
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("crearPregunta"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Pregunta...']")).sendKeys("¿Pregunta selenium?");
        // Se hace click en crear
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button2 = driver.findElement(By.id("crear"));
        button2.click();
        String expected = "http://localhost:3000/temaReunion/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(45)
    public void crearPreguntaFail(){
        driver.navigate().refresh();
        // Se hace click en crear pregunta
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("crearPregunta"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear
        WebElement button2 = driver.findElement(By.id("crear"));
        button2.click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar
        WebElement button3 = driver.findElement(By.className("btn-close"));
        button3.click();
    }

    @Test
    @Order(46)
    public void pruebaVotarPregunta(){
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
        driver.get("http://localhost:3000/registrarse");
        driver.findElement(By.xpath("//input[@placeholder='Nombre de Usuario']")).sendKeys("usuario2");
        driver.findElement(By.xpath("//input[@placeholder='Apellido de Usuario']")).sendKeys("usuario2");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("usuario2@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Contraseña']")).sendKeys("Contrasena_1");
        driver.findElement(By.xpath("//input[@placeholder='Repetir Contraseña']")).sendKeys("Contrasena_1");
        // Bajamos con el scroll para ver todo el form.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        try{
            Thread.sleep(6000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("usuario2@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("Contrasena_1");
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.get("http://localhost:3000/verMasProyecto/2");
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("Contrasena_proyecto");
        WebElement button3 = driver.findElement(By.id("botonRegistrarProyecto"));
        button3.click();
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en mis proyectos
        WebElement button4 = driver.findElement(By.id("misProyectos"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button5 = driver.findElement(By.id("ingresarAproyecto"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button6 = driver.findElement(By.id("ingresarReunion"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar tema
        WebElement button7 = driver.findElement(By.id("ingresarTema"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton votar
        WebElement button8 = driver.findElement(By.id("votarPregunta"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en Si
        WebElement button9 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button9.click();
        String expected = "http://localhost:3000/temaReunion/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(47)
    public void pruebaVotarPreguntaDosVeces(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton votar
        WebElement button = driver.findElement(By.id("votarPregunta"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en Si
        WebElement button2 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button2.click();

        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Ya ha votado por esta pregunta')]"));
        Assertions.assertNotNull(message);
    }

    @Test
    @Order(48)
    public void aceptarPregunta(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
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
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("dyllan_s@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se hace click en mis proyectos
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("misProyectos"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button4 = driver.findElement(By.id("ingresarAproyecto"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button5 = driver.findElement(By.id("ingresarReunion"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar tema
        WebElement button7 = driver.findElement(By.id("ingresarTema"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton aceptar
        WebElement button8 = driver.findElement(By.id("aceptarPregunta"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en Si
        WebElement button9 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button9.click();
        String expected = "http://localhost:3000/temaReunion/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(49)
    public void ingresarPreguntasSeleccionadas(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton ingresar a preguntas seleccionadas
        WebElement button = driver.findElement(By.id("preguntasSeleccionadas"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String expected = "http://localhost:3000/preguntasSeleccionadas/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(50)
    public void responderPreguntaFail(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton responder
        WebElement button = driver.findElement(By.id("responderPregunta"));
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
        // Se selecciona boton responderPregunta
        WebElement button3 = driver.findElement(By.id("responderPregunta"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button4 = driver.findElement(By.id("volver"));
        button4.click();
    }

    @Test
    @Order(51)
    public void responderPregunta(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton responder
        WebElement button = driver.findElement(By.id("responderPregunta"));
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
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una respuesta']")).sendKeys("Soy respuesta selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton responderPregunta
        WebElement button3 = driver.findElement(By.id("responderPregunta"));
        button3.click();
        String expected = "http://localhost:3000/preguntasSeleccionadas/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(52)
    public void editarRespuestaFail(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton editar
        WebElement button = driver.findElement(By.id("editarRespuesta"));
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
        // Se selecciona boton editarRespuesta
        WebElement button3 = driver.findElement(By.id("editarRespuesta"));
        button3.click();
        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button5 = driver.findElement(By.id("volver"));
        button5.click();
    }

    @Test
    @Order(53)
    public void editarRespuesta(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton editar
        WebElement button = driver.findElement(By.id("editarRespuesta"));
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
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una respuesta']")).sendKeys("Soy respuesta edit selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton editarRespuesta
        WebElement button3 = driver.findElement(By.id("editarRespuesta"));
        button3.click();
        String expected = "http://localhost:3000/preguntasSeleccionadas/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(54)
    public void descargarEnPDF(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton descargar
        WebElement button = driver.findElement(By.id("descargar"));
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
        String expected = "http://localhost:3000/preguntasSeleccionadas/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        // Se hace click en cerrar sesion
        WebElement button3 = driver.findElement(By.id("cerrarSesion"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button4 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    @Test
    @Order(55)
    public void CrearRequisito(){
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("usuario2@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("Contrasena_1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la página de creación de cuenta
        // Se hace click en mis proyectos
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("misProyectos"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button4 = driver.findElement(By.id("ingresarAproyecto"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button5 = driver.findElement(By.id("ingresarReunion"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarTema
        WebElement button6 = driver.findElement(By.id("ingresarTema"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en preguntasSeleccionadas
        WebElement button7 = driver.findElement(By.id("preguntasSeleccionadas"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Se hace click en crearRequisito
        WebElement button8 = driver.findElement(By.id("crearRequisito"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button9 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button9.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de requisito']")).sendKeys("requisito selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una breve descripción']")).sendKeys("descripcion selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un valor para prioridad (1 a 5)']")).sendKeys("1");
        Select tipoRequisito = new Select(driver.findElement(By.name("id_tipo_requisito")));
        tipoRequisito.selectByValue("1");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Se hace click crear requisito
        WebElement button10 = driver.findElement(By.id("crearRequisitoBoton"));
        button10.click();
        String expected = "http://localhost:3000/preguntasSeleccionadas/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    @Test
    @Order(56)
    public void CrearRequisitoFail(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crearRequisito
        WebElement button1 = driver.findElement(By.id("crearRequisito"));
        button1.click();
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de requisito']")).sendKeys("requisito selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una breve descripción']")).sendKeys("descripcion selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Se hace click crear requisito
        WebElement button3 = driver.findElement(By.id("crearRequisitoBoton"));
        button3.click();

        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button4 = driver.findElement(By.id("volver"));
        button4.click();
    }
    
    @Test
    @Order(57)
    public void CrearRequisito2(){
        driver.navigate().refresh();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crearRequisito
        WebElement button1 = driver.findElement(By.id("crearRequisito"));
        button1.click();
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de requisito']")).sendKeys("requisito 2 selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una breve descripción']")).sendKeys("descripcion 2 selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un valor para prioridad (1 a 5)']")).sendKeys("2");
        Select tipoRequisito = new Select(driver.findElement(By.name("id_tipo_requisito")));
        tipoRequisito.selectByValue("1");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click crear requisito
        WebElement button3 = driver.findElement(By.id("crearRequisitoBoton"));
        button3.click();
        String expected = "http://localhost:3000/preguntasSeleccionadas/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(58)
    public void AceptarRequisito(){
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
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("dyllan_s@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la página de creación de cuenta
        // Se hace click en mis proyectos
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("misProyectos"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button4 = driver.findElement(By.id("ingresarAproyecto"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button5 = driver.findElement(By.id("ingresarReunion"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarTema
        WebElement button6 = driver.findElement(By.id("ingresarTema"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en preguntasSeleccionadas
        WebElement button7 = driver.findElement(By.id("preguntasSeleccionadas"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Se hace click en crearRequisito
        WebElement button8 = driver.findElement(By.id("irRequisitos"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Se hace click en aceptar requisito
        WebElement button10 = driver.findElement(By.id("aceptarRequisito"));
        button10.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button11 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button11.click();
        String expected = "http://localhost:3000/requisitosCreados/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(59)
    public void EditarRequisito(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editar requisito
        WebElement button2 = driver.findElement(By.id("editarRequisito"));
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de requisito nuevo']")).sendKeys("requisito edit 2 selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una descripción nueva']")).sendKeys("descripcion 2 selenium");
        driver.findElement(By.xpath("//input[@placeholder='1:Alta Prioridad / 5:Baja Prioridad']")).sendKeys("3");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton editar requisito
        WebElement button4 = driver.findElement(By.id("editarRequisitoBoton"));
        button4.click();
        String expected = "http://localhost:3000/requisitosCreados/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(60)
    public void EditarRequisitoFail(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en aceptar requisito
        WebElement button2 = driver.findElement(By.id("editarRequisito"));
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de requisito nuevo']")).sendKeys("requisito edit 2 selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una descripción nueva']")).sendKeys("descripcion 2 selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Se hace click editar requisito
        WebElement button4 = driver.findElement(By.id("editarRequisitoBoton"));
        button4.click();

        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Completa este campo')]"));
        Assertions.assertNotNull(message);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button5 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button6 = driver.findElement(By.id("volver"));
        button6.click();
    }

    @Test
    @Order(61)
    public void RechazarRequisito(){
        try{ 
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en aceptar requisito
        WebElement button2 = driver.findElement(By.id("eliminarRequisito"));
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
        String expected = "http://localhost:3000/requisitosCreados/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(62)
    public void DescargarRequisito(){
        try{ 
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en aceptar requisito
        WebElement button2 = driver.findElement(By.id("descargar"));
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
        String expected = "http://localhost:3000/requisitosCreados/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(63)
    public void RequisitosAceptadosYdescarga(){
        try{ 
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en requisitos aceptados
        WebElement button2 = driver.findElement(By.id("requisitosAceptados"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en descargar
        WebElement button3= driver.findElement(By.id("descargar"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button4 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String expected = "http://localhost:3000/requisitosAceptados/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        // Se hace click en cerrar sesion
        WebElement button5 = driver.findElement(By.id("cerrarSesion"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button6 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    @Order(64)
    public void CrearComentario(){
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("usuario2@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("Contrasena_1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la página de creación de cuenta
        // Se hace click en mis proyectos
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("misProyectos"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button4 = driver.findElement(By.id("ingresarAproyecto"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button5 = driver.findElement(By.id("ingresarReunion"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarTema
        WebElement button6 = driver.findElement(By.id("ingresarTema"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button7 = driver.findElement(By.id("crearPregunta"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Pregunta...']")).sendKeys("Soy pregunta para comentar");
        // Se hace click en crear
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button8 = driver.findElement(By.id("crear"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en comentarPregunta
        WebElement button9 = driver.findElement(By.id("comentarPregunta"));
        button9.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crearcomentario
        WebElement button10 = driver.findElement(By.id("crearComentario"));
        button10.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingresar comentario']")).sendKeys("Soy comentario selenium");
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear
        WebElement button11 = driver.findElement(By.id("crear"));
        button11.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String expected = "http://localhost:3000/comentarPregunta/2/2/3/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(65)
    public void editarComentario(){
        // Se hace click en editar
        WebElement button10 = driver.findElement(By.id("editarComentario"));
        button10.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button11 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button11.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un comentario']")).sendKeys("Soy comentario selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en comentarPregunta
        WebElement button12 = driver.findElement(By.id("editarComentarioBoton"));
        button12.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String expected = "http://localhost:3000/comentarPregunta/2/2/3/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(66)
    public void borrarComentario(){
        // Se hace click en eliminar comentario
        WebElement button1 = driver.findElement(By.id("eliminarComentario"));
        button1.click();
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
        String expected = "http://localhost:3000/comentarPregunta/2/2/3/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        // Se hace click en cerrar sesion
        WebElement button3 = driver.findElement(By.id("cerrarSesion"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button4 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    @Order(67)
    public void eliminarTemaReunionYcerrarProyecto(){        
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("dyllan_s@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se hace click en mis proyectos
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("misProyectos"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button4 = driver.findElement(By.id("ingresarAproyecto"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button5 = driver.findElement(By.id("ingresarReunion"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //Se elimina el tema
        WebElement button6 = driver.findElement(By.id("eliminarTema"));
        button6.click();
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Se hace click en si
        WebElement button7 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //Se selecciona volver
        WebElement button8 = driver.findElement(By.id("volver"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //Se elimina la reunión
        WebElement button9 = driver.findElement(By.id("eliminarReunion"));
        button9.click();
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button10 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button10.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //Se selecciona volver
        WebElement button11 = driver.findElement(By.id("volver"));
        button11.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //Se cierra el proyecto
        WebElement button12 = driver.findElement(By.id("cerrarProyecto"));
        button12.click();
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button13 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button13.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String expected = "http://localhost:3000/misProyectos";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    @Test
    @Order(68)
    public void editarPerfil(){        
        // Se hace click en editar perfil
        WebElement button = driver.findElement(By.id("verPerfil"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button2 = driver.findElement(By.id("editarContraseña"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='********']")).sendKeys("contrasena_edit");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button3 = driver.findElement(By.id("crearCambio"));
        button3.click();
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        String expected = "http://localhost:3000/verPerfil";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        // Se hace click en cerrar sesion
        WebElement button4 = driver.findElement(By.id("cerrarSesion"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button5 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    
    @Test
    @Order(69)
    public void pruebaCambiarRol(){
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("admin@admin.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("contrasenaAdmin");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en usuarios registrados
        WebElement button = driver.findElement(By.id("UsuarioRegistrados"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en modificar roles
        WebElement button2 = driver.findElement(By.id("modificarRol"));
        button2.click();
        try{
            Thread.sleep(3500);
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
        // Se hace click en usuarios modificar rol
        WebElement button4 = driver.findElement(By.id("editarRolBoton"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button5 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar sesion
        WebElement button6 = driver.findElement(By.id("cerrarSesion"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button7 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button7.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    @Test
    @Order(70)
    public void pruebaEliminarProyecto(){
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("admin@admin.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("contrasenaAdmin");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en eliminar proyecto
        WebElement button = driver.findElement(By.id("EliminarProyecto"));
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
        String expected = "http://localhost:3000/main";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    } */


    // A continuación se efectuan las pruebas de integración, donde JF corresponden a las de jefe de proyecto y U a las de usuario.

    // Crear usuario JF, inicio de sesión JF, creación de proyectos, ingresar a proyecto, creación de reunión, creación de tema y cerrar sesión.

    @Test
    @Order(71)
    public void pruebaIntegración1(){
        driver.get("http://localhost:3000/registrarse");
        driver.navigate().refresh();
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Bajamos con el scroll para ver todo el form.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.findElement(By.xpath("//input[@placeholder='Nombre de Usuario']")).sendKeys("Pedrito");
        driver.findElement(By.xpath("//input[@placeholder='Apellido de Usuario']")).sendKeys("Perez");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("Pedrito@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Contraseña']")).sendKeys("Contrasena_1");
        driver.findElement(By.xpath("//input[@placeholder='Repetir Contraseña']")).sendKeys("Contrasena_1");
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.get("http://localhost:3000/");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("dyllan_s@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click crear proyecto
        WebElement button = driver.findElement(By.id("crearProyecto"));
        button.click();
        driver.findElement(By.xpath("//input[@placeholder='Proyecto....']")).sendKeys("Soy un proyecto integración");
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("24-05-2023");
        driver.findElement(By.xpath("//input[@placeholder='Objetivos del proyecto']")).sendKeys("Soy un objetivo del proyecto de integración");
        driver.findElement(By.xpath("//input[@placeholder='Contraseña']")).sendKeys("Contrasena_proyecto1");
        driver.findElement(By.xpath("//input[@placeholder='Repetir Contraseña']")).sendKeys("Contrasena_proyecto1");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click crear proyecto
        WebElement button2 = driver.findElement(By.id("misProyectos"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editar proyecto
        WebElement button3 = driver.findElement(By.id("editarProyecto"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button4 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de proyecto nuevo']")).sendKeys("edit integración con selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un objetivo nuevo']")).sendKeys("obj edit integración con selenium");
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("25-04-2023");
        WebElement button5 = driver.findElement(By.id("editarProyecto"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button6 = driver.findElement(By.id("ingresarAproyecto"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear reunion
        WebElement button7 = driver.findElement(By.id("crearReunion"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("25-04-2022");
        WebElement button8 = driver.findElement(By.id("nombreProyecto"));
        button8.click();
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un lugar de reunión']")).sendKeys("En selenium integración");
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement hora = driver.findElement(By.id("hora_reunion"));
        hora.sendKeys("08:30");
        // Se hace click en crear reunion
        WebElement button9 = driver.findElement(By.id("reunionCrear"));
        button9.click();
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editarReunion
        WebElement button10 = driver.findElement(By.id("editarReunion"));
        button10.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button11 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button11.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de lugar de reunion nuevo']")).sendKeys("edit integración selenium lugar de reunion");
        driver.findElement(By.xpath("//input[@class='pickers']")).sendKeys("30-04-2023");
        WebElement hora2 = driver.findElement(By.id("hora_reunion"));
        hora2.sendKeys("10:30");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button12 = driver.findElement(By.id("editarReunion"));
        button12.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button13 = driver.findElement(By.id("ingresarReunion"));
        button13.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear tema
        WebElement button14 = driver.findElement(By.id("crearTema"));
        button14.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Nombre de tema...']")).sendKeys("Soy un tema de selenium integración");
        driver.findElement(By.xpath("//input[@placeholder='Descripción']")).sendKeys("Soy descripcion selenium integración");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona crear
        WebElement button15 = driver.findElement(By.id("crear"));
        button15.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editar tema
        WebElement button16 = driver.findElement(By.id("editarTema"));
        button16.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button17 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button17.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de tema nuevo']")).sendKeys("Soy un nombre editado integración de selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una descripción de tema nuevo']")).sendKeys("Soy una descripcion editada integración de selenium");
        // Se hace click en editar tema
        WebElement button18 = driver.findElement(By.id("editarTema"));
        button18.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar sesion
        WebElement button19 = driver.findElement(By.id("cerrarSesion"));
        button19.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button20 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button20.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);

    }
    // Crear usuario U, inicio de sesión U, registro de proyecto, ingresar a proyecto, ingresar a reunión, ingresar a tema y cerrar sesión.
    @Test
    @Order(72)
    public void pruebaIntegración2(){
        driver.get("http://localhost:3000/registrarse");
        driver.navigate().refresh();
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.get("http://localhost:3000/");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("Pedrito@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("Contrasena_1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.get("http://localhost:3000/verMasProyecto/2");
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("Contrasena_proyecto1");
        WebElement button = driver.findElement(By.id("botonRegistrarProyecto"));
        button.click();
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en mis proyectos
        WebElement button2 = driver.findElement(By.id("misProyectos"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button3 = driver.findElement(By.id("ingresarAproyecto"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button4 = driver.findElement(By.id("ingresarReunion"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar sesion
        WebElement button5 = driver.findElement(By.id("cerrarSesion"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button6 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button6.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);

    }

    // Inicio de sesión JF, ingresar a proyecto, ingresar a reunión, ingresar a glosarios, crear glosario, ingresar a glosario, crear término, descargar en pdf y cerrar sesión.

    @Test
    @Order(73)
    public void pruebaIntegración3(){
        driver.get("http://localhost:3000/");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("dyllan_s@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en mis proyectos
        WebElement button = driver.findElement(By.id("misProyectos"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button2 = driver.findElement(By.id("ingresarAproyecto"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button3 = driver.findElement(By.id("ingresarReunion"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ir a glosarios
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button4 = driver.findElement(By.id("irGlosario"));
        button4.click();
        // Se hace click en ir a glosarios
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button5 = driver.findElement(By.id("crearGlosario"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // se crea un glosario
        driver.findElement(By.xpath("//input[@placeholder='Nombre de glosario...']")).sendKeys("glosario selenium integración");
        driver.findElement(By.xpath("//input[@placeholder='Descripción']")).sendKeys("descripcion selenium integración");
        WebElement button6 = driver.findElement(By.id("crear"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button7 = driver.findElement(By.id("editarGlosario"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button8 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de glosario nuevo']")).sendKeys("glosario edit selenium integración");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una descripción de glosario nuevo']")).sendKeys("descripcion edit selenium integración");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button9 = driver.findElement(By.id("editarGlosario"));
        button9.click();
        // Se hace click en ir a glosario
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button10 = driver.findElement(By.id("ingresarAglosario"));
        button10.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear término
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button11 = driver.findElement(By.id("crearTermino"));
        button11.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Nombre de término...']")).sendKeys("Término creado con selenium integración");
        driver.findElement(By.xpath("//input[@placeholder='Descripción']")).sendKeys("Descripción término selenium integración");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button12 = driver.findElement(By.id("crear"));
        button12.click();
        // Se hace click en editar término
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button13 = driver.findElement(By.id("editarTermino"));
        button13.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button14 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button14.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de término nuevo']")).sendKeys("Nombre término editado con selenium integración");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una descripción de término nuevo']")).sendKeys("Descripción término edito con selenium integración");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button15 = driver.findElement(By.id("editarTermino"));
        button15.click();
        // Se hace click en descargar
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button16 = driver.findElement(By.id("descargar"));
        button16.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button17 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button17.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en eliminar término
        WebElement button18 = driver.findElement(By.id("eliminarTermino"));
        button18.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button19 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button19.click();
        // Se hace click en volver
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button20 = driver.findElement(By.id("volver"));
        button20.click();
        // Se hace click en eliminar glosario
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button21 = driver.findElement(By.id("deleteGlosario"));
        button21.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en Si
        WebElement button22 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button22.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en volver
        WebElement button23 = driver.findElement(By.id("volver"));
        button23.click();
        // Se hace click en ingresar tema
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button24 = driver.findElement(By.id("ingresarTema"));
        button24.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar sesion
        WebElement button25 = driver.findElement(By.id("cerrarSesion"));
        button25.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button26 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button26.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    // Inicio de sesión U, ingresar a proyecto, ingresar a reunión, ingresar a tema, crear pregunta, votar pregunta y cerrar sesión.

    @Test
    @Order(74)
    public void pruebaIntegración4(){
        driver.get("http://localhost:3000/");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("Pedrito@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("Contrasena_1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en mis proyectos
        WebElement button = driver.findElement(By.id("misProyectos"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button2 = driver.findElement(By.id("ingresarAproyecto"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button3 = driver.findElement(By.id("ingresarReunion"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se ingresa a tema
        WebElement button4 = driver.findElement(By.id("ingresarTema"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear pregunta
        WebElement button5 = driver.findElement(By.id("crearPregunta"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Pregunta...']")).sendKeys("¿Pregunta selenium integración?");
        // Se hace click en crear
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button6 = driver.findElement(By.id("crear"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton votar
        WebElement button7 = driver.findElement(By.id("votarPregunta"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en Si
        WebElement button8 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar sesion
        WebElement button9 = driver.findElement(By.id("cerrarSesion"));
        button9.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button10 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button10.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    // Inicio de sesión JF, ingresar a proyecto, ingresar a reunión, ingresar a tema, aceptar pregunta, crear pregunta y cerrar sesión.

    @Test
    @Order(75)
    public void pruebaIntegración5(){
        driver.get("http://localhost:3000/");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("dyllan_s@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en mis proyectos
        WebElement button = driver.findElement(By.id("misProyectos"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button2 = driver.findElement(By.id("ingresarAproyecto"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button3 = driver.findElement(By.id("ingresarReunion"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se ingresa a tema
        WebElement button4 = driver.findElement(By.id("ingresarTema"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton aceptar
        WebElement button5 = driver.findElement(By.id("aceptarPregunta"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en Si
        WebElement button6 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear pregunta
        WebElement button7 = driver.findElement(By.id("crearPregunta"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Pregunta...']")).sendKeys("¿Pregunta 2 selenium integración?");
        // Se hace click en crear
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button8 = driver.findElement(By.id("crear"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar sesion
        WebElement button9 = driver.findElement(By.id("cerrarSesion"));
        button9.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button10 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button10.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
    // Inicio de sesión U, ingresar a proyecto, ingresar a reunión, ingresar a tema, crear comentario a pregunta, editar comentario, eliminar comentario, ingresar a preguntas seleccionadas, descargar preguntas seleccionadas, crear requisitos, editar requisito y cerrar sesión.
    @Test
    @Order(76)
    public void pruebaIntegración6(){
        driver.get("http://localhost:3000/");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("Pedrito@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("Contrasena_1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en mis proyectos
        WebElement button = driver.findElement(By.id("misProyectos"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button2 = driver.findElement(By.id("ingresarAproyecto"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button3 = driver.findElement(By.id("ingresarReunion"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se ingresa a tema
        WebElement button4 = driver.findElement(By.id("ingresarTema"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en comentarPregunta
        WebElement button5 = driver.findElement(By.id("comentarPregunta"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crearcomentario
        WebElement button6 = driver.findElement(By.id("crearComentario"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingresar comentario']")).sendKeys("Soy comentario integración selenium");
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crear
        WebElement button7 = driver.findElement(By.id("crear"));
        button7.click();
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editar
        WebElement button8 = driver.findElement(By.id("editarComentario"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button9 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button9.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un comentario']")).sendKeys("Soy comentario integracióne edit selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editarComentario
        WebElement button10 = driver.findElement(By.id("editarComentarioBoton"));
        button10.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en eliminar comentario
        WebElement button11 = driver.findElement(By.id("eliminarComentario"));
        button11.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button12 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button12.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en volver
        WebElement button13 = driver.findElement(By.id("volver"));
        button13.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton ingresar a preguntas seleccionadas
        WebElement button14 = driver.findElement(By.id("preguntasSeleccionadas"));
        button14.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton descargar
        WebElement button15 = driver.findElement(By.id("descargar"));
        button15.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button16 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button16.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crearRequisito
        WebElement button17 = driver.findElement(By.id("crearRequisito"));
        button17.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button18 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button18.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de requisito']")).sendKeys("requisito integración selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una breve descripción']")).sendKeys("descripcion integración selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un valor para prioridad (1 a 5)']")).sendKeys("2");
        Select tipoRequisito = new Select(driver.findElement(By.name("id_tipo_requisito")));
        tipoRequisito.selectByValue("1");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click crear requisito
        WebElement button19 = driver.findElement(By.id("crearRequisitoBoton"));
        button19.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ir a requisitos
        WebElement button20 = driver.findElement(By.id("irRequisitos"));
        button20.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en editar
        WebElement button21 = driver.findElement(By.id("editarRequisito"));
        button21.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button22 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button22.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de requisito nuevo']")).sendKeys("requisito edit integración 2 selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una descripción nueva']")).sendKeys("descripcion integración 2 selenium");
        driver.findElement(By.xpath("//input[@placeholder='1:Alta Prioridad / 5:Baja Prioridad']")).sendKeys("3");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton editarRespuesta
        WebElement button23 = driver.findElement(By.id("editarRequisitoBoton"));
        button23.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar sesion
        WebElement button24 = driver.findElement(By.id("cerrarSesion"));
        button24.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button25 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button25.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    // Inicio de sesión JF, ingresar a proyecto, ingresar a reunión, ingresar a tema, ingresar a preguntas seleccionadas, crear respuesta a pregunta, editar la respuesta, ir a requisitos, aceptar requisito y cerrar sesión.

    @Test
    @Order(77)
    public void pruebaIntegración7(){
        driver.get("http://localhost:3000/");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("dyllan_s@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en mis proyectos
        WebElement button = driver.findElement(By.id("misProyectos"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button2 = driver.findElement(By.id("ingresarAproyecto"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button3 = driver.findElement(By.id("ingresarReunion"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se ingresa a tema
        WebElement button4 = driver.findElement(By.id("ingresarTema"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton ingresar a preguntas seleccionadas
        WebElement button5 = driver.findElement(By.id("preguntasSeleccionadas"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton responder
        WebElement button6 = driver.findElement(By.id("responderPregunta"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button7 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una respuesta']")).sendKeys("Soy respuesta integración selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton responderPregunta
        WebElement button8 = driver.findElement(By.id("responderPregunta"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton editar
        WebElement button9 = driver.findElement(By.id("editarRespuesta"));
        button9.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button10 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button10.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una respuesta']")).sendKeys("Soy respuesta edit integración selenium");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton editarRespuesta
        WebElement button11 = driver.findElement(By.id("editarRespuesta"));
        button11.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ir a requisitos
        WebElement button12 = driver.findElement(By.id("irRequisitos"));
        button12.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en aceptar requisito
        WebElement button13 = driver.findElement(By.id("aceptarRequisito"));
        button13.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button14 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button14.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar sesion
        WebElement button15 = driver.findElement(By.id("cerrarSesion"));
        button15.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button16 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button16.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    // Inicio de sesión U , ingresar a proyecto, ingresar a reunión, ingresar a tema, ingresar a preguntas seleccionadas, ingresar a requisitos, ingresar a requisitos aceptados, descargar requisitos aceptados y cerrar sesión.
    
    @Test
    @Order(78)
    public void pruebaIntegración8(){
        driver.get("http://localhost:3000/");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("Pedrito@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("Contrasena_1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en mis proyectos
        WebElement button = driver.findElement(By.id("misProyectos"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button2 = driver.findElement(By.id("ingresarAproyecto"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button3 = driver.findElement(By.id("ingresarReunion"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se ingresa a tema
        WebElement button4 = driver.findElement(By.id("ingresarTema"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton ingresar a preguntas seleccionadas
        WebElement button5 = driver.findElement(By.id("preguntasSeleccionadas"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en crearRequisito
        WebElement button6 = driver.findElement(By.id("crearRequisito"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button7 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un nombre de requisito']")).sendKeys("requisito 2 integración selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese una breve descripción']")).sendKeys("descripcion 2 integración selenium");
        driver.findElement(By.xpath("//input[@placeholder='Ingrese un valor para prioridad (1 a 5)']")).sendKeys("2");
        Select tipoRequisito = new Select(driver.findElement(By.name("id_tipo_requisito")));
        tipoRequisito.selectByValue("1");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click crear requisito
        WebElement button8 = driver.findElement(By.id("crearRequisitoBoton"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ir a requisitos
        WebElement button9 = driver.findElement(By.id("irRequisitos"));
        button9.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en requisitos aceptados
        WebElement button10 = driver.findElement(By.id("requisitosAceptados"));
        button10.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en descargar requisitos aceptados
        WebElement button11= driver.findElement(By.id("descargar"));
        button11.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button12 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button12.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar sesion
        WebElement button13 = driver.findElement(By.id("cerrarSesion"));
        button13.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button14 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button14.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    // Inicio de sesión JF , ingresar a proyecto, ingresar a reunión, ingresar a tema, ingresar a preguntas seleccionadas, ingresar a requisitos, rechazar requisito, eliminar tema, eliminar reunion, cerrar proyecto, editar contraseña y cerrar sesión.

    @Test
    @Order(79)
    public void pruebaIntegración9(){
        driver.get("http://localhost:3000/");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("dyllan_s@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en mis proyectos
        WebElement button = driver.findElement(By.id("misProyectos"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresar a proyecto
        WebElement button2 = driver.findElement(By.id("ingresarAproyecto"));
        button2.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ingresarReunion
        WebElement button3 = driver.findElement(By.id("ingresarReunion"));
        button3.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se ingresa a tema
        WebElement button4 = driver.findElement(By.id("ingresarTema"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se selecciona boton ingresar a preguntas seleccionadas
        WebElement button5 = driver.findElement(By.id("preguntasSeleccionadas"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en ir a requisitos
        WebElement button6 = driver.findElement(By.id("irRequisitos"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en rechazar requisito
        WebElement button7 = driver.findElement(By.id("eliminarRequisito"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button8 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //Se selecciona volver
        WebElement button9 = driver.findElement(By.id("volver"));
        button9.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //Se selecciona volver
        WebElement button10 = driver.findElement(By.id("volver"));
        button10.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //Se selecciona volver
        WebElement button11 = driver.findElement(By.id("volver"));
        button11.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //Se elimina el tema
        WebElement button12 = driver.findElement(By.id("eliminarTema"));
        button12.click();
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button13 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button13.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //Se selecciona volver
        WebElement button14 = driver.findElement(By.id("volver"));
        button14.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //Se elimina la reunión
        WebElement button15 = driver.findElement(By.id("eliminarReunion"));
        button15.click();
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button16 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button16.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //Se selecciona volver
        WebElement button17 = driver.findElement(By.id("volver"));
        button17.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //Se cierra el proyecto
        WebElement button18 = driver.findElement(By.id("cerrarProyecto"));
        button18.click();
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button19 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button19.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Luego se edita el perfil
        WebElement button20 = driver.findElement(By.id("verPerfil"));
        button20.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button21 = driver.findElement(By.id("editarContraseña"));
        button21.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='********']")).sendKeys("1234");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button22 = driver.findElement(By.id("crearCambio"));
        button22.click();
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar sesion
        WebElement button23 = driver.findElement(By.id("cerrarSesion"));
        button23.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button24 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button24.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    // Login usuario admin, cambio  de rol a usuario, eliminar proyecto y cerrar sesión
    @Test
    @Order(80)
    public void pruebaIntegración10(){
        driver.get("http://localhost:3000/");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("admin@admin.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("contrasenaAdmin");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en usuarios registrados
        WebElement button = driver.findElement(By.id("UsuarioRegistrados"));
        button.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en modificar roles
        WebElement button2 = driver.findElement(By.id("modificarRol"));
        button2.click();
        try{
            Thread.sleep(3500);
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
        // Se hace click en usuarios modificar rol
        WebElement button4 = driver.findElement(By.id("editarRolBoton"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button5 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en uvolver
        WebElement button6 = driver.findElement(By.id("Volver"));
        button6.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en eliminar proyecto
        WebElement button7 = driver.findElement(By.id("EliminarProyecto"));
        button7.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en si
        WebElement button8 = driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button8.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en cerrar sesion
        WebElement button9 = driver.findElement(By.id("cerrarSesion"));
        button9.click();
        try{
           Thread.sleep(3000);
        }catch (InterruptedException e){
           e.printStackTrace();
        }
        // Se hace click en si
        WebElement button10= driver.findElement(By.xpath("//div['swal-overlay swal-overlay--show-modal']//div['swal-modal']//div['swal-footer']//div['swal-button-container']//button[@class='swal-button swal-button--confirm']"));
        button10.click();
        // Se verifica la redirección a la página de Login
        String expected = "http://localhost:3000/";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);

    }
}