import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private ReviewSystem reviewSystem;
    
    @Before
    public void setUp() {
        reviewSystem = new ReviewSystem();
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        User authorA001 = new User();
        authorA001.setName("A001");
        
        // Create 5 papers and link them to author A001
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorA001.addSubmittedPaper(paper);
        }
        
        // Count papers for Author A001
        int result = reviewSystem.countSubmittedPapers(authorA001);
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        User authorA002 = new User();
        authorA002.setName("A002");
        
        // Count papers for Author A002
        int result = reviewSystem.countSubmittedPapers(authorA002);
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        User authorA003 = new User();
        authorA003.setName("A003");
        
        Paper paperP24 = new Paper();
        paperP24.setTitle("P24");
        authorA003.addSubmittedPaper(paperP24);
        
        // Count papers for Author A003
        int result = reviewSystem.countSubmittedPapers(authorA003);
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles and Papers P25-P27 linked to U1
        User userU1 = new User();
        userU1.setName("U1");
        
        // Create 3 papers and link them to user U1 (author role)
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            userU1.addSubmittedPaper(paper);
        }
        
        // Add some reviews to demonstrate reviewer role (should not affect paper count)
        Review review1 = new Review();
        Review review2 = new Review();
        userU1.addReview(review1);
        userU1.addReview(review2);
        
        // Count papers for Author A004 (using userU1 as author)
        int result = reviewSystem.countSubmittedPapers(userU1);
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 and Paper P28, Create Author A006 and Paper P29
        User authorA005 = new User();
        authorA005.setName("A005");
        
        User authorA006 = new User();
        authorA006.setName("A006");
        
        // Create Paper P28 linked to A005
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.addSubmittedPaper(paperP28);
        
        // Create Paper P29 linked to A006
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.addSubmittedPaper(paperP29);
        
        // Count papers for Author A005
        int result = reviewSystem.countSubmittedPapers(authorA005);
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}