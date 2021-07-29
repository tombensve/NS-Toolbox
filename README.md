# RPNQuery

Queries some data using an RPN based query string.

If you have even used a HP calculator like "HP 15c" for example then you understand RPN. This uses the same RPN principles, but is not a calculator :-).

The order of things are: `value value operation`, or `value value operation value value operation operation` in which the last operation is operating on the results of first two, and will take the stack down to one entry, which is where you want to end up. This avoids the need for parentesis grouping. You can also add to the query by adding to the end. If you are not familiar with RPN then it definately looks a bit weird :-). 

There are values and there are operations. Values can be named data reference or a constant. When a value is seen it is added to a query stack. When an operation is seen it is applied to the last 2 entries on the stack. Both entries are then replaced by the result of the operation, thus moving backwards in a shrinking stack. Operations will resolve to a boolean value.

Further down in this document there is an example of data, and a query against it that shows the state of the stack for each data and operation.

## Data queried

My personal need and why I'm doing this, is a Map with String keys and string or numeric values, but I'll try to not lock myself into any specific structure.

## Functionallity

User provides a stack of data and operations. Operations always work on top 2 stack entries, which are removed by an operation and operation result is added.

### Operations ( ... )

- equals (==)

- not equals (!=)

- contains ({})

- does not contain (}{)

- less than (\<)

- less than or equals (\<=)

- greater  than (\>)

- greater than or equals (\>=)

### Values [ ... ]

- **\[true\]**

- **\[false\]**

- String constant **\['...'\]**

- Field value reference **\[name\]**

- Any number: **\[1\], \[100]\, \[5.8\], \[78.3f\], \[9843275677723.4567658d\], ...**  

### Example

#### Sample Data

I use JSON as example format, but data can be a simple java.util.Map, java.util.Properties, or whatever.

    {
        "name": "MyServiceId2,
        "type": "service",
        "qwe": 92,
        "rty": 236
    }

#### Sample Query

    "[name]['MyServiceId'](==)[qwe][100](<)(==)[rty][100](>)(==)"
    
Slightly longer version:
    
    "[name]['MyServiceId'](==)[qwe][100](<)(==)[rty][100](>)(==)[type]['service'](==)(==)"
    
This query checks that _name_ is 'MyServiceId' and  that _qwe_ is less than 100 and that _rty_ is greater than 100.

#### Evaluation

Operations work on last 2 stack entries which are replaced by result.

    Value/op               Stack
    
    [name]                 'MyServiceId'
    ['MyServiceId']        'MyServiceId' 'MyServiceId'
    (==)                   true
    [qwe]                  true 92
    [100]                  true 92 100
    (<)                    true true
    (==)                   true
    [rty]                  true 236
    [100]                  true 236 100
    (>)                    true true
    (==)                   true

Longer version additions

    [type]                 true 'service'
    ['service']            true 'service' 'service'
    (==)                   true true
    (==)                   true
    
