# Modelish 
#### ( For the lack of a better name )

This is a truly simple API for defining models as interfaces and have them dynamically implemented runtime. 

This uses the fluent ([https://dzone.com/articles/java-fluent-api-design](https://dzone.com/articles/java-fluent-api-design)) variant of models.

This is very a very simple and small tool. Jar file is ~6400 bytes and contains 6 classes, 3 of them interfaces.
The only code generation done is by the JDK at runtime, `java.lang.reflect.Proxy` is used. 

## Example model

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

Note that 
- the name is the same for both setter and getter.
- the overall code to implement this is quite small.

## Usage


```java
    TestModel userInfo = Modelish.create( TestModel.class )
            .name("Tommy Svensson")
            .age(53)
            .address("Stockholm")
            ._lock();

```

After the lock() call the model cannot be modified. There is intentionally no unlock. 

Note that for complex models with submodels there is now also an `recursiveLock()` method that locks model and any submodels.

## Clone and modify model

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

With this model `_clone()` can be called to get a current clone of model that will be open for changes until _lock() is called.

----

See [Test](https://github.com/tombensve/NS-Toolbox/blob/main/Modelish/src/test/java/se/natusoft/tools/modelish/ModelishTest.java) for an example of usage.