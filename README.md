# Locations Service
Web service for fetching city, state (province) and country data via REST API. Built with Groovy,
powered by Spring Boot and made for Java 8.

## Building & Running Locally
### Requirements
* Java 8 -- that's it!

### Build
Clone this repo and run `./gradlew build`

### Run Locally
Run:

```
./gradlew build && java -Dspring.config=. -jar build/libs/locations-service-1.0.0.jar
```

After 15 - 20 seconds the service will startup and will be available off of `localhost:9200`.

## Using the API
### Data Source == null :-/
The original intent of this project was to use an embedded H2 database and load the SQL from
[hiiamrohit's project](https://github.com/hiiamrohit/Countries-States-Cities-database). I also
include Liquibase configs to run this DB's SQL script migrations automatically on app startup.

**I simply ran out of time to do that**, and so the service connects to an empty H2 database and
all the data coming back form the API endpoints is just static/hardcoded/in-memory data.

### TLS
This service uses HTTPS exclusively (although I'm just using a self-signed cert under the hood).

### Authentication
#### Create a User
Uses standard JWT (authorization bearer tokens). Once the service stands you will need to
authenticate by hitting the `/signUp` endpoint like so:

```
curl -k -i -H "Content-Type: application/json" -X PUT -d \
    '{"email":"someuser@example.com","password":"abcdef1$"}' \
    https://localhost:9200/v1/auth/signUp
``` 

This creates a new valid in-memory user and stores their hashed/salted login credentials.

#### Sign In
Next you will need to sign that user into the API by hitting the `/signIn` endpoint like so:

```
curl -k -i -H "Content-Type: application/json" -X POST -d \
    '{"email":"someuser@example.com","password":"abcdef1$"}' \
    https://localhost:9200/v1/auth/signIn
```

If you authenticate successfully, you will get an HTTP response that looks something like:

```
HTTP/1.1 200 OK
Date: Fri, 02 Mar 2018 08:09:51 GMT
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
X-Frame-Options: DENY
Access-Control-Expose-Headers: Authorization
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6aGFydmV5QHBvYm94LmNvbSIsImV4cCI6MTUyMDA2NDU5MX0.RX0wl44HBshilM8j5ItrR0SO0YwnLGNLKbEuOjN0pzNz0DUN6LMNQRlfTQwsXILRLLDmWWr5SxjLdDCK7Aoscw
Content-Length: 0
```

The important line there is:

```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6aGFydmV5QHBvYm94LmNvbSIsImV4cCI6MTUyMDA2NDU5MX0.RX0wl44HBshilM8j5ItrR0SO0YwnLGNLKbEuOjN0pzNz0DUN6LMNQRlfTQwsXILRLLDmWWr5SxjLdDCK7Aoscw
```

That's the Authorization response header that contains your bearer token. The value of that token is:

```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6aGFydmV5QHBvYm94LmNvbSIsImV4cCI6MTUyMDA2NDU5MX0.RX0wl44HBshilM8j5ItrR0SO0YwnLGNLKbEuOjN0pzNz0DUN6LMNQRlfTQwsXILRLLDmWWr5SxjLdDCK7Aoscw
```

And **that** is your JWT. From here on out, when you see `<jwt>` just replace it with the JWT you get back from the `/signIn` endpoint.

### Get Data!
#### Get All Countries
The Get All Countries Endpoint can be accessed by hitting:

```
curl -k -i -H "Content-Type: application/json" -H \
    "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6aGFydmV5QHBvYm94LmNvbSIsImV4cCI6MTUyMDA2NDU5MX0.RX0wl44HBshilM8j5ItrR0SO0YwnLGNLKbEuOjN0pzNz0DUN6LMNQRlfTQwsXILRLLDmWWr5SxjLdDCK7Aoscw" \
    -X GET 'https://localhost:9200/v1/data/locations/countries'
```

Take note of the 2-digit country codes, you can use them for playing around with the next endpoint.

#### Get States/Provinces by Country Code
You can see what states/provinces comprise a given country by hitting this endpoint:

```
curl -k -i -H "Content-Type: application/json" -H \
    "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6aGFydmV5QHBvYm94LmNvbSIsImV4cCI6MTUyMDA2NDU5MX0.RX0wl44HBshilM8j5ItrR0SO0YwnLGNLKbEuOjN0pzNz0DUN6LMNQRlfTQwsXILRLLDmWWr5SxjLdDCK7Aoscw" \
    -X GET 'https://localhost:9200/v1/data/locations/provinces?countryCode=US'
```

Take note of the `id` fields; you can use these for playing around with the next endpoint.

#### Get Cities by Province Id
You can see what cities comprise a given state/province.

```
curl -k -i -H "Content-Type: application/json" -H \
    "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6aGFydmV5QHBvYm94LmNvbSIsImV4cCI6MTUyMDA2NDU5MX0.RX0wl44HBshilM8j5ItrR0SO0YwnLGNLKbEuOjN0pzNz0DUN6LMNQRlfTQwsXILRLLDmWWr5SxjLdDCK7Aoscw" \
    -X GET 'https://localhost:9200/v1/data/locations/cities?provinceId=1'
```
