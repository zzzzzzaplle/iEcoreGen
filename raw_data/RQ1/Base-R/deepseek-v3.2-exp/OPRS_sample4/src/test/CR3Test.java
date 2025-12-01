import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Author authorA001;
    private Author authorA002;
    private Author authorA003;
    private Author authorA004;
    private Author authorA005;
    private Author authorA006;
    
    @Before
    public void setUp() {
        // Initialize authors for test cases
        authorA001 = new Author();
        authorA001.setName("A001");
        
        authorA002 = new Author();
        authorA002.setName("A002");
        
        authorA003 = new Author();
        authorA003.setName("A003");
        
        authorA004 = new Author();
        authorA004.setName("A004");
        
        authorA005 = new Author();
        authorA005.setName("A005");
        
        authorA006 = new Author();
        authorA006.setName("A006");
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 with 5 papers (P19-P23)
        List<Paper> papers = new ArrayList<>();
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
        }
        authorA001.setSubmittedPapers(papers);
        
        // Input: Count papers for Author A001
        int result = authorA001.countTotalSubmittedPapers();
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 with no papers
        // (No papers need to be added since list is initialized empty)
        
        // Input: Count papers for Author A002
        int result = authorA002.countTotalSubmittedPapers();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 with 1 paper (P24)
        List<Paper> papers = new ArrayList<>();
        Paper paper = new Paper();
        paper.setTitle("P24");
        papers.add(paper);
        authorA003.setSubmittedPapers(papers);
        
        // Input: Count papers for Author A003
        int result = authorA003.countTotalSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles (Author A004 with 3 papers)
        List<Paper> papers = new ArrayList<>();
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
        }
        authorA004.setSubmittedPapers(papers);
        
        // Input: Count papers for Author A004
        int result = authorA004.countTotalSubmittedPapers();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: 
        // 1. Create Author A005 with Paper P28
        List<Paper> papersA005 = new ArrayList<>();
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        papersA005.add(paperP28);
        authorA005.setSubmittedPapers(papersA005);
        
        // 2. Create Author A006 with Paper P29
        List<Paper> papersA006 = new ArrayList<>();
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        papersA006.add(paperP29);
        authorA006.setSubmittedPapers(papersA006);
        
        // Input: Count papers for Author A005
        int result = authorA005.countTotalSubmittedPapers();
        
        // Expected Output: 1 (only P28 belongs to A005, P29 belongs to A006)
        assertEquals(1, result);
    }
}