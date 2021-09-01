# Project
Student management app:
The task here is to create a spring boot application to manage and organize students.
A student is subscribed at an university and can be created, deleted and updated.
A university contains a list of students, which all have different states.

The application user should be able to see the list of users and university and their relation.
He can also create more students or change an university for a specific user.

More functionalities are always welcome.

All should be realised in form of a REST-API via either a relational or no-sql database.


# How to set up this particular project (Windows)
Install mongodb and get any JDK version above 1.8.  
After installing mongodb, create a directory called "data/db" in your C:/ drive
Now launch mongod, create another cmd and connect to the instance via the following command:  
``mongo --port 27017``  
Once a successful connection has been established, you'd want to look forward to create an administrator
account.  
use the following commands  
``use admin``  
followed by  
``db.createUser({ user: "yasin", pwd: "yazici", roles: [ { role: "userAdminAnyDatabase", db: "admin" } ] })``  
Feel free to close the connection via the CLI (but keep the mongod command prompt open!)  

On overall it should look similar to this:  
![image](https://user-images.githubusercontent.com/10624021/131264788-8c9ac722-db72-4721-8cdb-8c2f435d0410.png)

Now, import this project via your favourite IDE through git: https://github.com/YasinY/studentmanagement.git
or download the source code and import it as *maven project*.

For IntelliJ IDEA: 
https://www.lagomframework.com/documentation/1.6.x/java/IntellijMaven.html

For Eclipse:
https://www.lagomframework.com/documentation/1.6.x/java/EclipseMavenInt.html

For NetBeans:
https://dzone.com/articles/importing-a-maven-project-in-netbeans

In this case I am using IntelliJ IDEA:  
![image](https://user-images.githubusercontent.com/10624021/131264904-9823bb93-88fe-43fb-8569-e9cd3085b7bd.png)

The project might take a little while to load, as a lot of dependencies have to be imported.  
![image](https://user-images.githubusercontent.com/10624021/131264932-e1d66b1f-d6af-4b85-9968-55c328a2b16f.png)


Create a Run Configuration, basing off the class ``StudentManagementApplication`` located in the package ``com.yasinyazici.studentmanagement``, as seen in the video
  
![image](https://user-images.githubusercontent.com/10624021/131264985-95a0ecb9-33d3-4bd3-b70f-e574c76df630.png)


voilá, now you should be able to run the Spring-Boot application.  
![image](https://user-images.githubusercontent.com/10624021/131265023-e4ff3d67-3431-4015-bfdd-c56f12548dca.png)

in case you want to test out the routes:

For all routes you require basic auth. I suggest postman, as you can easily set up global variables for setting an username and password.
Which is in this case:

``Username: yasin``  
``Password: yazici``  
You can change the username and password required for basic auth at the application.properties file.  
The security is the default security provider by spring-security.  
  
![image](https://user-images.githubusercontent.com/10624021/131265182-e79d5d8d-130f-4cb2-922f-f3c61a323f8a.png)


### Routes:  
POST - localhost:8080/student  
Creates a student if it doesn't already exist and adds him into the defined university - if the university is available in the corresponding collection.  
Basic Auth required: yes  
Example Body:  
`` {
    "universityName": "Universität Wien",
    "name": "Yasin",
    "address": "NicebnAUTZe"
} ``  
  
GET - localhost:8080/student  
Gets a list of ALL current students, for every university.  
Basic Auth required: yes  

GET - localhost:8080/student?university=University Name  
Gets a list of ALL current students, for the specified university  
Basic Auth required: yes  
  
PATCH - localhost:8080/student  
Updates a students university name, if the university is available in the corresponding collection.  
Basic Auth required: yes    
Example Body:  
`` {
    "universityName": "Universität Hamburg",
    "name": "Yasin",
    "address": "NicebnAUTZe"
} ``  
  
DELETE - localhost:8080/student  
Deletes a student and wipes his records off the University.  
Basic Auth required: yes  
Example Body:  
``
{
    "name": "Yasin",
    "address": "NicebnAUTZe"
} 
``

GET - localhost:8080/universities  
Lists all registered universities  
Basic auth required: yes

POST - localhost:8080/university/exmatriculate  
Exmatriculates a student from his current university, 
although the entry remains on both entities  
Basic Auth required: yes  
Example Body:  
``
{
"name": "Yasin",
"address": "NicebnAUTZe",
"finished": false
}
``
  
# TODO:
Unit Testing  
Refactoring of the endpoints (duplicate code)  
Documentation

  

Error when creating student explained:  
https://stackoverflow.com/questions/46671472/illegal-reflective-access-by-org-springframework-cglib-core-reflectutils1

WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.springframework.cglib.core.ReflectUtils (file:/C:/Users/Yasin/.m2/repository/org/springframework/spring-core/5.3.9/spring-core-5.3.9.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
WARNING: Please consider reporting this to the maintainers of org.springframework.cglib.core.ReflectUtils
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
