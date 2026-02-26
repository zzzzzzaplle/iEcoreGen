import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Author author;
    
    @Before
    public void setUp() {
        // Reset author before each test
        author = null;
    }
    
    @Test
    public void testCase1_authorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        author = new Author();
        author.setName("A001");
        
        // Create 5 papers and add them to author
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.addSubmittedPaper(paper);
        }
        
        // Expected Output: 5
        assertEquals(5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_newAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        author = new Author();
        author.setName("A002");
        
        // Expected Output: 0
        assertEquals(0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        author = new Author();
        author.setName("A003");
        
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.addSubmittedPaper(paper);
        
        // Expected Output: 1
        assertEquals(1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_multiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles and Papers P25-P27 linked to U1
        
        // Since Java doesn't support multiple inheritance, we'll use Author role for counting papers
        Author author = new Author();
        author.setName("U1");
        
        // Create 3 papers and add them to author
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.addSubmittedPaper(paper);
        }
        
        // Also demonstrate reviewer functionality (though not directly tested)
        Review review = new Review();
        review.setGrade("Accept");
        author.addSubmittedReview(review);
        
        // Expected Output: 3 (only papers count, reviews don't affect paper count)
        assertEquals(3, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 with Paper P28, and Author A006 with Paper P29
        
        // Create Author A005 with Paper P28
        Author authorA005 = new Author();
        authorA005.setName("A005");
        
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.addSubmittedPaper(paperP28);
        
        // Create Author A006 with Paper P29 (should not be counted for A005)
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.addSubmittedPaper(paperP29);
        
        // Test A005's paper count - Expected Output: 1
        assertEquals(1, authorA005.countSubmittedPapers());
        
        // Verify A006 has 1 paper (additional verification)
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}