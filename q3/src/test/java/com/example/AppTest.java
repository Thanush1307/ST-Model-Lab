package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Unit test for simple App.
 */
public class AppTest {
        WebDriver driver;

        @BeforeTest
        public void setup() {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                driver.get("https://www.opentable.com");
                driver.manage().window().maximize();
        }

        @Test(priority = 0)
        public void testCase1() throws IOException, InterruptedException {
                FileInputStream fs = new FileInputStream("E:\\dbankdemo.xlsx");
                XSSFWorkbook work = new XSSFWorkbook(fs);
                XSSFSheet sheet = work.getSheet("login");
                XSSFRow row = sheet.getRow(6);
                String location = row.getCell(0).getStringCellValue();
                driver.findElement(By.xpath("//*[@id=\"mainContent\"]/header/div/span/div/div/div[2]/div[1]/div/input"))
                                .sendKeys(location);
                driver.findElement(By.xpath("//*[@id=\"mainContent\"]/header/div/span/div/div/div[2]/div[2]/button"))
                                .click();
                Thread.sleep(2000);
                driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/section/div[6]/div/label[4]/span[2]"))
                                .click();
                Thread.sleep(2000);
                driver.findElement(By.partialLinkText("Far & East")).click();
                Thread.sleep(2000);
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File source = screenshot.getScreenshotAs(OutputType.FILE);
                String path = "C:\\Users\\Swetha\\Documents\\semester 4\\software testing\\lab Questions\\q3\\hotel.png";
                FileUtils.copyFile(source, new File(path));
                String currWindow = driver.getWindowHandle();
                Set<String> windows = driver.getWindowHandles();
                for (String window : windows) {
                        if (!currWindow.equals(window)) {
                                driver.switchTo().window(window);
                                break;
                        }
                }
                Thread.sleep(4000);
                Select partySize = new Select(
                                driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpPartySizePicker\"]")));
                partySize.selectByIndex(3);
                Thread.sleep(2000);
                driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpDayPicker\"]/div/div/span")).click();
                Thread.sleep(4000);
                while (true) {
                        String curr = driver
                                        .findElement(By.xpath(
                                                        "//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/div"))
                                        .getText();
                        System.out.println(curr);
                        if (curr.equals("November 2024")) {
                                driver.findElement(By.xpath(
                                                "//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/table/tbody/tr[3]/td[2]/button"))
                                                .click();
                                break;
                        }
                        driver.findElement(
                                        By.xpath("//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/div/div[2]/button[2]"))
                                        .click();
                }
                Select time = new Select(
                                driver.findElement(By.xpath("//*[@id=\"restProfileSideBartimePickerDtpPicker\"]")));
                time.selectByVisibleText("6:30 PM");
                Thread.sleep(2000);
             //   driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div[2]/div[2]/div/article/div/div[5]/button"))
                               // .click();
                driver.findElement(By.xpath("//*[@id=\"baseApp\"]/div/header/div[2]/div[2]/div[1]/button")).click();
                Thread.sleep(5000);

        }

        @AfterTest
        public void quit() {
                driver.quit();
        }
}