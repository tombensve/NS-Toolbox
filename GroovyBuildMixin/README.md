# Groovy Build Mixin

This provided Groovy 4.0.1 compilation of code.

Add the following code to the pom wanting to build groovy code:

            <plugin>
                <groupId>com.github.odavid.maven.plugins</groupId>
                <artifactId>mixin-maven-plugin</artifactId>
                <version>0.1-alpha-40</version>
                <configuration>
                    <mixins>
                        <mixin>
                            <groupId>se.natusoft.tools</groupId>
                            <artifactId>groovy-mixin</artifactId>
                            <version>3.0.0</version>
                        </mixin>
                    </mixins>
                </configuration>
            </plugin>

