# MongoDb reference controller:
- GET `http://localhost:8080/mongoref`

#### Countries:
- GET /countries
- GET /countries/{countryCode}
- POST /countries `+ requestbody`

      {
      "riik_kood": string,  
      "nimetus": string
      }

- PUT /countries `+ requestbody`
  > update country name
    - f.e:

          {  
           "riik_kood": "EST", 
           "nimetus": "Eesti Vabariik"
           }

- DELETE /countries/{countryCode}

#### Occupations:
- GET /occupations
- GET /occupations/{occupationCode}
- POST /occupations `+ requestbody`

      {  
      "amet_kood": integer,  
      "nimetus": string,  
      "kirjeldus": string  
      }

- PUT /occupations `+ requestbody`
  > update occupations name or description
- DELETE /occupations/{occupationCode}

#### Employee status types:
- GET /employeeStatusTypes
- GET /employeeStatusTypes/{employeeStatusTypeCode}
- POST /employeeStatusTypes `+ requestbody`

      {  
      "tootaja_seisundi_liik_kood": integer,  
      "nimetus": string,  
      "kirjeldus": string  
      }

- PUT /employeeStatusTypes `+ requestbody`
  > update employeeStatusType name or description
- DELETE /employeeStatusTypes/{employeeStatusTypeCode}

#### Persons:
- GET /persons
- GET /persons/{objectId}
  > f.e: `/persons/622e5b50498b505a432f222c`
- GET /persons/{countryCode}/{personalIdentificationCode}
  > f. e: ``/persons/EST/39501010123``
- POST /persons `+ requestbody`

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
    - f.e:


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
    - f.e:


     { 
     "_id": "622e5b50498b505a432f222c", 
     "isikukood": "39501010123", 
     "synni_kp": "1995-01-01T00:00:00", 
     "eesnimi": "Mait Juss" 
     }

- DELETE /persons/{_id}

#### User accounts:
- GET /userAccounts
- GET /userAccounts/{personId} <- get UserAccount which is associated with a defined person
- POST /userAccounts `+ requestbody`

      {  
      "isik_id": string,  
      "parool": string,  
      "on_aktiivne": boolean  
      }

    - f.e:


    {
    "isik_id": "62399cf62e1d5c53bfd81af3", 
    "parool": "testimisParool 2222", 
    "on_aktiivne": true
    } 

- PUT /userAccounts `+ requestbody`
  > update UserAccount password or boolean isActive
- DELETE /userAccounts/{employeeStatusTypeCode}

#### Employee:
- GET /employees
- GET /employees/{personId}
  > get Employee which is associated with a defined person
- POST /employees `+ requestBody`

      {  
      "isik_id": string, 
      "tootaja_seisundi_liik_kood": integer, 
      "mentor_id": string 
      }
    - f.e:

          {  
          "isik_id": "62399cf62e1d5c53bfd81af3",  
          "tootaja_seisundi_liik_kood": 2,  
          "mentor_id": "622e5b71498b505a432f222e"  
          } 

- PUT /employees `+ requestBody`
  > update Employee status or mentor
  > **NOTE**: if mentorId is not set in requestBody, then mentorId **will be set as null**
- DELETE /employees/{personId}

#### Employment:
- GET /employments
  >get all employments
- GET /employments/occupationCode={occupationCode}
  > get all employments for occupation
- GET /employments/personId={personId}
  > get all active employments for employee
- POST /employments `+ requestBody`

      {  
      "isik_id": string,  
      "amet_kood": integer,  
      "alguse_aeg": date  
      }
    - f.e:

          {  
          "isik_id": "62399cf62e1d5c53bfd81af3", 
          "amet_kood": 3, 
          "alguse_aeg": "2022-03-10T00:00:00" 
          }

- PUT /employments/personId={personId}/occupationCode={occupationCode}
  > end active employment for employee in specific occupation