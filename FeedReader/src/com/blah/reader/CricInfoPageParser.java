package com.blah.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("serial")
public class CricInfoPageParser extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://www.espncricinfo.com/ci/engine/current/match/scores/liveframe.html?mode=realtime");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println(driver.getPageSource());

		List<WebElement> matches = driver.findElements(By
				.className("match_box"));
		Match bean = null;
		List<Match> matchList = new ArrayList<Match>();
		
		for (WebElement match : matches) {
			String matchHeading = match.findElement(By.className("potMatchHeading")).findElement(By.xpath("./span/a")).getText();
			String matchScores = getElementText("mat_scores", match);
			String matchPlayers = getElementText("mat_players", match);
			String matchStatus = getElementText("mat_status", match);
			
			bean = new Match();
			bean.setMatchName(matchHeading.substring(0, matchHeading.indexOf(" at")).trim());
			bean.setLocation(matchHeading.substring(matchHeading.indexOf("at ")+3,matchHeading.length()).trim());
			bean.setScore(matchScores);
			bean.setPlayerScore(matchPlayers);
			bean.setMatchStatus(matchStatus);
			matchList.add(bean);
		}

		Gson gson =  new GsonBuilder().setPrettyPrinting().create(); //new Gson();
		String json = gson.toJson(matchList);
		
		resp.setContentType("application/json");
		resp.getWriter().println(json);
		resp.getWriter().flush();
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
