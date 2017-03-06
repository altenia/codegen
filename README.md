Codegen
=======
Java-based code generation  tool.

# Use-case
Given a database table, generate JPA Entity class, Spring Repository class, 
Service class, Controller class, and Unit/Integration test codes.

# Usage

`java -jar codegen-1.0-SNAPSHOT.jar [-t target folder] <source liquibase file>`

Example:
  ```java -jar codegen-1.0-SNAPSHOT.jar C:\Users\ysahn\IdeaProjects\codegen\src\test\resources\liquibase.sample.xml```

# Current Limitation
- Only Liquibase file are understood.
- Generates Entity class only. (Repo and Controller classes genererator partially implemented) 
- Output to console only.
- Specific generics are not correctly rendered, e.g. Optional<entity> are rendered as Optional<Object>

# Future works
- Generate Service class
- Generate Resource class
- Generate Unit Test class
- Generate Integration Test Class