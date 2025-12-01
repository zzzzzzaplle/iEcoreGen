### CR - 1: Retrieve a list of all toxic product names present in a specific warehouse. Return an empty list if no toxic products are found or if the warehouse does not exist.  


```
Computational requirement: Retrieve a list of all toxic product names present in a specific warehouse. Return an empty list if no toxic products are found or if the warehouse does not exist.  

+ Test Case 1: "Warehouse with multiple toxic products"
    SetUp:
    1.Create Warehouse "W1" in "CityA" with surface 500m², in company C1.
    2.Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1".
    3.Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2".
    4.Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3".
    Action: Retrieve toxic products in W1.
    Expected Output: ["ChemX", "PaintY"]
---
+ Test Case 2: "Warehouse with no toxic products"
    SetUp:
    1. Create Warehouse "W2" in "CityB" with surface 300m², in company C2.
    2. Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1".
    3. Add Product "CableB" (toxic=false, volume=3) from Supplier "S4".
    Action: Retrieve toxic products in W2.
    Expected Output: []
---
+ Test Case 3: "Empty warehouse"
    SetUp:
    1. Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3.
    2. Action: Retrieve toxic products in W3.
    Expected Output: []
---
+ Test Case 4: "Non-existent warehouse"
    SetUp:
    1. Initialize company C3 with Warehouse "W4" only.
    Action: Retrieve toxic products in "W5" (doesn't exist in C3).
    Expected Output: []
---
+ Test Case 5: "Warehouse with single toxic product"
    SetUp:
    1. Create Warehouse "W6" in "CityD" with surface 400m², in company C4.
    2. Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5".
    Action: Retrieve toxic products in W6.
    Expected Output: ["AcidK"]

```

***

### CR - 2: Retrieve the list of all unique suppliers whose products are stored in a specific warehouse. Return an empty list if the warehouse contains no products or does not exist. 


```
Computational requirement: Retrieve the list of all unique suppliers whose products are stored in a specific warehouse. Return an empty list if the warehouse contains no products or does not exist. 

+ Test Case 1: "Warehouse with products from multiple suppliers"
    SetUp:
    1. Create Warehouse "W7" in "CityE" with surface 350m², in company C1.
    2. Add Product "Item1" from Supplier "S6".
    3. Add Product "Item2" from Supplier "S7".
    4. Add Product "Item3" from Supplier "S6" (duplicate supplier).
    Action: Retrieve unique suppliers for W7.
    Expected Output: ["S6", "S7"]
---
+ Test Case 2: "Warehouse with single product"
    SetUp:
    1. Create Warehouse "W8" in "CityF" with surface 250m², in company C1.
    2. Add Product "Item4" from Supplier "S8".
    Action: Retrieve unique suppliers for W8.
    Expected Output: ["S8"]
---
+ Test Case 3: "Empty warehouse"
    SetUp:
    1. Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1.
    2. Action: Retrieve unique suppliers for W9.
    Expected Output: []
---
+ Test Case 4: "Non-existent warehouse"
    SetUp:
    1. Initialize company C1 with no warehouses.
    Action: Retrieve unique suppliers for "W10".
    Expected Output: []
---
+ Test Case 5: "Warehouse with products from same supplier"
    SetUp:
    1. Create Warehouse "W11" in "CityH" with surface 300m², in company C1.
    2. Add Product "A1" from Supplier "S9".
    3. Add Product "A2" from Supplier "S9".
    Action: Retrieve unique suppliers for W11.
    Expected Output: ["S9"]

```

***

### CR - 3: Count the total volume of products supplied by a specific supplier name within a specific company. Return 0 if no products are found. 


```
Computational requirement: Count the total volume of products supplied by a specific supplier name within a specific company. Return 0 if no products are found. 

+ Test Case 1: "Supplier with multiple products"
    SetUp:
    1. Create Supplier "S10"
    2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
    3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
    Action: List volumes for supplier "S10" in company C1.
    Expected Output: 15
---
+ Test Case 2: "Supplier with no products"
    SetUp:
    1. Create company C2 without warehouse.
    2. Create Supplier "S11" (no products linked).
    Action: List volumes for supplier "S11" in company C2.
    Expected Output: 0
---
+ Test Case 3: "Non-existent supplier"
    SetUp:
    1. Create Supplier "S10"
    2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
    3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
    4. Create Supplier "S12" (no products linked).
    Action: List volumes for supplier name "S13" in company C1.
    Expected Output: 0
---
+ Test Case 4: "Supplier with single product"
    SetUp:
    1. Add Supplier "S14" to system.
    2. Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3.
    Action: List volumes for supplier "S14" in company C3.
    Expected Output: 8
---
+ Test Case 5: "Supplier with products in multiple warehouses"
    SetUp:
    1. Add Supplier "S15" to system.
    2. Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4.
    3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
    4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
    Action: List volumes for supplier "S15" in company C4.
    Expected Output: 12
```

***

### CR - 4: Verify that a warehouse contains a specific product by product name. Return false if the warehouse or product does not exist; return true if the product is in the warehouse. 


```
Computational requirement: Verify that a warehouse contains a specific product by product name. Return false if the warehouse or product does not exist; return true if the product is in the warehouse. 

+ Test Case 1: "Product exists in warehouse"
    SetUp:
    1. Create Warehouse "W17" in "CityI", in company C1.
    2. Add Product "WidgetA" to W17.
    Action: Verify "WidgetA" in W17.
    Expected Output: true
---
+ Test Case 2: "Product doesn't exist in warehouse"
    SetUp:
    1. Create Warehouse "W18" in "CityJ", in company C1.
    2. Add Product "GadgetB" to W18.
    Action: Verify "ToolC" in W18.
    Expected Output: false
---
+ Test Case 3: "Non-existent warehouse"
    SetUp:
    1. Initialize Warehouse "W19" in company C1.
    2. Add Product "ItemX" to "W18" in company C2.
    Action: Verify "ItemX" in Warehouse "W19".
    Expected Output: false
---
+ Test Case 4: "Empty warehouse check"
    SetUp:
    1. Create Warehouse "W20" in "CityK" (no products), in company C1.
    Action: Verify "PartY" in W20.
    Expected Output: false
---
+ Test Case 5: "Multiple products in warehouse"
    SetUp:
    1. Create Warehouse "W21" in "CityL", in company C1.
    2. Add Products ["CompA", "CompB", "CompC"] to W21.
    Action: Verify "CompB" in W21.
    Expected Output: true

```

***

### CR - 5: Verify that the company contains a specific product by product name and return its locations. Return a list of warehouse cities and addresses if found; return an empty list if the product is not found. 


```
Computational requirement: Verify that the company contains a specific product by product name and return its locations. Return a list of warehouse cities and addresses if found; return an empty list if the product is not found. 

+ Test Case 1: "Product in multiple warehouses"
    SetUp:
    1. Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
    2. Add Product "MaterialX" to both W22 and W23.
    Action: Find locations of "MaterialX" in C1.
    Expected Output: 
    - "city":"CityM","address":"W22_addr"
    - "city":"CityN","address":"W23_addr"
---
+ Test Case 2: "Product in single warehouse"
    SetUp:
    1. Create Warehouse "W24" in "CityO", in company C1.
    2. Add Product "DeviceY" to W24.
    Action: Find locations of "DeviceY" in C1.
    Expected Output: 
    - "city":"CityO","address":"W24_addr"
---
+ Test Case 3: "Non-existent product"
    SetUp:
    1. Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
    Action: Find locations of "ToolW" in C1.
    Expected Output: []
---
+ Test Case 4: "Product in no warehouses"
    SetUp:
    1. Create Warehouses ["W26", "W27"] with other products. 
    2. Warehouses are both in company C1.
    Action: Find locations of "ItemV" in C1.
    Expected Output: []
---
+ Test Case 5: "Product in all warehouses"
    SetUp:
    1. Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in compant C1.
    2. Add Product "CommonPart" to both warehouses.
    Action: Find locations of "CommonPart" in C1.
    Expected Output: 
    - "city":"CityP","address":"W28_addr"
    - "city":"CityQ","address":"W29_addr"
```

***