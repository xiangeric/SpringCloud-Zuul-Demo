Server port:
============
Zuul: 8080
Eureka client: 6060
Eureka server: 7070
Oauth2 server: 9090

OAuth2 JWT flow:
1. Access Eureka Client by Zuul Server in the first time: http://localhost:8080/myzuul/client/test
2. Zuul Server detect user that user has not JWT token, then redirect user to OAuth Server page to login in.
  when login in successfully, OAuth Server will create a JWT token for user, then redirect user to Zuul Server's login entry with JWT token
  When Zuul has the JWT token, it will validate the JWT with OAUth2 server. if validate successfully, zuul will redirect user to Eureka client with JWT token
3. Eureka Client will validate the JWT, if validate successfully, then allow user to access
when user access Eureka client with JWT token in the second time, user not need to login in again as user has a valid JWT token
