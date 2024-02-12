import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


public class Controller {
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "data/crawl";
//        String userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";
        String userAgentString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36";

        // Number of threads to use during crawling. Increasing this typically makes crawling faster. But crawling
        // speed depends on many other factors as well. You can experiment with this to figure out what number of
        // threads works best for you.
        int numberOfCrawlers = 16;

        // for our crawling specs
        CrawlConfig config = new CrawlConfig();

        // Set the folder where intermediate crawl data is stored (e.g. list of urls that are extracted from previously
        // fetched pages and need to be crawled later).
        config.setCrawlStorageFolder(crawlStorageFolder);

        // Be polite: Make sure that we don't send more than 1 request per second (1000 milliseconds between requests).
        // Otherwise it may overload the target servers.
        config.setPolitenessDelay(1000);

        // You can set the maximum crawl depth here. The default value is -1 for unlimited depth.
        config.setMaxDepthOfCrawling(16);

        // You can set the maximum number of pages to crawl. The default value is -1 for unlimited number of pages.
        config.setMaxPagesToFetch(20000);
//        config.setMaxPagesToFetch(20);

        // Should binary data should also be crawled? example: the contents of pdf, or the metadata of images etc
        config.setIncludeBinaryContentInCrawling(true);

        config.setUserAgentString(userAgentString);

        // Do you need to set a proxy? If so, you can use:
        // config.setProxyHost("proxyserver.example.com");
        // config.setProxyPort(8080);

        // If your proxy also needs authentication:
        // config.setProxyUsername(username); config.getProxyPassword(password);

        // This config parameter can be used to set your crawl to be resumable
        // (meaning that you can resume the crawl from a previously
        // interrupted/crashed crawl). Note: if you enable resuming feature and
        // want to start a fresh crawl, you need to delete the contents of
        // rootFolder manually.
        config.setResumableCrawling(false);

        // Set this to true if you want crawling to stop whenever an unexpected error
        // occurs. You'll probably want this set to true when you first start testing
        // your crawler, and then set to false once you're ready to let the crawler run
        // for a long time.
        //config.setHaltOnError(true);

        // Instantiate the controller for this crawl.
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        // STARTER 'seed'
        controller.addSeed("https://www.latimes.com");

        // To demonstrate an example of how you can pass objects to crawlers, we use an AtomicInteger that crawlers
        // increment whenever they see a url which points to an image.
        //AtomicInteger numSeenImages = new AtomicInteger();

        // Start the crawl. This is a blocking operation, meaning that your code
        // will reach the line after this only when crawling is finished.
        //controller.start(factory, numberOfCrawlers);
        // GO!!!!
        controller.start(MyCrawler.class, numberOfCrawlers);

        StringBuilder out1 = new StringBuilder("URL,Status\n");
        StringBuilder out2 = new StringBuilder("URL,Size(Byte),Number of outlinks found,Content-type\n");
        StringBuilder out3 = new StringBuilder("Encountered URL (including repeats),Indicator\n");

        for (Object t : controller.getCrawlersLocalData()) {
            String[] tasks = (String[]) t;
            out1.append(tasks[0]);
            out2.append(tasks[1]);
            out3.append(tasks[2]);
        }

        writeCSV(out1, "fetch_latimes.csv");
        writeCSV(out2, "visit_latimes.csv");
        writeCSV(out3, "urls_latimes.csv");
    }

    private static void writeCSV(StringBuilder output, String s) throws IOException {
        PrintWriter writer = new PrintWriter(s, StandardCharsets.UTF_8);
        writer.println(output.toString().trim());
        writer.flush();
        writer.close();
    }
}
