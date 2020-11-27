package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/** Created by Ankish Sharma on 11/09/2020. */
public class DriverUtils {

  public static WebDriver _driver;

  @BeforeSuite(alwaysRun = true)
  public void setUp() {}

  public static WebDriver getDriver() {
    if (_driver == null) {
      WebDriverManager.chromedriver().setup();
      _driver = new ChromeDriver();
      _driver.manage().window().maximize();
    }
    return _driver;
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    _driver.close();
    _driver.quit();
  }

  public static String takeScreenshot()  {
    TakesScreenshot scrShot =((TakesScreenshot)_driver);
    File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
    File destFile = new File(System.getProperty("user.dir")+"/src/test/resources/screenshots/screenshot"+new Random().nextInt(1000)+".jpg" );
    try {
      FileUtils.copyFile(SrcFile,destFile);
      return destFile.getAbsolutePath().replaceAll(System.getProperty("user.dir"),"");
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}
