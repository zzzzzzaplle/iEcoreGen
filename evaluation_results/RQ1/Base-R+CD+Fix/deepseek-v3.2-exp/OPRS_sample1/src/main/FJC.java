import java.util.ArrayList;
import java.util.List;

class User {
    private String name;
    private List<UserRole> roles;
    
    public User() {
        this.roles = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<UserRole> getRoles() {
        return roles;
    }
    
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
    
    public void addRole(UserRole role) {
        if (role != null) {
            this.roles.add(role);
        }
    }
}

abstract class UserRole {
}

class Author extends UserRole {
    private List<Paper> papers;
    
    public Author() {
        this.papers = new ArrayList<>();
    }
    
    public List<Paper> getPapers() {
        return papers;
    }
    
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }
    
    /**
     * Submits a paper to the system by adding it to the author's list of papers
     * @param paper the paper to be submitted
     */
    public void submitPaper(Paper paper) {
        if (paper != null) {
            this.papers.add(paper);
        }
    }
    
    /**
     * Counts the total number of papers submitted by this author
     * @return the number of submitted papers
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }
    
    /**
     * Calculates the acceptance rate of papers for this author
     * @return the acceptance rate as a decimal between 0.0 and 1.0, or 0.0 if no papers submitted
     */
    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }
        
        long acceptedCount = papers.stream()
            .filter(paper -> paper.getDecision() == Grade.ACCEPT)
            .count();
            
        return (double) acceptedCount / papers.size();
    }
    
    /**
     * Finds authors with positive reviews (all papers have at least one ACCEPT review)
     * Note: This method seems misplaced in Author class since it operates on multiple authors.
     * It would be better placed in a service class, but implemented here as per requirements.
     * @return list of authors with positive reviews (currently returns empty list as implementation is incomplete)
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This implementation would need access to all authors and their papers
        // Currently returns empty list as placeholder
        return new ArrayList<>();
    }
}

class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;
    
    public Reviewer() {
        this.assignments = new ArrayList<>();
    }
    
    public List<ReviewAssignment> getAssignments() {
        return assignments;
    }
    
    public void setAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }
    
    public List<ReviewAssignment> getReviewAssignments() {
        return this.assignments;
    }
    
    /**
     * Calculates the number of unsubmitted reviews for this reviewer
     * @return the count of unsubmitted reviews (where grade is UNDECIDED)
     */
    public int calculateUnsubmittedReviews() {
        return (int) assignments.stream()
            .filter(assignment -> assignment.getGrade() == Grade.UNDECIDED)
            .count();
    }
    
    /**
     * Calculates the acceptance proportion of this reviewer's historical grades
     * @return the average score between 0.0 and 1.0 where Accept=1 and Reject=0
     */
    public double calculateAcceptanceProportion() {
        List<ReviewAssignment> submittedReviews = assignments.stream()
            .filter(assignment -> assignment.getGrade() != Grade.UNDECIDED)
            .toList();
            
        if (submittedReviews.isEmpty()) {
            return 0.0;
        }
        
        double totalScore = submittedReviews.stream()
            .mapToDouble(assignment -> assignment.getGrade() == Grade.ACCEPT ? 1.0 : 0.0)
            .sum();
            
        return totalScore / submittedReviews.size();
    }
}

class CoChair extends UserRole {
    /**
     * Makes a final decision for a paper
     * @param paper the paper to make decision for
     * @param decision the final decision (ACCEPT or REJECT)
     * @return 1 if decision was successfully made, 0 otherwise
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper != null && (decision == Grade.ACCEPT || decision == Grade.REJECT)) {
            paper.setDecision(decision);
            return 1;
        }
        return 0;
    }
}

enum PaperType {
    RESEARCH,
    EXPERIENCE
}

enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;
    
    public Paper() {
        this.decision = Grade.UNDECIDED;
        this.reviews = new ArrayList<>();
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public PaperType getType() {
        return type;
    }
    
    public void setType(PaperType type) {
        this.type = type;
    }
    
    public Grade getDecision() {
        return decision;
    }
    
    public void setDecision(Grade decision) {
        this.decision = decision;
    }
    
    public List<ReviewAssignment> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }
    
    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject
     * @return true if all submitted reviews have the same grade (either all ACCEPT or all REJECT), false otherwise
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }
        
        List<ReviewAssignment> submittedReviews = reviews.stream()
            .filter(review -> review.getGrade() != Grade.UNDECIDED)
            .toList();
            
        if (submittedReviews.isEmpty()) {
            return false;
        }
        
        Grade firstGrade = submittedReviews.get(0).getGrade();
        return submittedReviews.stream()
            .allMatch(review -> review.getGrade() == firstGrade);
    }
    
    /**
     * Calculates the acceptance rate for this paper (not typically calculated per paper)
     * This method seems misplaced - acceptance rate is usually calculated per author or conference
     * @return 1.0 if paper is accepted, 0.0 if rejected, 0.0 if undecided
     */
    public double calculateAcceptanceRate() {
        return decision == Grade.ACCEPT ? 1.0 : 0.0;
    }
}

class ReviewAssignment {
    private String feedback;
    private Grade grade;
    
    public ReviewAssignment() {
        this.grade = Grade.UNDECIDED;
    }
    
    public String getFeedback() {
        return feedback;
    }
    
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    public Grade getGrade() {
        return grade;
    }
    
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}