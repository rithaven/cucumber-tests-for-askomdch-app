package AddToCartSteps;// Import Cucumber annotations for step definitions
import io.cucumber.java.en.*;

// Selenium WebDriver imports
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

// Time utilities
import java.time.Duration;



public class AddToCartSteps {

    WebDriver driver;          // WebDriver instance to control browser
    WebDriverWait wait;        // Explicit wait to wait for elements

    // 🔧 Debug settings for slow motion
    boolean DEBUG = true;      // Set to true to see slow-motion and highlights
    int STEP_DELAY = 1500;     // 1.5 seconds delay between actions

    /* ----------------- Helper Methods ----------------- */

    // Pause execution for STEP_DELAY milliseconds
    private void pause() {
        if (DEBUG) { // Only pause if DEBUG is true
            try {
                Thread.sleep(STEP_DELAY); // Sleep
            } catch (InterruptedException e) {
                throw new RuntimeException(e); // Wrap exceptions
            }
        }
    }

    // Highlight an element with red border and yellow background
    private void highlight(WebElement element) {
        if (DEBUG) { // Only highlight if DEBUG is true
            JavascriptExecutor js = (JavascriptExecutor) driver; // JS executor
            js.executeScript(
                    "arguments[0].style.border='3px solid red'; arguments[0].style.background='#fff3cd';",
                    element // Apply CSS
            );
            pause(); // Pause to let user see highlight
            js.executeScript(
                    "arguments[0].style.border=''; arguments[0].style.background='';",
                    element // Remove highlight after pause
            );
        }
    }

    // Smoothly scroll an element into center of viewport
    private void smoothScrollTo(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
                element
        );
        pause(); // Pause to let user see scrolling
    }

    /* ----------------- Steps ----------------- */

    // Step for navigating to home page
    @Given("user is on the home page")
    public void user_is_on_the_home_page() {
        driver = new ChromeDriver(); // Launch Chrome browser
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10s

        driver.manage().window().maximize(); // Maximize browser
        pause(); // Pause to see window maximize

        driver.get("https://askomdch.com/"); // Open home page
        pause(); // Pause to see page load
    }

    // Step for adding featured product to cart
    @When("user adds a featured product to the cart")
    public void user_adds_featured_product_to_cart() {

        // Find the "Featured Products" section
        WebElement featuredSection = driver.findElement(
                By.xpath("//h2[contains(text(),'Featured Products')]"));
        smoothScrollTo(featuredSection); // Scroll smoothly to section

        // Find the Add to Cart button for Anchor Bracelet
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@aria-label='Add “Anchor Bracelet” to your cart']")));
        highlight(addToCartBtn); // Highlight button

        addToCartBtn.click(); // Click Add to Cart
        pause(); // Pause to see click action

        // Wait until View Cart link appears after adding product
        WebElement viewCart = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a.added_to_cart.wc-forward")));
        highlight(viewCart); // Highlight View Cart link
    }

    // Step for clicking the View Cart link
    @And("user clicks on View Cart")
    public void user_clicks_on_view_cart() {

        WebElement viewCart = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a.added_to_cart.wc-forward"))); // Wait until visible

        smoothScrollTo(viewCart); // Scroll link to center of screen
        highlight(viewCart);      // Highlight View Cart link
        viewCart.click();         // Click the link
        pause();                  // Pause to see click action

        wait.until(ExpectedConditions.urlToBe("https://askomdch.com/cart/")); // Wait for URL change
        pause(); // Pause to let user see cart page
    }

    // Step for verifying the product appears in the cart
    @Then("the cart page should display the selected product")
    public void cart_page_should_display_product() {
        WebElement productName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Anchor Bracelet')]"))); // Wait for product name
        highlight(productName); // Highlight product name

        Assert.assertEquals("Anchor Bracelet", productName.getText()); // Verify correct product
        pause(); // Pause for visibility
    }

    // Step for verifying the product quantity is 1
    @And("the product quantity should be 1")
    public void product_quantity_should_be_one() {
        WebElement quantityInput = driver.findElement(By.cssSelector("input.qty")); // Locate quantity input
        highlight(quantityInput); // Highlight input

        String quantity = quantityInput.getAttribute("value"); // Get value
        Assert.assertEquals("1", quantity); // Verify quantity is 1
        pause(); // Pause to see value

        driver.quit(); // Close browser
    }
}
