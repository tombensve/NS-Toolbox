# NS-Toolbox

This is a project of tools that I use in other projects.

It is made up of several jars rather than one fat jar, so only what is needed can be used.

This will probably grow over time.

## Versions 

Will keep same version on all modules, becomes too messy otherwise. This means that modules that have not changed still get a newer version. The information here should show what has changed.

### 1.0.1

Restructured and split into many jars, one for APIs and one for each tool.

### 1.1.0

Added [Modelish](Modelish/), a fluent data model defined as interface and instantiated using the Modelish class.

### 1.1.1

Modelish now supports cloning a model, modify it, and then lock the clone. For this `CloneableModelishModel` must be extended by the model API interface. 
