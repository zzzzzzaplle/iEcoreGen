import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author();
        
        // Create 5 papers and submit them
        Paper p19 = new Paper("P19", PaperType.RESEARCH);
        Paper p20 = new Paper("P20", PaperType.RESEARCH);
        Paper p21 = new Paper("P21", PaperType.EXPERIENCE);
        Paper p22 = new Paper("P22", PaperType.RESEARCH);
        Paper p23 = new Paper("P23", PaperType.EXPERIENCE);
        
        author.submitPaper(p19);
        author.submitPaper(p20);
        author.submitPaper(p21);
        author.submitPaper(p22);
        author.submitPaper(p23);
        
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
        Paper p24 = new Paper("P24", PaperType.RESEARCH);
        
        author.submitPaper(p24);
        
        // Expected Output: 1
        assertEquals(1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        // Create Papers P25-P27 linked to U1
        User user = new User("U1");
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        
        // Add both roles to the user
        List<UserRole> roles = new ArrayList<>();
        roles.add(authorRole);
        roles.add(reviewerRole);
        user.setRoles(roles);
        
        // Create and submit papers using the author role
        Paper p25 = new Paper("P25", PaperType.RESEARCH);
        Paper p26 = new Paper("P26", PaperType.EXPERIENCE);
        Paper p27 = new Paper("P27", PaperType.RESEARCH);
        
        authorRole.submitPaper(p25);
        authorRole.submitPaper(p26);
        authorRole.submitPaper(p27);
        
        // Expected Output: 3 (count from the author role)
        assertEquals(3, authorRole.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 and Paper P28 (A005)
        // Create Author A006 and Paper P29 (A006)
        Author authorA005 = new Author();
        Author authorA006 = new Author();
        
        Paper p28 = new Paper("P28", PaperType.RESEARCH);
        Paper p29 = new Paper("P29", PaperType.EXPERIENCE);
        
        authorA005.submitPaper(p28);
        authorA006.submitPaper(p29);
        
        // Expected Output: 1 (only P28 belongs to A005)
        assertEquals(1, authorA005.countSubmittedPapers());
        // Verify A006 has 1 paper as well
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}