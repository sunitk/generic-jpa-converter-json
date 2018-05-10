# Spring Boot 2 generic JPA converter to serialize and deserialize an entity attribute as JSON
Generic JPA converter for JSON. Uses Spring Boot, JPA with Hibernate.

Sometimes an entity attribute is not just a native or simple type like a Date, Long or String. 
A Java POJO like structure is required as an attribute. One might argue that a separate entity 
should be created which results in a separate table and a reference to it should be used like in 
a OneToOne or ManyToOne or OneToMany type of relationship. But there are special business conditions 
where creating a separate entity is not feasible. So, storing this entity as a JSON string is a good solution.

This repository contains code which accompanies the blog post [Spring Boot 2 generic JPA converter to serialize and deserialize an entity attribute as JSON](https://sunitkatkar.blogspot.com/2018/05/spring-boot-2-generic-jpa-converter-to.html)

## Getting Started

This is a typical maven project. Download the source as a zip file or checkout the code and import as an Existing Maven project in your IDE.

### Prerequisites

* Java 8
* Spring Boot 2
* MySQL
* Not mandatory, but you can use any suitable IDE like Spring STS

 

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) 
* [Thymeleaf](https://www.thymeleaf.org/) - Rendering HTML pages


## Authors

* **Sunit Katkar** - *Initial work* - [Sunit Katkar](https://sunitkatkar.blogspot.com/)

## License

This project is licensed under the Apache License - see the [LICENSE.md](LICENSE.md) file for details

## Request
You are free to fork this repository, but please drop me a note at sunitkatkar@gmail.com
