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
        Dog buster = new Dog();
        buster.setName("Buster");
        countHouse1.addPet(buster);
        
        Cat princess = new Cat();
        princess.setName("Princess");
        countHouse1.addPet(princess);
        
        Dog sam = new Dog();
        sam.setName("Sam");
        countHouse1.addPet(sam);
        
        // Action: Get pet count for CountHouse1
        int actualCount = countHouse1.getPetCount();
        
        // Expected Output: 3
        assertEquals("Pet count should be 3 for house with 3 pets", 3, actualCount);
    }
    
    @Test
    public void testCase2_CountAfterAdditions() {
        // SetUp: Create House "CountHouse2"
        House countHouse2 = new House();
        
        // SetUp: Add Cat "Tiger"
        Cat tiger = new Cat();
        tiger.setName("Tiger");
        countHouse2.addPet(tiger);
        
        // SetUp: Add Dog "Wolf"
        Dog wolf = new Dog();
        wolf.setName("Wolf");
        countHouse2.addPet(wolf);
        
        // Action: Get total pet count
        int actualCount = countHouse2.getPetCount();
        
        // Expected Output: 2
        assertEquals("Pet count should be 2 after adding two pets", 2, actualCount);
    }
    
    @Test
    public void testCase3_CountAfterRemoval() {
        // SetUp: Create House "CountHouse3"
        House countHouse3 = new House();
        
        // SetUp: Add Dog "Chief", Cat "Queen"
        Dog chief = new Dog();
        chief.setName("Chief");
        countHouse3.addPet(chief);
        
        Cat queen = new Cat();
        queen.setName("Queen");
        countHouse3.addPet(queen);
        
        // SetUp: Remove "Chief"
        countHouse3.removePet(chief);
        
        // Action: Get pet count after removal
        int actualCount = countHouse3.getPetCount();
        
        // Expected Output: 1
        assertEquals("Pet count should be 1 after removing one pet", 1, actualCount);
    }
    
    @Test
    public void testCase4_CountEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        House countHouse4 = new House();
        
        // Action: Get pet count
        int actualCount = countHouse4.getPetCount();
        
        // Expected Output: 0
        assertEquals("Pet count should be 0 for empty house", 0, actualCount);
    }
    
    @Test
    public void testCase5_CountBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5"
        House countHouse5 = new House();
        
        // SetUp: Add 10 Cats (named "Cat1" to "Cat10")
        for (int i = 1; i <= 10; i++) {
            Cat cat = new Cat();
            cat.setName("Cat" + i);
            countHouse5.addPet(cat);
        }
        
        // Action: Get pet count
        int actualCount = countHouse5.getPetCount();
        
        // Expected Output: 10
        assertEquals("Pet count should be 10 for house with 10 pets", 10, actualCount);
    }
}