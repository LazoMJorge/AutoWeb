package org.example.Utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShot {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreenShot.class);

    public static String captureScreenshot(WebDriver driver, String folder, String screenName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String cleanName = screenName.replaceAll("[\\\\/:*?\"<>|]", "_");
        String screenShotPath = System.getProperty("user.dir") + folder + File.separator
                + "evidencia_" + getCurrentDateTime() + "_" + cleanName + ".png";
        try {
            File dest = new File(screenShotPath);
            if (dest.getParentFile() != null && !dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            FileHandler.copy(src, dest);
        } catch (IOException e) {
            LOGGER.warn("No se pudo capturar pantalla: {}", e.getMessage());
        }
        return screenShotPath;
    }

    public static String getCurrentDateTime() {
        DateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return customFormat.format(new Date());
    }

    public static String getResourcePath(String path) {
        return System.getProperty("user.dir") + path;
    }

    public static void createFolder(String folderName) {
        File folder = new File(getResourcePath(folderName));
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}
