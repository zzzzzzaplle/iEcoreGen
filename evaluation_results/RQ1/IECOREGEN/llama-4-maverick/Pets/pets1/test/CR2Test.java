package edu.pets.pets1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.pets.PetsFactory;
import edu.pets.House;
import edu.pets.Dog;
import edu.pets.Cat;

public class CR2Test {
    
    private PetsFactory factory;
    
    @Before
    public void setUp() {
        factory = PetsFactory.eINSTANCE;
    }
    
    /**
     * Test Case 1: "Add new pet to empty house"
     * SetUp:
     * 1. Create House "HomeA"
     * 2. Create Dog "Rex" (no house assigned)
     * Action: Add Rex to HomeA
     * Expected Output: true
     * Post-Condition: HomeA contains Rex
     */
    @Test
    public void testCase1_addNewPetToEmptyHouse() {
        // SetUp
        House homeA = factory.createHouse();
        Dog rex = factory.createDog();
        rex.setName("Rex");
        
        // Action
        boolean result = homeA.addPet(rex);
        
        // Expected Output
        assertTrue(result);
        
        // Post-Condition
        assertEquals(1, homeA.getLived().size());
        assertTrue(homeA.getLived().contains(rex));
        assertEquals(homeA, rex.getHouse());
    }
    
    /**
     * Test Case 2: "Add pet already in the house"
     * SetUp:
     * 1. Create House "HomeB" and "HomeC"
     * 2. Add Cat "Oliver" to HomeB
     * Action: Add Oliver to HomeC
     * Expected Output: false
     * Post-Condition: HomeC remains unchanged
     */
    @Test
    public void testCase2_addPetAlreadyInHouse() {
        // SetUp
        House homeB = factory.createHouse();
        House homeC = factory.createHouse();
        Cat oliver = factory.createCat();
        oliver.setName("Oliver");
        
        // Add Oliver to HomeB first
        assertTrue(homeB.addPet(oliver));
        
        // Action: Try to add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output
        assertFalse(result);
        
        // Post-Condition
        assertEquals(0, homeC.getLived().size());
        assertEquals(1, homeB.getLived().size());
        assertEquals(homeB, oliver.getHouse());
    }
    
    /**
     * Test Case 3: "Add same pet twice to same house"
     * SetUp:
     * 1. Create House "HomeD"
     * 2. Add Dog "Rocky" to HomeD
     * Action: Attempt to add Rocky again to HomeD
     * Expected Output: false
     */
    @Test
    public void testCase3_addSamePetTwiceToSameHouse() {
        // SetUp
        House homeD = factory.createHouse();
        Dog rocky = factory.createDog();
        rocky.setName("Rocky");
        
        // Add Rocky to HomeD first
        assertTrue(homeD.addPet(rocky));
        
        // Action: Try to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output
        assertFalse(result);
        
        // Verify Rocky is still in the house only once
        assertEquals(1, homeD.getLived().size());
        assertEquals(homeD, rocky.getHouse());
    }
    
    /**
     * Test Case 4: "Add multiple unique pets"
     * SetUp:
     * 1. Create House "HomeE"
     * 2. Create Dog "Bella" and Cat "Lucy" (no houses)
     * Action: 
     * - Add Bella to HomeE → true
     * - Add Lucy to HomeE → true
     * Expected Output: Both operations return true
     * Post-Condition: HomeE contains both pets
     */
    @Test
    public void testCase4_addMultipleUniquePets() {
        // SetUp
        House homeE = factory.createHouse();
        Dog bella = factory.createDog();
        bella.setName("Bella");
        Cat lucy = factory.createCat();
        lucy.setName("Lucy");
        
        // Action
        boolean result1 = homeE.addPet(bella);
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output
        assertTrue(result1);
        assertTrue(result2);
        
        // Post-Condition
        assertEquals(2, homeE.getLived().size());
        assertTrue(homeE.getLived().contains(bella));
        assertTrue(homeE.getLived().contains(lucy));
        assertEquals(homeE, bella.getHouse());
        assertEquals(homeE, lucy.getHouse());
    }
    
    /**
     * Test Case 5: "Add pet with null name"
     * SetUp:
     * 1. Create House "HomeF"
     * 2. Create Dog with null name
     * Action: Add null-named dog to HomeF
     * Expected Output: false
     */
    @Test
    public void testCase5_addPetWithNullName() {
        // SetUp
        House homeF = factory.createHouse();
        Dog dogWithNullName = factory.createDog();
        // Note: Name is null by default
        
        // Action
        boolean result = homeF.addPet(dogWithNullName);
        
        // Expected Output
        assertFalse(result);
        
        // Verify dog was not added
        assertEquals(0, homeF.getLived().size());
        assertNull(dogWithNullName.getHouse());
    }
}