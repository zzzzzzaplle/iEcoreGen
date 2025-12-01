import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private ReviewSystemService reviewSystemService;
    
    @Before
    public void setUp() {
        // Initialize the service before each test
        reviewSystemService = new ReviewSystemService();
    }
    
    @Test
    public void testCase1_AuthorWithFivePapers() {
        // Test Case 1: "Author with 5 papers"
        // Create Author A001
        User authorA001 = new User();
        authorA001.setName("A001");
        
        // Create Papers P19-P23 linked to A001
        List<Paper> papers = new ArrayList<>();
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            paper.setAuthor(authorA001);
            papers.add(paper);
        }
        authorA001.setSubmittedPapers(papers);
        
        // Count papers for Author A001
        int result = reviewSystemService.countSubmittedPapers(authorA001);
        
        // Expected Output: 5
        assertEquals("Author with 5 papers should return 5", 5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWithZeroPapers() {
        // Test Case 2: "New author with 0 papers"
        // Create Author A002 (no papers)
        User authorA002 = new User();
        authorA002.setName("A002");
        authorA002.setSubmittedPapers(new ArrayList<Paper>()); // Explicitly set empty list
        
        // Count papers for Author A002
        int result = reviewSystemService.countSubmittedPapers(authorA002);
        
        // Expected Output: 0
        assertEquals("New author with 0 papers should return 0", 0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Create Author A003
        User authorA003 = new User();
        authorA003.setName("A003");
        
        // Create Paper P24 linked to A003
        List<Paper> papers = new ArrayList<>();
        Paper paper24 = new Paper();
        paper24.setTitle("P24");
        paper24.setAuthor(authorA003);
        papers.add(paper24);
        authorA003.setSubmittedPapers(papers);
        
        // Count papers for Author A003
        int result = reviewSystemService.countSubmittedPapers(authorA003);
        
        // Expected Output: 1
        assertEquals("Single-paper author should return 1", 1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Create User U1 with Author and Reviewer roles
        User userU1 = new User();
        userU1.setName("U1");
        
        // Create Papers P25-P27 linked to U1
        List<Paper> papers = new ArrayList<>();
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            paper.setAuthor(userU1);
            papers.add(paper);
        }
        userU1.setSubmittedPapers(papers);
        
        // Also add some reviews to simulate reviewer role (should not affect paper count)
        List<Review> reviews = new ArrayList<>();
        Review review = new Review();
        reviews.add(review);
        userU1.setReviews(reviews);
        
        // Count papers for Author A004 (which is user U1)
        int result = reviewSystemService.countSubmittedPapers(userU1);
        
        // Expected Output: 3
        assertEquals("Multi-role user with 3 papers should return 3", 3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Create Author A005
        User authorA005 = new User();
        authorA005.setName("A005");
        
        // Create Papers P28 (A005)
        List<Paper> papersA005 = new ArrayList<>();
        Paper paper28 = new Paper();
        paper28.setTitle("P28");
        paper28.setAuthor(authorA005);
        papersA005.add(paper28);
        authorA005.setSubmittedPapers(papersA005);
        
        // Create Author A006
        User authorA006 = new User();
        authorA006.setName("A006");
        
        // Create Papers P29 (A006)
        List<Paper> papersA006 = new ArrayList<>();
        Paper paper29 = new Paper();
        paper29.setTitle("P29");
        paper29.setAuthor(authorA006);
        papersA006.add(paper29);
        authorA006.setSubmittedPapers(papersA006);
        
        // Count papers for Author A005
        int result = reviewSystemService.countSubmittedPapers(authorA005);
        
        // Expected Output: 1
        assertEquals("Author A005 should have exactly 1 paper", 1, result);
    }
}