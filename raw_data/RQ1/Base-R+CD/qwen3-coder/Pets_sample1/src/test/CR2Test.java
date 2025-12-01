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
        
        bella = new Dog();
        bella.setName("Bella");
        
        nullNamedDog = new Dog();
        // nullNamedDog.setName(null); // name is null by default
        
        oliver = new Cat();
        oliver.setName("Oliver");
        
        lucy = new Cat();
        lucy.setName("Lucy");
    }
    
    @Test
    public void testCase1_addNewPetToEmptyHouse() {
        // SetUp: Create House "HomeA" and Dog "Rex" (no house assigned)
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue(result);
        
        // Post-Condition: HomeA contains Rex
        List<Pet> pets = homeA.getPets();
        assertEquals(1, pets.size());
        assertEquals(rex, pets.get(0));
    }
    
    @Test
    public void testCase2_addPetAlreadyInHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse(result);
        
        // Post-Condition: HomeC remains unchanged
        assertEquals(0, homeC.getPetCount());
        assertEquals(1, homeB.getPetCount());
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
        List<Pet> pets = homeE.getPets();
        assertTrue(pets.contains(bella));
        assertTrue(pets.contains(lucy));
    }
    
    @Test
    public void testCase5_addPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        // nullNamedDog already has null name by default
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse(result);
    }
}