import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the review system with multiple roles
 */
class User {
    private String name;
    private List<UserRole> roles;

    /**
     * Default constructor
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
     * Adds a role to the user
     * @param role the role to add
     */
    public void addRole(UserRole role) {
        if (role != null) {
            this.roles.add(role);
        }
    }
}

/**
 * Abstract base class for user roles
 */
abstract class UserRole {
    // Base class for all user roles
}

/**
 * Represents an author role with paper submission capabilities
 */
class Author extends UserRole {
    private List<Paper> papers;

    /**
     * Default constructor
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
     * Submits a paper to the system
     * @param paper the paper to submit
     */
    public void submitPaper(Paper paper) {
        if (paper != null) {
            this.papers.add(paper);
        }
    }

    /**
     * Counts the total number of papers submitted by this author
     * @return the number of submitted papers
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author
     * @return acceptance rate as a double between 0.0 and 1.0, or 0.0 if no papers submitted
     */
    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }
        
        long acceptedCount = papers.stream()
            .filter(paper -> paper.getDecision() == Grade.ACCEPT)
            .count();
            
        return (double) acceptedCount / papers.size();
    }
}

/**
 * Represents a reviewer role with review assignment capabilities
 */
class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    /**
     * Default constructor
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

    public List<ReviewAssignment> getReviewAssignments() {
        return new ArrayList<>(assignments);
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer
     * @return number of unsubmitted reviews, 0 if all reviews are submitted
     */
    public int calculateUnsubmittedReviews() {
        return (int) assignments.stream()
            .filter(assignment -> assignment.getGrade() == Grade.UNDECIDED)
            .count();
    }

    /**
     * Calculates the acceptance proportion of this reviewer's historical grades
     * @return average score between 0.0 and 1.0 based on historical grades (Accept=1, Reject=0)
     */
    public double calculateAcceptanceProportion() {
        List<ReviewAssignment> submittedReviews = assignments.stream()
            .filter(assignment -> assignment.getGrade() != Grade.UNDECIDED)
            .toList();
            
        if (submittedReviews.isEmpty()) {
            return 0.0;
        }
        
        double totalScore = submittedReviews.stream()
            .mapToDouble(assignment -> assignment.getGrade() == Grade.ACCEPT ? 1.0 : 0.0)
            .sum();
            
        return totalScore / submittedReviews.size();
    }
}

/**
 * Represents a co-chair role with final decision capabilities
 */
class CoChair extends UserRole {
    
    /**
     * Default constructor
     */
    public CoChair() {
    }

    /**
     * Makes a final decision for a paper
     * @param paper the paper to make decision for
     * @param decision the final decision (ACCEPT or REJECT)
     * @return 1 if decision was made successfully, -1 if paper is null or decision is invalid
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper == null || decision == Grade.UNDECIDED) {
            return -1;
        }
        paper.setDecision(decision);
        return 1;
    }
}

/**
 * Enum representing different types of papers
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE
}

/**
 * Enum representing possible grades for reviews and decisions
 */
enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

/**
 * Represents a paper submitted to the system
 */
class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    /**
     * Default constructor
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
     * Checks if all reviews for this paper are exclusively Accept or exclusively Reject
     * @return true if all reviews have the same grade (either all ACCEPT or all REJECT), false otherwise
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }
        
        Grade firstGrade = null;
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() == Grade.UNDECIDED) {
                return false; // Undecided reviews break the consistency
            }
            if (firstGrade == null) {
                firstGrade = review.getGrade();
            } else if (review.getGrade() != firstGrade) {
                return false; // Mixed grades found
            }
        }
        return true;
    }
}

/**
 * Represents a review assignment for a paper
 */
class ReviewAssignment {
    private String feedback;
    private Grade grade;

    /**
     * Default constructor
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