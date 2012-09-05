package com.blah.reader;
import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;


@SuppressWarnings("serial")
public class FeedReaderServlet extends HttpServlet {
	public void doGet(HttpServletRequest rq, HttpServletResponse resp) throws IOException {
		URL url = new URL("http://www.espncricinfo.com/rss/livescores.xml");
		XmlReader xmlReader = new XmlReader(url);
		try {
			resp.setContentType("text/plain");
			SyndFeed feed = new SyndFeedInput().build(xmlReader);
			
			StringBuilder buffer = new StringBuilder();
			for(Object anEntry : feed.getEntries()) {
				SyndEntry entry = (SyndEntry)anEntry;
				buffer.append(entry.getDescription().getValue()).append('\n');
			}
			resp.getWriter().println(buffer.toString());
			resp.getWriter().flush();
		} catch (IllegalArgumentException e) {
			resp.sendError(500);
		} catch (FeedException e) {
			resp.sendError(500);
		}
	}
}
