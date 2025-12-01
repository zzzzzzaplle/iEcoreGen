import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private House house;
    
    @Test
    public void testCase1_countPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1"
        house = new House();
        
        // Add Dog "Buster", Cat "Princess", Dog "Sam"
        Pet dog1 = new Dog("Buster");
        Pet cat1 = new Cat("Princess");
        Pet dog2 = new Dog("Sam");
        
        // Add pets to house (Note: Need to set house for each pet and add to house's pet list)
        dog1.setHouse(house);
        cat1.setHouse(house);
        dog2.setHouse(house);
        
        // Since House class doesn't have explicit addPet method, we need to access the internal list
        // Using reflection to access private pets field
        try {
            java.lang.reflect.Field petsField = House.class.getDeclaredField("pets");
            petsField.setAccessible(true);
            List<Pet> petsList = (List<Pet>) petsField.get(house);
            petsList.add(dog1);
            petsList.add(cat1);
            petsList.add(dog2);
        } catch (Exception e) {
            fail("Failed to access pets field: " + e.getMessage());
        }
        
        // Action: Get pet count for CountHouse1
        int result = house.countPets();
        
        // Expected Output: 3
        assertEquals("House with 3 pets should return count of 3", 3, result);
    }
    
    @Test
    public void testCase2_countAfterAdditions() {
        // SetUp: Create House "CountHouse2"
        house = new House();
        
        // Add Cat "Tiger"
        Pet cat1 = new Cat("Tiger");
        // Add Dog "Wolf"
        Pet dog1 = new Dog("Wolf");
        
        // Add pets to house
        cat1.setHouse(house);
        dog1.setHouse(house);
        
        try {
            java.lang.reflect.Field petsField = House.class.getDeclaredField("pets");
            petsField.setAccessible(true);
            List<Pet> petsList = (List<Pet>) petsField.get(house);
            petsList.add(cat1);
            petsList.add(dog1);
        } catch (Exception e) {
            fail("Failed to access pets field: " + e.getMessage());
        }
        
        // Action: Get total pet count
        int result = house.countPets();
        
        // Expected Output: 2
        assertEquals("House with 2 pets should return count of 2", 2, result);
    }
    
    @Test
    public void testCase3_countAfterRemoval() {
        // SetUp: Create House "CountHouse3"
        house = new House();
        
        // Add Dog "Chief", Cat "Queen"
        Pet dog1 = new Dog("Chief");
        Pet cat1 = new Cat("Queen");
        
        // Add pets to house
        dog1.setHouse(house);
        cat1.setHouse(house);
        
        try {
            java.lang.reflect.Field petsField = House.class.getDeclaredField("pets");
            petsField.setAccessible(true);
            List<Pet> petsList = (List<Pet>) petsField.get(house);
            petsList.add(dog1);
            petsList.add(cat1);
            
            // Remove "Chief"
            petsList.remove(dog1);
        } catch (Exception e) {
            fail("Failed to access pets field: " + e.getMessage());
        }
        
        // Action: Get pet count after removal
        int result = house.countPets();
        
        // Expected Output: 1
        assertEquals("House with 1 pet after removal should return count of 1", 1, result);
    }
    
    @Test
    public void testCase4_countEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        house = new House();
        
        // Action: Get pet count
        int result = house.countPets();
        
        // Expected Output: 0
        assertEquals("Empty house should return count of 0", 0, result);
    }
    
    @Test
    public void testCase5_countBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5"
        house = new House();
        
        // Add 10 Cats (named "Cat1" to "Cat10")
        try {
            java.lang.reflect.Field petsField = House.class.getDeclaredField("pets");
            petsField.setAccessible(true);
            List<Pet> petsList = (List<Pet>) petsField.get(house);
            
            for (int i = 1; i <= 10; i++) {
                Cat cat = new Cat("Cat" + i);
                cat.setHouse(house);
                petsList.add(cat);
            }
        } catch (Exception e) {
            fail("Failed to access pets field: " + e.getMessage());
        }
        
        // Action: Get pet count
        int result = house.countPets();
        
        // Expected Output: 10
        assertEquals("House with 10 pets should return count of 10", 10, result);
    }
}