import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_CountPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1" and add Dog "Buster", Cat "Princess", Dog "Sam"
        House house = new House();
        house.addPet(new Dog("Buster"));
        house.addPet(new Cat("Princess"));
        house.addPet(new Dog("Sam"));
        
        // Action: Get pet count for CountHouse1
        int actualCount = house.countPets();
        
        // Expected Output: 3
        assertEquals("House with 3 pets should return count 3", 3, actualCount);
    }
    
    @Test
    public void testCase2_CountAfterAdditions() {
        // SetUp: Create House "CountHouse2" and add Cat "Tiger", Dog "Wolf"
        House house = new House();
        house.addPet(new Cat("Tiger"));
        house.addPet(new Dog("Wolf"));
        
        // Action: Get total pet count
        int actualCount = house.countPets();
        
        // Expected Output: 2
        assertEquals("House with 2 pets should return count 2", 2, actualCount);
    }
    
    @Test
    public void testCase3_CountAfterRemoval() {
        // SetUp: Create House "CountHouse3" and add Dog "Chief", Cat "Queen"
        House house = new House();
        Dog chief = new Dog("Chief");
        Cat queen = new Cat("Queen");
        house.addPet(chief);
        house.addPet(queen);
        
        // Remove "Chief"
        house.removePet(chief);
        
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
        // SetUp: Create House "CountHouse5" and add 10 Cats (named "Cat1" to "Cat10")
        House house = new House();
        for (int i = 1; i <= 10; i++) {
            house.addPet(new Cat("Cat" + i));
        }
        
        // Action: Get pet count
        int actualCount = house.countPets();
        
        // Expected Output: 10
        assertEquals("House with 10 pets should return count 10", 10, actualCount);
    }
}