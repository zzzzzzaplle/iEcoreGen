package edu.conference.conference1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.conference.Author;
import edu.conference.Paper;
import edu.conference.User;
import edu.conference.UserRole;
import edu.conference.Reviewer;
import edu.conference.ConferenceFactory;
import edu.conference.ConferencePackage;
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
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        
        Author author = factory.createAuthor();
        
        // Create 5 papers and link them to the author
        for (int i = 19; i <= 23; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("P" + i);
            author.getPapers().add(paper);
        }
        
        // Expected Output: 5
        assertEquals(5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        
        Author author = factory.createAuthor();
        
        // Expected Output: 0
        assertEquals(0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        
        Author author = factory.createAuthor();
        Paper paper = factory.createPaper();
        paper.setTitle("P24");
        author.getPapers().add(paper);
        
        // Expected Output: 1
        assertEquals(1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        // Create Papers P25-P27 linked to U1
        
        User user = factory.createUser();
        user.setName("U1");
        
        // Create Author role
        Author author = factory.createAuthor();
        user.getRoles().add(author);
        
        // Create Reviewer role
        Reviewer reviewer = factory.createReviewer();
        user.getRoles().add(reviewer);
        
        // Create 3 papers and link them to the author role
        for (int i = 25; i <= 27; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("P" + i);
            author.getPapers().add(paper);
        }
        
        // Expected Output: 3 (count papers for the Author role)
        assertEquals(3, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: 
        // 1. Create Author A005 and Paper P28 (A005)
        // 2. Create Author A006 and Paper P29 (A006)
        
        Author authorA005 = factory.createAuthor();
        Paper paperP28 = factory.createPaper();
        paperP28.setTitle("P28");
        authorA005.getPapers().add(paperP28);
        
        Author authorA006 = factory.createAuthor();
        Paper paperP29 = factory.createPaper();
        paperP29.setTitle("P29");
        authorA006.getPapers().add(paperP29);
        
        // Expected Output: 1 (Author A005 should only have 1 paper)
        assertEquals(1, authorA005.countSubmittedPapers());
        
        // Verify Author A006 also has only 1 paper
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}