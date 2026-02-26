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
     * Adds a role to the user's list of roles
     * @param role The UserRole to add to the user
     */
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
     * Submits a paper by adding it to the author's list of papers
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
        return papers.size();
    }
    
    /**
     * Calculates the acceptance rate of papers for this author
     * @return The acceptance rate as a decimal between 0.0 and 1.0
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
     * Finds authors with positive reviews (all papers have ACCEPT decision)
     * Note: This method would need access to other authors, but based on the current design,
     * it's implemented to work on the current author's papers only
     * @return List of authors with positive reviews (in this case, just this author if applicable)
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        List<Author> authorsWithPositiveReviews = new ArrayList<>();
        
        boolean allAccepted = papers.stream()
            .allMatch(paper -> paper.getDecision() == Grade.ACCEPT);
            
        if (allAccepted && !papers.isEmpty()) {
            authorsWithPositiveReviews.add(this);
        }
        
        return authorsWithPositiveReviews;
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
     * @return The number of reviews that haven't been submitted (grade is UNDECIDED)
     */
    public int calculateUnsubmittedReviews() {
        return (int) assignments.stream()
            .filter(assignment -> assignment.getGrade() == Grade.UNDECIDED)
            .count();
    }
    
    /**
     * Calculates the acceptance proportion of this reviewer's historical grades
     * @return The proportion of ACCEPT grades as a decimal between 0.0 and 1.0
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }
        
        long submittedReviews = assignments.stream()
            .filter(assignment -> assignment.getGrade() != Grade.UNDECIDED)
            .count();
            
        if (submittedReviews == 0) {
            return 0.0;
        }
        
        long acceptCount = assignments.stream()
            .filter(assignment -> assignment.getGrade() == Grade.ACCEPT)
            .count();
        
        return (double) acceptCount / submittedReviews;
    }
}

class CoChair extends UserRole {
    /**
     * Makes a final decision for a paper
     * @param paper The paper to make a decision for
     * @param decision The final decision (ACCEPT or REJECT)
     * @return 1 if successful, 0 if failed (e.g., paper is null)
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
     * Calculates the acceptance rate for this specific paper based on submitted reviews
     * @return The proportion of ACCEPT grades among submitted reviews
     */
    public double calculateAcceptanceRate() {
        List<ReviewAssignment> submittedReviews = reviews.stream()
            .filter(review -> review.getGrade() != Grade.UNDECIDED)
            .toList();
            
        if (submittedReviews.isEmpty()) {
            return 0.0;
        }
        
        long acceptCount = submittedReviews.stream()
            .filter(review -> review.getGrade() == Grade.ACCEPT)
            .count();
        
        return (double) acceptCount / submittedReviews.size();
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