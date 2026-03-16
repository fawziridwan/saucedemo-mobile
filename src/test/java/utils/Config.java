package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Config {
    private static WebDriver driver;

    @Before
    public void setUpAppium() throws IOException {
        String currentDir = System.getProperty("user.dir");
        String A = "\\src\\test\\resources\\config.properties";
        System.out.println(currentDir + A);
        Properties prop = new Properties();
        InputStream input = new FileInputStream(currentDir + "\\" + A);
        prop.load(input);

        String deviceName = prop.getProperty("device.name");
        String udid = prop.getProperty("udid");
        String platformName = prop.getProperty("platform.name");
        String appPackage = prop.getProperty("app.package");
        String appActivity = prop.getProperty("app.activity");
        String automationName = prop.getProperty("automation.name");
        String platformVersion = prop.getProperty("platform.version");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        caps.setCapability(MobileCapabilityType.UDID, udid);
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.swaglabsmobileapp");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        caps.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir")+"\\src\\apps\\sample.apk");
        caps.setCapability("appium:automationName", automationName);
        caps.setCapability("appium:appWaitActivity", "com.*");
        caps.setCapability("appActivity", appActivity);
        caps.setCapability("appium:forceAppLaunch", true);
        caps.setCapability("appium:autoGrantPermissions", true);
        caps.setCapability("appium:newCommandTimeout", "600");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        driver.quit();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
