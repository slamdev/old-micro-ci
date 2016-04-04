package com.github.slamdev.starter;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder.webAppContextSetup;

@Configuration
public class WebDriverProvider {

    private static final Map<Class<? extends WebDriver>, DriverFactory> DRIVER_FACTORIES = new HashMap<>();

    static {
        DRIVER_FACTORIES.put(HtmlUnitDriver.class, c -> webAppContextSetup(c).build());
        DRIVER_FACTORIES.put(ChromeDriver.class, c -> {
            ChromeDriverManager.getInstance().setup();
            return new ChromeDriver();
        });
    }

    @Autowired
    private WebApplicationContext context;

    @Bean
    public WebDriver produceWebDriver() {
        return DRIVER_FACTORIES.get(HtmlUnitDriver.class).create(context);
    }

    private interface DriverFactory<T extends WebDriver> {
        T create(WebApplicationContext context);
    }
}
