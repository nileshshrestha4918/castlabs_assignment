## How to build, package, and run the application

1. Make sure you have Docker, Java 17 and maven installed .

2. Open a terminal or command prompt and navigate to the project's root directory.

3. Build the application using the following command:
   
   `mvn clean install` / `./mvnw clean install`
4. Build the docker image using the following command:

   `docker build -t castlab .`
5. Run the build docker image using following command:

   `docker run -p 8080:8080 castlab`
6. Go to postman or browser and call the 
      
   `http://localhost:8080/analyze?url=http://demo.castlabs.com/tmp/text0.mp4`
   