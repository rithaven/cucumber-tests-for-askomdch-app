package runner; // package for test runner

// Import Cucumber runner
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

// Import JUnit runner
import org.junit.runner.RunWith;

// Use Cucumber as JUnit test runner
@RunWith(Cucumber.class)

// Configure Cucumber options
@CucumberOptions(
        features = "src/test/java/features", // path to .feature files
        glue = {"AddToCartSteps"},           // package containing step definitions
        plugin = {"pretty"},                 // prints readable logs to console
        monochrome = true                    // removes ugly characters from console
)
public class TestRunner {
    // Empty class: Cucumber reads feature files and step definitions automatically
}
