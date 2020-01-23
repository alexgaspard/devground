## Motivation

A spring-boot application used to test different development techniques from here and there.
The original structure follows hexagonal architecture and comes from [this project](https://github.com/gshaw-pivotal/spring-hexagonal-example).

The code style is inconsistent as I wanted to try out different ways of achieving something.
For example, to benefit from the hexagonal architecture, the wiring can be in `ApplicationConfiguration.java` for pure Java classes or in `application/pom.xml` for Spring instanced classes.
Choosing a h2 database or a mariadb instance needs only to change the dependency used for `offer-persistence-?-adapter` in `application/pom.xml`.

## Other inspirations

- [API versioning](https://medium.com/@XenoSnowFox/youre-thinking-about-api-versioning-in-the-wrong-way-6c656c1c516b)
- [BDD for domain modules](https://cucumber.io/)
- [Integration tests](https://www.testcontainers.org/)
