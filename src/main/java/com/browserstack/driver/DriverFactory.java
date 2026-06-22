package com.browserstack.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Creates a RemoteWebDriver pointed at the BrowserStack hub.
 *
 * No browser/OS capabilities are set in code: the BrowserStack Java SDK
 * (attached via the {@code -javaagent:} jar) reads {@code browserstack.yml},
 * injects the platform matrix, starts the session, and reports the result.
 * Credentials are read from the BROWSERSTACK_USERNAME / BROWSERSTACK_ACCESS_KEY
 * environment variables (or from browserstack.yml).
 */
public class DriverFactory {

    private static final String HUB_URL = "https://hub.browserstack.com/wd/hub";

    public static WebDriver createDriver() {
        try {
            // Empty capabilities — the SDK injects platform + credentials from browserstack.yml.
            DesiredCapabilities capabilities = new DesiredCapabilities();
            return new RemoteWebDriver(new URL(HUB_URL), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid BrowserStack hub URL: " + HUB_URL, e);
        }
    }
}
