package edu.pets.pets5.test;

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
    
    @Test
    public void testCase1_addNewPetToEmptyHouse() {
        // SetUp:
        // 1. Create House "HomeA"
        House homeA = factory.createHouse();
        
        // 2. Create Dog "Rex" (no house assigned)
        Dog rex = factory.createDog();
        rex.setName("Rex");
        
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue(result);
        
        // Post-Condition: HomeA contains Rex
        assertTrue(homeA.getLived().contains(rex));
        assertEquals(homeA, rex.getHouse());
    }
    
    @Test
    public void testCase2_addPetAlreadyInHouse() {
        // SetUp:
        // 1. Create House "HomeB" and "HomeC"
        House homeB = factory.createHouse();
        House homeC = factory.createHouse();
        
        // 2. Add Cat "Oliver" to HomeB
        Cat oliver = factory.createCat();
        oliver.setName("Oliver");
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse(result);
        
        // Post-Condition: HomeC remains unchanged
        assertFalse(homeC.getLived().contains(oliver));
        assertEquals(homeB, oliver.getHouse());
    }
    
    @Test
    public void testCase3_addSamePetTwiceToSameHouse() {
        // SetUp:
        // 1. Create House "HomeD"
        House homeD = factory.createHouse();
        
        // 2. Add Dog "Rocky" to HomeD
        Dog rocky = factory.createDog();
        rocky.setName("Rocky");
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_addMultipleUniquePets() {
        // SetUp:
        // 1. Create House "HomeE"
        House homeE = factory.createHouse();
        
        // 2. Create Dog "Bella" and Cat "Lucy" (no houses)
        Dog bella = factory.createDog();
        bella.setName("Bella");
        Cat lucy = factory.createCat();
        lucy.setName("Lucy");
        
        // Action: 
        // - Add Bella to HomeE → true
        boolean result1 = homeE.addPet(bella);
        
        // - Add Lucy to HomeE → true
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue(result1);
        assertTrue(result2);
        
        // Post-Condition: HomeE contains both pets
        assertTrue(homeE.getLived().contains(bella));
        assertTrue(homeE.getLived().contains(lucy));
        assertEquals(homeE, bella.getHouse());
        assertEquals(homeE, lucy.getHouse());
    }
    
    @Test
    public void testCase5_addPetWithNullName() {
        // SetUp:
        // 1. Create House "HomeF"
        House homeF = factory.createHouse();
        
        // 2. Create Dog with null name
        Dog dogWithNullName = factory.createDog();
        // Name is null by default
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(dogWithNullName);
        
        // Expected Output: false
        assertFalse(result);
    }
}