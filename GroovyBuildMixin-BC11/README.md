# Groovy Build Mixin

This provides Groovy 4.0.17 compilation of code with JDK 11 bytecode.

You must add the following to your pom.xml:

        ...
        <dependencies>
            <dependency>
                <groupId>org.apache.groovy</groupId>
                <artifactId>groovy</artifactId>
                <version>4.0.18</version>
            </dependency>
        </dependencies>
        ...
        <plugins>
            ...
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
            ...
        </plugins>
        ...

Note that the dependency on Groovy is required since it does not follow with the mixin. I believe this
to be intentional since it can cause a lot of confusion by inheriting dependencies via a mixin.
