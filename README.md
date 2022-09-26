# cancun-hotel-spring

Project that aims to do the basic management of a check in and check out service of a hotel. This project has the following main dependencies: Lombok, Spring, Hibernate, Mockito, Docker, PostgreSQL.

<h2>🛠 Getting Started</h2>

<h3>Starting the database.</h3>

The database(PostgreSQL) is being used inside a docker, so docker initialization is required.

    docker-composer up

<br>

<h3>Creating Tables</h3>

To create the tables just run the run.sh script inside the scripts folder using:

    bash run.sh

Once the above steps are done you can start the project. At startup the default user will be created with your credentials.

**username:** root
**password:** root@123456

<h2>Testing</h2>
All the documentation was made using Swagger. To access it, just follow this link:
http://your_host:8090/swagger-ui/