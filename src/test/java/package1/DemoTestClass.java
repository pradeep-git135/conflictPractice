package package1;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(genericUtility.ListenerImlimentation.class)
public class DemoTestClass {
	
	@Test
	public void tc01() {
		System.out.println("inside tc01");
	}
	@Test
	public void tc02() {
		System.out.println("inside tc02");
		throw new SkipException("intentional skip");
	}
	
	public WebDriver driver;
	@Test
	public void tc03() {
		System.out.println("inside tc03");
		WebDriverManager.chromedriver().setup();
		 driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.amazon.in/");
		Assert.fail();
	}
	

}
