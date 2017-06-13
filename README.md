# **This README is a snapshot from June 12, 2017; All active development is in the 0.x branch at this time**

## Seltzer
Seltzer is a server meant for providing Selenium access and functionality to OSGi applications. Selenium does not provide a proper OSGi manifest and instead of rebundling it and any necessary dependencies, Seltzer was built. 

When the server is running, it is accessed by sending it `Command` objects, and it will reply with `Response` objects. There are several currently available `Command` objects, and they are replied to with a small handful of appropriate `Response`s.

Selenium is a huge project with an incredible amount of functionality. Not everything is provided in Seltzer (yet!). The maintainers are adding functionality as it becomes necessary for us, but we are open to suggestions and requests.

### License
Seltzer is licensed under the MIT License.

### Projects
* `seltzer-parent` - The parent project
* `selter-core` - **OSGi incompatible**; The server itself, responsible for managing Selenium sessions and executing commands
* `seltzer-cr` - **OSGi compatible**; The project providing Command and Response objects and the relevant enums for them to operate
* `seltzer-util` - **OSGi compatible**; An upcoming project (`0.14.0` or `0.15.0`) that will provide easier communication with a Seltzer server
 
### Requirements
* JDK 7.0+ (no lower versions tested)
* Apache Maven 3.3+ (no lower versions tested)
 
### Dependencies (If You're Not Using Maven)
* `seltzer-cr`
    * Google Gson
        * Group ID: `com.code.google.gson`
        * Artifact ID: `gson`
        * Version: `2.8.0`
    * JUnit
        * Group ID: `junit`
        * Artifact ID: `junit`
        * Version: `4.12`
* `seltzer-core`
    * Seltzer Command/Reponse
        * Group ID: `hall.caleb.seltzer`
        * Artifact ID: `seltzer-cr`
        * Version: `[0.10.0, 1.0.0)`
    * Selenium Java
        * Group ID: `org.seleniumhq.selenium`
        * Artifact ID: `selenium-java`
        * Version: `3.0.1`
    * Google Gson
        * Group ID: `com.code.google.gson`
        * Artifact ID: `gson`
        * Version: `2.8.0`
    * Log4J Core
        * Group ID: `org.apache.logging.log4j`
        * Artifact ID: `log4j-core`
        * Version: `2.7`
    * Log4J API
        * Group ID: `org.apache.logging.log4j`
        * Artifact ID: `log4j-api`
        * Version: `2.7`

### Compiling Seltzer
If you compile seltzer-parent using Maven, you need to set your goal to `install` so that `seltzer-cr` will get installed to your local repository, allowing `seltzer-core` to build from there. You also need to provide a system property called `repo.path` which points to the location of the cloned Seltzer repository. This is necessary for proper location of the Chrome web driver that Selenium uses. If you don't provide `repo.path`, the project won't build due to failed unit tests.

On Windows, things are a little easier. There is a provided batch file that needs 1 alteration. The path within it needs to be changed to your local path to the repo's location. After that, run it and Maven will handle everything else.

Maven will build a version of `seltzer-core` that includes its dependencies, making it easy to run it wherever you need to.

### Running Seltzer
Seltzer is easy to run. Look at the included `run.bat` to see the Java call. Don't forget `repo.path`!

### Using Seltzer with an OSGi Application
If you're using Maven, then `seltzer-cr` will have its OSGi manifest built for you Otherwise, here's the key lines for the manifest:
* `Import-Package: com.google.gson`
* `Bundle-SymbolicName: seltzer-cr`
* `Bundle-Name: Seltzer Command/Response`
* `Export-Package: hall.caleb.seltzer.objects.command;uses:="hall.caleb.seltzer.enums,com.google.gson",hall.caleb.seltzer.objects.response;uses:="hall.caleb.seltzer.enums,com.google.gson",hall.caleb.seltzer.enums`

Make sure to include `seltzer-cr-x.y.z.jar` in your OSGi's bundle list and your application will be able to import `Command` and `Response` objects. This README will be updated with instructions for communication between an OSGi application and Seltzer when the `seltzer-util` project is included and building.