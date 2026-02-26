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
    // Base class for user roles
}

/**
 * Represents an author user role.
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
     * Acceptance rate = number of accepted papers / total papers submitted.
     * @return The acceptance rate as a double between 0.0 and 1.0
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
     * Finds authors with positive reviews (at least one accepted paper).
     * @return A list of authors with at least one accepted paper
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        List<Author> result = new ArrayList<>();
        if (this.calculateAcceptanceRate() > 0) {
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
     * A review is considered unsubmitted if its grade is UNDECIDED.
     * @return The number of unsubmitted reviews
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
     * Accept = 1, Reject = 0, UNDECIDED is not counted.
     * @return The average score between 0.0 and 1.0
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }
        
        int totalScore = 0;
        int validReviews = 0;
        
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() != Grade.UNDECIDED) {
                validReviews++;
                if (assignment.getGrade() == Grade.ACCEPT) {
                    totalScore += 1;
                }
            }
        }
        
        if (validReviews == 0) {
            return 0.0;
        }
        
        return (double) totalScore / validReviews;
    }
}

/**
 * Represents a co-chair user role.
 */
class CoChair extends UserRole {
    public CoChair() {
        // Default constructor
    }

    /**
     * Makes a final decision for a paper.
     * @param paper The paper to make a decision on
     * @param decision The decision (ACCEPT or REJECT)
     * @return 1 if decision was made successfully, 0 otherwise
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
 * Represents a paper in the review system.
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
     * @return true if all reviews have the same grade (all Accept or all Reject), false otherwise
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
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
        
        // Must have at least one review and all must be either ACCEPT or REJECT
        return firstGrade == Grade.ACCEPT || firstGrade == Grade.REJECT;
    }
}

/**
 * Represents a review assignment for a paper.
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