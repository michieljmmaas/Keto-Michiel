# KetoTracker
Een Java Web Applicatie om mijn Keto Dieet bij te houden. 

## Installation:
 - Clone Repo
 - Maak een MariaDB database aan op port `3306` met de naam `keto`
 - Load de laatse [SQL_Snapshot](demo/src/Artifacts/05042020-dump.sql) in
 - Run `mvn clean install` om alle dependecies te installeren
 
 - Start de applicatie vanaf [Application.java](demo/src/main/java/hello/Application.java)
   - Dit start zelf de Tomcat Server (`port 8080`) op, en maakt connectie met de database.
   - Wanneer de server is opgestart opent het zelf een tabblad in je browser met de Index pagina
   
 ## Deployment
 Het is ook mogelijk om een `.jar` bestand te maken van de applicatie. Wanneer deze wordt uitgevoerd start het zelf de Tomcat Server en Database op. 
 
 Doe dit door `mvn clean package` te runnen in de root van het project. 
 
