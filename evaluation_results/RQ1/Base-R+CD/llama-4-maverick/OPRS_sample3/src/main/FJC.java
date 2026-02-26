import java.util.ArrayList;
import java.util.List;

/**
 * Enum representing the type of a paper.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE
}

/**
 * Enum representing the grade of a review or decision.
 */
enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

/**
 * Represents a user role in the system.
 */
abstract class UserRole {
}

/**
 * Represents an author, extending UserRole.
 */
class Author extends UserRole {
    private List<Paper> papers;

    /**
     * Default constructor for Author.
     */
    public Author() {
        this.papers = new ArrayList<>();
    }

    /**
     * Gets the list of papers submitted by this author.
     * @return List of papers.
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * Sets the list of papers submitted by this author.
     * @param papers List of papers.
     */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Submits a paper.
     * @param paper The paper to be submitted.
     */
    public void submitPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author.
     * @return The number of papers submitted.
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author.
     * @return The acceptance rate.
     */
    public double calculateAcceptanceRate() {
        int acceptedCount = 0;
        for (Paper paper : papers) {
            if (paper.getDecision() == Grade.ACCEPT) {
                acceptedCount++;
            }
        }
        return (double) acceptedCount / papers.size();
    }

    // Method not directly related to the given functional requirements but part of the design model
    // For demonstration, it is assumed that this method is not needed for the given task
    // public List<Author> findAuthorsWithPositiveReviews() {
    //     // Implementation not directly related to the task
    // }
}

/**
 * Represents a reviewer, extending UserRole.
 */
class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    /**
     * Default constructor for Reviewer.
     */
    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    /**
     * Gets the list of review assignments for this reviewer.
     * @return List of review assignments.
     */
    public List<ReviewAssignment> getAssignments() {
        return assignments;
    }

    /**
     * Sets the list of review assignments for this reviewer.
     * @param assignments List of review assignments.
     */
    public void setAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * @return The number of unsubmitted reviews.
     */
    public int calculateUnsubmittedReviews() {
        int count = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.UNDECIDED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the proportion of accepted papers based on this reviewer's historical grades.
     * @return The proportion of accepted papers.
     */
    public double calculateAcceptanceProportion() {
        int totalReviews = 0;
        int acceptedCount = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() != Grade.UNDECIDED) {
                totalReviews++;
                if (assignment.getGrade() == Grade.ACCEPT) {
                    acceptedCount++;
                }
            }
        }
        if (totalReviews == 0) {
            return 0.0;
        }
        return (double) acceptedCount / totalReviews;
    }
}

/**
 * Represents a co-chair, extending UserRole.
 */
class CoChair extends UserRole {
    /**
     * Makes a final decision on a paper.
     * @param paper The paper to make a decision on.
     * @param decision The decision to be made.
     * @return 1 if successful, otherwise an error code.
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        paper.setDecision(decision);
        return 1; // Simplified success indicator
    }
}

/**
 * Represents a user in the system.
 */
class User {
    private String name;
    private List<UserRole> roles;

    /**
     * Default constructor for User.
     */
    public User() {
        this.roles = new ArrayList<>();
    }

    /**
     * Gets the name of this user.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this user.
     * @param name The name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of roles for this user.
     * @return List of roles.
     */
    public List<UserRole> getRoles() {
        return roles;
    }

    /**
     * Sets the list of roles for this user.
     * @param roles List of roles.
     */
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    /**
     * Adds a role to this user.
     * @param role The role to be added.
     */
    public void addRole(UserRole role) {
        this.roles.add(role);
    }
}

/**
 * Represents a paper in the system.
 */
class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    /**
     * Default constructor for Paper.
     */
    public Paper() {
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the title of this paper.
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this paper.
     * @param title The title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the type of this paper.
     * @return The type.
     */
    public PaperType getType() {
        return type;
    }

    /**
     * Sets the type of this paper.
     * @param type The type to be set.
     */
    public void setType(PaperType type) {
        this.type = type;
    }

    /**
     * Gets the decision on this paper.
     * @return The decision.
     */
    public Grade getDecision() {
        return decision;
    }

    /**
     * Sets the decision on this paper.
     * @param decision The decision to be set.
     */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /**
     * Gets the list of reviews for this paper.
     * @return List of reviews.
     */
    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews for this paper.
     * @param reviews List of reviews.
     */
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }

    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject before the chair makes a final decision.
     * @return True if all reviews are consistent, false otherwise.
     */
    public boolean isAllReviewsConsistent() {
        boolean hasAccept = false;
        boolean hasReject = false;
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() == Grade.ACCEPT) {
                hasAccept = true;
            } else if (review.getGrade() == Grade.REJECT) {
                hasReject = true;
            }
            if (hasAccept && hasReject) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Represents a review assignment.
 */
class ReviewAssignment {
    private String feedback;
    private Grade grade;

    /**
     * Default constructor for ReviewAssignment.
     */
    public ReviewAssignment() {
    }

    /**
     * Gets the feedback for this review assignment.
     * @return The feedback.
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback for this review assignment.
     * @param feedback The feedback to be set.
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade for this review assignment.
     * @return The grade.
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the grade for this review assignment.
     * @param grade The grade to be set.
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}