# NS-Toolbox

This is a project of tools that I use in other projects.

It is made up of several jars rather than one fat jar, so only what is needed can be used.

This will probably grow over time.

Each tool has its own submodule. See Readme.md in each for more info.

----

## Versioning 

This is a set of different tools that belong to one git codebase even if they are separate freestanding tools. I did not want to create a separate repo for each, it would be too much. These are relatively small things. 

It took me 3 tries to come up with a versioning strategy that did not suck. The root pom have version `static` and will never change, as hinted by the name. Each other tool has its own version, and references the _static_ parent.

All tools belong to the `se.natusoft.tools.toolbox` group.

----

## Current content

- [APIs](ns-toolbox-apis/README.md)

- [Modelish](Modelish/README.md)

- [RPNQuery](RPNQuery/README.md)

- [Filtering service loader](filtering-service-loader/README.md)

- [GroovyBuildMixin](GroovyBuildMixin/README.md)
