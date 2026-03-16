package utils;

import org.openqa.selenium.By;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlReader {
    private static Map<String, Object> locators;

    static {
        try {
            Yaml yaml = new Yaml();
            locators = new java.util.HashMap<>();
            String[] files = {"login.yaml", "products.yaml", "cart.yaml", "checkout.yaml"};
            
            for (String fileName : files) {
                InputStream inputStream = YamlReader.class
                        .getClassLoader()
                        .getResourceAsStream("locators/" + fileName);
                if (inputStream != null) {
                    Map<String, Object> moduleLocators = yaml.load(inputStream);
                    if (moduleLocators != null) {
                        String moduleName = fileName.replace(".yaml", "");
                        // Standardize module name to first letter uppercase for consistency with previous calls
                        String formattedModuleName = moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1) + "Page";
                        locators.put(formattedModuleName, moduleLocators);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load locator yaml files");
        }
    }

    @SuppressWarnings("unchecked")
    public static String getLocatorValue(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> currentMap = locators;
        String locatorValue = "";

        for (int i = 0; i < keys.length; i++) {
            Object value = currentMap.get(keys[i]);
            if (i == keys.length - 1) {
                locatorValue = value.toString();
            } else {
                currentMap = (Map<String, Object>) value;
            }
        }
        return locatorValue;
    }

    @SuppressWarnings("unchecked")
    public static By getLocator(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> currentMap = locators;
        String locatorValue = "";

        for (int i = 0; i < keys.length; i++) {
            Object value = currentMap.get(keys[i]);
            if (i == keys.length - 1) {
                locatorValue = value.toString();
            } else {
                currentMap = (Map<String, Object>) value;
            }
        }

        if (locatorValue.startsWith("//") || locatorValue.startsWith("(")) {
            return By.xpath(locatorValue);
        } else if (locatorValue.startsWith("id=")) {
            return By.id(locatorValue.substring(3));
        } else if (locatorValue.startsWith("name=")) {
            return By.name(locatorValue.substring(5));
        } else if (locatorValue.startsWith("accessibility=")) {
            // Note: For Appium, this is usually MobileBy.AccessibilityId or By.ByAccessibilityId
            // But since we are using standard By, we might need to handle it depending on what's available
            return By.xpath("//*[@content-desc='" + locatorValue.substring(14) + "']");
        } else {
            return By.xpath(locatorValue); // Default to xpath
        }
    }
}
