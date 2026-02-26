import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_countPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1" and add Dog "Buster", Cat "Princess", Dog "Sam"
        House house = new House();
        Dog dog1 = new Dog("Buster");
        Cat cat1 = new Cat("Princess");
        Dog dog2 = new Dog("Sam");
        
        house.addPet(dog1);
        house.addPet(cat1);
        house.addPet(dog2);
        
        // Action: Get pet count for CountHouse1
        int result = house.getPetCount();
        
        // Expected Output: 3
        assertEquals("House with 3 pets should return count 3", 3, result);
    }
    
    @Test
    public void testCase2_countAfterAdditions() {
        // SetUp: Create House "CountHouse2" and add Cat "Tiger", Dog "Wolf"
        House house = new House();
        Cat cat = new Cat("Tiger");
        Dog dog = new Dog("Wolf");
        
        house.addPet(cat);
        house.addPet(dog);
        
        // Action: Get total pet count
        int result = house.getPetCount();
        
        // Expected Output: 2
        assertEquals("House with 2 pets should return count 2", 2, result);
    }
    
    @Test
    public void testCase3_countAfterRemoval() {
        // SetUp: Create House "CountHouse3", add Dog "Chief", Cat "Queen", then remove "Chief"
        House house = new House();
        Dog dog = new Dog("Chief");
        Cat cat = new Cat("Queen");
        
        house.addPet(dog);
        house.addPet(cat);
        house.removePet(dog);
        
        // Action: Get pet count after removal
        int result = house.getPetCount();
        
        // Expected Output: 1
        assertEquals("House after removing one pet should return count 1", 1, result);
    }
    
    @Test
    public void testCase4_countEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        House house = new House();
        
        // Action: Get pet count
        int result = house.getPetCount();
        
        // Expected Output: 0
        assertEquals("Empty house should return count 0", 0, result);
    }
    
    @Test
    public void testCase5_countBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5" and add 10 Cats (named "Cat1" to "Cat10")
        House house = new House();
        
        for (int i = 1; i <= 10; i++) {
            Cat cat = new Cat("Cat" + i);
            house.addPet(cat);
        }
        
        // Action: Get pet count
        int result = house.getPetCount();
        
        // Expected Output: 10
        assertEquals("House with 10 pets should return count 10", 10, result);
    }
}