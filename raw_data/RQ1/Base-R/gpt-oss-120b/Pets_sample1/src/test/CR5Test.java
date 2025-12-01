import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private House house;
    
    @Before
    public void setUp() {
        house = new House();
    }
    
    @Test
    public void testCase1_CountPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1" and add Dog "Buster", Cat "Princess", Dog "Sam"
        house = new House();
        Dog dog1 = new Dog();
        dog1.setName("Buster");
        house.addPet(dog1);
        
        Cat cat1 = new Cat();
        cat1.setName("Princess");
        house.addPet(cat1);
        
        Dog dog2 = new Dog();
        dog2.setName("Sam");
        house.addPet(dog2);
        
        // Action: Get pet count for CountHouse1
        int result = house.getPetCount();
        
        // Expected Output: 3
        assertEquals("Pet count should be 3 for populated house", 3, result);
    }
    
    @Test
    public void testCase2_CountAfterAdditions() {
        // SetUp: Create House "CountHouse2", add Cat "Tiger", then add Dog "Wolf"
        house = new House();
        
        Cat cat = new Cat();
        cat.setName("Tiger");
        house.addPet(cat);
        
        Dog dog = new Dog();
        dog.setName("Wolf");
        house.addPet(dog);
        
        // Action: Get total pet count
        int result = house.getPetCount();
        
        // Expected Output: 2
        assertEquals("Pet count should be 2 after two additions", 2, result);
    }
    
    @Test
    public void testCase3_CountAfterRemoval() {
        // SetUp: Create House "CountHouse3", add Dog "Chief", Cat "Queen", then remove "Chief"
        house = new House();
        
        Dog dog = new Dog();
        dog.setName("Chief");
        house.addPet(dog);
        
        Cat cat = new Cat();
        cat.setName("Queen");
        house.addPet(cat);
        
        // Remove "Chief"
        house.removePet(dog);
        
        // Action: Get pet count after removal
        int result = house.getPetCount();
        
        // Expected Output: 1
        assertEquals("Pet count should be 1 after removal", 1, result);
    }
    
    @Test
    public void testCase4_CountEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        house = new House();
        
        // Action: Get pet count
        int result = house.getPetCount();
        
        // Expected Output: 0
        assertEquals("Pet count should be 0 for empty house", 0, result);
    }
    
    @Test
    public void testCase5_CountBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5" and add 10 Cats (named "Cat1" to "Cat10")
        house = new House();
        
        for (int i = 1; i <= 10; i++) {
            Cat cat = new Cat();
            cat.setName("Cat" + i);
            house.addPet(cat);
        }
        
        // Action: Get pet count
        int result = house.getPetCount();
        
        // Expected Output: 10
        assertEquals("Pet count should be 10 for 10 pets", 10, result);
    }
}