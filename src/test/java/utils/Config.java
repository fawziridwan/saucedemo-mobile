package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
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
    private static String platformName;

    @Before
    public void setUpAppium() throws IOException {
        String currentDir = System.getProperty("user.dir");
        String configPath = "\\src\\test\\resources\\config.properties";
        Properties prop = new Properties();
        InputStream input = new FileInputStream(currentDir + configPath);
        prop.load(input);

        platformName = prop.getProperty("platform.name");
        String deviceName = prop.getProperty("device.name");
        String udid = prop.getProperty("udid");
        String automationName = prop.getProperty("automation.name");
        String platformVersion = prop.getProperty("platform.version");
        String appPath = prop.getProperty("app.path");
        String appPackage = prop.getProperty("app.package");
        String appActivity = prop.getProperty("app.activity");
        String bundleId = prop.getProperty("bundle.id");
        String serverUrl = prop.getProperty("appium.server.url", "http://127.0.0.1:4723/wd/hub");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "600");

        if (appPath != null && !appPath.isEmpty()) {
            caps.setCapability(MobileCapabilityType.APP, currentDir + "\\" + appPath.replace("/", "\\"));
        }

        if (udid != null && !udid.isEmpty()) {
            caps.setCapability(MobileCapabilityType.UDID, udid);
        }

        if (platformName.equalsIgnoreCase("Android")) {
            caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
            caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
            caps.setCapability("appium:appWaitActivity", "com.*");
            caps.setCapability("appium:forceAppLaunch", true);
            caps.setCapability("appium:autoGrantPermissions", true);
            driver = new AndroidDriver<>(new URL(serverUrl.endsWith("/wd/hub") ? serverUrl : serverUrl + "/wd/hub"), caps);
        } else if (platformName.equalsIgnoreCase("iOS")) {
            if (bundleId != null) {
                caps.setCapability("bundleId", bundleId);
            }
            caps.setCapability("appium:useNewWDA", false);
            driver = new IOSDriver<>(new URL(serverUrl.endsWith("/wd/hub") ? serverUrl : serverUrl + "/wd/hub"), caps);
        }

        driver.manage().timeouts().implicitlyWait(Long.parseLong(prop.getProperty("implicit.wait", "30")), TimeUnit.SECONDS);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static String getPlatformName() {
        return platformName != null ? platformName.toLowerCase() : "android";
    }
}
