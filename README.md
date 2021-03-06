gpaNINJA
========

An automatic GPA calculator for York University. Takes student credentials 
(Passport York ID) to scrape transcript from school server, automatically 
computing GPA in one click for a seamless UX. Provides additional stats such 
as GPA on a 4.0 scale and major specific stats (LE supported for this only
currently).


### Why?

There currently are no automatic GPA calculators for York. The school servers 
compute overall GPA only twice a year, at the end of the winter and summer 
semesters, to determine ones academic standing. This is inconvenient, not to 
mention the stats disappear once the next semester starts, in addition to 
limited progress information. The only GPA calculators that are York specific 
require manual input of grades, tedious to say the least, and error prone. 
[Example](http://www.yorku.ca/laps/students/gpa.html)


### Privacy & Use

No user data is stored or logged. The only exception is when an unknown special
case grade is encountered, which is logged, but anonymously.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


### Tech Stack

- Java (7)
- [Apache Tomcat](http://tomcat.apache.org) (7.0.42)
- [Bootstrap](http://getbootstrap.com) (3.2.0)
- [jsoup](http://jsoup.org) (1.7.3)
- [JUnit](http://junit.org) (4.11)
- [Gradle](http://www.gradle.org) (2.1)
    - [Gradle CSS Plugin](https://github.com/eriwen/gradle-css-plugin) (1.11.1)


## Setup

**Gradle**

```bash
# To create Eclipse project files
gradle eclipse

# To build the WAR file and run the JUnit tests
# WAR file: build/libs/gpaNINJA.war
# Test results: build/reports/tests/index.html
gradle build
```

**Java Truststore**

The **wrem.sis.yorku.ca** certificate must be added to your Java 
[truststore](http://stackoverflow.com/a/318450) otherwise HTTPS requests
will not be possible (you will get an exception) 

``` bash
javac src/main/java/dev/InstallCert.java
java -cp ./src/main/java/ dev.InstallCert wrem.sis.yorku.ca

# You should now have a jssecacerts file in your current dir. To check that the 
# cert has been added run the below command. The store password is 'default' 
# or 'changeit' depending on your platform
keytool -list -keystore jssecacerts | grep wrem

# Now to have Java use it, you have two options

# 1. Replace Java's cacerts
#    NOTE: This affects all Java programs, and when Java is updated, this 
#          change will not persist
mv jssecacerts $JAVA_HOME/jre/lib/security/cacerts

# 2. Point Tomcat to this truststore
# Update the following in <TOMCAT_HOME>/bin/catalina.sh
JAVA_OPTS="$JAVA_OPTS -Djavax.net.ssl.trustStore="<PATH_TO_TRUSTSTORE>" -Djavax.net.ssl.trustStorePassword="<TRUSTSTORE_PASSWORD>""
```

For more, see:
- http://stackoverflow.com/a/14837863
- http://stackoverflow.com/q/5162279

**Tomcat SSL**

To generate your own SSL certificate

```bash
# Don't forget the password you set
keytool -genkey -alias tomcat -keyalg RSA

# You'll find it in your home dir
~/.keystore
```

To enable SSL on Tomcat, in **server.xml**, add the following

```xml
<Connector SSLEnabled="true" acceptCount="100" clientAuth="false"
 disableUploadTimeout="true" enableLookups="false" maxThreads="25"
 port="8443"
 keystoreFile="<PATH_TO_KEYSTORE>" keystorePass="<PASSWORD>"
 protocol="org.apache.coyote.http11.Http11NioProtocol" scheme="https"
 secure="true" sslProtocol="TLS" />
```

To force HTTPS, in **web.xml**, add the following

```xml
<security-constraint>
    <web-resource-collection>
        <web-resource-name>securedapp</web-resource-name>
        <url-pattern>/*</url-pattern>
    </web-resource-collection>
    <user-data-constraint>
        <transport-guarantee>CONFIDENTIAL</transport-guarantee>
    </user-data-constraint>
</security-constraint>
```

For more, see:

- http://stackoverflow.com/a/7793684
- http://andyarismendi.blogspot.ca/2012/01/changing-tomcats-ca-trust-keystore-file.html


### License

This project is under the **GNU General Public License v2.0**.
