# Search module for my Hotel application

This module gives back a list of hotels or a certain hotel. This data will be consumed by the the other services and the planned native application in a later stage.

## Database:  
Table **hotels**

Name | Type 
------------ | -------------
id | int NOT NULL IDENTITY
name | varchar(50)
roomname | varchar(50)
country | varchar(50)
city | varchar(50)
address | varchar(100)
lat | decimal(9,6)
lon | decimal(9,6)

Table **prices**

Name | Type 
------------ | -------------
hotelid | int NOT NULL
roomid | int NOT NULL
roomname | varchar(50)
price | int NOT NULL

Note: constraint FK_HOTELID FOREIGN KEY (hotelid) references hotels (id)


### Endpoints:  

#### List Hotels with their details

***Endpoint:*** {host}/v1/hotels

***Example:*** http://localhost:2222/v1/hotels
#### List a certain hotel with its short details
***Endpoint:*** {host}/v1/hotel  

Params | Type  
------------ | -------------   
id | int   

***Example:*** http://localhost:2222/v1/hotel?id=1