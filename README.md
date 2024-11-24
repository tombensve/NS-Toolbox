# NS-Toolbox

This is a project of tools that I use in other projects.

It is made up of several jars rather than one fat jar, so only what is needed can be used.

This will probably grow over time.

Each tool has its own submodule. See Readme.md in each for more info.

## Binaries

[See here for how to get binaries](https://tombensve.github.io)

## Versioning 

This is a set of different tools that belong to one git codebase even if they are separate freestanding tools. I did not want to create a separate repo for each, it would be too much. These are relatively small things. 

It took me 3 tries to come up with a versioning strategy that did not suck. The root pom have version `static` and will never change, as hinted by the name. Each other tool has its own version, and references the _static_ parent.

## Groovy

Since Java went down with the sun, I have now decided from now on to use the very nice Groovy JVM language instead of Java for my GitHub code. Groovy is a **far better** language than Java and 100% JVM / Java compatible. Groovy have been along for a very long time. Groovy had features from the start that Java didn't get until much later, and Groovy did them right. Different from Java, Groovy also has real functions, called closures! Java have lambdas, bound to single method interfaces creates a lot of limitations. In use they look similar, but in functionality they are quite different.

### Byte Code

Groovy can now can produce byte code up to 23 as of Groovy 4.0.21, which is currently used!

The byte code level is specified with this property in the pom.xml:

    <bytecode.version>11</bytecode.version>

This is currently the default byte code level I have decided to use, since I believe that many are still using this JDK version due to not having time to test all code against higher versions. Even if you have good unit tests they might not test for the kind of things that causes incompatibilities, and you will have to ensure that the test coverage is really good, to feel safe moving on over 11!

### Version standard

My current version standard that I will use is:

    1.2.3-BC11

----

## Current content

- [ASFLicAnnotation](ASFLicAnnotation/README.md)

- [Modelish](Modelish/README.md) Provides Java Bean and fluent (I think it's called) style property accessors
using interfaces which gets a proxy implementation. Supports building and immutability.

- [Dater](Dater/README.md) Parses JSON date format strings and provides java.time objects.
