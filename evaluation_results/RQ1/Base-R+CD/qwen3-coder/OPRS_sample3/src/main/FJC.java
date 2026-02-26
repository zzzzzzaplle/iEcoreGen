import java.util.*;

/**
 * Represents a user in the review system.
 */
class User {
    private String name;
    private List<UserRole> roles;

    /**
     * Default constructor initializes an empty list of roles.
     */
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
     * Adds a role to this user.
     *
     * @param role The role to add
     */
    public void addRole(UserRole role) {
        this.roles.add(role);
    }
}

/**
 * Abstract base class for different types of user roles.
 */
abstract class UserRole {
    // Base class for user roles
}

/**
 * Represents an author user who can submit papers.
 */
class Author extends UserRole {
    private List<Paper> papers;

    /**
     * Default constructor initializes an empty list of papers.
     */
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
     *
     * @param paper The paper to submit
     */
    public void submitPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author.
     *
     * @return The number of submitted papers
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author.
     * Acceptance rate = (number of accepted papers) / (total papers submitted).
     *
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
     * Finds authors with positive reviews (all reviews are ACCEPT).
     * Note: This implementation returns an empty list as it requires access to all authors in the system.
     *
     * @return An empty list (placeholder implementation)
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This would require access to all authors in the system
        // Placeholder implementation returns empty list
        return new ArrayList<>();
    }
}

/**
 * Represents a reviewer user who can review papers.
 */
class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    /**
     * Default constructor initializes an empty list of review assignments.
     */
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
     * Gets the list of review assignments for this reviewer.
     *
     * @return List of review assignments
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return this.assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * A review is considered unsubmitted if its grade is UNDECIDED.
     *
     * @return The number of unsubmitted reviews, or 0 if all are submitted
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
     * Converts this reviewer's historical grades into a numerical average score.
     * Accept = 1, Reject = 0.
     *
     * @return The average score between 0.0 and 1.0, or 0.0 if no graded reviews exist
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        int gradedCount = 0;

        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() != Grade.UNDECIDED) {
                sum += (assignment.getGrade() == Grade.ACCEPT) ? 1 : 0;
                gradedCount++;
            }
        }

        if (gradedCount == 0) {
            return 0.0;
        }

        return (double) sum / gradedCount;
    }
}

/**
 * Represents a co-chair user who can make final decisions on papers.
 */
class CoChair extends UserRole {
    /**
     * Default constructor.
     */
    public CoChair() {
        // No specific initialization needed
    }

    /**
     * Makes a final decision on a paper.
     *
     * @param paper    The paper to decide on
     * @param decision The decision (ACCEPT or REJECT)
     * @return 1 if successful, 0 otherwise
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (decision == Grade.UNDECIDED) {
            return 0;
        }
        paper.setDecision(decision);
        return 1;
    }
}

/**
 * Enum representing the type of paper.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE
}

/**
 * Enum representing the grade of a review.
 */
enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

/**
 * Represents a paper submitted for review.
 */
class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    /**
     * Default constructor initializes an empty list of reviews and sets decision to UNDECIDED.
     */
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
     * Checks if all reviews for this paper are either exclusively ACCEPT or exclusively REJECT.
     *
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

            if (firstGrade == null) {
                firstGrade = review.getGrade();
            } else if (firstGrade != review.getGrade()) {
                return false;
            }
        }

        // All reviews must be either ACCEPT or REJECT (not UNDECIDED)
        return firstGrade != null && firstGrade != Grade.UNDECIDED;
    }
}

/**
 * Represents a review assignment for a paper.
 */
class ReviewAssignment {
    private String feedback;
    private Grade grade;

    /**
     * Default constructor initializes grade to UNDECIDED.
     */
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