import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    @Test
    public void testCase1_authorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author();
        author.setName("A001");
        
        // Create 5 papers and link them to the author
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.addPaper(paper);
        }
        
        // Expected Output: 5
        assertEquals(5, author.countPapers());
    }
    
    @Test
    public void testCase2_newAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        author.setName("A002");
        
        // Expected Output: 0
        assertEquals(0, author.countPapers());
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        author.setName("A003");
        
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.addPaper(paper);
        
        // Expected Output: 1
        assertEquals(1, author.countPapers());
    }
    
    @Test
    public void testCase4_multiRoleUser() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        // Create Papers P25-P27 linked to U1
        Author author = new Author();
        author.setName("U1");
        
        // Create 3 papers and link them to the author
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.addPaper(paper);
        }
        
        // Expected Output: 3
        assertEquals(3, author.countPapers());
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 and Paper P28 (A005)
        // Create Author A006 and Paper P29 (A006)
        Author authorA005 = new Author();
        authorA005.setName("A005");
        
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.addPaper(paperP28);
        
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.addPaper(paperP29);
        
        // Expected Output for A005: 1
        assertEquals(1, authorA005.countPapers());
    }
}