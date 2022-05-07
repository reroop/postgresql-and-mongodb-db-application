# The Influence of Using Schema-on-write and JSON Documents in the Database to the Creation of PostgreSQL and MongoDB Database Applications
Databases, where the data structure and constraints are defined and enforced at the database level, have schema-on-write. In window-on-data database applications, one uses graphical user interface to read and manage (create, update, delete) the data in the database. The goal of the work is to investigate how the use of schema-on-write and recording data as JSON documents influence the creation of window-on-data database applications. To fulfil the goal, an experiment is conducted. It means that databases with different designs and a database application for each design are implemented. Requirements for the data (what data is needed and what rules the data must satisfy), functional and non-functional requirements of the application, and the user interface of the application are the same in all the cases. However, different database management systems (DBMSs) and physical database design solutions are used. Databases are created in one SQL DBMS (PostgreSQL) and in one document-based NoSQL DBMS (MongoDB).

The main differences in the designs are the usage of embedded (nested; hierarchical) and referenced (not-hierarchical) JSON documents, which also affects the data validation in the database. The database designs that are implemented in the databases are the following.
-	"Traditional" PostgreSQL database without columns that have the JSONB type.
     -	PostgreSQL database where data is in columns with the JSONB type and:
-	the documents are hierarchical, i.e., follow the nested document model,
-	the documents are not hierarchical.
-	MongoDB database where documents are checked against the schema and:
     -	the documents are hierarchical, i.e., follow the embedded document model,
     -	the documents are not hierarchical.

The database applications are meant for managing data about workers. The databases and database applications are not created for a specific organization. All the database applications have the same user interface. However, they have different back-ends due to the database that they must use. As many as possible data validations (checks) are implemented at the database level with the help of declarative constraints. Necessary checks, which are not possible to implement in the database declaratively, are implemented in the application back-end. All the applications must return an error message to the user interface, when the data inserted by the user violates a validation rule. All the applications are implemented by using Java with Spring Boot framework and TypeScript with React library.
As a result of the work, databases with different design and database applications have been developed. Based on these a comparison has been made, where the respective databases and applications are evaluated from multiple viewpoints. The results of the subjective evaluations are justified by the results of further analysis, which are based on the development of the databases and applications.

The main conclusions of the work are the following.
-	Schema changes and validation rules for JSON documents are easier to implement in MongoDB databases.
-	PostgreSQL has the capability to construct more complex validation rules and offer better error messages (based on user-friendliness).
-	The usage of hierarchical and non-hierarhical JSON documents to record data changes the complexity of applications in different aspects.