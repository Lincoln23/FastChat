## FastChat


The logic behind a ChatBot which analyzes user inputs and queries a database

Built using:
  - [Spring Boot][SpringBoot]
  - [AWS comprehend][Comprehend]
  - [AWS EC2][EC2] or on localhost
  - [AWS RDS][RDS] (MySQL)

## Usage and Paramters
  - By default the service is hosted on port **8080**
  - Acceptes **Get** requests
  * `text` - Required: The user input message
  * `limit` - Optional: limits the number of input returned
```sh
http://Your IP or localhost :8080/${input}?limit=${int value}
```
Example
```sh
http://localhost:8080/who works in toronto and for amazon?limit=20
```
## Result
  - Response is in **JSON**
* `Response` - There are two parts to the response
  * `resultList` - An **JSON Array** which contains all the information from the query
  * `plainText` - A simplified string that combines all the information from `resultList` into a sentence



## Installation
  - Java 8
  - Import as a Maven Project
  - Create an AWS account and and create an Access/Secret Key
  - Launch an MySQL RDS instance

** **NOTE** **
You will need to configure your own SQL database and will need to change `Dao/AWS_RDS_dao`  so it correcty queries the data


Put the apporiate values in  `{resources/application.properites}`
  ```sh
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
AWS_ACCESS_KEY=
AWS_SECRET_KEY=
...
```

## Testing
You can write your own Junit testing in `{src/test/java}`



   [EC2]: <https://aws.amazon.com/ec2/>
   [RDS]:<https://aws.amazon.com/rds/>
   [SpringBoot]: <https://spring.io/projects/spring-boot>
   [Comprehend]: <https://aws.amazon.com/comprehend/>
   [AWS]: <https://aws.amazon.com/sdk-for-java/>

## TODO
  - Swtich to elastic search and OpenNLP
