import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Before
    public void setUp() {
        // Clear the static list of authors before each test to ensure isolation
        Author.getAllAuthors().clear();
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author authorA001 = new Author();
        
        // Create 5 papers and submit them
        Paper p19 = new Paper("P19", PaperType.RESEARCH);
        Paper p20 = new Paper("P20", PaperType.RESEARCH);
        Paper p21 = new Paper("P21", PaperType.EXPERIENCE);
        Paper p22 = new Paper("P22", PaperType.RESEARCH);
        Paper p23 = new Paper("P23", PaperType.EXPERIENCE);
        
        authorA001.submitPaper(p19);
        authorA001.submitPaper(p20);
        authorA001.submitPaper(p21);
        authorA001.submitPaper(p22);
        authorA001.submitPaper(p23);
        
        // Expected Output: 5
        assertEquals(5, authorA001.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        Author authorA002 = new Author();
        
        // Expected Output: 0
        assertEquals(0, authorA002.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author authorA003 = new Author();
        Paper p24 = new Paper("P24", PaperType.RESEARCH);
        
        authorA003.submitPaper(p24);
        
        // Expected Output: 1
        assertEquals(1, authorA003.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles, and Papers P25-P27 linked to U1
        
        // Create user and add roles
        User userU1 = new User("U1");
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        userU1.addRole(authorRole);
        userU1.addRole(reviewerRole);
        
        // Create papers and submit them through the author role
        Paper p25 = new Paper("P25", PaperType.RESEARCH);
        Paper p26 = new Paper("P26", PaperType.EXPERIENCE);
        Paper p27 = new Paper("P27", PaperType.RESEARCH);
        
        authorRole.submitPaper(p25);
        authorRole.submitPaper(p26);
        authorRole.submitPaper(p27);
        
        // Expected Output: 3 (count papers submitted by the author role)
        assertEquals(3, authorRole.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 with Paper P28, and Author A006 with Paper P29
        
        Author authorA005 = new Author();
        Author authorA006 = new Author();
        
        Paper p28 = new Paper("P28", PaperType.RESEARCH);
        Paper p29 = new Paper("P29", PaperType.EXPERIENCE);
        
        authorA005.submitPaper(p28);
        authorA006.submitPaper(p29);
        
        // Expected Output: 1 (only count papers belonging to A005)
        assertEquals(1, authorA005.countSubmittedPapers());
    }
}