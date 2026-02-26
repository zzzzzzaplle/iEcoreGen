import java.util.ArrayList;
import java.util.List;

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
     * Gets the name of the user.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the roles of the user.
     * @return the roles
     */
    public List<UserRole> getRoles() {
        return roles;
    }

    /**
     * Sets the roles of the user.
     * @param roles the roles to set
     */
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    /**
     * Adds a role to the user.
     * @param role the role to add
     */
    public void addRole(UserRole role) {
        this.roles.add(role);
    }
}

/**
 * Abstract class representing a user role.
 */
abstract class UserRole {
}

/**
 * Represents an author in the system.
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
     * Gets the papers submitted by the author.
     * @return the papers
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * Sets the papers submitted by the author.
     * @param papers the papers to set
     */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Submits a paper.
     * @param paper the paper to submit
     */
    public void submitPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by the author.
     * @return the count of papers
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * @return the acceptance rate
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

    /**
     * Finds authors with positive reviews (not implemented as per the given design model).
     * @return a list of authors
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This method is not implemented as per the given design model
        // It's assumed to be part of the Author class for the sake of completeness
        return new ArrayList<>();
    }
}

/**
 * Represents a reviewer in the system.
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
     * Gets the review assignments for the reviewer.
     * @return the assignments
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    /**
     * Sets the review assignments for the reviewer.
     * @param assignments the assignments to set
     */
    public void setReviewAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * @return the count of unsubmitted reviews
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
     * Calculates the proportion of accepted papers based on the reviewer's historical grades.
     * @return the acceptance proportion
     */
    public double calculateAcceptanceProportion() {
        int acceptCount = 0;
        int totalCount = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.ACCEPT) {
                acceptCount++;
            }
            if (assignment.getGrade() != Grade.UNDECIDED) {
                totalCount++;
            }
        }
        return totalCount > 0 ? (double) acceptCount / totalCount : 0.0;
    }
}

/**
 * Represents a co-chair in the system.
 */
class CoChair extends UserRole {
    /**
     * Default constructor for CoChair.
     */
    public CoChair() {
    }

    /**
     * Makes a final decision on a paper.
     * @param paper the paper to decide on
     * @param decision the decision to make
     * @return an integer indicating the result (not specified in the requirements)
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        paper.setDecision(decision);
        return 0; // Return value is not specified in the requirements
    }
}

/**
 * Enum representing the type of a paper.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE
}

/**
 * Enum representing a grade or decision.
 */
enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
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
     * Gets the title of the paper.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the paper.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the type of the paper.
     * @return the type
     */
    public PaperType getType() {
        return type;
    }

    /**
     * Sets the type of the paper.
     * @param type the type to set
     */
    public void setType(PaperType type) {
        this.type = type;
    }

    /**
     * Gets the decision on the paper.
     * @return the decision
     */
    public Grade getDecision() {
        return decision;
    }

    /**
     * Sets the decision on the paper.
     * @param decision the decision to set
     */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /**
     * Gets the reviews for the paper.
     * @return the reviews
     */
    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    /**
     * Sets the reviews for the paper.
     * @param reviews the reviews to set
     */
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }

    /**
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject.
     * @return true if all reviews are the same, false otherwise
     */
    public boolean isAllReviewsSame() {
        if (reviews.isEmpty()) {
            return true;
        }
        Grade firstGrade = reviews.get(0).getGrade();
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() != firstGrade && review.getGrade() != Grade.UNDECIDED) {
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
     * Gets the feedback for the review.
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback for the review.
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade for the review.
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the grade for the review.
     * @param grade the grade to set
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}