package edu.pets.pets2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.pets.PetsFactory;
import edu.pets.House;
import edu.pets.Dog;
import edu.pets.Cat;
import edu.pets.Pet;

import org.eclipse.emf.common.util.EList;

public class CR1Test {
    
    private PetsFactory factory;
    
    @Before
    public void setUp() {
        factory = PetsFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_retrieveNamesFromHouseWithMultiplePets() {
        // SetUp:
        // 1. Create House "Home1"
        House home1 = factory.createHouse();
        
        // 2. Add Dog named "Buddy" to Home1
        Dog buddy = factory.createDog();
        buddy.setName("Buddy");
        home1.addPet(buddy);
        
        // 3. Add Cat named "Whiskers" to Home1
        Cat whiskers = factory.createCat();
        whiskers.setName("Whiskers");
        home1.addPet(whiskers);
        
        // Action: Retrieve pet names from Home1
        EList<String> petNames = home1.retrievePetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        assertEquals(2, petNames.size());
        assertTrue(petNames.contains("Buddy"));
        assertTrue(petNames.contains("Whiskers"));
    }
    
    @Test
    public void testCase2_retrieveNamesFromHouseWithSinglePet() {
        // SetUp:
        // 1. Create House "Home2"
        House home2 = factory.createHouse();
        
        // 2. Add Cat named "Mittens" to Home2
        Cat mittens = factory.createCat();
        mittens.setName("Mittens");
        home2.addPet(mittens);
        
        // Action: Retrieve pet names from Home2
        EList<String> petNames = home2.retrievePetNames();
        
        // Expected Output: List containing ["Mittens"]
        assertEquals(1, petNames.size());
        assertEquals("Mittens", petNames.get(0));
    }
    
    @Test
    public void testCase3_retrieveNamesFromEmptyHouse() {
        // SetUp:
        // 1. Create House "Home3" (no pets)
        House home3 = factory.createHouse();
        
        // Action: Retrieve pet names from Home3
        EList<String> petNames = home3.retrievePetNames();
        
        // Expected Output: []
        assertEquals(0, petNames.size());
    }
    
    @Test
    public void testCase4_retrieveNamesAfterPetRemoval() {
        // SetUp:
        // 1. Create House "Home4"
        House home4 = factory.createHouse();
        
        // 2. Add Dog "Max" and Cat "Luna"
        Dog max = factory.createDog();
        max.setName("Max");
        home4.addPet(max);
        
        Cat luna = factory.createCat();
        luna.setName("Luna");
        home4.addPet(luna);
        
        // 3. Remove "Max" from Home4
        home4.removePet(max);
        
        // Action: Retrieve pet names from Home4
        EList<String> petNames = home4.retrievePetNames();
        
        // Expected Output: List containing ["Luna"]
        assertEquals(1, petNames.size());
        assertEquals("Luna", petNames.get(0));
    }
    
    @Test
    public void testCase5_retrieveNamesFromHouseWithSameNamePets() {
        // SetUp:
        // 1. Create House "Home5"
        House home5 = factory.createHouse();
        
        // 2. Add Dog "Spot" and Cat "Spot"
        Dog spotDog = factory.createDog();
        spotDog.setName("Spot");
        home5.addPet(spotDog);
        
        Cat spotCat = factory.createCat();
        spotCat.setName("Spot");
        home5.addPet(spotCat);
        
        // Action: Retrieve pet names from Home5
        EList<String> petNames = home5.retrievePetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        assertEquals(2, petNames.size());
        assertEquals("Spot", petNames.get(0));
        assertEquals("Spot", petNames.get(1));
    }
}