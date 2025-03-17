package dev.tanhu.service.content.acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/create",
                    "src/test/resources/features/read",
                    "src/test/resources/features/delete",
                    "src/test/resources/features/update"},

        glue = {"dev.tanhu.service.content.acceptance.steps.create",
                "dev.tanhu.service.content.acceptance.steps.read",
                "dev.tanhu.service.content.acceptance.steps.delete",
                "dev.tanhu.service.content.acceptance.steps.update"}
)
public class CucumberTest {
}
