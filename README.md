# Chiarafiles
 is an opensource javaweb program allow you to upload files to chiaradatabase blob system. 
And is the source code of chiarafiles.org service. 


The  of this programm is distributed under the gnu public license 3. see the file COPYING for more details.

Please if you contribute, contribute first on 

 -	AES encryption. 

 - 	Increase the upload limit with chiaradatabase. 





Chiarafiles use jquery cassandra driver datastax for java,chiaradatabase and boostrap.
Before compiling you need to configure the database connection and chiaradatabase in : com.gaspard.you.ConfigurationChiara

And you need to own your maven repo chiaradatabase java connector please check on this repo for more info: https://github.com/gaspardchiara/chiaradatabaseconnector

For compile chiarafiles we use maven simply do : 
```java
mvn clean install
```
And   deploy the war file in target/chiara.xxx.war with your favourite servlet container.

Thanks for your interest of chiarafiles !
Please note : the code view is better on netbeans IDE


# license 
[GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html)
