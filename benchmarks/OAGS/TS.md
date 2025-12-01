### CR 1 : Calculate the total price of an artist’s artworks: Given an artist, sum the prices of all their artworks.



```
Computational requirement: Calculate the total price of an artist’s artworks: Given an artist, sum the prices of all their artworks.

+ Test Case 1: Calculate Total Artwork Price for a Single Artwork  
    Input: Calculate total price for an artist with one artwork. 
    SetUp:  
    1. Create an artist named "Alice" with ID: A001
    2. Create an artwork titled "Sunset Painting" with description "A beautiful sunset painting" and price: 500 CNY.
    3. The artist "Alice" has only this artwork.
    Expected Output: Total artwork price = 500 CNY
---
+ Test Case 2: Calculate Total Artwork Price with Multiple Artworks  
    Input: Calculate total price for an artist with multiple artworks. 
    SetUp:  
    1. Create an artist named "Bob" with ID: A002
    2. Create an artwork titled "Starry Night" with price: 1200 CNY.
    3. Create an artwork titled "Ocean View" with price: 800 CNY.
    4. The artist "Bob" has both of these artworks.
    Expected Output: Total artwork price = 1200 + 800 = 2000 CNY
---
+ Test Case 3: Calculate Total Artwork Price with Zero Artworks  
    Input: Calculate total price for an artist with no artworks. 
    SetUp:  
    1. Create an artist named "Charlie" with ID: A003
    2. The artist "Charlie" has no artworks listed.
    Expected Output: Total artwork price = 0 CNY
---
+ Test Case 4: Calculate Total Artwork Price for Artworks with Different Prices  
    Input: Calculate total price for an artist with various priced artworks. 
    SetUp:  
    1. Create an artist named "Diana" with ID: A004
    2. Create an artwork titled "Marble Sculpture" with price: 2500 CNY.
    3. Create an artwork titled "Wooden Carving" with price: 1500 CNY.
    4. Create an artwork titled "Abstract Art" with price: 300 CNY.
    5. The artist "Diana" has all three artworks.
    Expected Output: Total artwork price = 2500 + 1500 + 300 = 4300 CNY
---
+ Test Case 5: Calculate Total Artwork Price for an Artist with Artworks of Zero Price  
    Input: Calculate total price for an artist where one or more artworks are free. 
    SetUp:  
    1. Create an artist named "Eve" with ID: A005
    2. Create an artwork titled "Sketch" with price: 0 CNY.
    3. Create an artwork titled "Portrait" with price: 1500 CNY.
    4. The artist "Eve" has both artworks.
    Expected Output: Total artwork price = 0 + 1500 = 1500 CNY
```

***

### CR 2 : Get reward points within a specific period: Given an artist and a time period, verify that the artist’s membership is valid during that interval and return the reward points (inclusive of the boundary dates).


```
Computational requirement: Get reward points within a specific period: Given an artist and a time period, verify that the artist’s membership is valid during that interval and return the reward points (inclusive of the boundary dates).

+ Test Case 1: "Valid Individual Membership Reward Points Calculation"
    Input: Get reward points for artist with ID A001 from 2023-01-01 to 2023-12-31.
    SetUp: 
    1. Create an artist with ID: A001, name: "Alice", phone number: "123456789", email: "alice@example.com", address: "123 Art St", gender: "Female".
    2. Assign an individual membership to artist A001 with ID: M001, start date: 2022-01-01, end date: 2024-01-01, and reward points: 100.
    Expected Output: Reward points = 100
---
+ Test Case 2: "Invalid Membership Period Calculation"
    Input: Get reward points for artist with ID A002 from 2023-06-01 to 2023-07-01.
    SetUp: 
    1. Create an artist with ID: A002, name: "Bob", phone number: "987654321", email: "bob@example.com", address: "456 Art Ave", gender: "Male".
    2. Assign an agency membership to artist A002 with ID: M002, start date: 2022-03-01, end date: 2022-09-01, and reward points: 200.
    Expected Output: Reward points = 0 (membership invalid during this period)
---
+ Test Case 3: "Agency Membership Reward Points Calculation"
    Input: Get reward points for artist with ID A003 from 2023-05-01 to 2023-10-01.
    SetUp: 
    1. Create an artist with ID: A003, name: "Catherine", phone number: "135792468", email: "catherine@example.com", address: "789 Art Blvd", gender: "Female".
    2. Assign an agency membership to artist A003 with ID: M003, start date: 2023-01-01, end date: 2024-01-01, and reward points: 300.
    Expected Output: Reward points = 300
---
+ Test Case 4: "Multiple Memberships Consideration"
    Input: Get reward points for artist with ID A004 from 2023-01-06 to 2023-01-21.
    SetUp:
    1. Create an artist with ID: A004, name: "David", phone number: "246813579", email: "david@example.com", address: "321 Art Rd", gender: "Male".
    2. Assign an individual membership to artist A004 with ID: M004, start date: 2023-01-05, end date: 2023-06-01, and reward points: 150.
    3. Update to assign an agency affiliate membership with ID: M005, start date: 2023-02-01, end date: 2024-02-01, and reward points: 100.
    Expected Output: Reward points = 150
---
+ Test Case 5: "Boundary Condition - Membership Expiration"
    Input: Get reward points for artist with ID A005 on the 2023-01-02.
    SetUp: 
    1. Create an artist with ID: A005, name: "Eva", phone number: "864209753", email: "eva@example.com", address: "654 Art Pl", gender: "Female".
    2. Assign an agency membership to artist A005 with ID: M006, start date: 2022-01-01, end date: 2023-01-01, and reward points: 250.
    Expected Output: Reward points = 0 (membership expired on the end date)
```

***

### CR 3 :  Count an artist’s artworks by category: Given an artist, count the number of their artworks in each category (painting, sculpture, architecture).

```
Computational requirement: Count an artist’s artworks by category: Given an artist, count the number of their artworks in each category (painting, sculpture, architecture).

+ Test Case 1: Count Artworks by Category for a Single Artist  
    Input: Count artworks for Artist A (John Doe)  
    SetUp:  
    1. Create an artist with name: "John Doe", ID: "A001", phone: "1234567890", email: "john.doe@example.com", address: "123 Art St", gender: "Male"  
    2. Add 3 paintings to John Doe:  
        - Artwork 1: Title: "Sunset", Description: "A beautiful sunset painting", Category: "painting", Price: 200 CNY  
        - Artwork 2: Title: "Still Life", Description: "A still life composition", Category: "painting", Price: 150 CNY  
        - Artwork 3: Title: "Bronze Statue", Description: "A bronze statue of a horse", Category: "sculpture", Price: 500 CNY  
    Expected Output: There are 2 painting artworks, 1 sculpture artwork, and 0 architecture artwork.

---
+ Test Case 2: Count Artworks by Category with Multiple Categories  
    Input: Count artworks for Artist B (Jane Smith)  
    SetUp:  
    1. Create an artist with name: "Jane Smith", ID: "A002", phone: "9876543210", email: "jane.smith@example.com", address: "456 Art Ave", gender: "Female"  
    2. Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith:  
        - Artwork 1: Title: "Abstract Colors", Description: "An abstract painting", Category: "painting", Price: 300 CNY  
        - Artwork 2: Title: "David", Description: "A sculpture of David", Category: "sculpture", Price: 700 CNY  
        - Artwork 3: Title: "Classic Statue", Description: "A classic statue", Category: "sculpture", Price: 600 CNY  
        - Artwork 4: Title: "Skyscraper", Description: "Modern architecture", Category: "architecture", Price: 900 CNY  
    Expected Output: There are 1 painting artwork, 2 sculpture artworks, and 1 architecture artwork.

---
+ Test Case 3: No Artworks for an Artist  
    Input: Count artworks for Artist C (Emily Brown)  
    SetUp:  
    1. Create an artist with name: "Emily Brown", ID: "A003", phone: "1112223333", email: "emily.brown@example.com", address: "789 Art Blvd", gender: "Female"  
    2. Do not add any artworks to Emily Brown.  
    Expected Output: There are 0 painting artwork, 0 sculpture artwork, and 0 architecture artwork.

---
+ Test Case 4: Count Artworks with Only One Category  
    Input: Count artworks for Artist D (Michael Johnson)  
    SetUp:  
    1. Create an artist with name: "Michael Johnson", ID: "A004", phone: "4445556666", email: "michael.johnson@example.com", address: "123 Art Lane", gender: "Male"  
    2. Add 3 sculptures and no other category:  
        - Artwork 1: Title: "Modern Art", Description: "A modern sculpture", Category: "sculpture", Price: 800 CNY  
        - Artwork 2: Title: "Wooden Carving", Description: "A wooden sculpture", Category: "sculpture", Price: 1200 CNY  
        - Artwork 3: Title: "Stone Figure", Description: "A stone sculpture", Category: "sculpture", Price: 1000 CNY  
    Expected Output: There are 0 painting artwork, 3 sculpture artworks, and 0 architecture artwork.

---
+ Test Case 5: Count Artworks with Multiple Artists  
    Input: Count artworks for multiple artists (Artist E and Artist F)  
    SetUp:  
    1. Create artist E (Alice White) with ID: "A005", and add artworks:  
        - Artwork 1: Title: "Landscapes", Category: "painting"  
        - Artwork 2: Title: "Steel Bridge", Category: "architecture"  
    2. Create artist F (David Green) with ID: "A006", and add artworks:  
        - Artwork 1: Title: "Marble Sculpture", Category: "sculpture"  
        - Artwork 2: Title: "City Skyline", Category: "architecture"  
    Expected Output:  
    - For Alice White: There are 1 painting artwork, 0 sculpture artwork, and 1 architecture artwork.
    - For David Green: There are 0 painting artworks, 1 sculpture artwork, and 1 architecture artwork.
```
***
