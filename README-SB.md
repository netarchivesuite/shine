# Setup at Statsbiblioteket

## Running
If anything is changed:

```
cd shine/
play stage
```

To run in background:

```
cd shine/
setsid nohup target/universal/stage/bin/shine
```

Then logout

## Sample configuration
Place in `~/shine.conf` and point to it with the `-Dconfig.file=` option. Remember to replace password to db.

```
application.secret="VALjnUt>B8Q:1c2AwK`HfM2B>7nTlqIyN?F_ftWu1_dgIMtE0bluRTP;xjZ@OKse"
application.langs="en"

db.default.driver=org.postgresql.Driver
db.default.url="jdbc:postgresql://itweb/shine"
db.default.user=shine
db.default.password=changeme

ebean.default="models.*"

application.context="/shine"

shine {
    host = "http://ariel:52300/solr/",
    http {
    },
    facets {
        basic {
            crawl_years { name="Crawl Years", limit=5, maxLimit=10 },
            public_suffix { name="Public Suffix", limit=5, maxLimit=10 },
            domain { name="Domain", limit=10, maxLimit=10 },
            content_type_norm { name="General Content Type", limit=3, maxLimit=10 }
        },
        additions {
            content_language { name="Language", limit=5, maxLimit=10 },
            postcode_district { name="Postcode District", limit=5, maxLimit=10 }
        },
        links {
            links_domains { name="Links Domains", limit=5, maxLimit=10 },
            links_public_suffixes { name="Links to Public Suffixes", limit=5, maxLimit=10 }
        },
        entities {
        },
        format {
        }
        collection {
        }
    },
    sorts {
        crawl_year="Crawl Year"
    },
    per_page = 10
    default_from_year = 2000
    default_end_year = 2015
    max_number_of_links_on_page = 10
    max_viewable_pages = 50
    facet_limit = 5
    show_browse = false
    show_collections_field = false
    show_concordance = false
    resource_limit = 10
    csv_max_limit = 20000
    csv_interval_limit = 1000
    web_archive_url = "http://elara.statsbiblioteket.dk/wayback/"
}
``` 

