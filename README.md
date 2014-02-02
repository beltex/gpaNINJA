gpaNINJA
========

### What

An automatic GPA calculator for York University. Takes student credentials 
(Passport York ID) to pull transcript from school server, automatically 
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
[Example](http://www.yorku.ca/laps/students/gpa.html).

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
- Apache Tomcat (7.0.42)
- Twitter Bootstrap (3.0.2)
- JSoup (1.7.2)
- JUnit (4.11)

### Live Instance

https://red.cse.yorku.ca:8443/gpaNINJA/

NOTE: The SSL certificate is self-signed, thus you will get a browser warning.

### Why is it called that?

It was my understanding that some unwritten rule existed among hackers in which 
project names are suffixed with 'NINJA', when a better name ceases to exist. 
Apparently, I'm the only one who's heard of this. :)

Setup Notes
========

**Java Truststore**

The **wrem.sis.yorku.ca** certificate must be added to your Java 
[truststore](http://stackoverflow.com/a/318450) otherwise HTTPS requests
will not be possible (you will get an exception) 

``` bash
javac src/dev/InstallCert.java
java -cp ./src dev.InstallCert wrem.sis.yorku.ca

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

**WAR File Export**

Currently, packaging is done through Eclipse, using the export to WAR option.
Given this, to allow for easy Jenkins deployment of the latest master branch
code, the webapps dir contains the exported WAR file. It is a TODO list item to
convert the project to something such as Maven or Gradle for more ideal project
building and deployment.
