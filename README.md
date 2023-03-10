# Intuit Payments REST Service

This is a REST API for the payments service

### First Assumption About Authentication
Only authenticated users can create payments for themselves and get their payment methods.
So im assuming there is a proxy authentication service that authenticates the request and passing a UserID in the header to the REST API.

### APIS

#### POST /users/payments
Creates a payment

**Headers**

must have UserID header for specifying the user making the request.

**Body JSON**
```json
{
   "amount": 70.5,
   "currency": "USD",
   "payeeId": "4c3e304e-ce79-4f53-bb26-4e198e6c780a",
   "paymentMethodId": "8e28af1b-a3a0-43a9-96cc-57d66dd68294"
}
```

**Returns**
1. 200 if created successfully.
2. 400 if bad json, payment method doesnt belong to user or payeeId doesnt exist
3. 500 if something went wrong or it failed to create a message in Kafka.
4. 401 if user is not authorized meaning UserID header doesnt exist in the request.


#### GET /users/payments/methods
Return the payment methods for a user

**Headers**

must have UserID header for specifying the user making the request.

**Returns**

1. 200 with json
```json
   [{
   "method": "MasterCard",
   "lastDigits": "4321"
   }]
```
2. 401 if user is not authorized meaning UserID header doesnt exist in the request.


#### GET /users?search=bob
Returns the users (people you can pay to) that answer the search query param

**Query Parameter** search - query to find users

**Returns**
1. 200 with json of users that answers the search
```json
[{
  "userId": "111111", 
  "name": "Peter Parker",
  "email": "blabla@bob.com"
}]
```

### Running Locally
You can run everything the service with Kafka using docker-compose
There is also Kafka UI in localhost:8080

## Architecture For the Entire Solution On AWS
<img width="1067" alt="Screen Shot 2023-02-12 at 19 36 46" src="https://user-images.githubusercontent.com/87701000/218327290-fe9ccdca-75ba-4a38-9243-27c26fd87485.png">

## Full AWS MANAGED Architecture Solution
<img width="1137" alt="Screen Shot 2023-02-13 at 14 42 29" src="https://user-images.githubusercontent.com/87701000/218460781-429cdb4a-7f5f-4d92-8304-09ebb9fbada8.png">

