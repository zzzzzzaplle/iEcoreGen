import java.util.*;

/**
 * Represents a user in the review system.
 */
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
     * Adds a role to the user.
     * @param role The role to add
     */
    public void addRole(UserRole role) {
        this.roles.add(role);
    }
}

/**
 * Abstract base class for different user roles.
 */
abstract class UserRole {
    public UserRole() {
    }
}

/**
 * Represents an author who can submit papers.
 */
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
     * Submits a paper by adding it to the author's list of papers.
     * @param paper The paper to submit
     */
    public void submitPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author.
     * @return The number of submitted papers
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author.
     * @return The acceptance rate as a decimal between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }
        
        int acceptedCount = 0;
        for (Paper paper : papers) {
            if (paper.getDecision() == Grade.ACCEPT) {
                acceptedCount++;
            }
        }
        
        return (double) acceptedCount / papers.size();
    }

    /**
     * Finds authors with positive reviews (not implemented in this basic version).
     * @return A list of authors with positive reviews
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This would require more complex logic to determine "positive reviews"
        // For now, returning an empty list as placeholder
        return new ArrayList<>();
    }
}

/**
 * Represents a reviewer who can review papers.
 */
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

    /**
     * Gets the review assignments for this reviewer.
     * @return List of review assignments
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return this.assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * A review is considered unsubmitted if it has no grade or feedback.
     * @return The number of unsubmitted reviews
     */
    public int calculateUnsubmittedReviews() {
        int count = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.UNDECIDED || 
                (assignment.getFeedback() == null || assignment.getFeedback().isEmpty())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the average acceptance proportion based on historical grades.
     * Accept = 1, Reject = 0.
     * @return The average score between 0.0 and 1.0
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }
        
        int sum = 0;
        int validAssignments = 0;
        
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() != Grade.UNDECIDED) {
                sum += (assignment.getGrade() == Grade.ACCEPT) ? 1 : 0;
                validAssignments++;
            }
        }
        
        if (validAssignments == 0) {
            return 0.0;
        }
        
        return (double) sum / validAssignments;
    }
}

/**
 * Represents a co-chair who can make final decisions on papers.
 */
class CoChair extends UserRole {
    public CoChair() {
    }

    /**
     * Makes a final decision on a paper.
     * @param paper The paper to decide on
     * @param decision The decision (ACCEPT or REJECT)
     * @return 1 if successful, 0 if failed
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (decision == Grade.UNDECIDED) {
            return 0; // Cannot make undecided decision
        }
        
        paper.setDecision(decision);
        return 1;
    }
}

/**
 * Enumeration of paper types.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE
}

/**
 * Enumeration of review grades.
 */
enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

/**
 * Represents a paper submitted to the review system.
 */
class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    public Paper() {
        this.reviews = new ArrayList<>();
        this.decision = Grade.UNDECIDED;
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
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject.
     * @return true if all reviews have the same grade (ACCEPT or REJECT), false otherwise
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }
        
        Grade firstGrade = null;
        for (ReviewAssignment review : reviews) {
            // Skip undecided reviews
            if (review.getGrade() == Grade.UNDECIDED) {
                continue;
            }
            
            // Set the first grade we encounter
            if (firstGrade == null) {
                firstGrade = review.getGrade();
            } 
            // If we find a different grade, return false
            else if (firstGrade != review.getGrade()) {
                return false;
            }
        }
        
        // Return true only if we found at least one decided review and all were the same
        return firstGrade != null;
    }
}

/**
 * Represents a review assignment for a reviewer.
 */
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