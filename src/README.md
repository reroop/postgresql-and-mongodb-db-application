**DbApplication backend**
-
- markdown cheatsheet: https://www.markdownguide.org/cheat-sheet/

When using MongoDb, start database by using command `mongod` in commandprompt.

****

**Controllers and urls:**
-

**Index controller:**

- http://localhost:8080/index

****
**MongoDb embedded controller:**

- http://localhost:8080/mongoemb
****
**MongoDb reference controllers endpoints:**
- GET http://localhost:8080/mongoref

Countries:
- GET /countries
- GET /countries/{countryCode}
- POST /countries `+ requestbody`
- PUT /countries `+ requestbody` <- update country name
- DELETE /countries/{countryCode}

***Request bodies:***

- country:

``
{
    "riik_kood": string,
    "nimetus": string
}
``

****
