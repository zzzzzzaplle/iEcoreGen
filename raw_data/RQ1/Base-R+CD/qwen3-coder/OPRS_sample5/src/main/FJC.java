import java.util.*;

/**
 * Represents a user in the review system.
 * Users can have multiple roles such as Author, Reviewer, or CoChair.
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
     * @return the name of the user
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
     * Gets the list of roles assigned to the user.
     * @return the list of roles
     */
    public List<UserRole> getRoles() {
        return roles;
    }

    /**
     * Sets the list of roles for the user.
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
 * Abstract class representing a user role in the system.
 */
abstract class UserRole {
    /**
     * Constructs a new UserRole.
     */
    public UserRole() {
    }
}

/**
 * Represents an author user role.
 * Authors can submit papers and track their acceptance rates.
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
     * Gets the list of papers submitted by this author.
     * @return the list of papers
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * Sets the list of papers for this author.
     * @param papers the papers to set
     */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Submits a paper by adding it to the author's paper list.
     * @param paper the paper to submit
     */
    public void submitPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author.
     * @return the number of submitted papers
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author.
     * Example: If an author submitted 10 papers with 6 accepted, return 0.60.
     * @return the acceptance rate as a double value between 0.0 and 1.0
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
     * Note: This method would typically be implemented at a higher level
     * as it requires access to all authors in the system.
     * @return list of authors with positive reviews
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This implementation would require access to all authors in the system
        // For now, returning an empty list as this would be implemented at system level
        return new ArrayList<>();
    }
}

/**
 * Represents a reviewer user role.
 * Reviewers can be assigned papers to review and submit reviews.
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
     * Gets the list of review assignments for this reviewer.
     * @return the list of review assignments
     */
    public List<ReviewAssignment> getAssignments() {
        return assignments;
    }

    /**
     * Sets the list of review assignments for this reviewer.
     * @param assignments the assignments to set
     */
    public void setAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * Gets the review assignments for this reviewer.
     * @return the list of review assignments
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return this.assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * Returns 0 if all reviews are submitted.
     * @return the number of unsubmitted reviews
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
     * Converts a reviewer's historical grades into a numerical average score.
     * Accept = 1, Reject = 0.
     * Example: 3 Accept and 1 Reject → (3×1 + 1×0)/4 = 0.75.
     * @return the average score as a double value between 0.0 and 1.0
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }
        
        int totalScore = 0;
        int gradedAssignments = 0;
        
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() != Grade.UNDECIDED) {
                gradedAssignments++;
                if (assignment.getGrade() == Grade.ACCEPT) {
                    totalScore += 1;
                }
                // Reject grade contributes 0 to the score
            }
        }
        
        if (gradedAssignments == 0) {
            return 0.0;
        }
        
        return (double) totalScore / gradedAssignments;
    }
}

/**
 * Represents a co-chair user role.
 * Co-chairs can make final decisions on papers.
 */
class CoChair extends UserRole {
    /**
     * Constructs a new CoChair.
     */
    public CoChair() {
    }

    /**
     * Makes a final decision on a paper.
     * @param paper the paper to make a decision on
     * @param decision the grade decision (ACCEPT or REJECT)
     * @return 1 if successful, 0 if unsuccessful
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (decision == Grade.UNDECIDED) {
            return 0; // Cannot make an undecided final decision
        }
        paper.setDecision(decision);
        return 1;
    }
}

/**
 * Enumeration representing the type of paper.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE
}

/**
 * Enumeration representing the grade of a review.
 */
enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

/**
 * Represents a paper in the review system.
 * Papers have titles, types, and are associated with reviews.
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
     * Gets the final decision for the paper.
     * @return the decision
     */
    public Grade getDecision() {
        return decision;
    }

    /**
     * Sets the final decision for the paper.
     * @param decision the decision to set
     */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /**
     * Gets the list of reviews for this paper.
     * @return the list of reviews
     */
    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews for this paper.
     * @param reviews the reviews to set
     */
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }

    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject.
     * @return true if all reviews have the same grade (all Accept or all Reject), false otherwise
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
     * @return the acceptance rate as a double value between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        
        int acceptCount = 0;
        int validReviews = 0;
        
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() != Grade.UNDECIDED) {
                validReviews++;
                if (review.getGrade() == Grade.ACCEPT) {
                    acceptCount++;
                }
            }
        }
        
        if (validReviews == 0) {
            return 0.0;
        }
        
        return (double) acceptCount / validReviews;
    }
}

/**
 * Represents a review assignment for a paper.
 * Contains feedback and a grade from a reviewer.
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
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback for this review.
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade for this review.
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the grade for this review.
     * @param grade the grade to set
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}