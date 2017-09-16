# Spring-MultiTenancy

This project describes a multi tenant architecture using Tenant per SCHEMA approach

1) In this approach, data for one particular tenant will always go into a particular schema. The database is shared amongst all the tenants but there is one schema per tenant.
2) In order to get this working, following needs to be done
   a) In the hibernate config & application properties file, please write the database url, user name, password that you would wish to connect with. <br />
   b) Start the application now and remember to hit the following url: https://localhost:8080/multitenancy/tenantId/empName   <br />
   tenantId: schema name in your database <br />
   empName: employeeName <br />
   c) If the schema would exist, the table Employee would be created in that schema and empName would be stored in that table <br />
   d) If the schema would not exist, it will throw an exception "JDBC connection could not be altered to schema (schemaname) <br />


