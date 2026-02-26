package edu.pets.pets3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.pets.*;

import org.eclipse.emf.common.util.EList;

public class CR1Test {
    
    private PetsFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = PetsFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_RetrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1" with Dog "Buddy" and Cat "Whiskers"
        House home1 = factory.createHouse();
        Dog dog1 = factory.createDog();
        dog1.setName("Buddy");
        Cat cat1 = factory.createCat();
        cat1.setName("Whiskers");
        
        home1.getLived().add(dog1);
        home1.getLived().add(cat1);
        
        // Action: Retrieve pet names from Home1
        EList<String> petNames = home1.retrievePetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        assertEquals(2, petNames.size());
        assertTrue(petNames.contains("Buddy"));
        assertTrue(petNames.contains("Whiskers"));
    }
    
    @Test
    public void testCase2_RetrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2" with Cat "Mittens"
        House home2 = factory.createHouse();
        Cat cat2 = factory.createCat();
        cat2.setName("Mittens");
        
        home2.getLived().add(cat2);
        
        // Action: Retrieve pet names from Home2
        EList<String> petNames = home2.retrievePetNames();
        
        // Expected Output: List containing ["Mittens"]
        assertEquals(1, petNames.size());
        assertEquals("Mittens", petNames.get(0));
    }
    
    @Test
    public void testCase3_RetrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        House home3 = factory.createHouse();
        
        // Action: Retrieve pet names from Home3
        EList<String> petNames = home3.retrievePetNames();
        
        // Expected Output: []
        assertNotNull(petNames);
        assertTrue(petNames.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4" with Dog "Max" and Cat "Luna", then remove "Max"
        House home4 = factory.createHouse();
        Dog dog4 = factory.createDog();
        dog4.setName("Max");
        Cat cat4 = factory.createCat();
        cat4.setName("Luna");
        
        home4.getLived().add(dog4);
        home4.getLived().add(cat4);
        
        // Remove "Max" from Home4
        home4.getLived().remove(dog4);
        
        // Action: Retrieve pet names from Home4
        EList<String> petNames = home4.retrievePetNames();
        
        // Expected Output: List containing ["Luna"]
        assertEquals(1, petNames.size());
        assertEquals("Luna", petNames.get(0));
    }
    
    @Test
    public void testCase5_RetrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5" with Dog "Spot" and Cat "Spot"
        House home5 = factory.createHouse();
        Dog dog5 = factory.createDog();
        dog5.setName("Spot");
        Cat cat5 = factory.createCat();
        cat5.setName("Spot");
        
        home5.getLived().add(dog5);
        home5.getLived().add(cat5);
        
        // Action: Retrieve pet names from Home5
        EList<String> petNames = home5.retrievePetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        assertEquals(2, petNames.size());
        assertEquals("Spot", petNames.get(0));
        assertEquals("Spot", petNames.get(1));
    }
}