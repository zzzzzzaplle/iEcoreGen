import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.List;

public class CR2Test {
    
    private House homeA, homeB, homeC, homeD, homeE, homeF;
    private Dog rex, rocky, bella, nullNamedDog;
    private Cat oliver, lucy;
    
    @Before
    public void setUp() {
        // Initialize houses
        homeA = new House();
        homeB = new House();
        homeC = new House();
        homeD = new House();
        homeE = new House();
        homeF = new House();
        
        // Initialize pets
        rex = new Dog();
        rex.setName("Rex");
        
        rocky = new Dog();
        rocky.setName("Rocky");
        
        oliver = new Cat();
        oliver.setName("Oliver");
        
        bella = new Dog();
        bella.setName("Bella");
        
        lucy = new Cat();
        lucy.setName("Lucy");
        
        nullNamedDog = new Dog();
        // nullNamedDog.setName is not called, so name remains null
    }
    
    @Test
    public void testCase1_addNewPetToEmptyHouse() {
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue(result);
        
        // Post-Condition: HomeA contains Rex
        List<Pet> petsInHomeA = homeA.getPets();
        assertEquals(1, petsInHomeA.size());
        assertEquals(rex, petsInHomeA.get(0));
        assertEquals(homeA, rex.getHome());
    }
    
    @Test
    public void testCase2_addPetAlreadyInTheHouse() {
        // SetUp: Add Cat "Oliver" to HomeB
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse(result);
        
        // Post-Condition: HomeC remains unchanged
        assertEquals(0, homeC.getPetCount());
        assertEquals(1, homeB.getPetCount());
        assertEquals(homeB, oliver.getHome());
    }
    
    @Test
    public void testCase3_addSamePetTwiceToSameHouse() {
        // SetUp: Add Dog "Rocky" to HomeD
        boolean firstAddResult = homeD.addPet(rocky);
        assertTrue(firstAddResult);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean secondAddResult = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse(secondAddResult);
        
        // Verify post-condition
        assertEquals(1, homeD.getPetCount());
        assertEquals(homeD, rocky.getHome());
    }
    
    @Test
    public void testCase4_addMultipleUniquePets() {
        // Action: Add Bella to HomeE → true
        boolean result1 = homeE.addPet(bella);
        
        // Action: Add Lucy to HomeE → true
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue(result1);
        assertTrue(result2);
        
        // Post-Condition: HomeE contains both pets
        assertEquals(2, homeE.getPetCount());
        assertTrue(homeE.getPets().contains(bella));
        assertTrue(homeE.getPets().contains(lucy));
        assertEquals(homeE, bella.getHome());
        assertEquals(homeE, lucy.getHome());
    }
    
    @Test
    public void testCase5_addPetWithNullName() {
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify post-condition
        assertEquals(0, homeF.getPetCount());
        assertNull(nullNamedDog.getHome());
    }
}