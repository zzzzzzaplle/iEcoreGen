### CR - 1: Assign a specific mailman to deliver a registered inhabitant's mail item. The mailman and the inhabitant must belong to the addressee's geographical area. And ensure the mail isn't already assigned to any mailman. Return true if the assignment is successful; otherwise, false. 

```
Computational requirement: Assign a specific mailman to deliver a registered inhabitant's mail item. The mailman and the inhabitant must belong to the addressee's geographical area. And ensure the mail isn't already assigned to any mailman. Return true if the assignment is successful; otherwise, false. 

+ Test Case 1: "Assign Mailman to Letter in Same Area"
    SetUp:
    1. Create GeographicalArea "NorthDistrict".
    2. Add Mailman "John" to NorthDistrict.
    3. Add Inhabitant "Alice" to NorthDistrict.
    4. Create Registered Letter "Letter1" for Alice.
    Action: Assign John to deliver Letter1.
    Expected Output: true (successful assignment)
---
+ Test Case 2: "Assign Mailman to Parcel in Different Area"
    SetUp:
    1. Create GeographicalArea "EastDistrict" and "WestDistrict".
    2. Add Mailman "Mike" to EastDistrict.
    3. Add Inhabitant "Bob" to WestDistrict.
    4. Create RegisteredParcel "Parcel1" for Bob.
    Action: Assign Mike to deliver Parcel1.
    Expected Output: false (mailman not in same area)
---
+ Test Case 3: "Assign Non-existent Mailman"
    SetUp:
    1. Create GeographicalArea "CentralDistrict", create Mailman "Peter".
    2. Add Inhabitant "Carol" to CentralDistrict.
    3. Create RegisteredLetter "Letter2" for Carol.
    Action: Assign Mailman "Peter" to Letter2.
    Expected Output: false (mailman not in aera)
---
+ Test Case 4: "Assign Mailman to Mail for Non-existent Inhabitant"
    SetUp:
    1. Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
    2. Add Mailman "Sarah" to SouthDistrict.
    3. Create RegisteredParcel "Parcel2" for non-existent "Dave".
    Action: Assign Sarah to deliver Parcel2.
    Expected Output: false (addressee doesn't exist)
---
+ Test Case 5: "Reassign Mailman to Already Assigned Mail"
    SetUp:
    1. Create GeographicalArea "Downtown".
    2. Add Mailman "Tom" and "Jerry" to Downtown.
    3. Add Inhabitant "Eve" to Downtown.
    4. Create RegisteredLetter "Letter3" for Eve, assigned to Tom.
    Action 1: Reassign Letter3 to Jerry.
    Expected Output 1: false (successful reassignment)
    Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown".
    Expected Output 2: false

```
***

### CR - 2: + List all registered mail items (letters and parcels) assigned to a specified mailman.

```
Computational requirement: + List all registered mail items (letters and parcels) assigned to a specified mailman. Include only mail items that specify the given mailman as the carrier. Return null if none exist.

+ Test Case 1: "List Multiple Mail Items for Mailman"
    SetUp:
    1. Create GeographicalArea "MainStreet".
    2. Add Mailman "Aaron" to MainStreet.
    3. Add Inhabitant "Zoe", "Peter" to MainStreet.
    4. Create and assign:
       - Letter10 for Zoe (Aaron)
       - Parcel6 for Zoe (Aaron)
       - Parcel7 for Peter (Aaron)
    Action: List all mail for Aaron.
    Expected Output: List containing Letter10, Parcel6, and Parcel7.
---
+ Test Case 2: "List Mail for Mailman with No Mail"
    SetUp:
    1. Create GeographicalArea "MarketSquare".
    2. Add Mailman "Mia" to MarketSquare.
    Action: List all mail for Mia.
    Expected Output: null (no mail items)
---
+ Test Case 3: "List Mail for Non-existent Mailman"
    Action: List all mail for non-existent "Noah".
    Expected Output: null
---
+ Test Case 4: "List Only Assigned Mail for Mailman"
    SetUp:
    1. Create GeographicalArea "OldTown".
    2. Add Inhabitant "Ella", "Jucy" to OldTown.
    3. Add Mailman "Peter", "Maria" to OldTown.
    3. Create Letter11 assigned to mailman "Peter" in "OldTown".
    4. Create and assign:
       - Letter12 for Jucy (Peter)
       - Parcel12 for Ella (Peter)
       - Parcel14 for Ella (Peter)
       - Parcel15 for Ella (Maria)
    Action: List all mail for Peter.
    Expected Output: List containing Letter11,Letter12, Parcel12,and Parcel14.
---
+ Test Case 5: "List Mail for Mailman with Mixed Mail Types"
    SetUp:
    1. Create GeographicalArea "OldTown".
    2. Add Inhabitant "Ella", "Jucy" to OldTown.
    3. Add Mailman "Peter", "Maria" to OldTown.
    4. Create and assign:
       - Letter12 for Jucy (Peter)
       - Parcel12 for Ella (Peter)
       - Parcel14 for Ella (Peter)
       - Parcel15 for Jucy (Maria)
    5. Remove Ella, Maria from OldTown. Assign Maria's mails to Peter.
    Action: List all mail for Peter.
    Expected Output: List containing Letter12 and Parcel15.
```
***

### CR - 3: Manage inhabitants. Add a new inhabitant to a geographical area. Remove an inhabitant from the geographical area. Delete any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman. Return true if the operation is successful, false otherwise. 

```
Computational requirement: Manage inhabitants. Add a new inhabitant to a geographical area. Remove an inhabitant from the geographical area. Delete any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman. Return true if the operation is successful, false otherwise. 

+ Test Case 1: "Add New Inhabitant to Area"
    SetUp:
    1. Create GeographicalArea "Riverside".
    Action: Add new Inhabitant "Linda" to Riverside.
    Expected Output: true (successful addition)
---
+ Test Case 2: "Remove Inhabitant with Assigned Mail"
    SetUp:
    1. Create GeographicalArea "Lakeside".
    2. Add Mailman "Ken" to Lakeside.
    3. Add Inhabitant "Paul" to Lakeside.
    4. Create and assign Letter6 for Paul (Ken).
    Action: Remove Paul from Lakeside.
    Expected Output: true (Paul removed, Letter6 deleted)
---
+ Test Case 3:  "Add Multiple Inhabitants to Area"
    SetUp:
    1. Create GeographicalArea "Downtown".
    Action & Expected Output: 
    - Add new Inhabitant "Linda" to Downtown, true.
    - Add new Inhabitant "Becy" to Downtown, true.
    - Remove Inhabitant "Linda" to Downtown, true.
---
+ Test Case 4: "Remove Non-existent Inhabitant"
    SetUp:
    1. Create GeographicalArea "Hillside".
    Action: Remove non-existent "Victor" from Hillside.
    Expected Output: false (inhabitant doesn't exist)
---
+ Test Case 5: "Remove Inhabitant with Multiple Mail Items"
    SetUp:
    1. Create GeographicalArea "Beachfront".
    2. Add Mailman "Amy" to Beachfront.
    3. Add Inhabitant "Rachel" to Beachfront.
    4. Create and assign:
       - Letter7 for Rachel (Amy)
       - Parcel4 for Rachel (Amy)
    Action: Remove Rachel from Beachfront.
    Expected Output: true (Rachel removed, both mail items deleted)
```
***

### CR - 4: Manage mailmen.

```
Computational requirement: Manage mailmen. Add a mailman if they're not already assigned the geographical area.  Removing a mailman requires: keeping at least one mailman in the area; specifying a different, existing mailman to take over deliveries; successfully reassigning all mail before removal. Return true if the operation is successful, false otherwise. 
+ Test Case 1: "Complex Mailman Removal with Multiple Reassignments"  
    SetUp:  
    1. Create GeographicalArea "CentralDistrict"  
    2. Add Mailmen "Alice", "Bob", "Charlie"
    3. Add Inhabitants "David", "Eve", "Frank"
    4. Create and assign:  
        - Letter1 (Alice→David)  
        - Parcel1 (Bob→Eve)  
        - Letter2 (Charlie→Frank)  
        - Parcel2 (Alice→Eve)  
        - Letter3 (Bob→David)  

    Action: Remove Alice (specify Bob as replacement)  
    Expected Output: true  
    Verifications:  
        - Alice removed  
        - Letter1 and Parcel2 reassigned to Bob  (now has 4 items) , Bob's original deliveries (Parcel1, Letter3) unchanged  
        - Charlie's assignments unchanged  (now has 1 item)
---
+ Test Case 2: "Cascading Mailman Removal with Dependencies"  
    SetUp:  
    1. Create GeographicalArea "NorthQuarter"  
    2. Add Mailmen ["Xavier", "Yvonne", "Zack"]  
    3. Add Inhabitant "Walter"  
    4. Create and assign:  
        - 5 Letters (Xavier→Walter)  
        - 3 Parcels (Yvonne→Walter)  
        - 2 Letters (Zack→Walter)  

    Action: Remove Yvonne (specify Xavier as replacement), then remove Xavier (specify Zack as replacement)  
    Expected Output: true, true  
    Verifications: Zack remains with all 10 deliveries  
---
+ Test Case 3: "Failed Removal Edge Cases"  
    SetUp:  
    1. Create GeographicalArea "SouthEnd"  
    2. Add Mailmen ["Paul", "Quinn"]  
    3. Add Inhabitant "Rachel"  
    4. Create Letter1 (Paul→Rachel)  

    Actions & Expected Outputs:  
    1. Attempt remove Paul specifying Quinn → true (normal case)  
    2. Attempt remove Quinn (last mailman) → false  
---

+ Test Case 4: "Mixed Successful and Failed Operations"  
    SetUp:  
    1. Create GeographicalArea "EastHaven"  
    2. Add Mailmen ["Mario", "Luigi"]  
    3. Add Inhabitants ["Peach", "Bowser"]  
    4. Create:  
    - 10 Letters (Mario→Peach)  
    - 5 Parcels (Luigi→Bowser)  

    Procedure:  
    1. Add duplicate "Mario" → false  
    2. Remove Mario (specify Luigi) → true  
    3. Verify Luigi now has 15 deliveries  
    4. Attempt remove Luigi → false (last mailman)  
    5. Add "Toad" → true  
    6. Remove Luigi (specify Toad) → true  
---
+ Test Case 5: "Stress Test with Maximum Reassignments"  
    SetUp:  
    1. Create GeographicalArea "WestRidge"  
    2. Add Mailmen ["Alpha", "Beta", "Gamma"]  
    3. Add 10 Inhabitants  
    4. Create 30 mail items (10 each mailman)  

    Action:  
    1. Remove Alpha (specify Beta) → true  
    2. Remove Beta (specify Gamma) → true  
    Verifications:  
    - Gamma ends with all 30 deliveries  
```
***

### CR - 5: List all registered mail items (letters and parcels) directed to a specified inhabitant. Include only mail items that specify the given inhabitant as the addressee. Return null if none exist.

```
Computational requirement: List all registered mail items (letters and parcels) directed to a specified inhabitant. Include only mail items that specify the given inhabitant as the addressee. Return null if none exist.

+ Test Case 1: "List Multiple Mail Items for Inhabitant"
    SetUp:
    1. Create GeographicalArea "MainStreet".
    2. Add Mailman "Aaron" to MainStreet.
    3. Add Inhabitant "Zoe" to MainStreet.
    4. Create and assign:
       - Letter10 for Zoe (Aaron)
       - Parcel6 for Zoe (Aaron)
    Action: List all mail for Zoe.
    Expected Output: List containing both Letter10 and Parcel6
---
+ Test Case 2: "List Mail for Inhabitant with No Mail"
    SetUp:
    1. Create GeographicalArea "MarketSquare".
    2. Add Inhabitant "Mia" to MarketSquare.
    Action: List all mail for Mia.
    Expected Output: null (no mail items)
---
+ Test Case 3: "List Mail for Non-existent Inhabitant"
    Action: List all mail for non-existent "Noah".
    Expected Output: null
---
+ Test Case 4: "List Only Assigned Mail for Inhabitant"
    SetUp:
    1. Create GeographicalArea "OldTown".
    2. Add Inhabitant "Ella" to OldTown.
    3. Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown".
    4. Create and assign:
       - Letter12 for Ella (Peter)
    Action: List all mail for Ella.
    Expected Output: Letter12
---
+ Test Case 5: "List Mail for Inhabitant with Mixed Mail Types"
    SetUp:
    1. Create GeographicalArea "NewDistrict".
    2. Add Mailman "Ruby" to NewDistrict.
    3. Add Inhabitant "Luke" to NewDistrict.
    4. Create and assign:
       - Letter12 for Luke (Ruby)
       - Parcel7 for Luke (Ruby)
       - Letter13 for Luke (Ruby)
    Action: List all mail for Luke.
    Expected Output: List containing all three mail items
```
***
