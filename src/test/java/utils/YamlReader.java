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
        Object value = null;

        for (int i = 0; i < keys.length; i++) {
            value = currentMap.get(keys[i]);
            if (i < keys.length - 1) {
                currentMap = (Map<String, Object>) value;
            }
        }

        if (value instanceof Map) {
            Map<String, String> platformMap = (Map<String, String>) value;
            String platform = Config.getPlatformName();
            return platformMap.getOrDefault(platform, platformMap.getOrDefault("android", ""));
        }

        return value != null ? value.toString() : "";
    }

    @SuppressWarnings("unchecked")
    public static By getLocator(String key) {
        String locatorValue = getLocatorValue(key);

        if (locatorValue.isEmpty()) {
            throw new RuntimeException("Locator not found for key: " + key);
        }

        if (locatorValue.startsWith("//") || locatorValue.startsWith("(")) {
            return By.xpath(locatorValue);
        } else if (locatorValue.startsWith("id=")) {
            return By.id(locatorValue.substring(3));
        } else if (locatorValue.startsWith("name=")) {
            return By.name(locatorValue.substring(5));
        } else if (locatorValue.startsWith("accessibility=")) {
            // Note: For Appium, this is usually MobileBy.AccessibilityId or By.ByAccessibilityId
            // In java-client 7.6.0, we can use MobileBy.AccessibilityId
            return io.appium.java_client.MobileBy.AccessibilityId(locatorValue.substring(14));
        } else {
            return By.xpath(locatorValue); // Default to xpath
        }
    }
}
