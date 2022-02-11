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
### Usage


```java
    TestModel testModel = Modelish.create( TestModel.class )
            .name("Tommy Svensson")
            .age(53)
            .address("Undisclosed").lock();

```

After the lock() call the model cannot be modified.

