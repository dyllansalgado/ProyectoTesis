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
    /*@Test
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
    public void pruebaIngresarAProyecto(){
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
        // Se hace click en ir a glosarios
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
        // Se hace click en ir a glosarios
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("crearGlosario"));
        button.click();
        // Se hace click en ir a glosarios
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
        // Se hace click en ir a glosarios
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
        // Se hace click en ir a glosarios
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
        // Se hace click en ir a glosarios
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
        // Se hace click en No
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
        driver.findElement(By.xpath("//input[@placeholder='Nombre de Usuario']")).sendKeys("usuario");
        driver.findElement(By.xpath("//input[@placeholder='Apellido de Usuario']")).sendKeys("usuario");
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("usuario@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("1");
        // Se selecciona el tema como servicio al cliente
        Select rol = new Select(driver.findElement(By.name("id_rol")));
        rol.selectByValue("2");
        // Bajamos con el scroll para ver todo el form.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        try{
            Thread.sleep(4500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try{
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("usuario@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("1");
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
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("1234");
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
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("admin@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("1");
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
    } */
    @Test
    @Order(1)
    public void login(){
        driver.navigate().refresh();
        driver.findElement(By.xpath("//input[@placeholder='ejemplo@gmail.com']")).sendKeys("admin@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='*****']")).sendKeys("1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Se verifica la redirección a la página de creación de cuenta
        // Se hace click en mis proyectos
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
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
        // Se hace click en ingresarTema
        WebElement button4 = driver.findElement(By.id("ingresarTema"));
        button4.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Se hace click en preguntasSeleccionadas
        WebElement button5 = driver.findElement(By.id("preguntasSeleccionadas"));
        button5.click();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String expected = "http://localhost:3000/preguntasSeleccionadas/2/2/3";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }
}