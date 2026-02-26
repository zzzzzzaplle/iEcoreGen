// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private House homeA, homeB, homeC, homeD, homeE, homeF;
    private Dog rex, rocky, bella;
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
        
        oliver = new Cat();
        oliver.setName("Oliver");
        
        rocky = new Dog();
        rocky.setName("Rocky");
        
        bella = new Dog();
        bella.setName("Bella");
        
        lucy = new Cat();
        lucy.setName("Lucy");
    }
    
    @Test
    public void testCase1_addNewPetToEmptyHouse() {
        // SetUp:
        // 1. Create House "HomeA"
        // 2. Create Dog "Rex" (no house assigned)
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue(result);
        
        // Post-Condition: HomeA contains Rex
        assertEquals(1, homeA.getPetCount());
        assertTrue(homeA.getPets().contains(rex));
        assertEquals(homeA, rex.getHome());
    }
    
    @Test
    public void testCase2_addPetAlreadyInAnotherHouse() {
        // SetUp:
        // 1. Create House "HomeB" and "HomeC"
        // 2. Add Cat "Oliver" to HomeB
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
        // SetUp:
        // 1. Create House "HomeD"
        // 2. Add Dog "Rocky" to HomeD
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify that the pet is still in the house only once
        assertEquals(1, homeD.getPetCount());
        assertEquals(homeD, rocky.getHome());
    }
    
    @Test
    public void testCase4_addMultipleUniquePets() {
        // SetUp:
        // 1. Create House "HomeE"
        // 2. Create Dog "Bella" and Cat "Lucy" (no houses)
        
        // Action: 
        // - Add Bella to HomeE → true
        boolean result1 = homeE.addPet(bella);
        
        // - Add Lucy to HomeE → true
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
        // SetUp:
        // 1. Create House "HomeF"
        // 2. Create Dog with null name
        Dog nullNameDog = new Dog();
        nullNameDog.setName(null);
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNameDog);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify that the pet was not added
        assertEquals(0, homeF.getPetCount());
        assertNull(nullNameDog.getHome());
    }
}