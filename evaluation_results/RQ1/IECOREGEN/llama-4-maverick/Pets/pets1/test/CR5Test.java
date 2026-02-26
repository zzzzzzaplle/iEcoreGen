package edu.pets.pets1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.pets.PetsFactory;
import edu.pets.House;
import edu.pets.Dog;
import edu.pets.Cat;

public class CR5Test {
    
    private PetsFactory factory;
    
    @Before
    public void setUp() {
        factory = PetsFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_countPetsInPopulatedHouse() {
        // SetUp:
        // 1. Create House "CountHouse1"
        House house = factory.createHouse();
        
        // 2. Add Dog "Buster", Cat "Princess", Dog "Sam"
        Dog dog1 = factory.createDog();
        dog1.setName("Buster");
        house.addPet(dog1);
        
        Cat cat1 = factory.createCat();
        cat1.setName("Princess");
        house.addPet(cat1);
        
        Dog dog2 = factory.createDog();
        dog2.setName("Sam");
        house.addPet(dog2);
        
        // Action: Get pet count for CountHouse1
        int count = house.getLived().size();
        
        // Expected Output: 3
        assertEquals(3, count);
    }
    
    @Test
    public void testCase2_countAfterAdditions() {
        // SetUp:
        // 1. Create House "CountHouse2"
        House house = factory.createHouse();
        
        // 2. Add Cat "Tiger"
        Cat cat = factory.createCat();
        cat.setName("Tiger");
        house.addPet(cat);
        
        // 3. Add Dog "Wolf"
        Dog dog = factory.createDog();
        dog.setName("Wolf");
        house.addPet(dog);
        
        // Action: Get total pet count
        int count = house.getLived().size();
        
        // Expected Output: 2
        assertEquals(2, count);
    }
    
    @Test
    public void testCase3_countAfterRemoval() {
        // SetUp:
        // 1. Create House "CountHouse3"
        House house = factory.createHouse();
        
        // 2. Add Dog "Chief", Cat "Queen"
        Dog dog = factory.createDog();
        dog.setName("Chief");
        house.addPet(dog);
        
        Cat cat = factory.createCat();
        cat.setName("Queen");
        house.addPet(cat);
        
        // 3. Remove "Chief"
        house.removePet(dog);
        
        // Action: Get pet count after removal
        int count = house.getLived().size();
        
        // Expected Output: 1
        assertEquals(1, count);
    }
    
    @Test
    public void testCase4_countEmptyHouse() {
        // SetUp:
        // 1. Create empty House "CountHouse4"
        House house = factory.createHouse();
        
        // Action: Get pet count
        int count = house.getLived().size();
        
        // Expected Output: 0
        assertEquals(0, count);
    }
    
    @Test
    public void testCase5_countBoundary10PlusPets() {
        // SetUp:
        // 1. Create House "CountHouse5"
        House house = factory.createHouse();
        
        // 2. Add 10 Cats (named "Cat1" to "Cat10")
        for (int i = 1; i <= 10; i++) {
            Cat cat = factory.createCat();
            cat.setName("Cat" + i);
            house.addPet(cat);
        }
        
        // Action: Get pet count
        int count = house.getLived().size();
        
        // Expected Output: 10
        assertEquals(10, count);
    }
}