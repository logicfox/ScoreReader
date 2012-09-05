package com.blah.reader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.PropertyResourceBundle;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

@SuppressWarnings("serial")
public class FileReaderServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest rq, HttpServletResponse resp) throws IOException {
			
		try {
			URL url = new URL("http://livechat.rediff.com/sports/score/score.txt");
			
			URLFetchService service = URLFetchServiceFactory.getURLFetchService();
			HTTPRequest request = new HTTPRequest(url, HTTPMethod.GET);
			HTTPResponse response = service.fetch(request);
			
			resp.setContentType("text/plain");
			
			StringBuilder buffer = new StringBuilder();
			PropertyResourceBundle bundle = new PropertyResourceBundle(new StringReader(new String(response.getContent(), Charset.defaultCharset())));
			buffer.append(bundle.getString("message")).append('\n');
			buffer.append(bundle.getString("tagline")).append('\n');
			
			resp.getWriter().println(buffer.toString());
			resp.getWriter().flush();
		} catch (IllegalArgumentException e) {
			resp.sendError(500);
		}
	}
}
