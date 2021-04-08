# Gradle Kotlin, ANTLR & Docker multi stage build

Simple calculator that parses and runs stuff like: "(1.1 + 1) * 2.5"

## This projects contains

* ANTLR
* Kotlin
* Gradle
* Junit5
* Docker


## To run

```
docker build -t example/calculator:latest .
docker run --rm example/calculator:latest /opt/app/bin/app "(1.1 + 1) * 2.5"
```
