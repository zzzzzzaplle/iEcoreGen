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
    
    private Dog buster;
    private Cat princess;
    private Dog sam;
    private Cat tiger;
    private Dog wolf;
    private Dog chief;
    private Cat queen;
    
    @Before
    public void setUp() {
        // Initialize houses
        countHouse1 = new House();
        countHouse2 = new House();
        countHouse3 = new House();
        countHouse4 = new House();
        countHouse5 = new House();
        
        // Initialize pets
        buster = new Dog();
        buster.setName("Buster");
        
        princess = new Cat();
        princess.setName("Princess");
        
        sam = new Dog();
        sam.setName("Sam");
        
        tiger = new Cat();
        tiger.setName("Tiger");
        
        wolf = new Dog();
        wolf.setName("Wolf");
        
        chief = new Dog();
        chief.setName("Chief");
        
        queen = new Cat();
        queen.setName("Queen");
    }
    
    @Test
    public void testCase1_countPetsInPopulatedHouse() {
        // SetUp:
        // 1. Create House "CountHouse1"
        // 2. Add Dog "Buster", Cat "Princess", Dog "Sam"
        countHouse1.addPet(buster);
        countHouse1.addPet(princess);
        countHouse1.addPet(sam);
        
        // Action: Get pet count for CountHouse1
        int result = countHouse1.getPetCount();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_countAfterAdditions() {
        // SetUp:
        // 1. Create House "CountHouse2"
        // 2. Add Cat "Tiger"
        countHouse2.addPet(tiger);
        // 3. Add Dog "Wolf"
        countHouse2.addPet(wolf);
        
        // Action: Get total pet count
        int result = countHouse2.getPetCount();
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_countAfterRemoval() {
        // SetUp:
        // 1. Create House "CountHouse3"
        // 2. Add Dog "Chief", Cat "Queen"
        countHouse3.addPet(chief);
        countHouse3.addPet(queen);
        // 3. Remove "Chief"
        countHouse3.removePet(chief);
        
        // Action: Get pet count after removal
        int result = countHouse3.getPetCount();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_countEmptyHouse() {
        // SetUp:
        // 1. Create empty House "CountHouse4"
        
        // Action: Get pet count
        int result = countHouse4.getPetCount();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_countBoundary10PlusPets() {
        // SetUp:
        // 1. Create House "CountHouse5"
        // 2. Add 10 Cats (named "Cat1" to "Cat10")
        for (int i = 1; i <= 10; i++) {
            Cat cat = new Cat();
            cat.setName("Cat" + i);
            countHouse5.addPet(cat);
        }
        
        // Action: Get pet count
        int result = countHouse5.getPetCount();
        
        // Expected Output: 10
        assertEquals(10, result);
    }
}