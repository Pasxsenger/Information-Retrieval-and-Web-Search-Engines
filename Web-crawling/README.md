# Summary

Work with a web crawler to measure aspects of a crawl, study the characteristics of the crawl, download web pages from the crawl, and gather webpage metadata, all from pre-selected news websites.





# Task

## 1. Crawling

Configure and compile the crawler and then have it crawl the **<u>Los Angeles Times</u>**

maximum pages to fetch: 20,000

maximum depth to fetch: 16

**required to use multiple crawling threads in this exercise**

*<u>Limit the crawler so it only visits HTML, doc, pdf and different image format URLs and records the meta data for those file types</u>*

Should also consider **URLs with no extension** as they may return a file of one of the above types.

File types CSS, js, JSON, and others should **not** be visited.



## 2. Collecting Statistics

### a. Collecting

#### fetch_latimes.csv

a two-column spreadsheet

column 1 contains the URL 

column 2 contains the HTTP/HTTPS status code received

**Note**:-> fetch.csv should have URLs from news site domain only

| URL                  | Status               |
| -------------------- | -------------------- |
| ... (<= 20,000 rows) | ... (<= 20,000 rows) |



#### visit_latimes.csv

a four-column spreadsheet

column 1 contains the URLs successfully downloaded

column 2 contains the size of the downloaded file (in Bytes, or any preferred unit)

column 3 contains the number of outlinks found

column 4 contains the resulting content-type

*<u>clearly the number of rows will be less than the number of rows in fetch_latimes.csv</u>*

| URL                                      | Size (Bytes) | Number of outlinks found | Content-type |
| ---------------------------------------- | ------------ | ------------------------ | ------------ |
| (<= Number of rows in fetch_latimes.csv) |              |                          |              |



#### urls_latimes.csv

a two-column spreadsheet 

column 1 contains the encountered URL (<mark>including repeats</mark>)

column 2 contains an indicator of whether the URL 

​	**a.** resides in the website (**OK**), or 

​	**b.** points outside of the website (**N_OK**).

 		(A file points out of the website if its URL does not start with the initial host/domain name, e.g. when crawling USA 		Today news website all inside URLs must start with www.usatoday.com)

This file will be much larger than fetch_latimes.csv and visit_latimes.csv



For example for New York Times - the URL 

http://www.nytimes.com/section/sports 

and the URL 

http://nytimes.com/section/sports

are both considered as residing in the same website,

whereas the following URL is **not** considered to be in the same website,

http://store.nytimes.com/



| encountered URL (including repeats) | Indicator |
| ----------------------------------- | --------- |
| ... (<= 20,000 rows)                | OK / N_OK |



### b. Statistics - CrawlReport_latimes.txt

#### Fetch statistics

* \# fetches attempted:

  The total number of URLs that the crawler attempted to fetch. This is usually equal to the **MAXPAGES** setting if the crawler reached that limit; less if the website is smaller than that.

* \# fetches succeeded:

  The number of URLs that were successfully downloaded in their entirety, i.e. returning an HTTP status code of **2XX**.

* \# fetches failed or aborted:

  The number of fetches that failed for whatever reason, **including, but not limited to**: HTTP redirections (3XX), client errors (4XX), server errors (5XX), and other network-related errors.

**\# fetches attempted = # fetches succeeded + # fetches aborted + # fetches failed**



The difference between aborted fetches and failed fetches:

* failed: can be due to HTTP errors and other network-related errors such as code 404

* aborted: the client (the crawler) decided to stop the fetching. (ex: Taking too much time to fetch) such as code 303

**sum up both the values and provide the combined result in the write-up**



#### Outgoing URLs

statistics about URLs extracted from visited HTML pages

* Total URLs extracted:

  The grand total number of URLs extracted (<mark>including repeats</mark>) from all visited pages

* \# unique URLs extracted:

  The number of *unique* URLs encountered by the crawler

* \# unique URLs within your news website:

  The number of *unique* URLs encountered that are associated with the news website, i.e. the URL begins with the given root URL of the news website, but the remainder of the URL is distinct

* \# unique URLs outside the news website:

  The number of *unique* URLs encountered that were *not* from the news website.

**\#unique URLs extracted = #unique URLs within + #unique URLs outside**

**\# total URLs extracted = the sum of all values in column 3 of visit.csv**

For text/html files, find the number of out links; For non-text/html files, the number should be 0.



#### Status codes

number of times various HTTP status codes were encountered during crawling,

including (but not limited to): 200, 301, 401, 402, 404, etc.



#### File sizes

statistics about file sizes of visited URLs – the number of files in each size range.



#### Content Type

a list of the different content types encountered





### Format of CrawlReport_latimes.txt

Use the following format to tabulate the statistics that you collated based on the crawler outputs.

CrawlReport_latimes.txt:

```
Name: Tommy Trojan
USC ID: 1234567890
News site crawled: nytimes.com
Number of threads: 7
Fetch Statistics
================
# fetches attempted:
# fetches succeeded:
# fetches failed or aborted:
Outgoing URLs:
==============
Total URLs extracted:
# unique URLs extracted:
# unique URLs within News Site:
# unique URLs outside News Site:
Status Codes:
=============
200 OK:
301 Moved Permanently:
401 Unauthorized:
403 Forbidden:
404 Not Found:
File Sizes:
===========
< 1KB:
1KB ~ <10KB:
10KB ~ <100KB:
100KB ~ <1MB:
>= 1MB:
Content Types:
==============
text/html:
image/gif:
image/jpeg:
image/png:
application/pdf:
```

