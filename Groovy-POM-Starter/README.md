# Groovy-POM-Starter

This folder contains 2 pom.xml files. These can be copied, pasted, and renamed
to use for building Groovy code. 

**Note** that these are for me, contains personal config! Anyone else that might find them useful can copy and modify them.   

For ease of use I'm also including them below.

## Root POM

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
        <groupId></groupId>
        <artifactId></artifactId>
        <version>1.0.0</version>
    
        <packaging>pom</packaging>
    
        <url>https://github/tombensve/</url>
    
        <description>
            NS-Toolbox Root
        </description>
    
        <organization>
            <name>Tommy Bengt Svensson</name>
            <url>https://github.com/tombensve/</url>
        </organization>
    
        <developers>
            <developer>
                <name>Tommy Svensson</name>
                <email>tommy@natusoft.se</email>
            </developer>
        </developers>
    
        <issueManagement>
            <system>github</system>
            <url>https://github.com/tombensve/</url>
        </issueManagement>
    
        <licenses>
            <license>
                <name>Apache 2.0</name>
                <url>http://www.apache.org/licenses/LICENSE-2.0</url>
            </license>
        </licenses>
    
        <modules>
        </modules>
    
        <!--
            Configuration
        -->
        <properties>
    
            <!--
                Version of Groovy to use.
            -->
            <groovy.version>4.0.21</groovy.version>
    
            <!--
                Version of byte-code to produce.
            -->
            <bytecode.version>11</bytecode.version>
    
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    
        </properties>
    
        <dependencies>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-api</artifactId>
                <version>5.10.1</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>5.10.1</version>
                <scope>test</scope>
            </dependency>
    
            <dependency>
                <groupId>org.apache.groovy</groupId>
                <artifactId>groovy</artifactId>
                <version>${groovy.version}</version>
            </dependency>
    
        </dependencies>
    
        <inceptionYear>2021</inceptionYear>
    
        <scm>
            <connection>scm:https://github.com/tombensve/.git</connection>
            <url>scm:https://github.com/tombensve/NS-Toolbox.git</url>
        </scm>
    
        <!--
            The variables used here are in my personal setting.xml! I make binaries
            available from my web server after bintray shut down.
    
            See: https://github.com/tombensve/About
        -->
        <distributionManagement>
            <repository>
                <id>repsy</id>
                <name>repsy</name>
                <url>https://repo.repsy.io/mvn/tombensve/natusoft-os</url>
            </repository>
        </distributionManagement>
    
    
        <repositories>
            <repository>
                <id>repsy</id>
                <name>My Private Maven Repository on Repsy</name>
                <url>https://repo.repsy.io/mvn/tombensve/natusoft-os</url>
            </repository>
    
        </repositories>
    
        <pluginRepositories>
    
            <pluginRepository>
                <id>repsy</id>
                <name>repsy</name>
                <url>https://repo.repsy.io/mvn/tombensve/natusoft-os</url>
                <releases>
                    <enabled>true</enabled>
                </releases>
            </pluginRepository>
    
        </pluginRepositories>
    
        <build>
            <sourceDirectory>src/main/groovy</sourceDirectory>
            <testSourceDirectory>src/test/groovy</testSourceDirectory>
    
            <extensions>
                <!-- Enabling the use of FTP -->
                <extension>
                    <groupId>org.apache.maven.wagon</groupId>
                    <artifactId>wagon-ftp</artifactId>
                    <version>2.3</version>
                </extension>
            </extensions>
    
            <pluginManagement>
                <plugins>
                    <plugin>
                        <artifactId>maven-compiler-plugin</artifactId>
                        <version>3.12.1</version>
                    </plugin>
    
                    <plugin>
                        <artifactId>maven-install-plugin</artifactId>
                        <version>3.1.1</version>
                    </plugin>
    
                    <plugin>
                        <artifactId>maven-source-plugin</artifactId>
                        <version>3.3.0</version>
                    </plugin>
    
                    <plugin>
                        <artifactId>maven-assembly-plugin</artifactId>
                        <version>3.6.0</version>
                    </plugin>
    
                    <plugin>
                        <groupId>se.natusoft.tools.codelicmgr</groupId>
                        <artifactId>CodeLicenseManager-maven-plugin</artifactId>
                        <version>2.2.6</version>
                    </plugin>
    
                    <plugin>
                        <groupId>org.codehaus.mojo</groupId>
                        <artifactId>build-helper-maven-plugin</artifactId>
                        <version>1.8</version>
                    </plugin>
    
                </plugins>
            </pluginManagement>
    
            <plugins>
    
                <plugin>
                    <groupId>org.codehaus.gmavenplus</groupId>
                    <artifactId>gmavenplus-plugin</artifactId>
                    <version>3.0.2</version>
    
                    <dependencies>
                        <dependency>
                            <groupId>org.apache.groovy</groupId>
                            <artifactId>groovy</artifactId>
                            <version>${groovy.version}</version>
                        </dependency>
                    </dependencies>
    
                    <configuration>
                        <targetBytecode>${bytecode.version}</targetBytecode>
                    </configuration>
    
                    <executions>
                        <execution>
                            <goals>
                                <goal>addSources</goal>
                                <goal>addTestSources</goal>
                                <goal>compile</goal>
                                <goal>compileTests</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
    
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.12.1</version>
                </plugin>
    
                <!--
                     Define project assemblies.
                -->
                <plugin>
                    <artifactId>maven-assembly-plugin</artifactId>
                    <version>3.6.0</version>
                </plugin>
    
                <!--
                    Force checksums when installing to repository.
                    I suspect that this might be unneccesarry since I think
                    deploy creates checksums anyhow when deploying to external
                    repository.
                -->
                <plugin>
                    <artifactId>maven-install-plugin</artifactId>
                    <version>3.1.1</version>
                    <configuration>
                        <!--createChecksum>true</createChecksum-->
                    </configuration>
                </plugin>
    
                <!--
                    Copy relevant license files to target on install.
                -->
                <plugin>
                    <groupId>se.natusoft.tools.codelicmgr</groupId>
                    <artifactId>CodeLicenseManager-maven-plugin</artifactId>
    
                    <executions>
                        <execution>
                            <id>install-licence-info</id>
                            <goals>
                                <goal>install</goal>
                            </goals>
                            <phase>install</phase>
                            <configuration>
    
                                <installOptions>
                                    <verbose>true</verbose>
                                    <licenseDir>target/license</licenseDir>
                                    <thirdpartyLicenseDir>target/license/thirdparty</thirdpartyLicenseDir>
                                </installOptions>
    
                                <!--
                                    Here we add those that are not auto resolved.
                                -->
                                <thirdpartyLicenses>
                                </thirdpartyLicenses>
    
                                <createLicensesMarkdown>true</createLicensesMarkdown>
                                <markdownTargetSubdir>lics</markdownTargetSubdir>
                                <markdownLinkPrefix>https://github.com/tombensve/${artifactId}/blob/master/lics/
                                </markdownLinkPrefix>
    
                            </configuration>
                        </execution>
                    </executions>
                </plugin>
    
                <!--
                    Package sources.
                -->
                <plugin>
                    <artifactId>maven-source-plugin</artifactId>
                    <executions>
                        <execution>
                            <id>attach-sources</id>
                            <phase>verify</phase>
                            <goals>
                                <goal>jar-no-fork</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
    
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>3.2.3</version>
                </plugin>
    
                <plugin>
                    <groupId>org.codehaus.gmavenplus</groupId>
                    <artifactId>gmavenplus-plugin</artifactId>
                    <version>3.0.2</version>
    
                    <dependencies>
                        <dependency>
                            <groupId>org.apache.groovy</groupId>
                            <artifactId>groovy</artifactId>
                            <version>${groovy.version}</version>
                        </dependency>
                    </dependencies>
    
                    <configuration>
                        <targetBytecode>${bytecode.version}</targetBytecode>
                    </configuration>
    
                    <executions>
                        <execution>
                            <goals>
                                <goal>addSources</goal>
                                <goal>addTestSources</goal>
                                <goal>compile</goal>
                                <goal>compileTests</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
    
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.12.1</version>
                </plugin>
    
            </plugins>
    
        </build>
    
        <!--
            Profiles
        -->
        <profiles>
            <profile>
                <id>apply-licence-info</id>
                <build>
                    <plugins>
                        <plugin>
    
                            <groupId>se.natusoft.tools.codelicmgr</groupId>
                            <artifactId>CodeLicenseManager-maven-plugin</artifactId>
                            <version>2.2.6</version>
    
                            <dependencies>
                                <dependency>
                                    <groupId>se.natusoft.tools.codelicmgr</groupId>
                                    <artifactId>CodeLicenseManager-licenses-common-opensource</artifactId>
                                    <version>2.2.6</version>
                                </dependency>
                                <dependency>
                                    <groupId>se.natusoft.tools.codelicmgr</groupId>
                                    <artifactId>CodeLicenseManager-source-updater-slashstar-comment</artifactId>
                                    <version>2.2.6</version>
                                </dependency>
                                <dependency>
                                    <groupId>se.natusoft.tools.codelicmgr</groupId>
                                    <artifactId>CodeLicenseManager-source-updater-html-xml</artifactId>
                                    <version>2.2.6</version>
                                </dependency>
                            </dependencies>
    
                            <configuration>
                                <project>
                                    <codeVersion>${project.version}</codeVersion>
                                </project>
                                <!-- All options are optional. -->
                                <codeOptions>
                                    <verbose>true</verbose>
                                    <!-- If not set uses extension on source file to resolve. -->
                                    <codeLanguage><!-- by source extension. -->
                                        Groovy
                                    </codeLanguage>
                                    <!-- If true updates the license information in source code. -->
                                    <updateLicenseInfo>true</updateLicenseInfo>
                                    <!-- If true updates the copyright information in source code. -->
                                    <updateCopyright>true</updateCopyright>
                                    <!-- If true updates the project information in source code. -->
                                    <updateProject>true</updateProject>
                                    <!-- If true will add authors information to source files that does not have it. This is not always implemented! -->
                                    <addAuthorsBlock>true</addAuthorsBlock>
                                    <!-- The directories to scan for source code to update with project & license information. -->
                                    <sourceCodeDirs>
                                        src/main/groovy/**/.*.groovy,src/test/groovy/**/.*.groovy,src/assembly/**/.*.xml
                                    </sourceCodeDirs>
    
                                </codeOptions>
    
                                <userData>
                                    <!--name></name>
                                    <value></value-->
                                </userData>
    
                            </configuration>
    
                            <executions>
                                <execution>
                                    <id>apply-licence-info</id>
                                    <goals>
                                        <goal>apply</goal>
                                    </goals>
                                    <phase>generate-sources</phase>
                                </execution>
                            </executions>
    
                        </plugin>
                    </plugins>
                </build>
            </profile>
        </profiles>
    
    </project>


## Module POM

    <project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
        <groupId>se.natusoft</groupId>
        <artifactId></artifactId>
        <version>1.0.0_${bytecode.version}</version>
    
        <parent>
            <groupId>se.natusoft.</groupId>
            <artifactId></artifactId>
            <version></version>
        </parent>
    
        <packaging>jar</packaging>
    
    </project>
