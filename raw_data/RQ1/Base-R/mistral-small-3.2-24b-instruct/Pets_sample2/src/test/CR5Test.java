import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_CountPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1"
        House countHouse1 = new House();
        
        // SetUp: Add Dog "Buster", Cat "Princess", Dog "Sam"
        Dog buster = new Dog("Buster");
        Cat princess = new Cat("Princess");
        Dog sam = new Dog("Sam");
        
        countHouse1.addPet(buster);
        countHouse1.addPet(princess);
        countHouse1.addPet(sam);
        
        // Action: Get pet count for CountHouse1
        int actualCount = countHouse1.countPets();
        
        // Expected Output: 3
        assertEquals("House with 3 pets should return count 3", 3, actualCount);
    }
    
    @Test
    public void testCase2_CountAfterAdditions() {
        // SetUp: Create House "CountHouse2"
        House countHouse2 = new House();
        
        // SetUp: Add Cat "Tiger"
        Cat tiger = new Cat("Tiger");
        countHouse2.addPet(tiger);
        
        // SetUp: Add Dog "Wolf"
        Dog wolf = new Dog("Wolf");
        countHouse2.addPet(wolf);
        
        // Action: Get total pet count
        int actualCount = countHouse2.countPets();
        
        // Expected Output: 2
        assertEquals("House with 2 pets should return count 2", 2, actualCount);
    }
    
    @Test
    public void testCase3_CountAfterRemoval() {
        // SetUp: Create House "CountHouse3"
        House countHouse3 = new House();
        
        // SetUp: Add Dog "Chief", Cat "Queen"
        Dog chief = new Dog("Chief");
        Cat queen = new Cat("Queen");
        
        countHouse3.addPet(chief);
        countHouse3.addPet(queen);
        
        // SetUp: Remove "Chief"
        countHouse3.removePet(chief);
        
        // Action: Get pet count after removal
        int actualCount = countHouse3.countPets();
        
        // Expected Output: 1
        assertEquals("House with 1 pet after removal should return count 1", 1, actualCount);
    }
    
    @Test
    public void testCase4_CountEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        House countHouse4 = new House();
        
        // Action: Get pet count
        int actualCount = countHouse4.countPets();
        
        // Expected Output: 0
        assertEquals("Empty house should return count 0", 0, actualCount);
    }
    
    @Test
    public void testCase5_CountBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5"
        House countHouse5 = new House();
        
        // SetUp: Add 10 Cats (named "Cat1" to "Cat10")
        for (int i = 1; i <= 10; i++) {
            Cat cat = new Cat("Cat" + i);
            countHouse5.addPet(cat);
        }
        
        // Action: Get pet count
        int actualCount = countHouse5.countPets();
        
        // Expected Output: 10
        assertEquals("House with 10 pets should return count 10", 10, actualCount);
    }
}