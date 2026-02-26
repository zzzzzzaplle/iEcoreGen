import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 with 5 papers
        Author author = new Author();
        List<Paper> papers = new ArrayList<>();
        
        // Create 5 papers and add to author
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
        }
        author.setPapers(papers);
        
        // Verify the count of submitted papers
        assertEquals("Author with 5 papers should return count 5", 5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 with no papers
        Author author = new Author();
        
        // Verify the count of submitted papers is 0
        assertEquals("New author with no papers should return count 0", 0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 with 1 paper
        Author author = new Author();
        Paper paper = new Paper();
        paper.setTitle("P24");
        
        List<Paper> papers = new ArrayList<>();
        papers.add(paper);
        author.setPapers(papers);
        
        // Verify the count of submitted papers
        assertEquals("Author with 1 paper should return count 1", 1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles
        User user = new User();
        user.setName("U1");
        
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        
        // Add papers to author role
        List<Paper> papers = new ArrayList<>();
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
        }
        authorRole.setPapers(papers);
        
        // Add roles to user
        List<UserRole> roles = new ArrayList<>();
        roles.add(authorRole);
        roles.add(reviewerRole);
        user.setRoles(roles);
        
        // Verify the count of submitted papers from author role
        assertEquals("Multi-role user with 3 papers should return count 3", 3, authorRole.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 with 1 paper and Author A006 with 1 paper
        Author authorA005 = new Author();
        Author authorA006 = new Author();
        
        // Create paper P28 for A005
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        List<Paper> papersA005 = new ArrayList<>();
        papersA005.add(paperP28);
        authorA005.setPapers(papersA005);
        
        // Create paper P29 for A006
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        List<Paper> papersA006 = new ArrayList<>();
        papersA006.add(paperP29);
        authorA006.setPapers(papersA006);
        
        // Verify A005 has exactly 1 paper
        assertEquals("Author A005 should have exactly 1 paper", 1, authorA005.countSubmittedPapers());
        
        // Additional verification that A006 also has 1 paper
        assertEquals("Author A006 should have exactly 1 paper", 1, authorA006.countSubmittedPapers());
    }
}