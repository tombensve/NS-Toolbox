# NS-Toolbox

This is a project of tools that I use in other projects.

It is made up of several jars rather than one fat jar, so only what is needed can be used.

This will probably grow over time.

Each tool has its own submodule. See Readme.md in each for more info.

----

## Versioning Aaaaarg!!!

I have concluded that versioning gets really, really messy since I'm trying to keep
several, different tools as one codebase under a common parent pom. 

Trying to only bump version of modified module failed due to top pom needing update,
and then under modules points to older top. It just gets really, really messy no
matter what I do! For example when I introduced version 3.0.0, if I point version
2.0.0 of a sub module to version 3.0.0 then there is an old pointing to 2.0.0 and
I'm suddenly replacing it rather than leaving old or making new ... and here my 
head starts spinning!! 

I'm giving up on this and just bump all versions when one version needs bumping. 

I have previously kept same versions on everything in multimodule maven projects.
Not doing that becomes mindbogglingly complex!!

The downside is that it creates new jars with new versions that have no new code.
The upside is that I'm not overwriting old modules pointing to a new parent, and 
I do get to keep my sanity ...

----

## Current content

- [APIs](ns-toolbox-apis/README.md)

- [Modelish](Modelish/README.md)

- [RPNQuery](RPNQuery/README.md)

- [Filtering service loader](filtering-service-loader/README.md)

- [GroovyBuildMixin](GroovyBuildMixin/README.md)
