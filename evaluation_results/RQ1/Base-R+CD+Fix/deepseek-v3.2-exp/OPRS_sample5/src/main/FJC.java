import java.util.ArrayList;
import java.util.List;

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
     * Adds a role to the user's list of roles
     * @param role the UserRole to add
     */
    public void addRole(UserRole role) {
        if (role != null) {
            this.roles.add(role);
        }
    }
}

abstract class UserRole {
}

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
     * Submits a paper by adding it to the author's list of papers
     * @param paper the Paper to submit
     */
    public void submitPaper(Paper paper) {
        if (paper != null) {
            this.papers.add(paper);
        }
    }

    /**
     * Counts the total number of papers submitted by this author
     * @return the total number of submitted papers
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author
     * Acceptance rate is calculated as (number of accepted papers) / (total submitted papers)
     * @return the acceptance rate as a double between 0.0 and 1.0, or 0.0 if no papers submitted
     */
    public double calculateAcceptanceRate() {
        if (this.papers.isEmpty()) {
            return 0.0;
        }
        
        long acceptedCount = this.papers.stream()
            .filter(paper -> paper.getDecision() == Grade.ACCEPT)
            .count();
            
        return (double) acceptedCount / this.papers.size();
    }

    /**
     * Finds authors with positive reviews (at least one accepted paper)
     * Note: This method seems to be misplaced as it should be a utility method rather than instance method
     * @return list of authors with positive reviews (currently unimplemented as it requires access to all authors)
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This method would need access to all authors to be properly implemented
        // For now, return empty list as placeholder
        return new ArrayList<>();
    }
}

class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    public void setReviewAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer
     * A review is considered unsubmitted if its grade is UNDECIDED
     * @return the number of unsubmitted reviews, or 0 if all reviews are submitted
     */
    public int calculateUnsubmittedReviews() {
        return (int) this.assignments.stream()
            .filter(assignment -> assignment.getGrade() == Grade.UNDECIDED)
            .count();
    }

    /**
     * Calculates the acceptance proportion of this reviewer's historical grades
     * Accept = 1, Reject = 0, UNDECIDED is not counted
     * @return the average score between 0.0 and 1.0, or 0.0 if no reviews with decisions
     */
    public double calculateAcceptanceProportion() {
        List<ReviewAssignment> decidedReviews = this.assignments.stream()
            .filter(assignment -> assignment.getGrade() != Grade.UNDECIDED)
            .toList();
            
        if (decidedReviews.isEmpty()) {
            return 0.0;
        }
        
        double totalScore = decidedReviews.stream()
            .mapToDouble(assignment -> assignment.getGrade() == Grade.ACCEPT ? 1.0 : 0.0)
            .sum();
            
        return totalScore / decidedReviews.size();
    }
}

class CoChair extends UserRole {
    /**
     * Makes a final decision for a paper
     * @param paper the Paper to make decision for
     * @param decision the final Grade decision (ACCEPT or REJECT)
     * @return 1 if decision was successfully made, 0 otherwise
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper == null || (decision != Grade.ACCEPT && decision != Grade.REJECT)) {
            return 0;
        }
        
        paper.setDecision(decision);
        return 1;
    }
}

enum PaperType {
    RESEARCH,
    EXPERIENCE
}

enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

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
     * Calculates the acceptance rate across all reviews for this paper
     * Only considers reviews with ACCEPT or REJECT grades (excludes UNDECIDED)
     * @return the proportion of ACCEPT reviews, or 0.0 if no decided reviews
     */
    public double calculateAcceptanceRate() {
        List<ReviewAssignment> decidedReviews = this.reviews.stream()
            .filter(review -> review.getGrade() != Grade.UNDECIDED)
            .toList();
            
        if (decidedReviews.isEmpty()) {
            return 0.0;
        }
        
        long acceptCount = decidedReviews.stream()
            .filter(review -> review.getGrade() == Grade.ACCEPT)
            .count();
            
        return (double) acceptCount / decidedReviews.size();
    }

    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject
     * UNDECIDED reviews are not considered in this calculation
     * @return true if all decided reviews are unanimous (all ACCEPT or all REJECT), false otherwise
     */
    public boolean isAllReviewsPositive() {
        List<ReviewAssignment> decidedReviews = this.reviews.stream()
            .filter(review -> review.getGrade() != Grade.UNDECIDED)
            .toList();
            
        if (decidedReviews.isEmpty()) {
            return false; // No decided reviews cannot be considered unanimous
        }
        
        Grade firstDecision = decidedReviews.get(0).getGrade();
        return decidedReviews.stream()
            .allMatch(review -> review.getGrade() == firstDecision);
    }
}

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