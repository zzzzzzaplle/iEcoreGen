import java.util.*;

/**
 * Represents a user in the review system.
 */
class User {
    private String name;
    private List<UserRole> roles;

    /**
     * Constructs a new User with default values.
     */
    public User() {
        this.roles = new ArrayList<>();
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the roles of the user.
     *
     * @return the list of roles
     */
    public List<UserRole> getRoles() {
        return roles;
    }

    /**
     * Sets the roles of the user.
     *
     * @param roles the roles to set
     */
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    /**
     * Adds a role to the user.
     *
     * @param role the role to add
     */
    public void addRole(UserRole role) {
        this.roles.add(role);
    }
}

/**
 * Abstract base class for different types of user roles.
 */
abstract class UserRole {
    /**
     * Default constructor for UserRole.
     */
    public UserRole() {
    }
}

/**
 * Represents an author user role.
 */
class Author extends UserRole {
    private List<Paper> papers;

    /**
     * Constructs a new Author with default values.
     */
    public Author() {
        this.papers = new ArrayList<>();
    }

    /**
     * Gets the papers submitted by this author.
     *
     * @return the list of papers
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * Sets the papers submitted by this author.
     *
     * @param papers the papers to set
     */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Submits a paper by adding it to the author's list of papers.
     *
     * @param paper the paper to submit
     */
    public void submitPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author.
     *
     * @return the number of submitted papers
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author.
     * Example: If an author submitted 10 papers with 6 accepted, return 0.60.
     *
     * @return the acceptance rate as a decimal between 0.0 and 1.0
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
     * This method would typically be implemented at a higher level,
     * but is included here per requirements.
     *
     * @return a list of authors with positive reviews
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This implementation assumes we need to return this author if all their papers have positive reviews
        // In a real application, this would likely be a static method or part of another service
        List<Author> result = new ArrayList<>();
        boolean allPapersPositive = true;
        
        for (Paper paper : papers) {
            if (!paper.isAllReviewsPositive()) {
                allPapersPositive = false;
                break;
            }
        }
        
        if (allPapersPositive) {
            result.add(this);
        }
        
        return result;
    }
}

/**
 * Represents a reviewer user role.
 */
class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    /**
     * Constructs a new Reviewer with default values.
     */
    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    /**
     * Gets the review assignments for this reviewer.
     *
     * @return the list of review assignments
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    /**
     * Sets the review assignments for this reviewer.
     *
     * @param assignments the assignments to set
     */
    public void setReviewAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * Adds a review assignment for this reviewer.
     *
     * @param assignment the assignment to add
     */
    public void addReviewAssignment(ReviewAssignment assignment) {
        this.assignments.add(assignment);
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * A review is considered unsubmitted if its grade is UNDECIDED.
     *
     * @return the number of unsubmitted reviews, or 0 if all reviews are submitted
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
     * Converts a reviewer's historical grades into a numerical average score.
     * Accept = 1, Reject = 0.
     *
     * @return the average score between 0.0 and 1.0
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }

        int totalScore = 0;
        int submittedReviews = 0;

        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() != Grade.UNDECIDED) {
                submittedReviews++;
                if (assignment.getGrade() == Grade.ACCEPT) {
                    totalScore += 1;
                }
            }
        }

        if (submittedReviews == 0) {
            return 0.0;
        }

        return (double) totalScore / submittedReviews;
    }
}

/**
 * Represents a co-chair user role.
 */
class CoChair extends UserRole {
    /**
     * Constructs a new CoChair with default values.
     */
    public CoChair() {
    }

    /**
     * Makes the final decision for a paper.
     *
     * @param paper    the paper to make decision for
     * @param decision the final decision (ACCEPT or REJECT)
     * @return 1 if decision was successfully made, 0 otherwise
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
 * Enumeration of possible paper types.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE
}

/**
 * Enumeration of possible review grades.
 */
enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

/**
 * Represents a paper in the review system.
 */
class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    /**
     * Constructs a new Paper with default values.
     */
    public Paper() {
        this.reviews = new ArrayList<>();
        this.decision = Grade.UNDECIDED;
    }

    /**
     * Gets the title of the paper.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the paper.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the type of the paper.
     *
     * @return the paper type
     */
    public PaperType getType() {
        return type;
    }

    /**
     * Sets the type of the paper.
     *
     * @param type the type to set
     */
    public void setType(PaperType type) {
        this.type = type;
    }

    /**
     * Gets the final decision for the paper.
     *
     * @return the decision
     */
    public Grade getDecision() {
        return decision;
    }

    /**
     * Sets the final decision for the paper.
     *
     * @param decision the decision to set
     */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /**
     * Gets the reviews for this paper.
     *
     * @return the list of reviews
     */
    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    /**
     * Sets the reviews for this paper.
     *
     * @param reviews the reviews to set
     */
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }

    /**
     * Adds a review assignment to this paper.
     *
     * @param review the review assignment to add
     */
    public void addReview(ReviewAssignment review) {
        this.reviews.add(review);
    }

    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject.
     *
     * @return true if all reviews have the same grade (ACCEPT or REJECT), false otherwise
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }

        // Check if all reviews are ACCEPT
        boolean allAccept = true;
        boolean allReject = true;

        for (ReviewAssignment review : reviews) {
            if (review.getGrade() != Grade.ACCEPT) {
                allAccept = false;
            }
            if (review.getGrade() != Grade.REJECT) {
                allReject = false;
            }
        }

        return allAccept || allReject;
    }

    /**
     * Calculates the acceptance rate of this paper based on its reviews.
     * Note: This implementation assumes we're calculating based on review grades.
     *
     * @return the acceptance rate as a decimal between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        if (reviews.isEmpty()) {
            return 0.0;
        }

        int acceptCount = 0;
        int submittedReviews = 0;

        for (ReviewAssignment review : reviews) {
            if (review.getGrade() != Grade.UNDECIDED) {
                submittedReviews++;
                if (review.getGrade() == Grade.ACCEPT) {
                    acceptCount++;
                }
            }
        }

        if (submittedReviews == 0) {
            return 0.0;
        }

        return (double) acceptCount / submittedReviews;
    }
}

/**
 * Represents a review assignment for a paper.
 */
class ReviewAssignment {
    private String feedback;
    private Grade grade;

    /**
     * Constructs a new ReviewAssignment with default values.
     */
    public ReviewAssignment() {
        this.grade = Grade.UNDECIDED;
    }

    /**
     * Gets the feedback for this review.
     *
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback for this review.
     *
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade for this review.
     *
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the grade for this review.
     *
     * @param grade the grade to set
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}