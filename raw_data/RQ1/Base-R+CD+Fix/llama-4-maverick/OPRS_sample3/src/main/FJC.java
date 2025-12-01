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
 * Abstract class representing a user role.
 */
abstract class UserRole {
}

/**
 * Class representing an author.
 */
class Author extends UserRole {
    private List<Paper> papers;

    /**
     * Constructor for Author.
     */
    public Author() {
        this.papers = new ArrayList<>();
    }

    /**
     * Gets the list of papers submitted by the author.
     * 
     * @return List of papers.
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * Sets the list of papers submitted by the author.
     * 
     * @param papers List of papers.
     */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Submits a paper.
     * 
     * @param paper Paper to be submitted.
     */
    public void submitPaper(Paper paper) {
        papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by the author.
     * 
     * @return Number of papers.
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * 
     * @return Acceptance rate between 0.0 and 1.0.
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

    // Method not used in the given functional requirements, but defined in the design model
    // public List<Author> findAuthorsWithPositiveReviews() {
    //     // Implementation not provided in the given requirements
    // }
}

/**
 * Class representing a reviewer.
 */
class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    /**
     * Constructor for Reviewer.
     */
    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    /**
     * Gets the list of review assignments for the reviewer.
     * 
     * @return List of review assignments.
     */
    public List<ReviewAssignment> getAssignments() {
        return assignments;
    }

    /**
     * Sets the list of review assignments for the reviewer.
     * 
     * @param assignments List of review assignments.
     */
    public void setAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * 
     * @return Number of unsubmitted reviews.
     */
    public int calculateUnsubmittedReviews() {
        int unsubmittedCount = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.UNDECIDED) {
                unsubmittedCount++;
            }
        }
        return unsubmittedCount;
    }

    /**
     * Calculates the proportion of accepted papers based on the reviewer's historical grades.
     * 
     * @return Proportion of accepted papers between 0.0 and 1.0.
     */
    public double calculateAcceptanceProportion() {
        int totalReviews = assignments.size();
        int acceptedCount = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.ACCEPT) {
                acceptedCount++;
            }
        }
        return (double) acceptedCount / totalReviews;
    }
}

/**
 * Class representing a co-chair.
 */
class CoChair extends UserRole {
    /**
     * Constructor for CoChair.
     */
    public CoChair() {
    }

    /**
     * Makes a final decision on a paper.
     * 
     * @param paper    Paper to make a decision on.
     * @param decision Decision to be made.
     * @return 1 if decision is made successfully, otherwise 0.
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        paper.setDecision(decision);
        return 1;
    }
}

/**
 * Class representing a user.
 */
class User {
    private String name;
    private List<UserRole> roles;

    /**
     * Constructor for User.
     */
    public User() {
        this.roles = new ArrayList<>();
    }

    /**
     * Gets the name of the user.
     * 
     * @return Name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * 
     * @param name Name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of roles for the user.
     * 
     * @return List of user roles.
     */
    public List<UserRole> getRoles() {
        return roles;
    }

    /**
     * Sets the list of roles for the user.
     * 
     * @param roles List of user roles.
     */
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    /**
     * Adds a role to the user.
     * 
     * @param role Role to be added.
     */
    public void addRole(UserRole role) {
        roles.add(role);
    }
}

/**
 * Class representing a paper.
 */
class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    /**
     * Constructor for Paper.
     */
    public Paper() {
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the title of the paper.
     * 
     * @return Title of the paper.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the paper.
     * 
     * @param title Title of the paper.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the type of the paper.
     * 
     * @return Type of the paper.
     */
    public PaperType getType() {
        return type;
    }

    /**
     * Sets the type of the paper.
     * 
     * @param type Type of the paper.
     */
    public void setType(PaperType type) {
        this.type = type;
    }

    /**
     * Gets the decision on the paper.
     * 
     * @return Decision on the paper.
     */
    public Grade getDecision() {
        return decision;
    }

    /**
     * Sets the decision on the paper.
     * 
     * @param decision Decision on the paper.
     */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /**
     * Gets the list of reviews for the paper.
     * 
     * @return List of review assignments.
     */
    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews for the paper.
     * 
     * @param reviews List of review assignments.
     */
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }

    /**
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject before the chair makes the final decision.
     * 
     * @return True if all reviews are the same, false otherwise.
     */
    public boolean isAllReviewsSame() {
        if (reviews.isEmpty()) {
            return true;
        }
        Grade grade = reviews.get(0).getGrade();
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() != grade) {
                return false;
            }
        }
        return true;
    }

    // Method not directly required but can be used to calculate acceptance rate
    // public double calculateAcceptanceRate() {
    //     // Implementation not directly provided in the given requirements
    // }
}

/**
 * Class representing a review assignment.
 */
class ReviewAssignment {
    private String feedback;
    private Grade grade;

    /**
     * Constructor for ReviewAssignment.
     */
    public ReviewAssignment() {
    }

    /**
     * Gets the feedback for the review assignment.
     * 
     * @return Feedback for the review.
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback for the review assignment.
     * 
     * @param feedback Feedback for the review.
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade for the review assignment.
     * 
     * @return Grade for the review.
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the grade for the review assignment.
     * 
     * @param grade Grade for the review.
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}