Codegen
=======
Java-based code generation  tool.

# Use-case
Given a database table, generate JPA Entity class, Spring Repository class, 
Service class, Controller class, and Unit/Integration test codes.

# Usage

`java -jar codegen-{version}.jar  [-log] [-console] [-t target folder] [-c config file] <source file>`

Example:
  ```java -jar codegen-1.0-SNAPSHOT.jar C:\Users\ysahn\IdeaProjects\codegen\src\test\resources\liquibase.sample.xml```

# Configuration file
The configuration by default is gen-profile.json

Below is a sample:
```json
{
	"project": "Sample",
	"source": "",
	"target": null,
	"defaultPackage": null,

	"readerClass": "com.altenia.tool.reader.LiquibaseXmlReader",
	"genlets": [{
		"genletClass": "com.altenia.tool.codegen.genlet.javagen.JavaEntityGenlet",
		"config": {
			"typeSuffix": ""
		}
	}, {
		"genletClass": "com.altenia.tool.codegen.genlet.javagen.JavaSpringControllerGenlet",
		"config": {
			"typeSuffix": "Controller"
		}
	}]
}
```

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


