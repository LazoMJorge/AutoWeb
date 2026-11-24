package org.example.Utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreAndPostCondition {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreAndPostCondition.class);
    private static final ThreadLocal<String> SCREENSHOT_FOLDER = ThreadLocal.withInitial(() -> Parameters.RutaEvidencia);

    public static String getScreenShotFolder() {
        return SCREENSHOT_FOLDER.get();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        LOGGER.info("_____________________________________________________");
        LOGGER.info("Running: {}", scenario.getName());
        OnStage.setTheStage(new OnlineCast());
        String cleanName = scenario.getName().replaceAll("[\\\\/:*?\"<>|]", "_");
        String folder = Parameters.RutaEvidencia + ScreenShot.getCurrentDateTime() + "_" + cleanName;
        SCREENSHOT_FOLDER.set(folder);
        ScreenShot.createFolder(folder);
    }

    @After
    public void afterScenario(Scenario scenario) {
        LOGGER.info("Finishing: {}", scenario.getName());
        if (scenario.isFailed() && ThucydidesWebDriverSupport.isDriverInstantiated()) {
            ScreenShot.captureScreenshot(
                    ThucydidesWebDriverSupport.getDriver(), getScreenShotFolder(), "FALLO");
        }
        SCREENSHOT_FOLDER.remove();
        OnStage.drawTheCurtain();
    }
}
