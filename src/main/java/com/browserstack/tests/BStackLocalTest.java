package com.browserstack.tests;

import org.openqa.selenium.WebDriver;

import com.browserstack.driver.DriverFactory;

/**
 * Local test — proves the BrowserStack Local tunnel is connected.
 *
 * With {@code browserstackLocal: true} in browserstack.yml, the SDK starts a
 * Local tunnel, so the remote browser can reach http://bs-local.com:45454.
 * The page served there has the title "BrowserStack Local".
 */
public class BStackLocalTest implements Runnable {

    @Override
    public void run() {
        try {
            main(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        WebDriver driver = DriverFactory.createDriver();
        try {
            driver.get("http://bs-local.com:45454/");

            String title = driver.getTitle();
            System.out.println("Local page title: " + title);

            if (!"BrowserStack Local".equals(title)) {
                throw new AssertionError(
                        "Title does not match 'BrowserStack Local' (got '" + title + "')");
            }

            System.out.println("Local test passed!");
        } finally {
            driver.quit();
        }
    }
}
