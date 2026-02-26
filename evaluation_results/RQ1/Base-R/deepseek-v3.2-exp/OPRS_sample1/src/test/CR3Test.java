import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_authorWithFivePapers() {
        // Create Author A001
        User authorA001 = new User();
        authorA001.setName("A001");
        
        // Create Papers P19-P23 linked to A001
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            paper.setAuthor(authorA001);
            authorA001.getSubmittedPapers().add(paper);
        }
        
        // Count papers for Author A001
        int result = ReviewSystem.countPapersByAuthor(authorA001);
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_newAuthorWithZeroPapers() {
        // Create Author A002 (no papers)
        User authorA002 = new User();
        authorA002.setName("A002");
        
        // Count papers for Author A002
        int result = ReviewSystem.countPapersByAuthor(authorA002);
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Create Author A003
        User authorA003 = new User();
        authorA003.setName("A003");
        
        // Create Paper P24 linked to A003
        Paper paperP24 = new Paper();
        paperP24.setTitle("P24");
        paperP24.setAuthor(authorA003);
        authorA003.getSubmittedPapers().add(paperP24);
        
        // Count papers for Author A003
        int result = ReviewSystem.countPapersByAuthor(authorA003);
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_multiRoleUserAuthorReviewer() {
        // Create User U1 with Author and Reviewer roles
        User userU1 = new User();
        userU1.setName("U1");
        
        // Create Papers P25-P27 linked to U1
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            paper.setAuthor(userU1);
            userU1.getSubmittedPapers().add(paper);
        }
        
        // Count papers for Author A004 (which is U1)
        int result = ReviewSystem.countPapersByAuthor(userU1);
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Create Author A005
        User authorA005 = new User();
        authorA005.setName("A005");
        
        // Create Paper P28 (A005)
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        paperP28.setAuthor(authorA005);
        authorA005.getSubmittedPapers().add(paperP28);
        
        // Create Author A006
        User authorA006 = new User();
        authorA006.setName("A006");
        
        // Create Paper P29 (A006)
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        paperP29.setAuthor(authorA006);
        authorA006.getSubmittedPapers().add(paperP29);
        
        // Count papers for Author A005
        int result = ReviewSystem.countPapersByAuthor(authorA005);
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}