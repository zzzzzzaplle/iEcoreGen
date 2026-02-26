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

    public void submitPaper(Paper paper) {
        papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author
     * @return the total number of papers submitted by this author
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author
     * @return the acceptance rate as a double between 0.0 and 1.0, or 0.0 if no papers submitted
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
     * Finds authors with positive reviews (not implemented in this context - returns empty list)
     * This method would need access to all authors in the system to be properly implemented
     * @return empty list as this functionality requires system-wide context
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // Implementation would require access to all papers and reviews in the system
        // For now, return empty list as this is beyond the scope of a single author
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
     * @return the number of reviews with UNDECIDED grade, or 0 if all reviews are submitted
     */
    public int calculateUnsubmittedReviews() {
        return (int) assignments.stream()
            .filter(assignment -> assignment.getGrade() == Grade.UNDECIDED)
            .count();
    }

    /**
     * Calculates the acceptance proportion of this reviewer's historical grades
     * @return the numerical average score between 0.0 and 1.0 based on Accept=1, Reject=0
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }
        
        long totalReviews = assignments.stream()
            .filter(assignment -> assignment.getGrade() != Grade.UNDECIDED)
            .count();
            
        if (totalReviews == 0) {
            return 0.0;
        }
        
        long acceptCount = assignments.stream()
            .filter(assignment -> assignment.getGrade() == Grade.ACCEPT)
            .count();
            
        return (double) acceptCount / totalReviews;
    }
}

class CoChair extends UserRole {
    /**
     * Makes a final decision for a paper
     * @param paper the paper to make decision for
     * @param decision the final decision (ACCEPT or REJECT)
     * @return 1 if decision was successfully made, -1 if paper is null or decision is invalid
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper == null || (decision != Grade.ACCEPT && decision != Grade.REJECT)) {
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
     * Calculates the acceptance rate for this paper (not applicable to single paper)
     * This method seems misplaced as acceptance rate is typically calculated across multiple papers
     * @return 1.0 if paper is accepted, 0.0 if rejected, 0.0 if undecided
     */
    public double calculateAcceptanceRate() {
        return decision == Grade.ACCEPT ? 1.0 : 0.0;
    }

    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject
     * @return true if all submitted reviews have the same grade (either all ACCEPT or all REJECT), 
     *         false if reviews have mixed grades or if there are no submitted reviews
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }
        
        // Filter out undecided reviews
        List<ReviewAssignment> submittedReviews = reviews.stream()
            .filter(review -> review.getGrade() != Grade.UNDECIDED)
            .toList();
            
        if (submittedReviews.isEmpty()) {
            return false;
        }
        
        // Check if all submitted reviews have the same grade
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