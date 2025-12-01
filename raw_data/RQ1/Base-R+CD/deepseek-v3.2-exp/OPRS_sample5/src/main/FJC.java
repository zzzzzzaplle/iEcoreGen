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
        roles.add(role);
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
     * Submits a paper to the system by adding it to the author's list of papers.
     * 
     * @param paper The paper to be submitted
     */
    public void submitPaper(Paper paper) {
        papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author.
     * 
     * @return The total number of submitted papers
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author.
     * The acceptance rate is defined as (number of accepted papers) / (total submitted papers).
     * 
     * @return The acceptance rate as a double between 0.0 and 1.0, or 0.0 if no papers submitted
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
     * Finds authors who have papers with all positive reviews (exclusively ACCEPT grades).
     * Note: This method is not fully implemented as it requires access to all authors.
     * 
     * @return List of authors with positive reviews (placeholder implementation)
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This would require access to all authors in the system
        // For now, return empty list as placeholder
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
     * Calculates the number of unsubmitted reviews for this reviewer.
     * A review is considered unsubmitted if its grade is UNDECIDED.
     * 
     * @return The number of unsubmitted reviews, or 0 if all reviews are submitted
     */
    public int calculateUnsubmittedReviews() {
        return (int) assignments.stream()
            .filter(assignment -> assignment.getGrade() == Grade.UNDECIDED)
            .count();
    }

    /**
     * Calculates the acceptance proportion of this reviewer's historical grades.
     * Accept grades are counted as 1, Reject grades as 0, and UNDECIDED grades are excluded.
     * 
     * @return The average score between 0.0 and 1.0, or 0.0 if no submitted reviews exist
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
     * Makes a final decision for a paper by setting its decision grade.
     * 
     * @param paper The paper for which to make the decision
     * @param decision The final decision grade (ACCEPT or REJECT)
     * @return 1 if decision was successfully made, 0 otherwise
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper == null || (decision != Grade.ACCEPT && decision != Grade.REJECT)) {
            return 0;
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
     * Calculates the acceptance rate for this paper based on submitted reviews.
     * Only considers reviews with ACCEPT or REJECT grades (excludes UNDECIDED).
     * 
     * @return The acceptance rate as a double between 0.0 and 1.0, or 0.0 if no submitted reviews
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

    /**
     * Checks if all submitted reviews for this paper are either exclusively ACCEPT or exclusively REJECT.
     * UNDECIDED reviews are excluded from this check.
     * 
     * @return true if all submitted reviews are unanimous (all ACCEPT or all REJECT), false otherwise
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