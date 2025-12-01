import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_authorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author();
        author.setName("A001");
        
        List<Paper> papers = new ArrayList<>();
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
        }
        author.setPapers(papers);
        
        // Input: Count papers for Author A001
        int result = author.countPapers();
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_newAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        author.setName("A002");
        
        // Input: Count papers for Author A002
        int result = author.countPapers();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        author.setName("A003");
        
        List<Paper> papers = new ArrayList<>();
        Paper paper = new Paper();
        paper.setTitle("P24");
        papers.add(paper);
        author.setPapers(papers);
        
        // Input: Count papers for Author A003
        int result = author.countPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_multiRoleUser() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        // Since Java doesn't support multiple inheritance, we'll test with Author role only
        // as specified in the test case requirement
        Author author = new Author();
        author.setName("U1");
        
        List<Paper> papers = new ArrayList<>();
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
        }
        author.setPapers(papers);
        
        // Input: Count papers for Author A004 (U1 in author role)
        int result = author.countPapers();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 with Paper P28, and Author A006 with Paper P29
        Author authorA005 = new Author();
        authorA005.setName("A005");
        
        List<Paper> papersA005 = new ArrayList<>();
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        papersA005.add(paperP28);
        authorA005.setPapers(papersA005);
        
        // Create separate author A006 with their own paper
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        List<Paper> papersA006 = new ArrayList<>();
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        papersA006.add(paperP29);
        authorA006.setPapers(papersA006);
        
        // Input: Count papers for Author A005
        int result = authorA005.countPapers();
        
        // Expected Output: 1 (only P28 should be counted for A005)
        assertEquals(1, result);
    }
}