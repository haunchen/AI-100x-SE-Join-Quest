package com.example;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/chinese-chess.feature",
    glue = "com.example",
    plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class RunCucumberTest {
}