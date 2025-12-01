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
    
    /**
     * Adds a role to the user's roles list
     * @param role The UserRole to add to the user
     */
    public void addRole(UserRole role) {
        if (role != null) {
            this.roles.add(role);
        }
    }
}

abstract class UserRole {
    public UserRole() {
    }
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
     * Submits a paper by adding it to the author's papers list
     * @param paper The paper to be submitted
     */
    public void submitPaper(Paper paper) {
        if (paper != null) {
            this.papers.add(paper);
        }
    }
    
    /**
     * Counts the total number of papers submitted by this author
     * @return The number of submitted papers
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }
    
    /**
     * Calculates the acceptance rate of papers for this author
     * @return The acceptance rate as a double between 0.0 and 1.0, returns 0.0 if no papers submitted
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
     * Finds authors with positive reviews (for future extension - currently returns empty list)
     * @return List of authors with positive reviews
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // Implementation would require access to all authors and their paper reviews
        // Returning empty list as placeholder implementation
        return new ArrayList<>();
    }
}

class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;
    
    public Reviewer() {
        this.assignments = new ArrayList<>();
    }
    
    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }
    
    public void setReviewAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }
    
    /**
     * Calculates the number of unsubmitted reviews for this reviewer
     * @return The number of unsubmitted reviews, returns 0 if all reviews are submitted
     */
    public int calculateUnsubmittedReviews() {
        return (int) assignments.stream()
            .filter(assignment -> assignment.getGrade() == Grade.UNDECIDED)
            .count();
    }
    
    /**
     * Calculates the acceptance proportion of this reviewer's historical grades
     * @return The average score between 0.0 and 1.0 where Accept=1 and Reject=0
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
    public CoChair() {
    }
    
    /**
     * Makes a final decision for a paper
     * @param paper The paper for which to make the decision
     * @param decision The final decision grade (ACCEPT or REJECT)
     * @return 1 if decision was made successfully, -1 if paper is null or decision is invalid
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper == null || decision == Grade.UNDECIDED) {
            return -1;
        }
        
        paper.setDecision(decision);
        return 1;
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
     * Calculates the acceptance rate for this paper (for consistency with Author method)
     * @return 1.0 if paper is accepted, 0.0 if rejected or undecided
     */
    public double calculateAcceptanceRate() {
        return decision == Grade.ACCEPT ? 1.0 : 0.0;
    }
    
    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject
     * @return true if all submitted reviews have the same grade (all Accept or all Reject), false otherwise
     */
    public boolean isAllReviewsPositive() {
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