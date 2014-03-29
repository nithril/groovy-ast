# groovy-ast

This project offers the following Groovy AST transformations

## CoerceReturn

Groovy allows lists as constructors:
```groovy
MyBean bean = [0,1,2,3,5]
```

Groovy 2 has introduced type Checking which can be enabled with `@TypeChecked`. Type checking and lists as constructors
are mutually exclusive.
The purpose of this AST is to allow lists as constructors with the type checker enabled. For the moment he feature is
restrained to return statement:
```groovy
MyBean f(){
    return [0,1,2,3,5]
}
```

Will be transformed to
```groovy
MyBean f(){
    return new MyBean(0,1,2,3,5)
}
```

This AST is more an AST test that anything else.

## RequestMappingChecker

As a Spring MVC user I want a checker able to validate `@RequestMapping` and `@PathVariable` consistencies.
Ie. a checker able to break compilation for this type of error:

```groovy
@RequestMapping(value = ["/path/{foo}/{bar}"])
public void test(@PathVariable String fo, @PathVariable String bar) {

}
```groovy

This AST allows to find these inconsistencies. It will return the error:
```text
test.groovy: 14: Error: @RequestMapping pattern {foo} not declared as @PathVariable
 @ line 14, column 5.
       @RequestMapping(value = ["/path/{foo}/{bar}"])
       ^
```

