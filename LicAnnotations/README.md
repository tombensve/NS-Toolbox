# Software License Annotations

This jar contains annotations for, currently 2 licenses:

- Apache 2.0,
- HSL 1.0 (extends Apache 2.0). 

All classes, interfaces, etc. should be annotated with this. The annotation will be 
retained in the binary, but only once, of course. 

In IDEs like IDEA, you can alt+command click the annotation and get the annotation text 
within the annotation.

The idea here is to make it much easier to include this text on each file by use
of annotation. Note, however, that the actual license text only resides within the
annotation! It is easy to access it by Ctrl-clicking the annotation which will produce
the text. 

----

**!!! DO NOTE THAT I'M NOT SURE THAT THIS WORKS LEGALLY BY USING ANNOTATIONS!**

But I also don't see why it should't! 

----

**I'm not an expert here! But I find this easier, and you get this text included once 
in binary jar!**

**You should always include the actual license document also! This is only intended to 
replace the comment header in each source file.**

With this, however, the license text will exist within each binary jar file! 

# Templates

Note that, except for the license annotation, these are for me to copy and paste!

## ASF

    @Apache_Software_License_2_0
    @interface SourceAvailableAt("https://github.com/tombensve/")
    @BinariesAvailableAt("https://repo.repsy.io/mvn/tombensve/natusoft-os/)

## HSF

    @Human_Software_License_1_0
    @SourceAvailableAt("https://github.com/tombensve/")
    @BinariesAvailableAt("https://repo.repsy.io/mvn/tombensve/natusoft-os/)
