## Here are some test helpers or brief information about the structure of code directories

### Directory: `/servlet/HelloWorld/`
##### Provided test URL and their related files

| URL                          | Related Source Code Files                |
| :--------------------------- | :--------------------------------------- |
| `locahost:8080/index.jsp`    | `/index.jsp`                             |
| `locahost:8080/testBean.jsp` | `/testBean.jsp`<br>`/WEB-INF/classes/tb/testBean.java` |
| `locahost:8080/helloServlet` | `/WEB-INF/web.xml`<br>`/WEB-INF/classes/testServlet.java` |

### Directory: `/servlet/General/`
##### Provided test operations and their related files

| Operation                                | Related Source Code Files                |
| :--------------------------------------- | :--------------------------------------- |
| Initialize database with SQL File        | `/initDB.sql`                            |
| Initialize database with Java            | `/jdbc-test/init.java`<br>`/jdbc-test/config.xml` |
| List Messages: Initial Version<br>(URL: `localhost:8080/index(initial).jsp`) | `/index(initial).jsp`<br>`/WEB-INF/lib/*` |
| List Messages: Version With Configuration File<br>(URL: `localhost:8080/` or `localhost:8080/index.jsp`) | `/index.jsp`<br>`/WEB-INF/config.xml`<br>`/WEB-INF/lib/*` |
| Insert Message: Java Implementation<br>(Via Submitting Insert Modal Form in index page) | `/insert(java).jsp`<br>`/WEB-INF/config.xml`<br> |
| Insert Message: JSTL Implementation<br>(Via Submitting Insert Modal Form in index page) | `/insert.jsp`<br>`/WEB-INF/config.xml`<br>`/WEB-INF/lib/*` |

### Directory: `/servlet/MVC/`
Requests are processed by "Controller" in "MVC" pattern, so generally all files are related with both list and insert operations = =
