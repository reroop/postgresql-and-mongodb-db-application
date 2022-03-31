# DbApplication backend
Useful links/credit:
- [Markdown cheatsheet](https://www.markdownguide.org/cheat-sheet/)
- [Stackedit.io](https://stackedit.io/app#)

## Notes

- to use MongoDbReference, start spring with profile: `gradlew bootRun --args='--spring.profiles.active=mongoref'`
- to use MongoDbEmbedded, start spring with profile: `gradlew bootRun --args='--spring.profiles.active=mongoemb'`
- to use PostgreSql Trad., start spring with profile: `gradlew bootRun --args='--spring.profiles.active=postgretrad'`


****
## Controllers and URLs

### Index controller:
- GET `http://localhost:8080/index`
****
### MongoDb reference controller:
- GET `http://localhost:8080/mongoref`
****
### MongoDb embedded controller:
- GET `http://localhost:8080/mongoemb`
****
### Postgre traditional controller:
- GET `http://localhost:8080/postgretrad`
****


