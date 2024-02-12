import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.regex.Pattern;
import java.util.Set;
import org.apache.http.Header;
import java.util.HashSet;


public class MyCrawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(
            ".*(\\.(" + "css|js|json|webmanifest|ttf|svg|wav|avi|mov|mpeg|mpg|ram|m4v|wma|wmv|mid|txt|mp2|mp3|mp4|zip|rar|gz|exe|ico))$");

    private String task1 = "";
    private String task2 = "";
    private String task3 = "";
    private HashSet<String> seen = new HashSet<>();

    @Override
    public Object getMyLocalData() {
        return new String[]{task1, task2, task3};
    }

    @Override
    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
        String url = webUrl.getURL().toLowerCase().replaceAll(",", "_");
        task1 += url + "," + statusCode + "\n";
        seen.add(url);
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase().replaceAll(",", "_");
        boolean isValid = href.startsWith("http://www.latimes.com/") || href.startsWith("https://www.latimes.com/")
                || href.startsWith("http://latimes.com/") || href.startsWith("https://latimes.com/");
        if (isValid)
            task3 += href + ",OK\n";
        else task3 += href + ",N_OK\n";
        boolean hasNotSeen = !seen.contains(href);
        return !FILTERS.matcher(href).matches() && isValid && hasNotSeen;
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL().toLowerCase().replaceAll(",", "_");
        int numberOfOutLinks = 0;
        int size = page.getContentData().length;
        String contentType = page.getContentType().split(";")[0];

        boolean isCorrectType = contentType.contains("html") | contentType.contains("image") |
                contentType.contains("doc") | contentType.contains("pdf");
        if (!isCorrectType)
            return;

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            numberOfOutLinks += links.size();
        }

        task2 += url + "," + size + "," + numberOfOutLinks + "," + contentType + "\n";
    }
}
