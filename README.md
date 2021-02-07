# metric-system
System that generates operating system metrics

## How tu run locally
Run consumer:
``` 
./gradlew metric-consumer:run
```
Run producer:
``` 
./gradlew metric-producer:run
```

## What was not done
* No schema for kafka messages. 
It required `schema-registry` which is not provided as a service. My initaial ide was to use
only kafka and postgres as service.
Due to this not generic JsonSerdes have been created.