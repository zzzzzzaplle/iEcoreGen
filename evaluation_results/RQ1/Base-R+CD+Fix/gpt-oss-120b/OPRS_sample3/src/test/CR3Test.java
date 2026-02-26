import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Author authorA001;
    private Author authorA002;
    private Author authorA003;
    private Author authorA004;
    private Author authorA005;
    private Author authorA006;
    
    @Before
    public void setUp() {
        // Clear all authors from previous tests
        List<Author> allAuthors = Author.getAllAuthors();
        if (!allAuthors.isEmpty()) {
            // Create a new list to avoid ConcurrentModificationException
            List<Author> authorsToRemove = new ArrayList<>(allAuthors);
            for (Author author : authorsToRemove) {
                Author.getAllAuthors().remove(author);
            }
        }
        
        // Initialize authors for test cases
        authorA001 = new Author();
        authorA002 = new Author();
        authorA003 = new Author();
        authorA004 = new Author();
        authorA005 = new Author();
        authorA006 = new Author();
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorA001.submitPaper(paper);
        }
        
        // Input: Count papers for Author A001
        int result = authorA001.countSubmittedPapers();
        
        // Expected Output: 5
        assertEquals("Author with 5 papers should return 5", 5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 (no papers) - already created in setUp
        
        // Input: Count papers for Author A002
        int result = authorA002.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals("New author with 0 papers should return 0", 0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 and Paper P24 linked to A003
        Paper paperP24 = new Paper();
        paperP24.setTitle("P24");
        authorA003.submitPaper(paperP24);
        
        // Input: Count papers for Author A003
        int result = authorA003.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals("Author with 1 paper should return 1", 1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles
        User userU1 = new User();
        userU1.setName("U1");
        
        // Add Author role to the user
        Author authorRole = authorA004;
        userU1.addRole(authorRole);
        
        // Add Reviewer role to the user
        Reviewer reviewerRole = new Reviewer();
        userU1.addRole(reviewerRole);
        
        // Create Papers P25-P27 linked to U1 (through the author role)
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorRole.submitPaper(paper);
        }
        
        // Input: Count papers for Author A004 (the author role attached to U1)
        int result = authorRole.countSubmittedPapers();
        
        // Expected Output: 3
        assertEquals("Multi-role user with 3 papers as author should return 3", 3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 and Paper P28 (A005)
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.submitPaper(paperP28);
        
        // Setup: Create Author A006 and Paper P29 (A006)
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.submitPaper(paperP29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals("Author A005 should have only 1 paper (P28)", 1, result);
        
        // Additional verification: Ensure author A006 has 1 paper
        int resultA006 = authorA006.countSubmittedPapers();
        assertEquals("Author A006 should have only 1 paper (P29)", 1, resultA006);
    }
}