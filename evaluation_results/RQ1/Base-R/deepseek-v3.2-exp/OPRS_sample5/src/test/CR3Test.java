import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Create Author A001
        Author author = new Author();
        author.setName("A001");
        
        // Create Papers P19-P23 linked to A001
        List<Paper> papers = new ArrayList<>();
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            paper.setAuthor(author);
            papers.add(paper);
        }
        author.setSubmittedPapers(papers);
        
        // Count papers for Author A001
        int result = author.countTotalPapers();
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Create Author A002 (no papers)
        Author author = new Author();
        author.setName("A002");
        
        // Count papers for Author A002
        int result = author.countTotalPapers();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Create Author A003
        Author author = new Author();
        author.setName("A003");
        
        // Create Paper P24 linked to A003
        List<Paper> papers = new ArrayList<>();
        Paper paper = new Paper();
        paper.setTitle("P24");
        paper.setAuthor(author);
        papers.add(paper);
        author.setSubmittedPapers(papers);
        
        // Count papers for Author A003
        int result = author.countTotalPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUser() {
        // Create User U1 with Author role
        Author author = new Author();
        author.setName("U1");
        
        // Also create Reviewer role (though not used in counting papers)
        Reviewer reviewer = new Reviewer();
        reviewer.setName("U1");
        
        // Create Papers P25-P27 linked to U1 (as author)
        List<Paper> papers = new ArrayList<>();
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            paper.setAuthor(author);
            papers.add(paper);
        }
        author.setSubmittedPapers(papers);
        
        // Count papers for Author A004 (U1 as author)
        int result = author.countTotalPapers();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Create Author A005
        Author authorA005 = new Author();
        authorA005.setName("A005");
        
        // Create Author A006
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        // Create Papers P28 (A005)
        List<Paper> papersA005 = new ArrayList<>();
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        paperP28.setAuthor(authorA005);
        papersA005.add(paperP28);
        authorA005.setSubmittedPapers(papersA005);
        
        // Create Papers P29 (A006)
        List<Paper> papersA006 = new ArrayList<>();
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        paperP29.setAuthor(authorA006);
        papersA006.add(paperP29);
        authorA006.setSubmittedPapers(papersA006);
        
        // Count papers for Author A005
        int result = authorA005.countTotalPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}