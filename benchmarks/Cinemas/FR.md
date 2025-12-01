// ==version1==
```
+ Add a room to the cinema. If the room is not already registered, add it and return true. If the room already exists, return false.

+ Check a room’s availability at a given time (yyyy-MM-dd HH:mm:ss). Check if the room is already added to the cinema and assigned for another screening at the given time. Return true if the room is available. Return false if the room is already assigned or the inputs are invalid. 

+ Add a film to the cinema. If the film does not already exist, add it and return true. If the film already exists, return false.

+ Remove a film from the cinema system at the specified current time (yyyy-MM-dd HH:mm:ss), and remove all of its future scheduled screenings (the screening time >= the current time). Return true if the film is removed successfully. Return false if it does not exist. 

+ Assign a screening to a film and room at a specific screening time (yyyy-MM-dd HH:mm:ss) and current time (yyyy-MM-dd HH:mm:ss). Verify that the current time should be before (<) the screening time. Verify film added to the cinema and room availability. If valid, assign the screening to the room. Return true if the screening is assigned successfully. Otherwise, return false. 

+ Cancel a future screening at a given current time. Check if: the screening exists in the cinema, the screening time is after the current time (screening time > current time). Return true if canceled successfully, false otherwise.
```
// ==end==
