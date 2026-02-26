import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_authorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author authorA001 = new Author("A001");
        
        // Create 5 papers and link them to author A001
        Paper paperP19 = new Paper("Paper P19", true);
        Paper paperP20 = new Paper("Paper P20", false);
        Paper paperP21 = new Paper("Paper P21", true);
        Paper paperP22 = new Paper("Paper P22", false);
        Paper paperP23 = new Paper("Paper P23", true);
        
        authorA001.addSubmittedPaper(paperP19);
        authorA001.addSubmittedPaper(paperP20);
        authorA001.addSubmittedPaper(paperP21);
        authorA001.addSubmittedPaper(paperP22);
        authorA001.addSubmittedPaper(paperP23);
        
        // Input: Count papers for Author A001
        int result = authorA001.countTotalPapersSubmitted();
        
        // Expected Output: 5
        assertEquals("Author with 5 papers should return 5", 5, result);
    }
    
    @Test
    public void testCase2_newAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        Author authorA002 = new Author("A002");
        
        // Input: Count papers for Author A002
        int result = authorA002.countTotalPapersSubmitted();
        
        // Expected Output: 0
        assertEquals("New author with 0 papers should return 0", 0, result);
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author authorA003 = new Author("A003");
        Paper paperP24 = new Paper("Paper P24", true);
        
        authorA003.addSubmittedPaper(paperP24);
        
        // Input: Count papers for Author A003
        int result = authorA003.countTotalPapersSubmitted();
        
        // Expected Output: 1
        assertEquals("Author with 1 paper should return 1", 1, result);
    }
    
    @Test
    public void testCase4_multiRoleUser() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        // Note: Since Java doesn't support multiple inheritance, we'll create an Author
        // that can also act as a Reviewer (though the specification mentions multi-role)
        Author authorA004 = new Author("A004");
        
        // Create Papers P25-P27 linked to U1 (as Author)
        Paper paperP25 = new Paper("Paper P25", true);
        Paper paperP26 = new Paper("Paper P26", false);
        Paper paperP27 = new Paper("Paper P27", true);
        
        authorA004.addSubmittedPaper(paperP25);
        authorA004.addSubmittedPaper(paperP26);
        authorA004.addSubmittedPaper(paperP27);
        
        // Input: Count papers for Author A004
        int result = authorA004.countTotalPapersSubmitted();
        
        // Expected Output: 3
        assertEquals("Author with 3 papers should return 3", 3, result);
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 and Paper P28 (A005)
        Author authorA005 = new Author("A005");
        Paper paperP28 = new Paper("Paper P28", true);
        authorA005.addSubmittedPaper(paperP28);
        
        // Create Author A006 and Paper P29 (A006)
        Author authorA006 = new Author("A006");
        Paper paperP29 = new Paper("Paper P29", false);
        authorA006.addSubmittedPaper(paperP29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countTotalPapersSubmitted();
        
        // Expected Output: 1
        assertEquals("Author A005 should have exactly 1 paper", 1, result);
        
        // Additional verification that Author A006 has 1 paper (not part of the requirement but good practice)
        assertEquals("Author A006 should have exactly 1 paper", 1, authorA006.countTotalPapersSubmitted());
    }
}