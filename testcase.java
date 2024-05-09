package dealsdray_test02;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;


public class testcase {
	public static WebDriver driver;

	public static void takeScreenshot(String resolution, String browsername) throws IOException {
		Screenshot scrShot = new AShot().takeScreenshot(driver);
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		File destDir = new File(System.getProperty("user.dir") + "\\Output\\Dell\\" + resolution);
		if (!destDir.exists()) {
		    destDir.mkdirs(); 
		}
		File destFile = new File(System.getProperty("user.dir") + "\\Output\\Dell\\" + resolution + "\\screenshot-"
				+ browsername +"-"+currentDate+"-"+currentTime.getHour()+":"+currentTime.getMinute()+".png");
		ImageIO.write(scrShot.getImage(), "PNG", destFile);

	}

	public static void runtests(String browsername) throws IOException {
		String[] sites = {"https://www.getcalley.com/", "https://www.getcalley.com/calley-call-from-browser/", "https://www.getcalley.com/calley-pro-features/", "https://www.getcalley.com/best-auto-dialer-app/", "https://www.getcalley.com/how-calley-auto-dialer-app-works/"};
		String[] desktopDimensions = {"1920x1080","1366x768","1536x864"};
		String[] mobileDimensions = {"360x640","414x896","375x667"};
		for (String site : sites) {
		    System.out.print(site + "\n");
			for(String d: desktopDimensions) {
				System.out.println(d);
				String[] dimension = d.split("x");
				driver.manage().window().setSize(new Dimension(Integer.parseInt(dimension[0]), Integer.parseInt(dimension[1])));
				driver.get(site);
				takeScreenshot(d, browsername);
				driver.close();
			}
			for(String m: mobileDimensions) {
				System.out.println(m);
				String[] dimension = m.split("x");
				driver.manage().window().setSize(new Dimension(Integer.parseInt(dimension[0]), Integer.parseInt(dimension[1])));
				driver.get(site);
				takeScreenshot(m, browsername);
				driver.close();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Running tests on Chrome");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		runtests("chrome");
		System.out.println("Running tests on Safari");
		WebDriverManager.safaridriver().setup();
		driver = new SafariDriver();
		runtests("safari");
		System.out.println("Running tests on Firefox");
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		runtests("firefox");
		}
	}
