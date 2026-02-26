import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_countPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1"
        House house = new House();
        
        // SetUp: Add Dog "Buster", Cat "Princess", Dog "Sam"
        house.addPet(new Dog("Buster"));
        house.addPet(new Cat("Princess"));
        house.addPet(new Dog("Sam"));
        
        // Action: Get pet count for CountHouse1
        int count = house.getPetCount();
        
        // Expected Output: 3
        assertEquals(3, count);
    }
    
    @Test
    public void testCase2_countAfterAdditions() {
        // SetUp: Create House "CountHouse2"
        House house = new House();
        
        // SetUp: Add Cat "Tiger"
        house.addPet(new Cat("Tiger"));
        
        // SetUp: Add Dog "Wolf"
        house.addPet(new Dog("Wolf"));
        
        // Action: Get total pet count
        int count = house.getPetCount();
        
        // Expected Output: 2
        assertEquals(2, count);
    }
    
    @Test
    public void testCase3_countAfterRemoval() {
        // SetUp: Create House "CountHouse3"
        House house = new House();
        
        // SetUp: Add Dog "Chief", Cat "Queen"
        Dog chief = new Dog("Chief");
        Cat queen = new Cat("Queen");
        house.addPet(chief);
        house.addPet(queen);
        
        // SetUp: Remove "Chief"
        house.removePet(chief);
        
        // Action: Get pet count after removal
        int count = house.getPetCount();
        
        // Expected Output: 1
        assertEquals(1, count);
    }
    
    @Test
    public void testCase4_countEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        House house = new House();
        
        // Action: Get pet count
        int count = house.getPetCount();
        
        // Expected Output: 0
        assertEquals(0, count);
    }
    
    @Test
    public void testCase5_countBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5"
        House house = new House();
        
        // SetUp: Add 10 Cats (named "Cat1" to "Cat10")
        for (int i = 1; i <= 10; i++) {
            house.addPet(new Cat("Cat" + i));
        }
        
        // Action: Get pet count
        int count = house.getPetCount();
        
        // Expected Output: 10
        assertEquals(10, count);
    }
}