### CR 1: Calculate the total number of goal-scoring announcements for the starting eleven players of a given football team. Count score announcements issued by the starting eleven. Because only forwards can generate score announcements, goals scored by other positions are ignored.

```
Computational requirement: Calculate the total number of goal-scoring announcements for the starting eleven players of a given football team. Count score announcements issued by the starting eleven. Because only forwards can generate score announcements, goals scored by other positions are ignored.

+ Test Case 1: "All Strikers with Goals"
    Input: Calculate total goal scoring announcements for the first eleven players.
    SetUp: 
    1. Create a football team named "Turgutlu".
    2. Add 11 players: 4 Strikers who scored 2 goals each, and 7 players with no goals.
    3. Announce scores for each of the 4 Strikers.
    Expected Output: Total announcements = 4 players × 2 goals = 8.

---
+ Test Case 2: "Mixed Players Scoring"
    Input: Calculate total goal scoring announcements for the first eleven players.
    SetUp:
    1. Create a football team named "Turgutlu".
    2. Add 11 players: 2 Strikers who scored 3 goals each, 2 Midfielders, and 7 players with no goals.
    3. Announce scores for Strikers.
    Expected Output: Total announcements = (2 players × 3 goals) = 6

---
+ Test Case 3: "First Eleven with No Goals"
    Input: Calculate total goal scoring announcements for the first eleven players.
    SetUp: 
    1. Create a football team named "Turgutlu".
    2. Add 11 players: All players (including 3 Strikers) scored 0 goals.
    Expected Output: Total announcements = 0.

---
+ Test Case 4: "Only Goalkeeper with Save Announcements"
    Input: Calculate total goal scoring announcements for the first eleven players.
    SetUp:
    1. Create a football team named "Turgutlu".
    2. Add 11 players, including 1 Goalkeeper who saved 2 goals.
    3. No Strikers have scored goals but the Goalkeeper had announcements for saves.
    Expected Output: Total announcements = 0 (only saves recorded, no score announcements).

---
+ Test Case 5: "No Players in First Eleven"
    Input: Calculate total goal scoring announcements for the first eleven players.
    SetUp: 
    1. Create a football team named "Turgutlu".
    2. Do not add any players to the first eleven.
    Expected Output: Total announcements = 0 (no players, no announcements).
```


***


### CR 2: Identify midfielder with highest number. Find the midfielder player with the highest number within all players of a football team. Return null if no midfielders found. 

```
Computational requirement: Identify midfielder with highest number. Find the midfielder player with the highest number within all players of a football team. Return null if no midfielders found. 

+ Test Case 1: "Midfielder with Highest Number - Basic Case" 
    Input: "Determine the midfielder with the highest number."
    SetUp:  
    1. Create a football team "Turgutlu FC".
    2. Add players: 
        - Name: "Player A", Age: 24, Number: 15, Position: "midfield"
        - Name: "Player B", Age: 26, Number: 22, Position: "midfield"
        - Name: "Player C", Age: 23, Number: 18, Position: "defense"
    Expected Output: "Player B, Number: 22"
---
+ Test Case 2: "Midfielder with Highest Number - Single Midfielder" 
    Input: "Determine the midfielder with the highest number."
    SetUp:  
    1. Create a football team "Turgutlu FC".
    2. Add players: 
        - Name: "Player D", Age: 28, Number: 12, Position: "midfield"
        - Name: "Player E", Age: 30, Number: 10, Position: "forward"
        - Name: "Player F", Age: 22, Number: 2, Position: "defense"
    Expected Output: "Player D, Number: 12"
---
+ Test Case 3: "Midfielder with Highest Number - Multiple Midfielders with Same Numbers" 
    Input: "Determine the midfielder with the highest number."
    SetUp:  
    1. Create a football team "Turgutlu FC".
    2. Add players: 
        - Name: "Player G", Age: 21, Number: 20, Position: "midfield"
        - Name: "Player H", Age: 29, Number: 20, Position: "midfield"
        - Name: "Player I", Age: 27, Number: 3, Position: "defense"
    Expected Output: "Player G, Number: 20" (Assuming it returns the first based on insertion order)
---
+ Test Case 4: "Midfielder with Highest Number - No Midfielders Available" 
    Input: "Determine the midfielder with the highest number."
    SetUp:  
    1. Create a football team "Turgutlu FC".
    2. Add players: 
        - Name: "Player J", Age: 25, Number: 9, Position: "forward"
        - Name: "Player K", Age: 30, Number: 1, Position: "goalkeeper"
        - Name: "Player L", Age: 23, Number: 4, Position: "defense"
    Expected Output: "No midfielders available"
---
+ Test Case 5: "Midfielder with Highest Number - All Positions Included" 
    Input: "Determine the midfielder with the highest number."
    SetUp:  
    1. Create a football team "Turgutlu FC".
    2. Add players: 
        - Name: "Player M", Age: 24, Number: 19, Position: "midfield"
        - Name: "Player N", Age: 26, Number: 25, Position: "midfield"
        - Name: "Player O", Age: 27, Number: 5, Position: "defense"
        - Name: "Player P", Age: 29, Number: 30, Position: "forward"
    Expected Output: "Player N, Number: 25"
```


***


### CR 3: Calculate average age of spare team players. Compute the average age of players in the spare team of a football team. Return the value as a floating-point number (e.g., 24.7). If the reserve team is empty, return 0.0.

```
Computational requirement: Calculate average age of spare team players. Compute the average age of players in the spare team of a football team. Return the value as a floating-point number (e.g., 24.7). If the reserve team is empty, return 0.0.

+ Test Case 1: Average Age Calculation with All Players  
    Input: Calculate the average age of spare players in the football team.  
    SetUp:  
    1. Create a football team "Turgutlu" with players:  
       - Player 1: Name: John, Age: 25, Team: Turgutlu, Position: Midfield  
       - Player 2: Name: Alex, Age: 28, Team: Turgutlu, Position: Forward  
       - Player 3: Name: Sam, Age: 30, Team: Turgutlu, Position: Defense  
       - Spare Player 1: Name: Mike, Age: 26, Team: Turgutlu 
       - Spare Player 2: Name: Karl, Age: 24, Team: Turgutlu  
    2. The first eleven are composed of Players 1, 2, and 3.  
    Expected Output: Average age = (26 + 24) / 2 = 25.0  

---
+ Test Case 2: Average Age Calculation with No Spare Players  
    Input: Calculate the average age of spare players in the football team.  
    SetUp:  
    1. Create a football team "Turgutlu" with players:  
       - Player 1: Name: Fatih, Age: 27, Team: Turgutlu, Position: Goalkeeper  
       - Player 2: Name: Gökhan, Age: 25, Team: Turgutlu, Position: Defense  
       - Player 3: Name: Hakan, Age: 29, Team: Turgutlu, Position: Midfield  
    2. All players are part of the first eleven.  
    Expected Output: Average age = 0.0 (No spare players available)  

---
+ Test Case 3: Average Age Calculation with Mixed Age Spare Players  
    Input: Calculate the average age of spare players in the football team.  
    SetUp:  
    1. Create a football team "Turgutlu" with players:  
       - Player 1: Name: Kerem, Age: 22, Team: Turgutlu, Position: Forward  
       - Player 2: Name: Levent, Age: 24, Team: Turgutlu, Position: Midfield  
       - Player 3: Name: Mehmet, Age: 26, Team: Turgutlu, Position: Defense  
       - Spare Player 1: Name: Nihat, Age: 35, Team: Turgutlu, Position: Forward
       - Spare Player 2: Name: Onur, Age: 22, Team: Turgutlu, Position: Midfield 
    Expected Output: Average age = (35 + 22) / 2 = 28.5  

---
+ Test Case 4: Average Age Calculation with One Spare Player  
    Input: Calculate the average age of spare players in the football team.  
    SetUp:  
    1. Create a football team "Turgutlu" with players:  
       - Player 1: Name: Tolga, Age: 28, Team: Turgutlu, Position: Goalkeeper  
       - Player 2: Name: Umut, Age: 25, Team: Turgutlu, Position: Defense  
       - Player 3: Name: Volkan, Age: 30, Team: Turgutlu, Position: Forward  
       - Spare Player 1: Name: Yiğit, Age: 31, Team: Turgutlu, Position: Forward
    Expected Output: Average age = 31.0 (Only one spare player)  

---
+ Test Case 5: Average Age Calculation with Two Identical Age Spare Players  
    Input: Calculate the average age of spare players in the football team.  
    SetUp:  
    1. Create a football team "Turgutlu" with players:  
       - Player 1: Name: Eren, Age: 24, Team: Turgutlu, Position: Forward  
       - Player 2: Name: Furkan, Age: 26, Team: Turgutlu, Position: Midfield  
       - Player 3: Name: Kadir, Age: 28, Team: Turgutlu, Position: Defense  
       - Spare Player 1: Name: Zafer, Age: 27, Team: Turgutlu, Position: Midfield
       - Spare Player 2: Name: Burak, Age: 27, Team: Turgutlu, Position: Midfield  
    Expected Output: Average age = (27 + 27) / 2 = 27.0  
```


***


### CR 4: Count the number of goal-saving announcements made by all goalkeepers on the team.  Only announcements generated by players whose position is GOALKEEPER are considered. Return 0 if no such announcements exist.


```
Computational requirement: Count the number of goal-saving announcements made by all goalkeepers on the team.  Only announcements generated by players whose position is GOALKEEPER are considered. Return 0 if no such announcements exist.

+ Test Case 1: Calculate Goal Saving Announcements for a Goalkeeper with No Saves  
    Input: Calculate goal-saving announcements for goalkeeper with no save recorded.  
    SetUp:  
    1. Create a football team.  
    2. Add a goalkeeper with number: 1, name: "John Doe", age: 30.  
    3. The goalkeeper has not made any saves in the match.  
    Expected Output: Total goal-saving announcements = 0.

---
+ Test Case 2: Calculate Goal Saving Announcements for a Goalkeeper with One Save  
    Input: Calculate goal-saving announcements for goalkeeper who made one save.  
    SetUp:  
    1. Create a football team.  
    2. Add a goalkeeper with number: 12, name: "Jane Smith", age: 28.  
    3. Goalkeeper made 1 announcement of type SAVE.  
    Expected Output: Total goal-saving announcements = 1.

---
+ Test Case 3: Calculate Goal Saving Announcements for a Goalkeeper with Multiple Saves  
    Input: Calculate goal-saving announcements for goalkeeper who made multiple saves.  
    SetUp:  
    1. Create a football team.  
    2. Add a goalkeeper with number: 5, name: "Mark Johnson", age: 32.  
    3. Goalkeeper made 3 announcements of type SAVE during the match.  
    Expected Output: Total goal-saving announcements = 3.

---
+ Test Case 4: Calculate Goal Saving Announcements for a Goalkeeper with Consecutive Saves  
    Input: Calculate goal-saving announcements for goalkeeper who made consecutive saves.  
    SetUp:  
    1. Create a football team.  
    2. Add a goalkeeper with number: 22, name: "Emily Davis", age: 26.  
    3. Goalkeeper made 5 announcements of type SAVE during the match, all in the second half.  
    Expected Output: Total goal-saving announcements = 5.

---
+ Test Case 5: Calculate Goal Saving Announcements for a Goalkeeper with Invalid Entries  
    Input: Calculate goal-saving announcements for goalkeeper with some invalid entries.  
    SetUp:  
    1. Create a football team.  
    2. Add a goalkeeper with number: 10, name: "Chris Brown", age: 31.  
    3. Goalkeeper made 2 announcements of type SAVE and 1 announcement of type GOAL (which should not be counted).  
    Expected Output: Total goal-saving announcements = 2.
```

***


### CR 5: Calculate the total number of announcements made by all forwards on the team. Return 0 if there are no forwards or no announcements.


```
Computational requirement: Calculate the total number of announcements made by all forwards on the team. Return 0 if there are no forwards or no announcements.

+ Test Case 1: "Total Announcements for Single Forward Player with One Score"
    Input: Calculate announcements for forward players where one player has scored.
    SetUp: 
    1. Create a football team named "Turgutlu".
    2. Create a forward player named "John" with number 9.
    3. Announce a score for John.
    Expected Output: Total announcements = 1 (SCORE for John)

---
+ Test Case 2: "Total Announcements for Multiple Forward Players with Scores"
    Input: Calculate announcements for forward players with multiple scores.
    SetUp: 
    1. Create a football team named "Turgutlu".
    2. Create forward players: "Alice" (number 10), "Bob" (number 11).
    3. Announce a score for Alice and a score for Bob.
    Expected Output: Total announcements = 2 (SCORES for Alice and Bob)

---
+ Test Case 3: "Total Announcements for Forward Players with Saves"
    Input: Calculate announcements for forward players where no scores but one save.
    SetUp: 
    1. Create a football team named "Turgutlu".
    2. Create a forward player named "Tom" with number 7.
    3. Create a goalkeeper named "Mike" with number 1.
    4. Announce a save for Mike.
    Expected Output: Total announcements = 0 (No SCORE announcements for forward players)

---
+ Test Case 4: "Total Announcements for Forward Players with Scores and Saves"
    Input: Calculate announcements for forward players with mixed announcements.
    SetUp: 
    1. Create a football team named "Turgutlu".
    2. Create forward players: "Emma" (number 8), "Lucas" (number 12).
    3. Announce a score for Emma.
    4. Announce a save for Mike (goalkeeper).
    Expected Output: Total announcements = 1 (SCORE for Emma; saves do not count)

---
+ Test Case 5: "Total Announcements with No Forward Players"
    Input: Calculate announcements for a team with no forward players.
    SetUp: 
    1. Create a football team named "Turgutlu".
    2. Create midfield player "Noah" (number 5) and defender "Liam" (number 4).
    3. Announce some saves and scores for others, but none for forwards.
    Expected Output: Total announcements = 0 (No forward player available)
```


***
