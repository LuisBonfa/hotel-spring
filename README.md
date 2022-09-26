# cancun-hotel-spring

Project that aims to do the basic management of a check in and check out service of a hotel. This project has the following main dependencies: Lombok, Spring, Hibernate, Mockito, Docker, PostgreSQL ,Flyway.

<h2>🛠 Getting Started</h2>

<h3>Starting the database.</h3>

The database(PostgreSQL) is being used inside a docker, so docker initialization is required.

    docker-composer up

If you do not want to user docker just run the script found in initiliazaiton.sql in your local database using **psql**
<h3>Creating Tables</h3>

To create the tables just run the run.sh script inside the scripts folder using:

    bash run.sh

After running the bash script this structure of tables will be created:

![image](https://user-images.githubusercontent.com/22968049/192282054-c107e877-e180-4a8a-9284-72b51de7b9fd.png)

**Booking:** Used to stores booking information and status.<br>
**Booking History:** Used to store booking history, for example if you change the date of the booking, the old date gets stored here.<br>
**User:**  Used to store user information.<br>
**Role:**  Used to store role information.<br>
**User role:**  Used to relate the user to roles, using this we can achieve in the future a complex user permission control.<br>

<h2>Starting the project</h2>
Once the above steps are done you can start the project. At startup the default user will be created with your credentials.

**username:** root<br>
**password:** root@123456

<br/>
<h2>Testing</h2>
All the documentation was made using Swagger. To access it, just follow this link:
http://your_host:8090/swagger-ui/
