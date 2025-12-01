import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_authorWith5Papers() {
        // Test Case 1: Author with 5 papers
        // Create Author A001
        User author = new User("A001");
        
        // Create Papers P19-P23 linked to A001
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper("P" + i, true);
            author.addSubmittedPaper(paper);
        }
        
        // Expected Output: 5
        assertEquals(5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_newAuthorWith0Papers() {
        // Test Case 2: New author with 0 papers
        // Create Author A002 (no papers)
        User author = new User("A002");
        
        // Expected Output: 0
        assertEquals(0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Test Case 3: Single-paper author
        // Create Author A003
        User author = new User("A003");
        
        // Create Paper P24 linked to A003
        Paper paper = new Paper("P24", true);
        author.addSubmittedPaper(paper);
        
        // Expected Output: 1
        assertEquals(1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_multiRoleUserAuthorReviewer() {
        // Test Case 4: Multi-role user (author+reviewer)
        // Create User U1 with Author and Reviewer roles (using Reviewer class since it extends User)
        Reviewer user = new Reviewer("U1");
        
        // Create Papers P25-P27 linked to U1
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper("P" + i, true);
            user.addSubmittedPaper(paper);
        }
        
        // Expected Output: 3
        assertEquals(3, user.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Test Case 5: Paper ownership validation
        // Create Author A005
        User authorA005 = new User("A005");
        
        // Create Papers P28 (A005)
        Paper paperP28 = new Paper("P28", true);
        authorA005.addSubmittedPaper(paperP28);
        
        // Create Author A006
        User authorA006 = new User("A006");
        
        // Create Papers P29 (A006)
        Paper paperP29 = new Paper("P29", true);
        authorA006.addSubmittedPaper(paperP29);
        
        // Expected Output: 1 (only papers belonging to A005 should be counted)
        assertEquals(1, authorA005.countSubmittedPapers());
    }
}