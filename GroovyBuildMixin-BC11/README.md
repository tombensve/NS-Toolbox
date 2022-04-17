# Groovy Build Mixin

This provides Groovy 4.0.1 compilation of code with JDK 11 bytecode.

Add the following code to the pom wanting to build groovy code:

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
                            <version>1.0.0_G4.0.1-BC11</version>
                        </mixin>
                    </mixins>
                </configuration>
            </plugin>

