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
        House countHouse1 = new House();
        
        Dog dog1 = new Dog();
        dog1.setName("Buster");
        
        Cat cat1 = new Cat();
        cat1.setName("Princess");
        
        Dog dog2 = new Dog();
        dog2.setName("Sam");
        
        countHouse1.addPet(dog1);
        countHouse1.addPet(cat1);
        countHouse1.addPet(dog2);
        
        // Action: Get pet count for CountHouse1
        int result = countHouse1.getPetCount();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_CountAfterAdditions() {
        // SetUp: Create House "CountHouse2", add Cat "Tiger", add Dog "Wolf"
        House countHouse2 = new House();
        
        Cat cat = new Cat();
        cat.setName("Tiger");
        
        Dog dog = new Dog();
        dog.setName("Wolf");
        
        countHouse2.addPet(cat);
        countHouse2.addPet(dog);
        
        // Action: Get total pet count
        int result = countHouse2.getPetCount();
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_CountAfterRemoval() {
        // SetUp: Create House "CountHouse3", add Dog "Chief", Cat "Queen", remove "Chief"
        House countHouse3 = new House();
        
        Dog dog = new Dog();
        dog.setName("Chief");
        
        Cat cat = new Cat();
        cat.setName("Queen");
        
        countHouse3.addPet(dog);
        countHouse3.addPet(cat);
        
        // Remove the dog
        countHouse3.removePet(dog);
        
        // Action: Get pet count after removal
        int result = countHouse3.getPetCount();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_CountEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        House countHouse4 = new House();
        
        // Action: Get pet count
        int result = countHouse4.getPetCount();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_CountBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5", add 10 Cats (named "Cat1" to "Cat10")
        House countHouse5 = new House();
        
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