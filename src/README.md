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


Occupations:

- GET /occupations
- GET /occupations/{occupationCode}
- POST /occupations `+ requestbody`
- PUT /occupations `+ requestbody` <- update occupations name or description
- DELETE /occupations/{countryCode}

Employee status types:

- GET /employeeStatusTypes
- GET /employeeStatusTypes/{employeeStatusTypeCode}
- POST /employeeStatusTypes `+ requestbody`
- PUT /employeeStatusTypes `+ requestbody` <- update employeeStatusType name or description
- DELETE /employeeStatusTypes/{employeeStatusTypeCode}

Persons:

- GET /persons
- GET /persons/{objectId} <- f.e `622e5b50498b505a432f222c`
- GET /persons/{countryCode}/{personalIdentificationCode} <- f. e ``EST; 39501010123``
- POST /persons `+ requestbody`

      {
      "isikukood": "39931125555",
      "riik_kood": "FIN",
      "e_meil": "kait@meil.com",
      "synni_kp": "1999-12-31T00:00:00",
      "eesnimi": "Kait",
      "perenimi": "Mets",
      "elukoht": "Kuuse 7, Rae vald, Harjumaa, Eesti"
      }
- PUT /persons `+ requestbody`
  
        {
        "_id": "622e5b50498b505a432f222c",
        "isikukood": "39501010123",
        "synni_kp": "1995-01-01",
        "eesnimi": "Mait Juss"
        }
- DELETE /persons/{_id}

User accounts:

- GET /userAccounts
- GET /userAccounts/{personId} <- get UserAccount which is associated with a defined person
- POST /userAccounts `+ requestbody`
  
f.e: 
  ``{
  "isik_id": "62399cf62e1d5c53bfd81af3",
  "parool": "testimisParool 2222",
  "on_aktiivne": true
  }``
     
- PUT /userAccounts `+ requestbody` <- update UserAccount password or boolean isActive
- DELETE /userAccounts/{employeeStatusTypeCode}

***Request bodies:***

- country:

``
{
    "riik_kood": string,
    "nimetus": string
}
``

- occupation:

``
{
"amet_kood": integer,
"nimetus": string,
"kirjeldus": string
}
``

- employee status type:

``
{
"tootaja_seisundi_liik_kood": integer,
"nimetus": string,
"kirjeldus": string
}
``

- person:

``
{
"_id": string,
"isikukood": string,
"riik_kood": string,
"e_meil": string,
"synni_kp": date,
"eesnimi": string,
"perenimi": string,
"elukoht": string
}
``

- user account:

  ``
  {
  "isik_id": string,
  "parool": string,
  "on_aktiivne": boolean
  }
  ``

****
