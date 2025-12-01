package edu.pets.pets5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.pets.PetsFactory;
import edu.pets.PetsPackage;
import edu.pets.House;
import edu.pets.Dog;
import edu.pets.Cat;
import edu.pets.Pet;
import edu.pets.*;

public class CR2Test {
    
    private PetsFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the PetsFactory using Ecore factory pattern
        factory = PetsFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_AddNewPetToEmptyHouse() {
        // SetUp: Create House "HomeA" and Dog "Rex" (no house assigned)
        House homeA = factory.createHouse();
        Dog rex = factory.createDog();
        rex.setName("Rex");
        
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue("Adding new pet to empty house should return true", result);
        
        // Post-Condition: HomeA contains Rex
        assertTrue("HomeA should contain Rex after successful addition", homeA.getLived().contains(rex));
        assertEquals("HomeA should have exactly 1 pet", 1, homeA.getLived().size());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInTheHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
        House homeB = factory.createHouse();
        House homeC = factory.createHouse();
        Cat oliver = factory.createCat();
        oliver.setName("Oliver");
        
        // Add Oliver to HomeB first
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet that already belongs to another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        assertTrue("HomeC should remain empty", homeC.getLived().isEmpty());
        assertTrue("HomeB should still contain Oliver", homeB.getLived().contains(oliver));
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", Add Dog "Rocky" to HomeD
        House homeD = factory.createHouse();
        Dog rocky = factory.createDog();
        rocky.setName("Rocky");
        
        // First addition should succeed
        boolean firstResult = homeD.addPet(rocky);
        assertTrue("First addition of Rocky should succeed", firstResult);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean secondResult = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", secondResult);
        
        // Verify HomeD still has only one instance of Rocky
        assertEquals("HomeD should contain exactly one pet", 1, homeD.getLived().size());
        assertTrue("HomeD should still contain Rocky", homeD.getLived().contains(rocky));
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // SetUp: Create House "HomeE", Create Dog "Bella" and Cat "Lucy" (no houses)
        House homeE = factory.createHouse();
        Dog bella = factory.createDog();
        bella.setName("Bella");
        Cat lucy = factory.createCat();
        lucy.setName("Lucy");
        
        // Action: Add Bella to HomeE ?true, Add Lucy to HomeE ?true
        boolean resultBella = homeE.addPet(bella);
        boolean resultLucy = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("Adding Bella should return true", resultBella);
        assertTrue("Adding Lucy should return true", resultLucy);
        
        // Post-Condition: HomeE contains both pets
        assertTrue("HomeE should contain Bella", homeE.getLived().contains(bella));
        assertTrue("HomeE should contain Lucy", homeE.getLived().contains(lucy));
        assertEquals("HomeE should contain exactly 2 pets", 2, homeE.getLived().size());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        House homeF = factory.createHouse();
        Dog nullNamedDog = factory.createDog();
        // Intentionally not setting name to keep it null
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify HomeF remains empty
        assertTrue("HomeF should remain empty after failed addition", homeF.getLived().isEmpty());
    }
}

