package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions
(features= {"src/test/resources/features"},
plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"} ,
glue="stepDefinition")

public class TestRunner extends AbstractTestNGCucumberTests{

}











	
	
	
	
