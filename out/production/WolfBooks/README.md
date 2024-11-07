# WolfBooks

### Team Members
Name | Unity ID
-----|---------
Rajat Girish Chandak | rchanda3
Aditya Chitlangia | achitla
Anant Patel | apatel29
Shubham Amit Saboo | sasaboo

Notes: 
* We used IntelliJ IDEA to develop and run the code. We configured the IDE to use `Java 17.0.1` or newer.
* We developed using MySQL Workbench and the latest MySQL Server `ver: 8.0.40`.
* We also included the JDBC driver `ver: 9.1.0` in the project file under `WolfBooks/lib/my-sql-connector-j-9.1.0.jar`.

Instructions to run the code (using IntelliJ IDEA):
1. Top right, navigate to `File --> Project Structure`.
   1. For SDK, select `Oracle OpenJDK 17.0.1` as the SDK.
      1. You may need to add the SDK under `Platform Settings --> SDKs`.
2. Top right, navigate to `File --> Project Structure`.
   1. Under `Project Settings --> Libraries`, click the '+' and select the .jar under `WolfBooks/lib/my-sql-connector-j-9.1.0.jar`
3. Click apply.
4. Navigate to `WolfBooks/src/main/java/WolfBooks/util/DatabaseConnection.java`.
   1. Change the dbUser and dbPassword strings to the credentials of your database.
5. Open App.java under `WolfBooks/src/main/java/WolfBooks/App.java`.
6. Run the app by clicking the green arrow in the top left of IntelliJ IDEA.
7. Interact with the application using the IntelliJ IDEA terminal.