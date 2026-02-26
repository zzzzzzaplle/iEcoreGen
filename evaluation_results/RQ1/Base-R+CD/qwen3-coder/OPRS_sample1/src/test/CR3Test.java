import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private Author author;
    
    @Before
    public void setUp() {
        author = new Author();
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author authorA001 = new Author();
        
        // Create 5 papers and submit them to author A001
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorA001.submitPaper(paper);
        }
        
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
        
        Paper paperP24 = new Paper();
        paperP24.setTitle("P24");
        authorA003.submitPaper(paperP24);
        
        // Expected Output: 1
        assertEquals(1, authorA003.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        // Create Papers P25-P27 linked to U1
        User userU1 = new User();
        userU1.setName("U1");
        
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        
        userU1.addRole(authorRole);
        userU1.addRole(reviewerRole);
        
        // Submit 3 papers to the author role
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorRole.submitPaper(paper);
        }
        
        // Expected Output: 3 (count papers for the Author role)
        assertEquals(3, authorRole.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 and Paper P28 (A005)
        // Create Author A006 and Paper P29 (A006)
        Author authorA005 = new Author();
        Author authorA006 = new Author();
        
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.submitPaper(paperP28);
        
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.submitPaper(paperP29);
        
        // Expected Output: 1 (only count papers belonging to A005)
        assertEquals(1, authorA005.countSubmittedPapers());
        // Verify that authorA006 has 1 paper (not part of the test spec but for completeness)
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}