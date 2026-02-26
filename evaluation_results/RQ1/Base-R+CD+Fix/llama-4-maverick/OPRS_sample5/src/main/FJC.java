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
     * Submits a paper.
     * 
     * @param paper the paper to be submitted
     */
    public void submitPaper(Paper paper) {
        papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by the author.
     * 
     * @return the total number of papers submitted
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * 
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
     * Finds authors with positive reviews (not implemented in this class, 
     * as per the given design model, it should be in Author class).
     * 
     * @return a list of authors with positive reviews
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This method is not fully defined in the given requirements,
        // so it's left as is for now.
        return new ArrayList<>();
    }

    /**
     * Gets the papers submitted by the author.
     * 
     * @return the list of papers
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * Sets the papers submitted by the author.
     * 
     * @param papers the list of papers to be set
     */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }
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
     * Gets the review assignments for the reviewer.
     * 
     * @return the list of review assignments
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * 
     * @return the number of unsubmitted reviews
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
     * Calculates the proportion of accepted papers reviewed by the reviewer.
     * 
     * @return the proportion of accepted papers
     */
    public double calculateAcceptanceProportion() {
        int acceptCount = 0;
        int totalCount = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() != Grade.UNDECIDED) {
                totalCount++;
                if (assignment.getGrade() == Grade.ACCEPT) {
                    acceptCount++;
                }
            }
        }
        return totalCount == 0 ? 0 : (double) acceptCount / totalCount;
    }

    /**
     * Gets the assignments for the reviewer.
     * 
     * @return the list of assignments
     */
    public List<ReviewAssignment> getAssignments() {
        return assignments;
    }

    /**
     * Sets the assignments for the reviewer.
     * 
     * @param assignments the list of assignments to be set
     */
    public void setAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
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
     * @param paper    the paper to be decided
     * @param decision the decision to be made
     * @return 1 if successful, otherwise 0 (for simplicity, as the actual return value is not specified)
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        paper.setDecision(decision);
        return 1; // Simplified return value
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
     * Adds a role to the user.
     * 
     * @param role the role to be added
     */
    public void addRole(UserRole role) {
        roles.add(role);
    }

    /**
     * Gets the name of the user.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * 
     * @param name the name to be set
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
     * @param roles the list of roles to be set
     */
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
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
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject.
     * 
     * @return true if all reviews are the same, false otherwise
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
     * @param title the title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the type of the paper.
     * 
     * @return the type
     */
    public PaperType getType() {
        return type;
    }

    /**
     * Sets the type of the paper.
     * 
     * @param type the type to be set
     */
    public void setType(PaperType type) {
        this.type = type;
    }

    /**
     * Gets the decision on the paper.
     * 
     * @return the decision
     */
    public Grade getDecision() {
        return decision;
    }

    /**
     * Sets the decision on the paper.
     * 
     * @param decision the decision to be set
     */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /**
     * Gets the reviews for the paper.
     * 
     * @return the list of reviews
     */
    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    /**
     * Sets the reviews for the paper.
     * 
     * @param reviews the list of reviews to be set
     */
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }
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
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback for the review assignment.
     * 
     * @param feedback the feedback to be set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade for the review assignment.
     * 
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the grade for the review assignment.
     * 
     * @param grade the grade to be set
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}