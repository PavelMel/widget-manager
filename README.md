# Widget-manager

A web service to work with widgets via HTTP REST API.

##Details
A Widget is an object on a plane in a Cartesian coordinate system that has coordinates (X, Y), Z-index, width, height, last modification date, and a unique identifier. X, Y, and Z-index are integers (may be negative).
A Z-index is a unique sequence common to all widgets that determines the order of widgets (regardless of their coordinates). Gaps are allowed. The higher the value, the higher the widget lies on the plane.

Operations to be provided by the web service:
- Creating a widget. Having a set of coordinates, Z-index, width, and height, we get a complete widget description in the response. The server generates the identifier. If a Z-index is not specified, the widget moves to the foreground (becomes maximum). If the existing Z-index is specified, then the new widget shifts widget with the same (and greater if needed) upwards.
- Changing widget data by Id. In response, we get an updated full description of the widget. We cannot change the widget id. All changes to widgets must occur atomically. That is, if we change the ​XY​ coordinates of the widget, then we should not get an intermediate state during concurrent reading. The rules related to the Z-index are the same as when creating a widget.
- Deleting a widget. We can delete the widget by its identifier.
- Getting a widget by Id. In response, we get a complete description of the widget. 
- Getting a list of widgets. In response, we get a list of all widgets sorted by Z-index, from smallest to largest.

###Additional features
####Pagination
pagination mechanism for a list of widgets request, which by default will produce 10 elements, but with the ability to specify a different number (up to 500)
####Filtering
While getting the list of widgets, we can specify the area in which widgets are located. Only the widgets that fall entirely into the region fall into the result.
####SQL database
There are two independent implementations of the widget repository. The choice of storage implementation should be determined in the server configuration and does not require changes in the code.

## Technology stack
* `Java 11`
* `Spring Boot 2.4.4`
* `Maven 3.6.3`
* `JUnit 5`


## Build and Run
### build
- `$ mvn clean install` - This command runs building project
### run  
- `$ mvn spring-boot:run` - This command runs the application with *default profile
- `$ mvn spring-boot:run -Dspring-boot.run.profiles=memory` - This command runs the application with in Memory storage profile
- `$ mvn spring-boot:run -Dspring-boot.run.profiles=db` - This command runs the application with in H2 Database storage profile

*Default profile is **memory**


## API
### models
#### Widget model
```
{
"id": 1,  - id of Widget
"x": 100, - x coordinate 
"y": 150, - y coordinate
"z": 2, - z coordinate 
"width": 30,  - width of Widget
"height": 50, - height of Widget
"lastModificationDate": "2021-03-31T17:19:09.361418+03:00" - date of Widget modification 
}
```
#### Dashboard model
```
{
"x": 100, - x coordinate
"y": 150, - y coordinate
"width": 30,  - width of Dashboard
"height": 50 - height of Dashboard
}
```
### Endpoints
#### GET /widgets
Get all widgets ordered by Z-Index ascending
#### GET /widgets/{id}
Widget by its unique id {id}
#### GET /widgets/page?pageNumber={pageNumber}&pageSize={pageSize}
Get paginated widgets ordered by z-index
- pageNumber (required) - sequential number of page
- pageSize - size of page ( default is 10, max is 500 )
#### GET /widgets/dashboard
Get only the widgets that fall entirely into the region fall into the result.

Request model
```
{
"x": 100, - x coordinate
"y": 150, - y coordinate
"width": 30,  - width of Dashboard
"height": 50 - height of Dashboard
}
```
* This request assumes that the x y coordinates are the top-left point of the dashboard and widget.


#### POST /widgets
Create a widget. The server generates the identifier. If a z-index is not specified, the widget moves to the foreground. If the existing z-index is specified, then the new widget shifts all widgets with the same and greater index upwards.

Request model
```
{
"x": 100, - x coordinate (required)
"y": 150, - y coordinate (required)
"z": 2, - z coordinate 
"width": 30,  - width of Widget (required)
"height": 50 - height of Widget (required)
}
```
#### PUT /widgets/{id}
Update a widget params with id = {id}

Request model
```
{
"x": 100, - x coordinate (required)
"y": 150, - y coordinate (required)
"z": 2, - z coordinate 
"width": 30,  - width of Widget (required)
"height": 50 - height of Widget (required)
}
```

#### DELETE /widgets/{id}
Delete a widget with id = {id}

#### Errors
If an error occurs during the execution of the request, the user receives a description of the error and the corresponding http code.

Request error model 
```
{
"message": "widget not found"
}
```

## Test coverage
* `jacoco-maven-plugin`
- total unit Tests 58 
- total coverage 62%
