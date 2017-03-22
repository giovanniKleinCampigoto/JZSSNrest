# JZSSNrest
This is a RESTfull API built in java, with the jersey and hibernate framework...
The REST client used was POSTMan

# Start Up Application

- Install tomcat 8.5 or 9 server;
- Have MySQL installed or install it;
- Use the script under /db directory to generate the database and tables;
- Place the zombierest.war file in the deployment folder into tomcat/webapps folder;
- Start tomcat server in tomcat/bin, with the command ./startup.sh;
- Acess the api using the endpoint http://localhost:8080/zombierest/webapi/survivors;

Documentation (JAVADOC) on methods is avalible at /docs, index.html.

# To deploy the application
- Install eclipse IDE, with jdk v 1.8 or superior;
- Install tomcat 9 server;
- Install MySQL Workbench;
- Clone this repository;
- Import project to eclipse (File > Import... > Project with existing files); 
- Import DB backup to MySQL Workbench located in the db folder (File > Import > Reverse Engineer );
- Run the project on server
- To Run the tests uncomment the last dependency at pom.xml and Run the class SurvivorResourceTest under /test directory as JUnit test.

# In regards to the api

I used POSTman for testing purposes, you can add it to chrome via chrome web store, is a client to the rest API altough,
if you run the JUnit tests client tests were generated.

All the HTTP methods can be done in POSTman as well if you follow the structure of the json files.

- Add survivors to the database

Do a POST request to the endpoint, http://localhost:8080/zombierest/webapi/survivors.
With the following .json structure:

```
    {	
			"name":"ajax(francis)",
			"age":45,
			"gender":"M",
			"lonx":1000,
			"lony":-1000,
			"infected":false,

			"inventory":{
			"water":1,
			"food":5,
			"meds":15,
			"ammo":50
			}	
		}
```
Inventories are declared upon survivor creation
    

- Update survivor location

Do a PUT request to the endpoint, http://localhost:8080/zombierest/webapi/survivors/{survivorId}.
With the following .json structure:

```
    {	
			"name":"ajax(francis)",
			"age":45,
			"gender":"M",
			"lonx":#newLon,
			"lony":#newLat,
			"infected":false
		}
```

Note: Inventories cannot be updated, via PUT HTTP method...

- Flag survivor as infected

Do a PUT request to the endpoint, http://localhost:8080/zombierest/webapi/survivors/{survivorId}/reportinfected/{infectedId}

- Trade Items

Do a PUT request to the endpoint, http://localhost:8080/zombierest/webapi/survivors/{survivorId}/trade/{traderId}
With the following .json structure:
```
[
	{
		"water":0,
		"food":0,
		"meds":1,
		"ammo":1
	},
	{
		"water":0,
		"food":0,
		"meds":0,
		"ammo":3
	}
]
```

- Reports

Do a GET request to the endpoint, http://localhost:8080/zombierest/webapi/reports

# About JSON

Example of JSON data is under the /json directory
