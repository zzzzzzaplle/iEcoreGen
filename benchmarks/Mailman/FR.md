// ==version1==
```
+ Assign a specific mailman to deliver a registered inhabitant's mail item. The mailman and the inhabitant must belong to the addressee's geographical area. And ensure the mail isn't already assigned to any mailman. Return true if the assignment is successful; otherwise, false. 

+ List all registered mail items (letters and parcels) assigned to a specified mailman. Include only mail items that specify the given mailman as the carrier. Return null if none exist.

+ Manage inhabitants. Add a new inhabitant to a geographical area. Remove an inhabitant from the geographical area. Delete any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman. Return true if the operation is successful, false otherwise. 

+ Manage mailmen. Add a mailman if they're not already assigned the geographical area.  Removing a mailman requires: keeping at least one mailman in the area; specifying a different, existing mailman to take over deliveries; successfully reassigning all mail before removal. Return true if the operation is successful, false otherwise. 

+ List all registered mail items (letters and parcels) directed to a specified inhabitant. Include only mail items that specify the given inhabitant as the addressee. Return null if none exist.
```
// ==end==
