import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author();
        
        // Create 5 papers
        Paper paper19 = new Paper("Paper 19", PaperType.RESEARCH);
        Paper paper20 = new Paper("Paper 20", PaperType.RESEARCH);
        Paper paper21 = new Paper("Paper 21", PaperType.EXPERIENCE);
        Paper paper22 = new Paper("Paper 22", PaperType.RESEARCH);
        Paper paper23 = new Paper("Paper 23", PaperType.EXPERIENCE);
        
        // Submit all 5 papers to the author
        author.submitPaper(paper19);
        author.submitPaper(paper20);
        author.submitPaper(paper21);
        author.submitPaper(paper22);
        author.submitPaper(paper23);
        
        // Expected Output: 5
        assertEquals(5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        
        // Expected Output: 0
        assertEquals(0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        Paper paper24 = new Paper("Paper 24", PaperType.RESEARCH);
        
        author.submitPaper(paper24);
        
        // Expected Output: 1
        assertEquals(1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        User user = new User("U1");
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        
        // Add both roles to the user
        user.addRole(authorRole);
        user.addRole(reviewerRole);
        
        // Create Papers P25-P27 linked to U1 (via author role)
        Paper paper25 = new Paper("Paper 25", PaperType.RESEARCH);
        Paper paper26 = new Paper("Paper 26", PaperType.EXPERIENCE);
        Paper paper27 = new Paper("Paper 27", PaperType.RESEARCH);
        
        authorRole.submitPaper(paper25);
        authorRole.submitPaper(paper26);
        authorRole.submitPaper(paper27);
        
        // Expected Output: 3 (count from author role only)
        assertEquals(3, authorRole.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 and Paper P28 (A005)
        Author authorA005 = new Author();
        Paper paper28 = new Paper("Paper 28", PaperType.RESEARCH);
        authorA005.submitPaper(paper28);
        
        // Create Author A006 and Paper P29 (A006)
        Author authorA006 = new Author();
        Paper paper29 = new Paper("Paper 29", PaperType.EXPERIENCE);
        authorA006.submitPaper(paper29);
        
        // Expected Output: 1 (only P28 belongs to A005)
        assertEquals(1, authorA005.countSubmittedPapers());
        
        // Verify that A006 has exactly 1 paper (P29)
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}