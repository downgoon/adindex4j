# adindex4j

a java implementation of ad retrival algorithm proposed in [Indexing Boolean Expressions-Ⅱ.pdf](http://opbs7gfa4.bkt.clouddn.com/paper/Indexing%20Boolean%20Expressions-%E2%85%A1.pdf)


## QuickStart

- **Requirement**: ``Java8``

- **Maven Dependency**

``` xml
<dependency>
  <groupId>com.downgoon</groupId>
  <artifactId>adindex4j</artifactId>
  <version>${version}</version>
</dependency>
```

the lastest release version can be found here [g:"com.downgoon" a:"adindex4j"](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.downgoon%22%20a%3A%22adindex4j%22)

- **Sample Code**

``` java
// 5 ad documents targeting several attributes
AdRetrival adRetrival = new BEScaner();

// notation form targeting: 1, "location=北京^gender=男"
adRetrival.appendDocument(1, "location=北京^gender=男");

// java-object form targeting: 2, "location=上海^gender=女"
adRetrival.appendDocument(new Document(2,
    new DNF( //
        new Conjunction( //
            new Assignment("location", Predicate.INCLUSIVE, "上海"), //
            new Assignment("gender", Predicate.INCLUSIVE, "女") //
        ) //
    )));

// a DNF with multiple conjunctions
adRetrival.appendDocument(3, "(location=北京|上海^gender=男) | (location=深圳^gender=女)");
adRetrival.appendDocument(4, "location!=北京|上海");

// query
String query = "location=北京^gender=男";

// results: [1, 3]
Set<Long> docIds = adRetrival.retrieveDocuments(query);
LOG.info("query: {}, results: {}", query, docIds);
```
