import java.util.*;

/**
 * Represents a user in the review system.
 */
class User {
    private String name;
    private List<UserRole> roles;

    /**
     * Constructs a User with default values.
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
     * @param role The role to add
     */
    public void addRole(UserRole role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }
}

/**
 * Abstract base class for different types of user roles.
 */
abstract class UserRole {
    // Base class for user roles
}

/**
 * Represents an author who can submit papers.
 */
class Author extends UserRole {
    private List<Paper> papers;

    /**
     * Constructs an Author with default values.
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
     * Submits a paper by this author.
     * @param paper The paper to submit
     */
    public void submitPaper(Paper paper) {
        if (papers == null) {
            papers = new ArrayList<>();
        }
        papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author.
     * @return The number of submitted papers
     */
    public int countSubmittedPapers() {
        return papers != null ? papers.size() : 0;
    }

    /**
     * Calculates the acceptance rate of papers for this author.
     * @return The acceptance rate as a decimal (e.g., 0.6 for 60%)
     */
    public double calculateAcceptanceRate() {
        if (papers == null || papers.isEmpty()) {
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
     * Finds authors who have at least one paper with positive reviews.
     * @return List of authors with positive reviews
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This method would require access to all authors in the system
        // For now, returning empty list as it's not fully implementable in this context
        return new ArrayList<>();
    }
}

/**
 * Represents a reviewer who can review papers.
 */
class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    /**
     * Constructs a Reviewer with default values.
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
     * Gets the review assignments for this reviewer.
     * @return List of review assignments
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * @return The count of unsubmitted reviews
     */
    public int calculateUnsubmittedReviews() {
        if (assignments == null) {
            return 0;
        }

        int count = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.UNDECIDED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the proportion of accepted papers in this reviewer's history.
     * @return The average score between 0.0 and 1.0
     */
    public double calculateAcceptanceProportion() {
        if (assignments == null || assignments.isEmpty()) {
            return 0.0;
        }

        int total = 0;
        int acceptCount = 0;

        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() != Grade.UNDECIDED) {
                total++;
                if (assignment.getGrade() == Grade.ACCEPT) {
                    acceptCount++;
                }
            }
        }

        if (total == 0) {
            return 0.0;
        }

        return (double) acceptCount / total;
    }
}

/**
 * Represents a co-chair who can make final decisions on papers.
 */
class CoChair extends UserRole {
    /**
     * Constructs a CoChair with default values.
     */
    public CoChair() {
        // Default constructor
    }

    /**
     * Makes a final decision on a paper.
     * @param paper The paper to decide on
     * @param decision The decision (ACCEPT or REJECT)
     * @return 1 if successful, 0 otherwise
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper == null || decision == Grade.UNDECIDED) {
            return 0;
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
 * Represents a paper submitted for review.
 */
class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    /**
     * Constructs a Paper with default values.
     */
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
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject.
     * @return true if all reviews have the same grade (all Accept or all Reject), false otherwise
     */
    public boolean isAllReviewsPositive() {
        if (reviews == null || reviews.isEmpty()) {
            return false;
        }

        Grade firstGrade = null;
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() == Grade.UNDECIDED) {
                return false;
            }
            
            if (firstGrade == null) {
                firstGrade = review.getGrade();
            } else if (firstGrade != review.getGrade()) {
                return false;
            }
        }
        
        return firstGrade != Grade.UNDECIDED;
    }
}

/**
 * Represents a review assignment for a paper.
 */
class ReviewAssignment {
    private String feedback;
    private Grade grade;

    /**
     * Constructs a ReviewAssignment with default values.
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