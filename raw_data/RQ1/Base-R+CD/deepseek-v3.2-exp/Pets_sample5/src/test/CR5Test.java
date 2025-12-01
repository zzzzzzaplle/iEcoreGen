import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private House countHouse1;
    private House countHouse2;
    private House countHouse3;
    private House countHouse4;
    private House countHouse5;
    
    @Before
    public void setUp() {
        // Initialize houses for test cases
        countHouse1 = new House();
        countHouse2 = new House();
        countHouse3 = new House();
        countHouse4 = new House();
        countHouse5 = new House();
    }
    
    @Test
    public void testCase1_CountPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1" and add Dog "Buster", Cat "Princess", Dog "Sam"
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
        int result = countHouse1.getPetCount();
        
        // Expected Output: 3
        assertEquals("House with 3 pets should return count 3", 3, result);
    }
    
    @Test
    public void testCase2_CountAfterAdditions() {
        // SetUp: Create House "CountHouse2", add Cat "Tiger", then add Dog "Wolf"
        Cat tiger = new Cat();
        tiger.setName("Tiger");
        countHouse2.addPet(tiger);
        
        Dog wolf = new Dog();
        wolf.setName("Wolf");
        countHouse2.addPet(wolf);
        
        // Action: Get total pet count
        int result = countHouse2.getPetCount();
        
        // Expected Output: 2
        assertEquals("House with 2 pets should return count 2", 2, result);
    }
    
    @Test
    public void testCase3_CountAfterRemoval() {
        // SetUp: Create House "CountHouse3", add Dog "Chief", Cat "Queen", then remove "Chief"
        Dog chief = new Dog();
        chief.setName("Chief");
        countHouse3.addPet(chief);
        
        Cat queen = new Cat();
        queen.setName("Queen");
        countHouse3.addPet(queen);
        
        // Remove "Chief"
        countHouse3.removePet(chief);
        
        // Action: Get pet count after removal
        int result = countHouse3.getPetCount();
        
        // Expected Output: 1
        assertEquals("House with 1 pet after removal should return count 1", 1, result);
    }
    
    @Test
    public void testCase4_CountEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        // No pets added - house is empty
        
        // Action: Get pet count
        int result = countHouse4.getPetCount();
        
        // Expected Output: 0
        assertEquals("Empty house should return count 0", 0, result);
    }
    
    @Test
    public void testCase5_CountBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5" and add 10 Cats (named "Cat1" to "Cat10")
        for (int i = 1; i <= 10; i++) {
            Cat cat = new Cat();
            cat.setName("Cat" + i);
            countHouse5.addPet(cat);
        }
        
        // Action: Get pet count
        int result = countHouse5.getPetCount();
        
        // Expected Output: 10
        assertEquals("House with 10 pets should return count 10", 10, result);
    }
}