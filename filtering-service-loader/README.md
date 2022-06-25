# Filtering Service Loader

An idea of building on top of Javas standard ServiceLoader and add properties on services, and support filtering on those properties.

----
**I'm considering not doing this!! If you want to filter multiple implementations of same
interface then add additional mini interfaces and let implementations implement them,
and then do an additional filtering on those.**
----

## 1.1.0

    <dependency>
        <groupId>se.natusoft.tools.toolbox</groupId>
        <artifactId>filtering-service-loader<</artifactId>
        <version>1.0.0</version>
    </dependency>
