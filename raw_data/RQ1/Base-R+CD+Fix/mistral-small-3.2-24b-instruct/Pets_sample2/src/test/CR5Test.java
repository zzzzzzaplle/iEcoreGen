import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    private House house;
    
    @Test
    public void testCase1_CountPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1"
        house = new House();
        
        // SetUp: Add Dog "Buster", Cat "Princess", Dog "Sam"
        Dog dog1 = new Dog();
        dog1.setName("Buster");
        house.addPet(dog1);
        
        Cat cat1 = new Cat();
        cat1.setName("Princess");
        house.addPet(cat1);
        
        Dog dog2 = new Dog();
        dog2.setName("Sam");
        house.addPet(dog2);
        
        // Action: Get pet count for CountHouse1
        int actualCount = house.getPetCount();
        
        // Expected Output: 3
        assertEquals("Pet count should be 3 for populated house", 3, actualCount);
    }
    
    @Test
    public void testCase2_CountAfterAdditions() {
        // SetUp: Create House "CountHouse2"
        house = new House();
        
        // SetUp: Add Cat "Tiger"
        Cat cat1 = new Cat();
        cat1.setName("Tiger");
        house.addPet(cat1);
        
        // SetUp: Add Dog "Wolf"
        Dog dog1 = new Dog();
        dog1.setName("Wolf");
        house.addPet(dog1);
        
        // Action: Get total pet count
        int actualCount = house.getPetCount();
        
        // Expected Output: 2
        assertEquals("Pet count should be 2 after two additions", 2, actualCount);
    }
    
    @Test
    public void testCase3_CountAfterRemoval() {
        // SetUp: Create House "CountHouse3"
        house = new House();
        
        // SetUp: Add Dog "Chief", Cat "Queen"
        Dog dog1 = new Dog();
        dog1.setName("Chief");
        house.addPet(dog1);
        
        Cat cat1 = new Cat();
        cat1.setName("Queen");
        house.addPet(cat1);
        
        // SetUp: Remove "Chief"
        house.removePet(dog1);
        
        // Action: Get pet count after removal
        int actualCount = house.getPetCount();
        
        // Expected Output: 1
        assertEquals("Pet count should be 1 after removing one pet", 1, actualCount);
    }
    
    @Test
    public void testCase4_CountEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        house = new House();
        
        // Action: Get pet count
        int actualCount = house.getPetCount();
        
        // Expected Output: 0
        assertEquals("Pet count should be 0 for empty house", 0, actualCount);
    }
    
    @Test
    public void testCase5_CountBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5"
        house = new House();
        
        // SetUp: Add 10 Cats (named "Cat1" to "Cat10")
        for (int i = 1; i <= 10; i++) {
            Cat cat = new Cat();
            cat.setName("Cat" + i);
            house.addPet(cat);
        }
        
        // Action: Get pet count
        int actualCount = house.getPetCount();
        
        // Expected Output: 10
        assertEquals("Pet count should be 10 for 10 pets", 10, actualCount);
    }
}