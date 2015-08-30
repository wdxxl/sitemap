package com.wdxxl.sitemap;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.TimeZone;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.SitemapIndexGenerator;
import com.redfin.sitemapgenerator.W3CDateFormat;
import com.redfin.sitemapgenerator.W3CDateFormat.Pattern;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

/**
 * Demo https://github.com/dfabulich/sitemapgen4j
 */
public class MySiteMap {
	public static void main(String[] args) throws MalformedURLException {
		MySiteMap mySiteMap = new MySiteMap();
		mySiteMap.gettingStart();
		mySiteMap.configuringOptions();
		mySiteMap.configuringMore();
		mySiteMap.configuringDataFormat();
		mySiteMap.siteMapIndexFile();
		mySiteMap.subSiteMap();
		mySiteMap.validateSiteMap();
	}

	private void gettingStart() throws MalformedURLException {
		File myDir = new File(System.getProperty("user.dir") + "/sitemap_index/gettingStart");
		WebSitemapGenerator wsg = new WebSitemapGenerator("http://www.example.com", myDir);
		wsg.addUrl("http://www.example.com/index.html"); // repeat multiple
															// times
		wsg.write();
	}

	private void configuringOptions() throws MalformedURLException {
		File myDir = new File(System.getProperty("user.dir") + "/sitemap_index/configuringOptions");
		WebSitemapGenerator wsg = WebSitemapGenerator.builder("http://www.example.com", myDir).gzip(true).build();
		// enable gzipped output
		wsg.addUrl("http://www.example.com/index.html");
		wsg.write();
	}

	private void configuringMore() throws MalformedURLException {
		File myDir = new File(System.getProperty("user.dir") + "/sitemap_index/configuringMore");
		WebSitemapGenerator wsg = new WebSitemapGenerator("http://www.example.com", myDir);
		WebSitemapUrl url = new WebSitemapUrl.Options("http://www.example.com/index.html").lastMod(new Date())
				.priority(1.0).changeFreq(ChangeFreq.HOURLY).build();
		// this will configure the URL with lastmod=now, priority=1.0,
		// changefreq=hourly
		wsg.addUrl(url);
		wsg.write();
	}

	private void configuringDataFormat() throws MalformedURLException {
		File myDir = new File(System.getProperty("user.dir") + "/sitemap_index/configuringDataFormat");
		// Use DAY pattern (2009-02-07), Greenwich Mean Time timezone
		W3CDateFormat dateFormat = new W3CDateFormat(Pattern.DAY);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		WebSitemapGenerator wsg = WebSitemapGenerator.builder("http://www.example.com", myDir).dateFormat(dateFormat)
				.build(); // actually use the configured dateFormat
		WebSitemapUrl url = new WebSitemapUrl.Options("http://www.example.com/index.html").lastMod(new Date())
				.priority(1.0).changeFreq(ChangeFreq.HOURLY).build();
		wsg.addUrl(url);
		wsg.write();
	}

	private void siteMapIndexFile() throws MalformedURLException {
		File myDir = new File(System.getProperty("user.dir") + "/sitemap_index/siteMapIndexFile");
		WebSitemapGenerator wsg = new WebSitemapGenerator("http://www.example.com", myDir);
		for (int i = 0; i < 60000; i++)
			wsg.addUrl("http://www.example.com/doc" + i + ".html");
		wsg.write();
		wsg.writeSitemapsWithIndex();// generate the sitemap_index.xml
	}

	private void subSiteMap() throws MalformedURLException {
		File myDir = new File(System.getProperty("user.dir") + "/sitemap_index/subSiteMap");
		WebSitemapGenerator wsg;
		// generate foo sitemap
		wsg = WebSitemapGenerator.builder("http://www.example.com", myDir).fileNamePrefix("foo").build();
		for (int i = 0; i < 5; i++)
			wsg.addUrl("http://www.example.com/foo" + i + ".html");
		wsg.write();
		// generate bar sitemap
		wsg = WebSitemapGenerator.builder("http://www.example.com", myDir).fileNamePrefix("bar").build();
		for (int i = 0; i < 5; i++)
			wsg.addUrl("http://www.example.com/bar" + i + ".html");
		wsg.write();
		// generate sitemap index for foo + bar
		File myFile = new File(System.getProperty("user.dir") + "/sitemap_index/subSiteMap/index.xml");
		SitemapIndexGenerator sig = new SitemapIndexGenerator("http://www.example.com", myFile);
		sig.addUrl("http://www.example.com/foo.xml");
		sig.addUrl("http://www.example.com/bar.xml");
		sig.write();
	}

	private void validateSiteMap() throws MalformedURLException{
		File myDir = new File(System.getProperty("user.dir") + "/sitemap_index/validateSiteMap");
		WebSitemapGenerator wsg = WebSitemapGenerator.builder("http://www.example.com", myDir).autoValidate(true)
				.build(); // validate the sitemap after writing
		wsg.addUrl("http://www.example.com/index.html");
		wsg.write();
	}
}
