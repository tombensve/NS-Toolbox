# Dater

This takes a String of format described [here](https://learn.microsoft.com/en-us/openspecs/ie_standards/ms-es3ex/e13c8110-e94f-49ef-9316-0bef4dd05833) 
and parses it. You can get a `LocalDate` or a `ZonedDateTime` as a `Temporal`.
You have to check if you don't know, and need to know. It is one of those 2.

As can be seen in test a toString() will almost reproduce input. _"java.time"_ 
for some reason adds 6 zeros before timezone data. 

There is a static utility method that fixes this:

```java
String javaTimeToStringFix(String javaTimeToStringOutput)
```
## Sample test showing usage

```java
    @Test
    void validateParsingFull2() {

        Dater dater = Dater.from( "2021-04-28T08:35:24.321Z" )

        Temporal date = dater.getTemporalObject(  )

        assert Dater.javaTimeToStringFix(date.toString(  )) == "2021-04-28T08:35:24.321Z"

        System.out.println("" + date)
    }

```


## Why ?

Well, I'm using Quarkus (implementation of [Jakarta EE](https://jakarta.ee/)) and I have not been
able to make it parse dates in JSON fields into java.time classes. No clarity of what is wrong
in error message either. So I was curious if this was very difficult to do. Other than weird
format from java.time classes on toString() it was relatively easy. It does get a bit messy
however. Not 100% satisfied with code, but cannot point out exactly what bothers me. I
haven't figured that out yet. 


