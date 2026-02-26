package edu.conference.conference3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.conference.Author;
import edu.conference.Paper;
import edu.conference.User;
import edu.conference.ConferenceFactory;
import edu.conference.ConferencePackage;
import org.eclipse.emf.common.util.EList;

public class CR3Test {
    
    private ConferenceFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the conference factory using Ecore factory pattern
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
        assertEquals("Author with 5 papers should return count 5", 5, result);
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
        assertEquals("New author with 0 papers should return count 0", 0, result);
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
        assertEquals("Author with 1 paper should return count 1", 1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Input: Count papers for Author A004
        // Setup: Create User U1 with Author and Reviewer roles and Papers P25-P27 linked to U1
        
        // Create user using factory pattern
        User user = factory.createUser();
        user.setName("U1");
        
        // Create author role and add to user
        Author author = factory.createAuthor();
        user.getRoles().add(author);
        
        // Create reviewer role and add to user
        edu.conference.Reviewer reviewer = factory.createReviewer();
        user.getRoles().add(reviewer);
        
        // Create 3 papers and link them to the author role
        for (int i = 25; i <= 27; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("P" + i);
            author.getPapers().add(paper);
        }
        
        // Expected Output: 3
        int result = author.countSubmittedPapers();
        assertEquals("Multi-role user with author role having 3 papers should return count 3", 3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Input: Count papers for Author A005
        // Setup: Create Author A005 with Paper P28, and Author A006 with Paper P29
        
        // Create first author and paper
        Author authorA005 = factory.createAuthor();
        Paper paperP28 = factory.createPaper();
        paperP28.setTitle("P28");
        authorA005.getPapers().add(paperP28);
        
        // Create second author and paper
        Author authorA006 = factory.createAuthor();
        Paper paperP29 = factory.createPaper();
        paperP29.setTitle("P29");
        authorA006.getPapers().add(paperP29);
        
        // Expected Output: 1 (only P28 belongs to A005)
        int result = authorA005.countSubmittedPapers();
        assertEquals("Author A005 should have exactly 1 paper", 1, result);
        
        // Verify that papers are properly separated between authors
        assertEquals("Author A005 should have paper P28", "P28", authorA005.getPapers().get(0).getTitle());
        assertEquals("Author A006 should have paper P29", "P29", authorA006.getPapers().get(0).getTitle());
    }
}