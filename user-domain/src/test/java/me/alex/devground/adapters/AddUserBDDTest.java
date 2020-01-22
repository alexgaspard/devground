package me.alex.devground.adapters;

import io.cucumber.junit.*;
import org.junit.runner.*;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber-report.json"})
public class AddUserBDDTest {

}