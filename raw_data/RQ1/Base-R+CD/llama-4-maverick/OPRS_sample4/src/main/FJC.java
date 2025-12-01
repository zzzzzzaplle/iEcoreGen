import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the system with a name and various roles.
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
     * Gets the roles associated with the user.
     * @return the roles
     */
    public List<UserRole> getRoles() {
        return roles;
    }

    /**
     * Sets the roles associated with the user.
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
 * Abstract base class for different user roles.
 */
abstract class UserRole {
}

/**
 * Represents an author who can submit papers.
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
     * @return the count of submitted papers
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

    // Note: findAuthorsWithPositiveReviews is not applicable here as per the given design model
    // It seems to be a static method or a method in a different class that operates on a list of authors
}

/**
 * Represents a reviewer who reviews papers.
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
 * Represents a co-chair who can make final decisions on papers.
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
     * @return 1 if decision is made, otherwise 0 (though the return value's significance is not clear from the given requirements)
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        paper.setDecision(decision);
        return 1; // The significance of the return value is not clear from the given requirements
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
 * Enum representing the grade or decision on a paper or review.
 */
enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

/**
 * Represents a paper with a title, type, and reviews.
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
            return true; // or false, depending on how you want to handle this edge case
        }
        Grade firstGrade = reviews.get(0).getGrade();
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() != firstGrade) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Represents a review assignment with feedback and a grade.
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