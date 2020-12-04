package com.szk302.sample.selenide.devicefarm;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.devicefarm.*;
import software.amazon.awssdk.services.devicefarm.model.*;

public class SampleTest {
	private static final String GOOGLE_URL = "https://www.google.com/";

	@BeforeClass
	public static void prepare() {
	    System.setProperty("chromeoptions.prefs", "intl.accept_languages=ja,en-us,en");
		CreateTestGridUrlRequest request = CreateTestGridUrlRequest.builder().expiresInSeconds(300) // 5 minutes
				.projectArn(
						"arn:aws:devicefarm:us-west-2:id:testgrid-project:22dc7ed3-7e74-4c32-96f9-8eb3ce810eed")
				.build();
		DeviceFarmClient client = DeviceFarmClient.builder().region(Region.US_WEST_2).build();
		CreateTestGridUrlResponse response = client.createTestGridUrl(request);
        Configuration.remote =response.url();
        Configuration.browserCapabilities.setCapability("platform", Platform.WINDOWS);
        Configuration.browserCapabilities.setCapability("browserName", "chrome");
	}

	@Test
	public void testSomeLibraryMethod() {

		open(GOOGLE_URL);
		screenshot("open_google");
		$(By.name("q")).setValue("Selenide");
		$(By.name("q")).pressEnter();
		screenshot("search_Selenide");

	}
}
