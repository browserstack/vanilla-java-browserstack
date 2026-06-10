package com.browserstack.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.browserstack.driver.DriverFactory;

/**
 * Sample test — e-commerce add-to-cart flow on https://bstackdemo.com/.
 *
 * Vanilla Java (no test framework): the flow runs inside {@code main()}.
 * The BrowserStack Java SDK ({@code -javaagent:}) instruments the RemoteWebDriver
 * constructor, starts the session, and marks it passed/failed automatically.
 * It also implements {@link Runnable} so it can be driven by ParallelTestRunner.
 */
public class BStackDemoTest implements Runnable {

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
            driver.get("https://bstackdemo.com/");

            // 1. Read the first product's name from the listing.
            WebElement productElement = driver.findElement(By.xpath("//*[@id=\"1\"]/p"));
            String productName = productElement.getText();
            System.out.println("Product on screen: " + productName);

            // 2. Click that product's "Add to cart".
            driver.findElement(By.xpath("//*[@id=\"1\"]/div[4]")).click();

            // 3. Wait for the cart pane to render.
            Thread.sleep(3000);
            WebElement cartContent = driver.findElement(By.cssSelector(".float-cart__content"));
            if (!cartContent.isDisplayed()) {
                throw new AssertionError("Cart pane (float-cart__content) is not displayed");
            }

            // 4. Read the product name shown in the cart and assert it matches.
            WebElement productInCart = driver.findElement(By.xpath("//p[@class='title']"));
            String cartProductName = productInCart.getText();
            System.out.println("Product in cart: " + cartProductName);

            if (!productName.equals(cartProductName)) {
                throw new AssertionError(
                        "Product in cart '" + cartProductName + "' does not match product on screen '"
                                + productName + "'");
            }

            System.out.println("Sample test passed!");
        } finally {
            driver.quit();
        }
    }
}
