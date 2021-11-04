# books-api

## Run without docker
----------------------------------------------
```
mvn install
```
```
java -jar ./target/books-api-1.0.0.jar
```

## Load the docker image and run
-----------------------------------------------
#### Download books-api.tar from the release page [books-api.tar v1.0.0](https://github.com/sujalmandal/books-api/releases/tag/1.0.0)
```
docker load < books-api.tar
```
```
docker image ls
```
```
docker run -d --name booksapp -p8080:8080 books-api:1.0.0
```

## Access details
------------------------------------------------------------------
This application is swagger enabled. 
To view and try the API's, visit : http://localhost:8080/swagger-ui.html

This application uses H2 (in-mem database).
To view the database, visit :  http://localhost:8080/h2-console/

| jdbc-url | user | password |
| ------ | ------ | -------- |
| jdbc:h2:mem:book-db | root | none |


## If you are building this project on your system and want to export a docker image.
###### (Dockerfile required in the directory)
-----------------------------------------------
```
mvn install
```
```
docker build -t books-api:1.0.0 ./
```
```
docker save books-api > books-api.tar
```
