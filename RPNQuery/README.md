# RPNQuery

Queries some data using an [RPN](https://en.wikipedia.org/wiki/Reverse_Polish_notation) based query string.

If you have even used a HP calculator like "HP 15c" for example then you understand RPN. This uses the same RPN principles, but is not a calculator :-).

The order of things are: `value value operation`, or `value value operation value value operation operation` in which the last operation is operating on the results of first two, and will take the stack down to one entry, which is where you want to end up. This avoids the need for parentesis grouping. You can also add to the query by adding to the end. If you are not familiar with RPN then it definately looks a bit weird :-). 

There are values and there are operations. Values can be named data reference or a constant. When a value is seen it is added to a query stack. When an operation is seen it is applied to the last 2 entries on the stack. Both entries are then replaced by the result of the operation, thus moving backwards in a shrinking stack. Operations will resolve to a boolean value.

Further down in this document there is an example of data, and a query against it that shows the state of the stack for each data and operation.

## Version

    <dependency>
        <groupId>se.natusoft.tools.toolbox</groupId>
        <artifactId>RPNQuery</artifactId>
        <version>1.1.0</version>
    </dependency>


### Functionallity

User provides a stack of data and operations. Operations always work on top 2 stack entries, which are removed by an operation and operation result is added.

#### Operations ( /... )

- **/=** _equals_

-  **/!=** _not equals_

- **/()** _contains_

- **/!()** _ does not contain_ 

- **/<** _less than_ 

- **/<=** _less than or equals_
 
- **/>** _greater  than_ 

- **/>=** _greater than or equals_
 
- **/T** _True  This is a special operation to check single end result for true. An alternative to adding  'true /='

- **/F** _False  This is a special operation to check single end result for false. An alternative to adding 'false /='

##### Values

- **T** _True_

- **F** _False_ 

- **'...'** _String constant_

- **name** _Field value reference_

- **1, 100, 5.8, 78.3f, 9843275677723.4567658d, ...** _Any number_ 

<!-- @PageBreak -->

#### Example

##### Sample Data

I use JSON as example format, but data can be a simple java.util.Map, java.util.Properties, or whatever.

    {
        "name": "MyServiceId,
        "type": "service",
        "qwe": 92,
        "rty": 236
    }

##### Sample Query

    "name 'MyServiceId' /= qwe 100 /< /= rty 100 /> /T"
    
    
Slightly longer version:
    
    "name 'MyServiceId' /= qwe 100 /< /= rty 100 /> /= type 'service' /= /T"
    
This query checks that name is 'MyServiceId' and that qwe is less than 100 and that rty is greater than 100.

##### Evaluation

Operations work on last 2 stack entries which are replaced by result.

    Value & op             Stack
    ---------------        ---------------------------
    
    name                   'MyServiceId'
    'MyServiceId'          'MyServiceId' 'MyServiceId'
    /=                     T
    qwe                    T 92
    100                    T 92 100
    /<                     T T
    /=                     T
    rty                    T 236
    100                    T 236 100
    />                     T T
    /T                     T

Longer Version additions

    type                   T 'service'
    'service'              T 'service' 'service'
    /=                     T T
    /T                     T
