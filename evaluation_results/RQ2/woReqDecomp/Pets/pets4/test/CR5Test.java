package edu.pets.pets4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.pets.PetsFactory;
import edu.pets.PetsPackage;
import edu.pets.House;
import edu.pets.Dog;
import edu.pets.Cat;
import org.eclipse.emf.ecore.EPackage;
import edu.pets.*;

public class CR5Test {
    
    private PetsFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the PetsPackage and factory
        PetsPackage.eINSTANCE.eClass();
        factory = PetsFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_CountPetsInPopulatedHouse() {
        // SetUp: Create House "CountHouse1" and add Dog "Buster", Cat "Princess", Dog "Sam"
        House house = factory.createHouse();
        
        Dog dog1 = factory.createDog();
        dog1.setName("Buster");
        house.getLived().add(dog1);
        
        Cat cat1 = factory.createCat();
        cat1.setName("Princess");
        house.getLived().add(cat1);
        
        Dog dog2 = factory.createDog();
        dog2.setName("Sam");
        house.getLived().add(dog2);
        
        // Action: Get pet count for CountHouse1
        int count = house.getPetCount(null);
        
        // Expected Output: 3
        assertEquals("House should contain 3 pets", 3, count);
    }
    
    @Test
    public void testCase2_CountAfterAdditions() {
        // SetUp: Create House "CountHouse2" and add Cat "Tiger", then Dog "Wolf"
        House house = factory.createHouse();
        
        Cat cat = factory.createCat();
        cat.setName("Tiger");
        house.getLived().add(cat);
        
        Dog dog = factory.createDog();
        dog.setName("Wolf");
        house.getLived().add(dog);
        
        // Action: Get total pet count
        int count = house.getPetCount(null);
        
        // Expected Output: 2
        assertEquals("House should contain 2 pets after additions", 2, count);
    }
    
    @Test
    public void testCase3_CountAfterRemoval() {
        // SetUp: Create House "CountHouse3", add Dog "Chief", Cat "Queen", then remove "Chief"
        House house = factory.createHouse();
        
        Dog dog = factory.createDog();
        dog.setName("Chief");
        house.getLived().add(dog);
        
        Cat cat = factory.createCat();
        cat.setName("Queen");
        house.getLived().add(cat);
        
        // Remove the dog
        house.getLived().remove(dog);
        
        // Action: Get pet count after removal
        int count = house.getPetCount(null);
        
        // Expected Output: 1
        assertEquals("House should contain 1 pet after removal", 1, count);
    }
    
    @Test
    public void testCase4_CountEmptyHouse() {
        // SetUp: Create empty House "CountHouse4"
        House house = factory.createHouse();
        
        // Action: Get pet count
        int count = house.getPetCount(null);
        
        // Expected Output: 0
        assertEquals("Empty house should return 0 pets", 0, count);
    }
    
    @Test
    public void testCase5_CountBoundary10PlusPets() {
        // SetUp: Create House "CountHouse5" and add 10 Cats (named "Cat1" to "Cat10")
        House house = factory.createHouse();
        
        for (int i = 1; i <= 10; i++) {
            Cat cat = factory.createCat();
            cat.setName("Cat" + i);
            house.getLived().add(cat);
        }
        
        // Action: Get pet count
        int count = house.getPetCount(null);
        
        // Expected Output: 10
        assertEquals("House should contain exactly 10 pets", 10, count);
    }
}
