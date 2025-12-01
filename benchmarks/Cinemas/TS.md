### CR - 1 : Add a room to the cinema. If the room is not already registered, add it and return true. If the room already exists, return false.

```
Computational requirement : Add a room to the cinema. If the room is not already registered, add it and return true. If the room already exists, return false.

+ Test Case 1: "Add new room to empty cinema"
    Input: Room R1
    Setup: 
    1. Create Cinema C1 with empty rooms
    2. Create Room R1
    3. Add Room R1 to empty cinema
    Expected Output: true
---
+ Test Case 2: "Add duplicate room"
    Input: Room R1
    Setup:
    1. Create Cinema C1, Room R1
    2. Add Room R1 to C1 (true)
    3. Add Room R1 to C1 again (false)
    Expected Output: true, false
---
+ Test Case 3: "Add multiple rooms"
    Input:  Room R1
    Setup:
    1. Create Cinema C1, Room R1, Room R2
    2. Add Room R1 to C1 (true)
    3. Add Room R2 to C1 (true)
    4. Add Room R1 to C1 again (false)
    Expected Output: true, true, false
---
+ Test Case 4: "Add multiple unique rooms"
    Input: Room R2
    Setup:
    1. Create Cinema C1
    2. Add Room1 to C1 (true)
    3. Add Room2 to C1 (true)
    Expected Output: true, true
---
+ Test Case 5: "Add duplicate room"
    Input: Room R1, R2
    Setup:
    1. Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
    2. Add Room R1 to C1 (true)
    3. Add Room R2 to C1 (true)
    4. Add Room R3 to C2 (true) 
    5. Add Room R1 to C1 (false)  
    Expected Output: true, true, true, false.
```

***

### CR - 2 : Check a room’s availability at a given time (yyyy-MM-dd HH:mm:ss). Check if the room is already added to the cinema and assigned for another screening at the given time. Return true if the room is available. Return false if the room is already assigned or the inputs are invalid. 

```
Computational requirement : Check a room’s availability at a given time (yyyy-MM-dd HH:mm:ss). Check if the room is already added to the cinema and assigned for another screening at the given time. Return true if the room is available. Return false if the room is already assigned or the inputs are invalid. 

+ Test Case 1: "Check available room with no screenings"
    Setup:
    1. Create Cinema C1
    2. Add Room1 to C1
    Action: check Room1 availability at the current time "2024-10-05 13:00:00"
    Expected Output: true
---
+ Test Case 2: "Check assigned room"
    Setup:
    1. Create Cinema C1, film F1
    2. Add Room1, film F1 to C1
    3. Assign film f1 screening at "2024-10-05 13:00:00", room Room1 ( the current time "2024-10-04 13:00:00")
    Action: check Room1 availability at the current time  "2024-10-05 13:00:00"
    Expected Output: false
---
+ Test Case 3: "Check room at exact screening time"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 and Room R1 to C1
    3. Assign screening for F1 in R1 at "2024-12-01 14:00:00"
    Action: Check R1 availability at the current time "2024-12-02 14:00:00"
    Expected Output: true
---
+ Test Case 4: "Check multiple rooms"
    Setup:
    1. Create Cinema C1, film F1, film F2
    2. Add Room1, Room2, film F1, film F2 to C1
    3. Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1 ( the current time : "2024-10-01 13:00:00")
    4. Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2 ( the current time : "2024-10-03 13:00:00")
    Action: check Room1, Room2 availability at the current time  "2024-10-05 13:00:00"
    Expected Output: S1: false, S2: false
---
+ Test Case 5: "Check different time slot"
    Setup:
    1. Create Cinema C1, Film F1
    2. Add Room1, Film F1 to C1
    3. Assign Film F1 screening at "2024-10-05 13:00:00", room Room1 ( the current time : "2024-09-03 13:00:00")
    Action: check Room1 availability at the current time "2024-10-05 14:00:00"
    Expected Output: true
```

***

### CR - 3 : Add a film to the cinema. If the film does not already exist, add it and return true. If the film already exists, return false.
```
Computational requirement : Add a film to the cinema. If the film does not already exist, add it and return true. If the film already exists, return false.

+ Test Case 1: "Add new film"
    Setup:
    1. Create Cinema C1, Film F1
    Action: add Film F1 to Cinema C1
    Expected Output: true
---
+ Test Case 2: "Add duplicate film"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to Cinema C1 first
    Action: add Film F1 to Cinema C1 again
    Expected Output: false
---
+ Test Case 3: "Add multiple unique films"
    Setup:
    1. Create Cinema C1, Film F1, Film F2
    2. Add Film F1 to Cinema C1 (true)
    Action: add Film F2 to Cinema C1 (true)
    Expected Output: true
---
+ Test Case 4: "Add multiple unique films"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to Cinema C1 (true)
    Action: add Film F2 to Cinema C1
    Expected Output: true
---
+ Test Case 5: "Add multiple unique films"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to Cinema C1 (true)
    3. Remove Film F1 from Cinema C1 (true)
    Action: add Film F1 to Cinema C1 again
    Expected Output: true

```

***

### CR - 4 : Remove a film from the cinema system at the specified current time (yyyy-MM-dd HH:mm:ss), and remove all of its future scheduled screenings (the screening time >= the current time). Return true if the film is removed successfully. Return false if it does not exist. 

```
Computational requirement : Remove a film from the cinema system at the specified current time (yyyy-MM-dd HH:mm:ss), and remove all of its future scheduled screenings (the screening time >= the current time). Return true if the film is removed successfully. Return false if it does not exist. 

+ Test Case 1: "Remove film with no screenings"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to C1
    Action: Remove Film F1 at current time "2024-12-01 10:00:00"
    Expected Output: true
---
+ Test Case 2: "Remove non-existent film"
    Setup:
    1. Create Cinema C1
    2. Do not add Film F1
    Action: Remove Film F1 at current time "2024-12-01 10:00:00"
    Expected Output: false (Film F1 do not exist in cinema)
---
+ Test Case 3: "Remove film with future screening"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to C1
    3. Assign screening for F1 at "2024-12-02 15:00:00" (the screening time)
    Action: Remove Film F1 at current time "2024-12-01 10:00:00"
    Expected Output: true, and there is no screening in the cinema.
---
+ Test Case 4: "Remove film with past screening"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to C1
    3. Schedule screening for F1 at "2024-11-30 18:00:00"
    Action: Remove Film F1 at current time "2024-12-01 10:00:00"
    Expected Output: true, and there is 1 screening in the cinema.
---
+ Test Case 5: "Remove film with current-time screening"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to C1
    3. Schedule screening for F1 at "2024-12-01 10:00:00"
    Action: Remove Film F1 at current time "2024-12-01 10:00:00"
    Expected Output: true, and there is no screening in the cinema.
```

***

### CR - 5 : Assign a screening to a film and room at a specific screening time (yyyy-MM-dd HH:mm:ss) and current time (yyyy-MM-dd HH:mm:ss). Verify that the current time should be before (<) the screening time. Verify film added to the cinema and room availability. If valid, assign the screening to the room. Return true if the screening is assigned successfully. Otherwise, return false. 

```
Computational requirement : Assign a screening to a film and room at a specific screening time (yyyy-MM-dd HH:mm:ss) and current time (yyyy-MM-dd HH:mm:ss). Verify that the current time should be before (<) the screening time. Verify film added to the cinema and room availability. If valid, assign the screening to the room. Return true if the screening is assigned successfully. Otherwise, return false. 

+ Test Case 1: "Valid future screening assignment"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to C1
    3. Add Room R1 to C1
    Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
    Expected Output: true
---
+ Test Case 2: "Assign to already booked room"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to C1
    3. Add Room R1 to C1
    Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00"
    Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
    Expected Output: false
---
+ Test Case 3: "Assign with non-existent film"
    Setup:
    1. Create Cinema C1
    2. Add Room R1 to C1
    Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
    Expected Output: false
---
+ Test Case 4: "Assign screening at current time boundary"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to C1
    3. Add Room R1 to C1
    Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
    Expected Output: false
---
+ Test Case 5: "Assign screening in past time"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to C1
    3. Add Room R1 to C1
    Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
    Expected Output: false
---
+ Test Case 6: "Assign to non-existent room"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to C1
    Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
    Expected Output: false
```

***

### CR - 6 : Cancel a future screening at a given current time. Check if: the screening exists in the cinema, the screening time is after the current time (screening time > current time). Return true if canceled successfully, false otherwise.

```
Computational requirement : Cancel a future screening at a given current time. Check if: the screening exists in the cinema, the screening time is after the current time (screening time > current time). Return true if canceled successfully, false otherwise.

+ Test Case 1: "Cancel future screening"
    Setup:
    1. Create Cinema C1
    2. Add Film1
    3. Add Room1
    4. Assign Screening1(Film1, Room1, "2024-10-03 12:00:00")
    Action: cancel Screening1 at "2024-10-02 12:00:00"
    Expected Output: true, there is no screening in the cinema C1.
---
+ Test Case 2: "Cancel non-existent screening"
    Setup:
    1. Create Cinema C1, Screening2 (the screening time: "2024-10-02 12:05:00")
    2. Add Film1
    3. Add Room1
    Action: cancel Screening2 at current time "2024-10-03 12:05:00"
    Expected Output: false
---
+ Test Case 3: "Cancel at exact screening time boundary"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to C1
    3. Add Room R1 to C1
    4. Assign screening S1 for F1 in R1 at "2024-12-03 10:00:00"
    Action: Cancel S1 at current time "2024-12-03 10:00:00"
    Expected Output: false, there is 1 screening in the cinema
---
+ Test Case 4: "Cancel already cancelled screening"
    Setup:
    1. Create Cinema C1
    2. Add Film1
    3. Add Room1
    4. Assign Screening1(Film1, Room1, "2024-10-03 12:00:00") then cancel Screening1 at "2024-10-02 12:00:00" (true)
    Action: cancel Screening1 at current time "2024-10-02 12:05:00"
    Expected Output: false, there is no screening in the cinema
---
+ Test Case 5: "Cancel past screening (after showtime)"
    Setup:
    1. Create Cinema C1
    2. Add Film F1 to C1
    3. Add Room R1 to C1
    4. Assign screening S1 for F1 in R1 at "2024-12-01 14:00:00"
    Action: Cancel S1 at current time "2024-12-03 10:00:00"
    Expected Output: false, there is 1 screening in the cinema

```

***