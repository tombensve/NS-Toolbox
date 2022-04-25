# Modelish 
#### ( For the lack of a better name )

This is a truly simple API for defining models as interfaces and have them dynamically implemented runtime. 

This uses the fluent ([https://dzone.com/articles/java-fluent-api-design](https://dzone.com/articles/java-fluent-api-design)) variant of models.

This is very a very simple and small tool. Jar file is ~6400 bytes and contains 6 classes, 3 of them interfaces.
The only code generation done is by the JDK at runtime, `java.lang.reflect.Proxy` is used. 

## Latest Version

### 3.0.2

    <dependency>
        <groupId>se.natusoft.tools.toolbox</groupId>
        <artifactId>Modelish</artifactId>
        <version>3.0.1</version>
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

```java
    TestModel userInfo = Modelish.create( TestModel.class )
            .name("Tommy Svensson")
            .age(53)
            .address("Stockholm")
            ._lock();

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

----

See [Test](https://github.com/tombensve/NS-Toolbox/blob/main/Modelish/src/test/groovy/se/natusoft/tools/modelish/ModelishTest.groovy) for an example of usage.
