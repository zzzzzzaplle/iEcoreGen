import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_authorWithFivePapers() {
        // Setup: Create Author A001 with 5 papers
        Author author = new Author();
        author.setName("A001");
        
        // Create 5 papers and add to author
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.addPaper(paper);
        }
        
        // Test: Count papers for Author A001
        int result = author.countPapers();
        
        // Verify: Expected output is 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_newAuthorWithZeroPapers() {
        // Setup: Create Author A002 with no papers
        Author author = new Author();
        author.setName("A002");
        
        // Test: Count papers for Author A002
        int result = author.countPapers();
        
        // Verify: Expected output is 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Setup: Create Author A003 with 1 paper
        Author author = new Author();
        author.setName("A003");
        
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.addPaper(paper);
        
        // Test: Count papers for Author A003
        int result = author.countPapers();
        
        // Verify: Expected output is 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_multiRoleUser() {
        // Setup: Create User with Author role (Author A004) with 3 papers
        Author author = new Author();
        author.setName("A004");
        
        // Create 3 papers and add to author
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.addPaper(paper);
        }
        
        // Test: Count papers for Author A004
        int result = author.countPapers();
        
        // Verify: Expected output is 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Setup: Create Author A005 with 1 paper
        Author authorA005 = new Author();
        authorA005.setName("A005");
        
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.addPaper(paperP28);
        
        // Create Author A006 with 1 paper (should not be counted for A005)
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.addPaper(paperP29);
        
        // Test: Count papers for Author A005
        int result = authorA005.countPapers();
        
        // Verify: Expected output is 1 (only P28 belongs to A005)
        assertEquals(1, result);
        
        // Additional verification: A006 should have 1 paper
        assertEquals(1, authorA006.countPapers());
    }
}