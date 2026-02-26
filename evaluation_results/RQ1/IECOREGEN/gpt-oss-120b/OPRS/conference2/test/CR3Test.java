package edu.conference.conference2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.conference.ConferenceFactory;
import edu.conference.ConferencePackage;
import edu.conference.Author;
import edu.conference.Paper;
import edu.conference.User;
import edu.conference.Reviewer;
import org.eclipse.emf.common.util.EList;

public class CR3Test {
    
    private ConferenceFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory for object creation
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Input: Count papers for Author A001
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        
        // Create author using factory pattern
        Author author = factory.createAuthor();
        
        // Create 5 papers and link them to the author
        for (int i = 19; i <= 23; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("P" + i);
            author.getPapers().add(paper);
        }
        
        // Expected Output: 5
        int result = author.countSubmittedPapers();
        assertEquals("Author with 5 papers should return count of 5", 5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Input: Count papers for Author A002
        // Setup: Create Author A002 (no papers)
        
        // Create author using factory pattern
        Author author = factory.createAuthor();
        
        // Expected Output: 0
        int result = author.countSubmittedPapers();
        assertEquals("New author with no papers should return count of 0", 0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Input: Count papers for Author A003
        // Setup: Create Author A003 and Paper P24 linked to A003
        
        // Create author using factory pattern
        Author author = factory.createAuthor();
        
        // Create single paper and link to author
        Paper paper = factory.createPaper();
        paper.setTitle("P24");
        author.getPapers().add(paper);
        
        // Expected Output: 1
        int result = author.countSubmittedPapers();
        assertEquals("Author with 1 paper should return count of 1", 1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Input: Count papers for Author A004
        // Setup: Create User U1 with Author and Reviewer roles, Papers P25-P27 linked to U1
        
        // Create user using factory pattern
        User user = factory.createUser();
        user.setName("U1");
        
        // Create author role and add to user
        Author author = factory.createAuthor();
        user.getRoles().add(author);
        
        // Create reviewer role and add to user
        Reviewer reviewer = factory.createReviewer();
        user.getRoles().add(reviewer);
        
        // Create 3 papers and link them to the author role
        for (int i = 25; i <= 27; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("P" + i);
            author.getPapers().add(paper);
        }
        
        // Expected Output: 3
        int result = author.countSubmittedPapers();
        assertEquals("Author role in multi-role user with 3 papers should return count of 3", 3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Input: Count papers for Author A005
        // Setup: Create Author A005 with Paper P28, Author A006 with Paper P29
        
        // Create first author and paper
        Author authorA005 = factory.createAuthor();
        Paper paperP28 = factory.createPaper();
        paperP28.setTitle("P28");
        authorA005.getPapers().add(paperP28);
        
        // Create second author and paper (should not be counted for authorA005)
        Author authorA006 = factory.createAuthor();
        Paper paperP29 = factory.createPaper();
        paperP29.setTitle("P29");
        authorA006.getPapers().add(paperP29);
        
        // Expected Output: 1 (only paper P28 belongs to authorA005)
        int result = authorA005.countSubmittedPapers();
        assertEquals("Author A005 should only count their own paper (P28), not A006's paper (P29)", 1, result);
        
        // Verify authorA006 has their own paper count
        int resultA006 = authorA006.countSubmittedPapers();
        assertEquals("Author A006 should have count of 1 for their paper P29", 1, resultA006);
    }
}