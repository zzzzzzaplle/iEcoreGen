### CR - 1: Retrieve a list of all pet names (dogs and cats) living in a specified house. Return empty list if there are no pets.

```
Computational requirement: Retrieve a list of all pet names (dogs and cats) living in a specified house. Return empty list if there are no pets.

+ Test Case 1: "Retrieve names from house with multiple pets"
    SetUp:
    1. Create House "Home1"
    2. Add Dog named "Buddy" to Home1
    3. Add Cat named "Whiskers" to Home1
    Action: Retrieve pet names from Home1
    Expected Output: List containing ["Buddy", "Whiskers"]
---
+ Test Case 2: "Retrieve names from house with single pet"
    SetUp:
    1. Create House "Home2"
    2. Add Cat named "Mittens" to Home2
    Action: Retrieve pet names from Home2
    Expected Output: List containing ["Mittens"]
---
+ Test Case 3: "Retrieve names from empty house"
    SetUp:
    1. Create House "Home3" (no pets)
    Action: Retrieve pet names from Home3
    Expected Output: []
---
+ Test Case 4: "Retrieve names after pet removal"
    SetUp:
    1. Create House "Home4"
    2. Add Dog "Max" and Cat "Luna"
    3. Remove "Max" from Home4
    Action: Retrieve pet names from Home4
    Expected Output: List containing ["Luna"]
---
+ Test Case 5: "Retrieve names from house with same-name pets"
    SetUp:
    1. Create House "Home5"
    2. Add Dog "Spot" and Cat "Spot"
    Action: Retrieve pet names from Home5
    Expected Output: List containing ["Spot", "Spot"]
```
***

### CR - 2: Add a pet to the house. Return false if the pet already belongs to any house (including this one); return true if the pet is added successfully.

```
Computational requirement: Add a pet to the house. Return false if the pet already belongs to any house (including this one); return true if the pet is added successfully.

+ Test Case 1: "Add new pet to empty house"
    SetUp:
    1. Create House "HomeA"
    2. Create Dog "Rex" (no house assigned)
    Action: Add Rex to HomeA
    Expected Output: true
    Post-Condition: HomeA contains Rex
---
+ Test Case 2: "Add pet already in the house"
    SetUp:
    1. Create House "HomeB" and "HomeC"
    2. Add Cat "Oliver" to HomeB
    Action: Add Oliver to HomeC
    Expected Output: false
    Post-Condition: HomeC remains unchanged
---
+ Test Case 3: "Add same pet twice to same house"
    SetUp:
    1. Create House "HomeD"
    2. Add Dog "Rocky" to HomeD
    Action: Attempt to add Rocky again to HomeD
    Expected Output: false
---
+ Test Case 4: "Add multiple unique pets"
    SetUp:
    1. Create House "HomeE"
    2. Create Dog "Bella" and Cat "Lucy" (no houses)
    Action: 
    - Add Bella to HomeE → true
    - Add Lucy to HomeE → true
    Expected Output: Both operations return true
    Post-Condition: HomeE contains both pets
---
+ Test Case 5: "Add pet with null name"
    SetUp:
    1. Create House "HomeF"
    2. Create Dog with null name
    Action: Add null-named dog to HomeF
    Expected Output: false
```
***

### CR - 3: Remove a specified pet from its current house. Return false if the pet does not belong to the given current house; return true if it is removed successfully.

```
Computational requirement: Remove a specified pet from its current house. Return false if the pet does not belong to the given current house; return true if it is removed successfully.

+ Test Case 1: "Remove existing pet"
    SetUp:
    1. Create House "HouseX"
    2. Add Cat "Shadow" to HouseX
    Action: Remove Shadow from HouseX
    Expected Output: true
    Post-Condition: HouseX has no pets
---
+ Test Case 2: "Remove non-existent pet"
    SetUp:
    1. Create House "HouseY"
    2. Add Dog "Cooper" to HouseY
    3. Create unassigned Cat "Mocha"
    Action: Remove Mocha from HouseY
    Expected Output: false
---
+ Test Case 3: "Remove pet from wrong house"
    SetUp:
    1. Create House "HouseZ" and "HouseW"
    2. Add Dog "Bear" to HouseZ
    Action: Remove Bear from HouseW
    Expected Output: false
---
+ Test Case 4: "Remove from empty house"
    SetUp:
    1. Create empty House "HouseV"
    2. Create unassigned Dog "Duke"
    Action: Remove Duke from HouseV
    Expected Output: false
---
+ Test Case 5: "Remove last pet from house"
    SetUp:
    1. Create House "HouseU"
    2. Add Cat "Cleo" (only pet)
    Action: Remove Cleo from HouseU
    Expected Output: true
    Post-Condition: HouseU - no pet

```
***

### CR - 4: Retrieve a list of pets of a specific input string ("dog" or "cat") that live in a particular house.
```
Computational requirement: Retrieve a list of pets of a specific input string ("dog" or "cat") that live in a particular house. Return an empty list if there are no pets.

+ Test Case 1: "Retrieve only dogs from mixed house"
    SetUp:
    1. Create House "PetHome1"
    2. Add Dog "Rusty", Cat "Snowball", Dog "Zeus"
    Action: Retrieve pets of type "dog" from PetHome1
    Expected Output: List containing ["Rusty", "Zeus"]
---
+ Test Case 2: "Retrieve cats from cat-only house"
    SetUp:
    1. Create House "PetHome2"
    2. Add Cat "Misty", Cat "Smokey"
    Action: Retrieve pets of input type "cat" from PetHome2
    Expected Output: List containing ["Misty", "Smokey"]
---
+ Test Case 3: "Retrieve type with no matches"
    SetUp:
    1. Create House "PetHome3"
    2. Add Dog "Ace", Dog "King"
    Action: Retrieve pets of input type "cat" from PetHome3
    Expected Output: Empty list
---
+ Test Case 4: "Retrieve from empty house"
    SetUp:
    1. Create empty House "PetHome4"
    Action: Retrieve pets of input type "dog"
    Expected Output: Empty list
---
+ Test Case 5: "Case-sensitive type retrieval"
    SetUp:
    1. Create House "PetHome5"
    2. Add Dog "Rover"
    Action: Retrieve pets of input type "dog"
    Expected Output: ["Rover"]

```
***

### CR - 5: Count the number of pets in a particular house. Return 0 if there are no pets.

```
Computational requirement: Count the number of pets in a particular house. Return 0 if there are no pets.

+ Test Case 1: "Count pets in populated house"
    SetUp:
    1. Create House "CountHouse1"
    2. Add Dog "Buster", Cat "Princess", Dog "Sam"
    Action: Get pet count for CountHouse1
    Expected Output: 3
---
+ Test Case 2: "Count after additions"
    SetUp:
    1. Create House "CountHouse2"
    2. Add Cat "Tiger"
    3. Add Dog "Wolf"
    Action: Get total pet count
    Expected Output: 2
---
+ Test Case 3: "Count after removal"
    SetUp:
    1. Create House "CountHouse3"
    2. Add Dog "Chief", Cat "Queen"
    3. Remove "Chief"
    Action: Get pet count after removal
    Expected Output: 1
---
+ Test Case 4: "Count empty house"
    SetUp:
    1. Create empty House "CountHouse4"
    Action: Get pet count
    Expected Output: 0
---
+ Test Case 5: "Count boundary (10+ pets)"
    SetUp:
    1. Create House "CountHouse5"
    2. Add 10 Cats (named "Cat1" to "Cat10")
    Action: Get pet count
    Expected Output: 10
```
***