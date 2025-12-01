import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_CountPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1"
        House house = new House();
        
        // SetUp: Add Dog "Buster", Cat "Princess", Dog "Sam"
        Dog dog1 = new Dog("Buster");
        Cat cat1 = new Cat("Princess");
        Dog dog2 = new Dog("Sam");
        
        house.addPet(dog1);
        house.addPet(cat1);
        house.addPet(dog2);
        
        // Action: Get pet count for CountHouse1
        int actualCount = house.countPets();
        
        // Expected Output: 3
        assertEquals("House with 3 pets should return count 3", 3, actualCount);
    }
    
    @Test
    public void testCase2_CountAfterAdditions() {
        // SetUp: Create House "CountHouse2"
        House house = new House();
        
        // SetUp: Add Cat "Tiger"
        Cat cat1 = new Cat("Tiger");
        house.addPet(cat1);
        
        // SetUp: Add Dog "Wolf"
        Dog dog1 = new Dog("Wolf");
        house.addPet(dog1);
        
        // Action: Get total pet count
        int actualCount = house.countPets();
        
        // Expected Output: 2
        assertEquals("House with 2 pets should return count 2", 2, actualCount);
    }
    
    @Test
    public void testCase3_CountAfterRemoval() {
        // SetUp: Create House "CountHouse3"
        House house = new House();
        
        // SetUp: Add Dog "Chief", Cat "Queen"
        Dog dog1 = new Dog("Chief");
        Cat cat1 = new Cat("Queen");
        
        house.addPet(dog1);
        house.addPet(cat1);
        
        // SetUp: Remove "Chief"
        house.removePet(dog1);
        
        // Action: Get pet count after removal
        int actualCount = house.countPets();
        
        // Expected Output: 1
        assertEquals("House with 1 pet after removal should return count 1", 1, actualCount);
    }
    
    @Test
    public void testCase4_CountEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        House house = new House();
        
        // Action: Get pet count
        int actualCount = house.countPets();
        
        // Expected Output: 0
        assertEquals("Empty house should return count 0", 0, actualCount);
    }
    
    @Test
    public void testCase5_CountBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5"
        House house = new House();
        
        // SetUp: Add 10 Cats (named "Cat1" to "Cat10")
        for (int i = 1; i <= 10; i++) {
            Cat cat = new Cat("Cat" + i);
            house.addPet(cat);
        }
        
        // Action: Get pet count
        int actualCount = house.countPets();
        
        // Expected Output: 10
        assertEquals("House with 10 cats should return count 10", 10, actualCount);
    }
}