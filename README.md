# JPA Demo code
## HibernateConfig file
- `getEntityManagerFactory()` and `getEntityManagerFactoryForTest()` are the 2 main methods used to get an EntityManagerFactory with the suitable properties for either:
  - test: using docker test container with a database that is reset after each test
  - development: using postgresql locally (using our docker container setup)
  - deployment: using environment variables to set connection to the postgresql server on docker at digital ocean
- set up the database to use for development in this files getDBName() method

## Entities
The entities are found in `dk.cphbusiness.persistence.model`
- Boat (id, brand, model, name, registrationDate)
  - ManyToOne (Harbour)
  - ManyToMany (Owner: owning side): 
    - joinTable
    - fetchType eager
  - OneToMany (Seats: unidirectional)
- Seat (number (pk), description)
  - No referece to Boat (unidirectional relationship)
- Harbour (id, street, name, zip, city)
  - OneToMany (Boat)
- Owner (id, name, phoneNumber, createdAt, updatedAt, category(ENUM))
  - ManyToMany (Boat)
    - mappedBy 
    - 
## dk.cphbusiness.persistence.jdbc
To compare to how database connection was done on 2nd semester, an example of using jdbc with a connection pool and datamappers are added to this package.

## POM.xml
- dependencies for 
  -  jackson
     lombok
     hibernate
     postgresql
     junit
     hamcrest
     testcontainers
     hikari
- plugins for
  - `properties-maven-plugin` by `org.codehaus.mojo`
  -  `maven-surefire-plugin` by `org.apache.maven.plugins`