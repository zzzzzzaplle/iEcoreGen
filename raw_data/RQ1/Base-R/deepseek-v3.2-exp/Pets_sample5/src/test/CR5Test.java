import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_countPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1" and add Dog "Buster", Cat "Princess", Dog "Sam"
        House countHouse1 = new House();
        Dog buster = new Dog();
        buster.setName("Buster");
        Cat princess = new Cat();
        princess.setName("Princess");
        Dog sam = new Dog();
        sam.setName("Sam");
        
        // Add pets to the house
        countHouse1.addPet(buster);
        countHouse1.addPet(princess);
        countHouse1.addPet(sam);
        
        // Action: Get pet count for CountHouse1
        int actualCount = countHouse1.countPets();
        
        // Expected Output: 3
        assertEquals("House with 3 pets should return count 3", 3, actualCount);
    }
    
    @Test
    public void testCase2_countAfterAdditions() {
        // SetUp: Create House "CountHouse2" and add Cat "Tiger", Dog "Wolf"
        House countHouse2 = new House();
        Cat tiger = new Cat();
        tiger.setName("Tiger");
        Dog wolf = new Dog();
        wolf.setName("Wolf");
        
        // Add pets to the house
        countHouse2.addPet(tiger);
        countHouse2.addPet(wolf);
        
        // Action: Get total pet count
        int actualCount = countHouse2.countPets();
        
        // Expected Output: 2
        assertEquals("House with 2 pets should return count 2", 2, actualCount);
    }
    
    @Test
    public void testCase3_countAfterRemoval() {
        // SetUp: Create House "CountHouse3" and add Dog "Chief", Cat "Queen", then remove "Chief"
        House countHouse3 = new House();
        Dog chief = new Dog();
        chief.setName("Chief");
        Cat queen = new Cat();
        queen.setName("Queen");
        
        // Add pets to the house
        countHouse3.addPet(chief);
        countHouse3.addPet(queen);
        
        // Remove "Chief"
        countHouse3.removePet(chief);
        
        // Action: Get pet count after removal
        int actualCount = countHouse3.countPets();
        
        // Expected Output: 1
        assertEquals("House with 1 pet after removal should return count 1", 1, actualCount);
    }
    
    @Test
    public void testCase4_countEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        House countHouse4 = new House();
        
        // Action: Get pet count
        int actualCount = countHouse4.countPets();
        
        // Expected Output: 0
        assertEquals("Empty house should return count 0", 0, actualCount);
    }
    
    @Test
    public void testCase5_countBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5" and add 10 Cats (named "Cat1" to "Cat10")
        House countHouse5 = new House();
        
        // Add 10 cats
        for (int i = 1; i <= 10; i++) {
            Cat cat = new Cat();
            cat.setName("Cat" + i);
            countHouse5.addPet(cat);
        }
        
        // Action: Get pet count
        int actualCount = countHouse5.countPets();
        
        // Expected Output: 10
        assertEquals("House with 10 pets should return count 10", 10, actualCount);
    }
}