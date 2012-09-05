package com.blah.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MainClass {

	
//	public static void main(String[] args) {
//		WebDriver driver = new HtmlUnitDriver();
//		driver.get("http://www.espncricinfo.com/ci/engine/current/match/scores/liveframe.html?mode=realtime");
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		System.out.println(driver.getPageSource());
//
//		List<WebElement> matches = driver.findElements(By
//				.className("match_box"));
//		StringBuilder buffer = new StringBuilder();
//		for (WebElement match : matches) {
//			buffer.append("---------------------------------------------------\n");
//			buffer.append(
//					match.findElement(By.className("potMatchHeading"))
//							.findElement(By.xpath("./span/a")).getText())
//					.append('\n');
//			buffer.append(getElementText("mat_scores", match)).append('\n');
//			buffer.append(getElementText("mat_players", match)).append('\n');
//			buffer.append(getElementText("mat_status", match)).append('\n');
//			buffer.append("---------------------------------------------------\n");
//		}
//		System.out.println(buffer.toString());
//	}
	 

	public static void main(String[] args) {
		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://www.espncricinfo.com/ci/engine/current/match/scores/liveframe.html?mode=realtime");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println(driver.getPageSource());

		List<WebElement> matches = driver.findElements(By
				.className("match_box"));
		Match bean = null;
		List<Match> matchList = new ArrayList<Match>();

		for (WebElement match : matches) {
			String matchHeading = match
					.findElement(By.className("potMatchHeading"))
					.findElement(By.xpath("./span/a")).getText();
			String matchScores = getElementText("mat_scores", match);
			String matchPlayers = getElementText("mat_players", match);
			String matchStatus = getElementText("mat_status", match);

			bean = new Match();
			bean.setMatchName(matchHeading.substring(0,
					matchHeading.indexOf(" at")).trim());
			bean.setLocation(matchHeading.substring(
					matchHeading.indexOf("at ") + 3, matchHeading.length())
					.trim());
			bean.setScore(matchScores);
			bean.setPlayerScore(matchPlayers);
			bean.setMatchStatus(matchStatus);
			matchList.add(bean);
			System.out.println(bean);
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(matchList);
		System.out.println(json);
	}

	private static String getElementText(String cssClassName, WebElement element) {
		String text = null;
		try {
			WebElement child = element.findElement(By.className(cssClassName));
			if (child != null) {
				text = child.getText();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return text;
	}

}