# NS-Toolbox

This is a project of tools that I use in other projects.

It is made up of several jars rather than one fat jar, so only what is needed can be used.

This will probably grow over time.

Each tool has its own submodule. See Readme.md in each for more info.

----

## Binaries

[See here for how to get binaries](https://tombensve.github.io)

## Versioning 

This is a set of different tools that belong to one git codebase even if they are separate freestanding tools. I did not want to create a separate repo for each, it would be too much. These are relatively small things. 

It took me 3 tries to come up with a versioning strategy that did not suck. The root pom have version `static` and will never change, as hinted by the name. Each other tool has its own version, and references the _static_ parent.

## Groovy

Since Java went down with the sun, I have now decided from now on to use the Groovy JVM language instead of Java for my GitHub code. Groovy is a **far better** language than Java and 100% JVM / Java compatible. Groovy have been along for a very long time. Groovy had features from the start that Java didn't get until much later, and Groovy did them right. Different from Java, Groovy also has real functions, called closures! Java have lambdas, bound to single method interfaces creating a lot of limitations. In use they look similar, but in functionality they are quite different. 

As of 4.x of Groovy you can decide what bytecode version to produce! I'm using 11 since it is the last backwards compatible JDK. 
That said, it seems like Groovy 4 allows you to specify the byte code level to produce independent of your groovy code.
I have now appended the byte-code level to the version, so packages for multiple bytecode versions can now
be built and pushed that will have a version ending in "_(byte code version)". Default is still 11.

----

## Current content

- [APIs](ns-toolbox-apis/README.md)

- [Modelish](Modelish/README.md) Provides Java Bean and fluent (I think it's called) style property accessors
using interfaces which gets a proxy implementation. Supports building and immutability.

- [Dater](Dater/README.md) Parses JSON date format strings and provides java.time objects.
