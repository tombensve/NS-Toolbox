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

### 3.2.1

Tiny change: Validators are now passed Method object rather than just "${method.name}" This in hope
of providing a bit more flexibility of what can be validated.

### 3.2.0

Validators are no longer internal even though 3 are provided by default:

- NoNullValidator
- NotEmptyValidator
- ValidRangeValidator

The new  interface ModelishValidator defines the "Validator" API. Such can be implemented and
then added to `ModelValidator.VALIDATORS`. This is a LinkedList of `ModelishValidator` implementations.

So if you have a model made with Modelish and wan't to validate it for something, write an implementation
and then add an instance of that implementation to `ModelValidator.VALIDATORS`. It is a static 
`LinkedList` containing 3 validators by default.

If you look at the 3 existing validators you can see that those identify annotations to check data
for by name, not type! This makes it more flexible. 

Do note that this static `LinkedList` is modifiable, which is why you can add validators to it.
But this also allows you to remove validators from it! This includes the default ones. I would
not recommend doing that, but it is possible!

This just offers for easy own runtime validation of own models

### 3.1.0

New annotations:

- @ValidRange: Now supports min and max range for numeric values. Note that the annotation values
  are of type _double_! That said, it works for _int_, _long_, and _float_ values in models also! I did not
  want 3 different annotations depending on type! Any user of this has to keep track of actual type to not
  allow to big values! For "int":s and "long":s you should specify .0 as decimal.

- @NotEmpty: Forces a required value. I.e null or "" is not accepted.

### 3.0.11

Added `@ModelsihPropoerty`annotation purely for documentation purposes.

### 3.0.10

Added `@ModelishModel` purely for documentation purposes.

### 3.0.9

Now only requires an annotation containing the strings "no" and "null" in the name (capitals
allowed also) for requiring a non nullable value. Thereby more annotations than the supplied
@NoNull will work, like JetBrains @NotNull annotation and any other containing the strings
"no" and "null" in any case, like @NotNull in my Docutations project or one you make
yourself.

### 3.0.8 

Restored _lock() and _recursiveLock() as aliases to _immutable() and _recursivelyImmutable().

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
