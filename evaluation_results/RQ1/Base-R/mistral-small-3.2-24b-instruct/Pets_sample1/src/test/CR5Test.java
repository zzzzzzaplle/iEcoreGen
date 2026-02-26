import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class CR5Test {
    
    private House house;
    
    @Before
    public void setUp() {
        // Reset house before each test
        house = null;
    }
    
    @Test
    public void testCase1_CountPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1" and add Dog "Buster", Cat "Princess", Dog "Sam"
        house = new House();
        Dog dog1 = new Dog("Buster");
        Cat cat1 = new Cat("Princess");
        Dog dog2 = new Dog("Sam");
        
        house.addPet(dog1);
        house.addPet(cat1);
        house.addPet(dog2);
        
        // Action: Get pet count for CountHouse1
        int count = house.getAllPetNames().size();
        
        // Expected Output: 3
        assertEquals("House with 3 pets should return count 3", 3, count);
    }
    
    @Test
    public void testCase2_CountAfterAdditions() {
        // SetUp: Create House "CountHouse2" and add Cat "Tiger", Dog "Wolf"
        house = new House();
        Cat cat1 = new Cat("Tiger");
        Dog dog1 = new Dog("Wolf");
        
        house.addPet(cat1);
        house.addPet(dog1);
        
        // Action: Get total pet count
        int count = house.getAllPetNames().size();
        
        // Expected Output: 2
        assertEquals("House with 2 pets should return count 2", 2, count);
    }
    
    @Test
    public void testCase3_CountAfterRemoval() {
        // SetUp: Create House "CountHouse3" and add Dog "Chief", Cat "Queen", then remove "Chief"
        house = new House();
        Dog dog1 = new Dog("Chief");
        Cat cat1 = new Cat("Queen");
        
        house.addPet(dog1);
        house.addPet(cat1);
        house.removePet(dog1);
        
        // Action: Get pet count after removal
        int count = house.getAllPetNames().size();
        
        // Expected Output: 1
        assertEquals("House with 1 pet after removal should return count 1", 1, count);
    }
    
    @Test
    public void testCase4_CountEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        house = new House();
        
        // Action: Get pet count
        int count = house.getAllPetNames().size();
        
        // Expected Output: 0
        assertEquals("Empty house should return count 0", 0, count);
    }
    
    @Test
    public void testCase5_CountBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5" and add 10 Cats (named "Cat1" to "Cat10")
        house = new House();
        
        for (int i = 1; i <= 10; i++) {
            Cat cat = new Cat("Cat" + i);
            house.addPet(cat);
        }
        
        // Action: Get pet count
        int count = house.getAllPetNames().size();
        
        // Expected Output: 10
        assertEquals("House with 10 pets should return count 10", 10, count);
    }
}