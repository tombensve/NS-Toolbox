# Groovy Build Mixin

This provides Groovy 4.0.17 compilation of code with JDK 11 bytecode.

You must first add the following dependency:

        <dependencies>
            <dependency>
                <groupId>org.apache.groovy</groupId>
                <artifactId>groovy</artifactId>
                <version>4.0.17</version>
            </dependency>
        </dependencies>

The groovy dependency that is provided in the mixin pom "mixed" in by the config below are apparently
**NOT INCLUDED** when the mixin is applied!

So after adding the groovy dependency above, add the following config to the pom wanting to build groovy code:

        <plugin>
            <groupId>com.github.odavid.maven.plugins</groupId>
            <artifactId>mixin-maven-plugin</artifactId>
            <version>0.1-alpha-39</version>
            <extensions>true</extensions>
            <configuration>
                <mixins>
                    <mixin>
                        <groupId>se.natusoft.tools.toolbox</groupId>
                        <artifactId>groovy-build-mixin</artifactId>
                        <version>1.0.0_G4.0.17-BC11</version>
                    </mixin>
                </mixins>
            </configuration>
        </plugin>
