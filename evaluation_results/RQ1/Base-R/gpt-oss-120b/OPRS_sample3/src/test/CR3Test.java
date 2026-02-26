import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Input: Count papers for Author A001
        // Setup:  
        // 1. Create Author A001
        // 2. Create Papers P19-P23 linked to A001
        // Expected Output: 5
        
        // Create Author A001
        Author author = new Author();
        author.setName("A001");
        
        // Create Papers P19-P23 linked to A001
        Paper paper19 = new Paper();
        paper19.setTitle("P19");
        author.submitPaper(paper19);
        
        Paper paper20 = new Paper();
        paper20.setTitle("P20");
        author.submitPaper(paper20);
        
        Paper paper21 = new Paper();
        paper21.setTitle("P21");
        author.submitPaper(paper21);
        
        Paper paper22 = new Paper();
        paper22.setTitle("P22");
        author.submitPaper(paper22);
        
        Paper paper23 = new Paper();
        paper23.setTitle("P23");
        author.submitPaper(paper23);
        
        // Verify the count
        assertEquals("Author with 5 papers should return count 5", 5, author.getPaperCount());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Input: Count papers for Author A002
        // Setup:  
        // 1. Create Author A002 (no papers)
        // Expected Output: 0
        
        // Create Author A002 with no papers
        Author author = new Author();
        author.setName("A002");
        
        // Verify the count
        assertEquals("New author with 0 papers should return count 0", 0, author.getPaperCount());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Input: Count papers for Author A003
        // Setup:  
        // 1. Create Author A003
        // 2. Create Paper P24 linked to A003
        // Expected Output: 1
        
        // Create Author A003
        Author author = new Author();
        author.setName("A003");
        
        // Create Paper P24 linked to A003
        Paper paper24 = new Paper();
        paper24.setTitle("P24");
        author.submitPaper(paper24);
        
        // Verify the count
        assertEquals("Single-paper author should return count 1", 1, author.getPaperCount());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Input: Count papers for Author A004
        // Setup:  
        // 1. Create User U1 with Author and Reviewer roles
        // 2. Create Papers P25-P27 linked to U1
        // Expected Output: 3
        
        // Create User U1 with Author role
        Author author = new Author();
        author.setName("U1");
        
        // Create Reviewer role for the same user
        Reviewer reviewer = new Reviewer();
        reviewer.setName("U1");
        
        // Create Papers P25-P27 linked to U1 (as Author)
        Paper paper25 = new Paper();
        paper25.setTitle("P25");
        author.submitPaper(paper25);
        
        Paper paper26 = new Paper();
        paper26.setTitle("P26");
        author.submitPaper(paper26);
        
        Paper paper27 = new Paper();
        paper27.setTitle("P27");
        author.submitPaper(paper27);
        
        // Verify the count from author perspective
        assertEquals("Multi-role user as author should return count 3", 3, author.getPaperCount());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Input: Count papers for Author A005
        // Setup:  
        // 1. Create Author A005
        // 2. Create Papers P28 (A005)
        // 3. Create Author A006
        // 4. Create Papers P29 (A006)
        // Expected Output: 1
        
        // Create Author A005
        Author authorA005 = new Author();
        authorA005.setName("A005");
        
        // Create Paper P28 linked to A005
        Paper paper28 = new Paper();
        paper28.setTitle("P28");
        authorA005.submitPaper(paper28);
        
        // Create Author A006
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        // Create Paper P29 linked to A006
        Paper paper29 = new Paper();
        paper29.setTitle("P29");
        authorA006.submitPaper(paper29);
        
        // Verify the count for Author A005 (should only count its own papers)
        assertEquals("Author A005 should only count its own paper", 1, authorA005.getPaperCount());
    }
}