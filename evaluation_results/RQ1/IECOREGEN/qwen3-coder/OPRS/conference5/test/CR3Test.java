package edu.conference.conference5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.conference.Author;
import edu.conference.Paper;
import edu.conference.User;
import edu.conference.Reviewer;
import edu.conference.ConferenceFactory;
import edu.conference.ConferencePackage;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

public class CR3Test {
    
    private ConferenceFactory factory;
    
    @Before
    public void setUp() {
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 with 5 papers
        Author author = factory.createAuthor();
        
        // Create 5 papers and link them to the author
        for (int i = 19; i <= 23; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("P" + i);
            author.getPapers().add(paper);
        }
        
        // Input: Count papers for Author A001
        int result = author.countSubmittedPapers();
        
        // Expected Output: 5
        assertEquals("Author with 5 papers should return count of 5", 5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 (no papers)
        Author author = factory.createAuthor();
        
        // Input: Count papers for Author A002
        int result = author.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals("New author with 0 papers should return count of 0", 0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 with 1 paper
        Author author = factory.createAuthor();
        
        // Create Paper P24 linked to A003
        Paper paper = factory.createPaper();
        paper.setTitle("P24");
        author.getPapers().add(paper);
        
        // Input: Count papers for Author A003
        int result = author.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals("Author with 1 paper should return count of 1", 1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserWith3Papers() {
        // Setup: Create User U1 with Author and Reviewer roles
        User user = factory.createUser();
        user.setName("U1");
        
        // Create Author role
        Author author = factory.createAuthor();
        user.getRoles().add(author);
        
        // Create Reviewer role
        Reviewer reviewer = factory.createReviewer();
        user.getRoles().add(reviewer);
        
        // Create Papers P25-P27 linked to the author role
        for (int i = 25; i <= 27; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("P" + i);
            author.getPapers().add(paper);
        }
        
        // Input: Count papers for Author A004 (the author role)
        int result = author.countSubmittedPapers();
        
        // Expected Output: 3
        assertEquals("Multi-role user with author role having 3 papers should return count of 3", 3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 and Author A006 with separate papers
        Author authorA005 = factory.createAuthor();
        Author authorA006 = factory.createAuthor();
        
        // Create Paper P28 for A005
        Paper paperP28 = factory.createPaper();
        paperP28.setTitle("P28");
        authorA005.getPapers().add(paperP28);
        
        // Create Paper P29 for A006
        Paper paperP29 = factory.createPaper();
        paperP29.setTitle("P29");
        authorA006.getPapers().add(paperP29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countSubmittedPapers();
        
        // Expected Output: 1 (only P28 belongs to A005, P29 belongs to A006)
        assertEquals("Author A005 should only count their own papers (1 paper)", 1, result);
        
        // Verify that A006 has their own paper count
        int resultA006 = authorA006.countSubmittedPapers();
        assertEquals("Author A006 should only count their own papers (1 paper)", 1, resultA006);
    }
}