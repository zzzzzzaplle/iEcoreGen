import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Create Author A001
        Author author = new Author();
        
        // Create Papers P19-P23 linked to A001
        Paper paper19 = new Paper("P19", PaperType.RESEARCH);
        Paper paper20 = new Paper("P20", PaperType.RESEARCH);
        Paper paper21 = new Paper("P21", PaperType.RESEARCH);
        Paper paper22 = new Paper("P22", PaperType.RESEARCH);
        Paper paper23 = new Paper("P23", PaperType.RESEARCH);
        
        // Submit all papers to the author
        author.submitPaper(paper19);
        author.submitPaper(paper20);
        author.submitPaper(paper21);
        author.submitPaper(paper22);
        author.submitPaper(paper23);
        
        // Expected Output: 5
        assertEquals(5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Create Author A002 (no papers)
        Author author = new Author();
        
        // Expected Output: 0
        assertEquals(0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Create Author A003
        Author author = new Author();
        
        // Create Paper P24 linked to A003
        Paper paper24 = new Paper("P24", PaperType.RESEARCH);
        author.submitPaper(paper24);
        
        // Expected Output: 1
        assertEquals(1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Create User U1 with Author and Reviewer roles
        User user = new User("U1");
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        user.addRole(authorRole);
        user.addRole(reviewerRole);
        
        // Create Papers P25-P27 linked to U1 (specifically to the Author role)
        Paper paper25 = new Paper("P25", PaperType.RESEARCH);
        Paper paper26 = new Paper("P26", PaperType.RESEARCH);
        Paper paper27 = new Paper("P27", PaperType.RESEARCH);
        
        authorRole.submitPaper(paper25);
        authorRole.submitPaper(paper26);
        authorRole.submitPaper(paper27);
        
        // Expected Output: 3 (from the Author role)
        assertEquals(3, authorRole.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Create Author A005
        Author authorA005 = new Author();
        
        // Create Paper P28 (A005)
        Paper paper28 = new Paper("P28", PaperType.RESEARCH);
        authorA005.submitPaper(paper28);
        
        // Create Author A006
        Author authorA006 = new Author();
        
        // Create Paper P29 (A006)
        Paper paper29 = new Paper("P29", PaperType.RESEARCH);
        authorA006.submitPaper(paper29);
        
        // Expected Output: 1 (only paper P28 belongs to A005)
        assertEquals(1, authorA005.countSubmittedPapers());
    }
}