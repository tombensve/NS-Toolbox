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
So theoretically you can just produce binaries for any JDK level without changing any code.

----

## Current content

- [APIs](ns-toolbox-apis/README.md)

- [Modelish](Modelish/README.md) Provides Java Bean and fluent (I think it's called) style property accessors
using interfaces which gets a proxy implementation. Supports building and imutability. This since 3.2.0 also supports validation of data in models
based on annotations on setters. Possible to supply own validators that will be triggered on set of values.

- [GroovyBuildMixin](GroovyBuildMixin-BC11/README.md) Mixins for building Groovy 4. Other modules use this. There is one for JDK 1.8 bytecode and one for JDK 11 bytecode. Even though I believe that Groovy will handle the non backwards compatibility issues I'm sticking to 11 for now. No customer I've been at as a consultant in Sweden have used anything higher than 11. Testing all existing code for higher JDKs is not a small jobb!! 

- [GroovyBuildMixin](GroovyBuildMixin-BC11/README.md) Mixins for building Groovy 4. Other modules use this. Now also supports byte code 17.

- [RPNQuery](RPNQuery/README.md) Queries data using RPN (Reverse Polish Notation). This did not turn out to be as nice as I imagined ...

<!-- - [Filtering service loader](filtering-service-loader/README.md) -->
