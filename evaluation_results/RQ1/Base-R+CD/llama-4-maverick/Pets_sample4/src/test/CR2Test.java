import org.junit.Test;
import static org.junit.Assert.*;

public class CR2Test {
    
    private House homeA, homeB, homeC, homeD, homeE, homeF;
    private Dog rex, rocky, bella, nullNamedDog;
    private Cat oliver, lucy;
    
    @Test
    public void testCase1_addNewPetToEmptyHouse() {
        // SetUp: Create House "HomeA" and Dog "Rex" (no house assigned)
        homeA = new House();
        rex = new Dog();
        rex.setName("Rex");
        
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
    public void testCase2_addPetAlreadyInHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
        homeB = new House();
        homeC = new House();
        oliver = new Cat();
        oliver.setName("Oliver");
        
        // Add Oliver to HomeB first
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
        homeD = new House();
        rocky = new Dog();
        rocky.setName("Rocky");
        
        // Add Rocky to HomeD first
        boolean firstAdd = homeD.addPet(rocky);
        assertTrue(firstAdd);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_addMultipleUniquePets() {
        // SetUp: Create House "HomeE", Create Dog "Bella" and Cat "Lucy" (no houses)
        homeE = new House();
        bella = new Dog();
        bella.setName("Bella");
        lucy = new Cat();
        lucy.setName("Lucy");
        
        // Action: Add Bella to HomeE → true, Add Lucy to HomeE → true
        boolean result1 = homeE.addPet(bella);
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
        homeF = new House();
        nullNamedDog = new Dog();
        nullNamedDog.setName(null);
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse(result);
    }
}