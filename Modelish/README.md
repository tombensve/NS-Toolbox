# Modelish 
#### ( For the lack of a better name )

This is a truly simple API for defining models as interfaces and have them dynamically implemented runtime.

This is a very simple and small tool. It makes use of `java.lang.reflect.Proxy`.

This does one and only one thing, defining data models, both JavaBean and fluent 
([https://dzone.com/articles/java-fluent-api-design](https://dzone.com/articles/java-fluent-api-design)) 
styles are supported. 

It might have problems with code trying to inspect models via reflection since they are proxy instances. 
These will also be slightly slower than a plain "java bean" due to being a proxy and storing values in 
a HashMap. 

## Latest Version

### 3.0.7

Now does same validations when setting model values from Map as when setting values using
models. That said, there currently is only @Nonull available.


    <dependency>
        <groupId>se.natusoft.tools.toolbox</groupId>
        <artifactId>Modelish</artifactId>
        <version>3.0.7</version>
    </dependency>

### 3.0.6

Now supports toString() and hashCode(). As an afterthought I came to the decision that these might
be useful ...

    <dependency>
        <groupId>se.natusoft.tools.toolbox</groupId>
        <artifactId>Modelish</artifactId>
        <version>3.0.6</version>
    </dependency>


### 3.0.5

Changed _lock() to _immutable() and _recursiveLock() to _recursivelyImmutable(). Thought this
was clearer even though a bit longer.

    <dependency>
        <groupId>se.natusoft.tools.toolbox</groupId>
        <artifactId>Modelish</artifactId>
        <version>3.0.5</version>
    </dependency>

### 3.0.4

    <dependency>
        <groupId>se.natusoft.tools.toolbox</groupId>
        <artifactId>Modelish</artifactId>
        <version>3.0.4</version>
    </dependency>


## Example model

### Java

```java

    public interface TestModel extends ModelishModel<TestModel> {
    
        String name();
        TestModel name(String name);
    
        int age();
        TestModel age(int age);
    
        String address();
        TestModel address(String address);
    
    }
```

### Groovy

```groovy

    interface TestModel extends ModelishModel<TestModel> {
    
        String name()
        TestModel name(String name)
    
        int age()
        TestModel age(int age)
    
        String address();
        TestModel address(String address)
    
    }
```

Note that 
- the name is the same for both setter and getter.
- the overall code to implement this is quite small.

## Usage

### Java

<!--
  If the below code example is red underlined, then you are using IDEA, and it has
  gotten rather confused.
-->
```java
    TestModel userInfo = Modelish.create( TestModel.class )
            .name("Tommy Svensson")
            .age(53)
            .address("Stockholm")
            ._immutable();

```

### Groovy 

```groovy
    TestModel userInfo = Modelish.create( TestModel.class )
            .name("Tommy Svensson")
            .age(53)
            .address("Stockholm")
            ._lock()

```

After the lock() call the model cannot be modified. There is intentionally no unlock. 

Note that for complex models with submodels there is now also an `recursiveLock()` method that locks model and any submodels.

## Clone and modify model

### Java

```java

    public interface TestModel extends Cloneable<TestModel> {
    
        String name();
        TestModel name(String name);
    
        int age();
        TestModel age(int age);
    
        String address();
        TestModel address(String address);
    
    }
```

### Groovy

```groovy

    interface TestModel extends Cloneable<TestModel> {
    
        String name()
        TestModel name(String name)
    
        int age()
        TestModel age(int age)
    
        String address()
        TestModel address(String address)
    
    }
```

With this model `_clone()` can be called to get a current clone of model that will be open for changes until _lock() is called.

## Supports JavaBean also!

The setters however have to return the model type! 

```groovy
    interface JBTest extends Model<JBTest> {

        JBTest setName( String name )
        String getName()
    }
```

## Since 3.0.4 Modelish -> Map and Map -> Modelish is possible!

### Groovy

The reason for the next 2 functionalities are to be able to use Groovys JSONSlurper or
Jackson Jr and probably others that can convert between JSON and Map<String, Object> to
easily be able to turn these models into JSON or create from JSON.

#### Getting Map

```groovy
        User user = Modelish.create( User.class )
                .id( "tbs" )
                .loginCount( 9843 )
                .userInfo( Modelish.create( UserInfo.class )
                        .name( "Tommy" )
                        .address( "Liljeholmen" )
                        .age( 54 )
                        ._immutable()
                )
        ._immutable()

        Map<String, Object> map = user._toMap()

```
**Note** here that even if the model is immutable, the Map of course is not, but changing the Map will not
affect the original. A copy of all values are made in conversion to Map. And in the example below, creating 
a new Model from a Map, it is a _new_ Map! It is impossible to set a Map on an existing instance.

#### Creating from Map

```groovy
        Map<String, Object> userMap = [
                "id"        : "tbs",
                "loginCount": 9972,
                "userInfo"  : [
                        "name"   : "Tommy Svensson",
                        "address": "Liljeholmen",
                        "age"    : 55
                ]
        ]

        User user = Modelish.newFromMap( User.class as Class<Model>, userMap ) as User

```

Note here that the user instance here happens to be created using a Map, but this instance is just as it would
have been creating it normally, and no data will be immutable without specifically telling the model that it
should be! And again all data from the Map has been copied. Modifying the Map after model creation will not
affect created model.

----

See [Test](https://github.com/tombensve/NS-Toolbox/blob/main/Modelish/src/test/groovy/se/natusoft/tools/modelish/ModelishTest.groovy) for an example of usage.
