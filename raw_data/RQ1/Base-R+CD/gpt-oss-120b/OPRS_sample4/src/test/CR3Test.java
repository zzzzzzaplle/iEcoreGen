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
        List<Paper> papers = new ArrayList<>();
        
        // Create 5 papers and add them to author
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper("P" + i, PaperType.RESEARCH);
            papers.add(paper);
        }
        author.setPapers(papers);
        
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
        List<Paper> papers = new ArrayList<>();
        
        Paper paper = new Paper("P24", PaperType.RESEARCH);
        papers.add(paper);
        author.setPapers(papers);
        
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
        
        // Add roles to user
        user.addRole(authorRole);
        user.addRole(reviewerRole);
        
        // Create papers and add to author role
        List<Paper> papers = new ArrayList<>();
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper("P" + i, PaperType.RESEARCH);
            papers.add(paper);
        }
        authorRole.setPapers(papers);
        
        // Expected Output: 3 (count from author role)
        assertEquals(3, authorRole.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 with Paper P28
        // Create Author A006 with Paper P29
        Author authorA005 = new Author();
        Author authorA006 = new Author();
        
        List<Paper> papersA005 = new ArrayList<>();
        List<Paper> papersA006 = new ArrayList<>();
        
        // Create paper for A005
        Paper paperP28 = new Paper("P28", PaperType.RESEARCH);
        papersA005.add(paperP28);
        authorA005.setPapers(papersA005);
        
        // Create paper for A006
        Paper paperP29 = new Paper("P29", PaperType.RESEARCH);
        papersA006.add(paperP29);
        authorA006.setPapers(papersA006);
        
        // Expected Output: 1 (only count papers belonging to A005)
        assertEquals(1, authorA005.countSubmittedPapers());
        // Verify A006 also has 1 paper
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}