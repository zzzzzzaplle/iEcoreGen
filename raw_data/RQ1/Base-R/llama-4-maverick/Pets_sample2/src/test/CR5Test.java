import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    private House house;
    
    @Test
    public void testCase1_CountPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1" and add Dog "Buster", Cat "Princess", Dog "Sam"
        house = new House();
        
        Dog dog1 = new Dog();
        dog1.setName("Buster");
        
        Cat cat1 = new Cat();
        cat1.setName("Princess");
        
        Dog dog2 = new Dog();
        dog2.setName("Sam");
        
        // Add pets to house
        house.addPet(dog1);
        house.addPet(cat1);
        house.addPet(dog2);
        
        // Action: Get pet count for CountHouse1
        int result = house.countPets();
        
        // Expected Output: 3
        assertEquals("House with 3 pets should return count 3", 3, result);
    }
    
    @Test
    public void testCase2_CountAfterAdditions() {
        // SetUp: Create House "CountHouse2" and add Cat "Tiger", Dog "Wolf"
        house = new House();
        
        Cat cat = new Cat();
        cat.setName("Tiger");
        
        Dog dog = new Dog();
        dog.setName("Wolf");
        
        // Add pets to house
        house.addPet(cat);
        house.addPet(dog);
        
        // Action: Get total pet count
        int result = house.countPets();
        
        // Expected Output: 2
        assertEquals("House with 2 pets should return count 2", 2, result);
    }
    
    @Test
    public void testCase3_CountAfterRemoval() {
        // SetUp: Create House "CountHouse3", add Dog "Chief", Cat "Queen", then remove "Chief"
        house = new House();
        
        Dog dog = new Dog();
        dog.setName("Chief");
        
        Cat cat = new Cat();
        cat.setName("Queen");
        
        // Add pets to house
        house.addPet(dog);
        house.addPet(cat);
        
        // Remove "Chief"
        house.removePet(dog);
        
        // Action: Get pet count after removal
        int result = house.countPets();
        
        // Expected Output: 1
        assertEquals("House with 1 pet after removal should return count 1", 1, result);
    }
    
    @Test
    public void testCase4_CountEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        house = new House();
        
        // Action: Get pet count
        int result = house.countPets();
        
        // Expected Output: 0
        assertEquals("Empty house should return count 0", 0, result);
    }
    
    @Test
    public void testCase5_CountBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5" and add 10 Cats (named "Cat1" to "Cat10")
        house = new House();
        
        // Add 10 cats
        for (int i = 1; i <= 10; i++) {
            Cat cat = new Cat();
            cat.setName("Cat" + i);
            house.addPet(cat);
        }
        
        // Action: Get pet count
        int result = house.countPets();
        
        // Expected Output: 10
        assertEquals("House with 10 pets should return count 10", 10, result);
    }
}