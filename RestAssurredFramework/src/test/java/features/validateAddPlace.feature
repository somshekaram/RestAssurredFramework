Feature: Validating place API's
@addPlace
Scenario Outline: Verify if place is suceessfully added using place AddPlaceAPI
       Given Add place payload with "<name>" "<language>" "<address>"
       When user calls "addPlaceAPI" with "post" http request
       Then the API call is sucess with status code 200
       And "status" in response body is "OK"
       And "scope" in response body is "APP"
       And verify placeid created that map to "<name>" using "getPlaceAPI"
Examples:
   |name    |language |address      |
   |Som |English  |Bangalore    | 
 # |BBhouse |Kannada |United States|   
 
 @deletePlace
 Scenario: Verify if DeleteAPI  functionality is working
   Given DeletePlace payload
   When  user calls "deletePlaceAPI" with "Post" http request
   Then the API call is sucess with status code 200
   And "status" in response body is "OK" 
       