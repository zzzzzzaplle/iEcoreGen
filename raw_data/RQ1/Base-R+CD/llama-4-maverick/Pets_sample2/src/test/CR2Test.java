import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        
        oliver = new Cat();
        oliver.setName("Oliver");
        
        rocky = new Dog();
        rocky.setName("Rocky");
        
        bella = new Dog();
        bella.setName("Bella");
        
        lucy = new Cat();
        lucy.setName("Lucy");
        
        nullNamedDog = new Dog();
        // nullNamedDog's name remains null
    }
    
    @Test
    public void testCase1_addNewPetToEmptyHouse() {
        // SetUp: Create House "HomeA" and Dog "Rex" (no house assigned)
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue(result);
        
        // Post-Condition: HomeA contains Rex
        assertEquals(1, homeA.getPetCount());
        assertTrue(homeA.getPets().contains(rex));
        assertEquals(homeA, rex.getLived());
    }
    
    @Test
    public void testCase2_addPetAlreadyInAnotherHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse(result);
        
        // Post-Condition: HomeC remains unchanged
        assertEquals(0, homeC.getPetCount());
        assertEquals(1, homeB.getPetCount());
        assertTrue(homeB.getPets().contains(oliver));
        assertEquals(homeB, oliver.getLived());
    }
    
    @Test
    public void testCase3_addSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", Add Dog "Rocky" to HomeD
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse(result);
        
        // Post-Condition: HomeD still contains only one instance of Rocky
        assertEquals(1, homeD.getPetCount());
        assertEquals(homeD, rocky.getLived());
    }
    
    @Test
    public void testCase4_addMultipleUniquePets() {
        // SetUp: Create House "HomeE", Create Dog "Bella" and Cat "Lucy" (no houses)
        
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
        assertEquals(homeE, bella.getLived());
        assertEquals(homeE, lucy.getLived());
    }
    
    @Test
    public void testCase5_addPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse(result);
        
        // Post-Condition: HomeF remains empty
        assertEquals(0, homeF.getPetCount());
        assertNull(nullNamedDog.getLived());
    }
}