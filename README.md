## _Dear Imperial programmer.._

My name is [Wilhuff Tarkin](https://starwars.fandom.com/en/wiki/Wilhuff_Tarkin) commander of the imperial fleet.
Emperor Palpatin himself has asked us to have detailed information on the trade routes of the galaxy. And we need your services to provide a technical solution to this problem.

The use case is the following, we need a web service (API) from which we can obtain information on trade routes.
- To have a map of commercial flights you need the list of routes and the distance between them.
In this case we need the response to have the following schema

     ```sh
     //The name of the planets must be in a language understandable by all operators
     //The distance must be in lunar days
     [
         {
           "Origin": "Tatooine",
           "Destination": "Alderaan",
           "Distance": 8743
         }
     ]
     ```
- In order to know the fuel cost of each route we need a service to which we pass two parameters, "Origin", "Destination" and we obtain as a response the total price of the route and a breakdown of the expenses of the routes, in the which will be the price per lunar day, the security cost of each planet and the elite security cost.
   - To calculate the security price, the rebel influence on the two planets must be taken into account. If the sum of the influence of the two planets exceeds 40%, an additional "EliteStromTrooper" rate must be applied, which will be the difference between the sum of the two planets and 40%.
   - The correct way to calculate the total price would be the following: base price = distance * lunar day price, the defense percentages are added and the defense rate is calculated based on the base price. Total price = Base Price + taxes.

     ```sh
     Example of the request
     //The name of the planets that are entered in the request must be in understandable language for the operators
     {
       "origin": "Tatooine",
       "destination": "Aldeeran"
     }
     ```
     In this case we need the response to have the following schema
    
     ```sh
     {
       "totalAmount": 1235.12,
       "pricesPerLunarDay": 12.35
       "taxes":{
           "originDefenseCost": 2.12,
           "destinationDefenseCost": 3.56,
           "eliteDefenseCost": 0.00
       }
     }
     ```

Admiral [Motti] (https://starwars.fandom.com/en/wiki/Conan_Antonio_Motti/Leyendas) has left the following notes after investigating him.

> Hello Commander, investigating thoroughly where to obtain the static information of the planets that are within the trade routes and the distance between them, there are the following public services of the union.
> * Planets: https://www.bloks.cat/ws/planets.json
> * Distances: https://www.bloks.cat/ws/distances.json
>
>These services are from the trade union, so each query entails a cost for the empire. My recommendation is that this information be persisted in some way to reduce costs.
>To consult the price of fuel we have a public service of the empire in which we can obtain the price of fuel per `lunar day`.
> * Price: https://www.bloks.cat/ws/prices.json
>
>Finally, our network of spies has made this web channel available to us to obtain the status of the rebel influence on each of the planets.
> * Rebels: https://www.bloks.cat/ws/spyreport.json


### The empire does not leave its programmers alone, so I will give you the following advice:
* The change from years to days is the terraneo. year = 365 days.
* We are dealing with decimals, try not to use floating point numbers.
* After each calculation, the result must be rounded to two decimal places.

### As Empire programmer we expect to find:
* See how you separate the project by N layers (Distributed Services, application layer, domain layer, ...).
* See how you use SOLID (separation of responsibilities, Dependency Inversion, ...)
* See how you control errors and how you log them.
* See if you use a correct and consistent naming-convention.
* See how you cover the code with UnitTests.

It is important to complete the test using the programming languages listed as technical requirements in the job offer.

  **Please, once the test is finished you simply have to create a request for incorporation (Pull Request) to the master branch of the repository, and the last commit contains the description "Finished". With this we can know that the test has been completed. Merge at the main branch.**
