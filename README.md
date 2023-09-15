# bookstore
### just example http request I used
POST http://localhost:8080/api/v1/users/register
{
    "name": "osman",
    "email":"gultekin@mail.com",
    "password":"1234"
}
POST http://localhost:8080/api/v1/users/login
{
    "email":"gultekin@mail.com",
    "password":"1234"
}

POST http://localhost:8080/api/v1/orders
{
  "books": [
    {
      "isbn": "1001",
      "quantity": 2
    },
    {
      "isbn": "1000",
      "quantity": 3
    }
  ]
}

GET http://localhost:8080/api/v1/orders/1
GET http://localhost:8080/api/v1/orders/details/1
GET http://localhost:8080/api/v1/books/1000
