## Here are some test helpers or brief information about the structure of code directories

### Directory: `/servlet/HelloWorld`

##### Some provided test URL and their related files

|             URL              |        Related Source Code Files         |
| :--------------------------: | :--------------------------------------: |
|  `locahost:8080/index.jsp`   |               `/index.jsp`               |
| `locahost:8080/testBean.jsp` | `/testBean.jsp`, `/WEB-INF/classes/tb/testBean.java` |
| `locahost:8080/helloServlet` | `/WEB-INF/web.xml`, `/WEB-INF/classes/testServlet.java` |



### Directory: `/servlet/General`
##### Some provided test operations and there related files

|                Operation                 |        Related Source Code Files         |
| :--------------------------------------: | :--------------------------------------: |
|    Initialize database with SQL File     |              `/initDB.sql`               |
|      Initialize database with Java       | `/jdbc-test/config.xml`, `/jdbc-test/init.java` |
| List Messages(`localhost:8080/` or `localhost:8080/index.jsp`) | `index.jsp`, `/WEB-INF/config.xml`, `/WEB-INF/lib/*` |
| Insert Message(Via Submitting Modal Form in `index.jsp`) | `insert.jsp`, `/WEB-INF/config.xml`, `/WEB-INF/lib/*` |



### Directory: `/servlet/MVC`
Requests are processed by "Controller" in "MVC" pattern, so generally all files are related with both list and insert operations = =.
