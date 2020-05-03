# jpa-example
My JPA experimentation box

## Avoid recurive serialization
If entities are not mapped to a non-entity DTO and are passed through to Jackson for Response serialization, then 
Jackson will call all getters and so will trigger a recursiveness error when we have 
a many-to-many relationship.

This same recursiveness will also occur when using mappers in a service, but this time it occurs inside
your codebase and will therefor be more easily detectable.

### Solution
#### JPA mapping
Map both entities with Lazy loading, declare a read-only transaction on your service.
Create a method that calls the entities repository and send the result to your mapper.

#### Entity to DTO mappers
Now, make sure that your mapper does not serialize your list/set of embedded entities again!
This can be done by creating a target class for your sub-entity (here I use Title) which does not include the lit of main entities (authors).

find AuthorDetailsById() => map to AuthorDetails => map all written books to Titles (excluding a lit of authos this time)

#### Remove lazy lists from equals. hashcode and toString
Use Lombok annotation like this on your entities:

    @EqualsAndHashCode(exclude = {"books"})
    @ToString(exclude = {"books"})
