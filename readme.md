#Project
Student management app:
The task is to create a spring boot application to manage and organize students.
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


---

# TODO:
Unit Testing  
Refactoring of the endpoints (duplicate code)  
Documentation  

https://stackoverflow.com/questions/46671472/illegal-reflective-access-by-org-springframework-cglib-core-reflectutils1

WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.springframework.cglib.core.ReflectUtils (file:/C:/Users/Yasin/.m2/repository/org/springframework/spring-core/5.3.9/spring-core-5.3.9.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
WARNING: Please consider reporting this to the maintainers of org.springframework.cglib.core.ReflectUtils
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release

TODO guide how to set up stuff, wat the point is and so on and forth

#SOFTWARECRAFTSMANSHIP
