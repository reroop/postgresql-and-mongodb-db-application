# MongoDb embedded controller:
- GET `http://localhost:8080/mongoemb`

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

### Persons and its embedded entities:
In this embedded 'Person' controller, the entity 'Person' contains much information, therefore the controller has many endpoints with the same base-URL, which interact with different entities.
The controller basically contains 'sub-controllers' for embedded entities like Employee, UserAccount etc.
****  

#### Persons:
- GET /persons
  > returns all Person entities
- GET /persons/{personId}
  > get specific person by (object)id
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

- POST /persons/{objectId}/userAccount `+ requestBody`

  	 {  
  	 "parool": string,    
  	 "on_aktiivne": boolean    
  	 } 

  - f.e:


			 /persons/623ad990acdcf7008e6f2354/userAccount 
			+ requestBody: 
			 { 
			 "parool": "testimisParool 2222",    
			 "on_aktiivne": true  
			 }   

- POST /persons/{objectId}/employee `+ requestBody`

  	{
  	"tootaja_seisundi_liik_kood": integer, 
  	"mentor_id": string    
  	} 

  - f.e:

    	 /persons/623ad990acdcf7008e6f2354/employee
    	 + requestBody: 
    	{ 
    	"tootaja_seisundi_liik_kood": 1 
    	}   

- POST /persons/{objectId}/employee/employments `+ requestBody`
  > add a new employment to employee

  	{  
  	"amet_kood": integer,     
  	"alguse_aeg": date    
  	}  
  - f.e:

    	/persons/623ad990acdcf7008e6f2354/employee/employments  
    	+ requestBody: 
    	{        
    	"amet_kood": 2,   
    	"alguse_aeg": "2022-03-15T09:00:00"   
    	}  

- PUT /persons `+ requestbody`
  >update person information
  - f.e:

    	{    
    	"_id": "623ad990acdcf7008e6f2354",    
    	"isikukood": "39501010123",  
    	"synni_kp": "1995-01-01", 
    	"eesnimi": "Mait Juss"
    	}   
- PUT /persons/{objectId}/userAccount
  > update user account info
  - f.e:

        {    
        "parool": "test parool222",    
        "on_aktiivne": true  
    	 }   
- PUT persons/{objectId}/employee
  > update Employee status or mentor  
  > **NOTE**: if mentorId is not set in requestBody, then mentorId **will be set as null**
  - f.e:

    	{    
    	"tootaja_seisundi_liik_kood": 2,    
    	"mentor_id": "622e5b71498b505a432f222e"    
    	}   
- PUT /persons/{objectId}/employee/employments/{occupationCode}
  > end active employment for employee in occupation