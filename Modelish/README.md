# Modelish

This is a truly simple API for defining models as interfaces and have them dynamically implemented runtime. 

This uses the fluent ([https://dzone.com/articles/java-fluent-api-design](https://dzone.com/articles/java-fluent-api-design)) variant of models.

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
- the overall code to implement this is quite small!

### Usage


```java
    TestModel testModel = Modelish.create( TestModel.class )
            .name("Tommy Svensson")
            .age(53)
            .address("Stockholm")
            .lock();

```

After the lock() call the model cannot be modified. There is intentionally no unlock.

