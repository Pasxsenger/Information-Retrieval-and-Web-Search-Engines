# Summary

- Use [Weaviate](https://weaviate.io/), which is a vector DB - stores data as vectors after vectorizing, and computes a search query by vectorizing it and does a similarity search with existing vectors
- Crawl the web using a Node package, to compile a 'knowledge base' [to use subsequently as input to build a [custom GPT](https://openai.com/blog/introducing-gpts)]
- Using a Python module, perform RAG(Retrieval-augmented generation) on a 'small', locally-hosted LLM (make that an 'S'LM)



Please make sure you have these installed, before starting: git, Docker, Node, Python (or Conda/Anaconda), [VS 2022](https://bytes.usc.edu/cs572/s24-s-e-a-r-c-hhh/hw/HW4/pics/vs2022_inst.png) [with 'Desktop development with C++' checked].



# Part 1

Use vector-based similarity search, to retrieve search results that are not keyword-driven.

Three steps:

- Install Weaviate plus vectorizer via Docker as images, run them as containers
- Specify a schema for data, upload data/knowledge (in .json format) to have it vectorized
- Run a query (which also gets vectorized and then sim-searched), get back results (as JSON)



## Step 1. Installing Weaviate and a vectorizer module

After installing Docker, bring it up (eg. on Windows, run Docker Desktop).

Then, in the (ana)conda shell, run this docker-compose command that uses this [yaml]() 'docker-compose.yml' config file to pull in two images: the 'weaviate' one, and a text2vec transformer called 't2v-transformers'

```shell
docker-compose up -d
```






Now we have the vectorizer transformer (to convert sentences to vectors), and weaviate (our vector DB search engine) running! On to data handling :)



# Command

```shell

pip3 install weaviate-client

python serveit.py
python weave-loadData.py
```

