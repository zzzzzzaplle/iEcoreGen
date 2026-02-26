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

    public void addRole(UserRole role) {
        roles.add(role);
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
     * Submits a paper to the system
     * @param paper The paper to be submitted
     */
    public void submitPaper(Paper paper) {
        papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author
     * @return The total number of submitted papers
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author
     * @return The acceptance rate as a decimal between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }
        
        long acceptedPapers = papers.stream()
            .filter(paper -> paper.getDecision() == Grade.ACCEPT)
            .count();
        
        return (double) acceptedPapers / papers.size();
    }

    /**
     * Finds authors with positive reviews (for future extension)
     * @return List of authors with positive reviews
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This method would need access to all authors, so it might be better placed in a service class
        // For now, returning empty list as placeholder
        return new ArrayList<>();
    }
}

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

    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer
     * @return The number of unsubmitted reviews, returns 0 if all reviews are submitted
     */
    public int calculateUnsubmittedReviews() {
        if (assignments.isEmpty()) {
            return 0;
        }
        
        long unsubmittedCount = assignments.stream()
            .filter(assignment -> assignment.getGrade() == Grade.UNDECIDED)
            .count();
        
        return (int) unsubmittedCount;
    }

    /**
     * Converts the reviewer's historical grades into a numerical average score
     * @return The average score between 0.0 and 1.0, where Accept = 1 and Reject = 0
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }
        
        long submittedReviews = assignments.stream()
            .filter(assignment -> assignment.getGrade() != Grade.UNDECIDED)
            .count();
        
        if (submittedReviews == 0) {
            return 0.0;
        }
        
        double totalScore = assignments.stream()
            .filter(assignment -> assignment.getGrade() != Grade.UNDECIDED)
            .mapToDouble(assignment -> assignment.getGrade() == Grade.ACCEPT ? 1.0 : 0.0)
            .sum();
        
        return totalScore / submittedReviews;
    }
}

class CoChair extends UserRole {
    /**
     * Makes a final decision for a paper
     * @param paper The paper to make decision for
     * @param decision The final decision (ACCEPT or REJECT)
     * @return 1 if decision was successfully made, 0 otherwise
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper == null || decision == Grade.UNDECIDED) {
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
     * Calculates the acceptance rate for this paper (for future extension)
     * @return The acceptance rate as a decimal between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        // This method might be better suited for a collection of papers
        // For now, returning 0.0 as placeholder
        return 0.0;
    }

    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject
     * @return true if all submitted reviews have the same grade (either all ACCEPT or all REJECT), false otherwise
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }
        
        List<ReviewAssignment> submittedReviews = reviews.stream()
            .filter(review -> review.getGrade() != Grade.UNDECIDED)
            .toList();
        
        if (submittedReviews.isEmpty()) {
            return false;
        }
        
        Grade firstGrade = submittedReviews.get(0).getGrade();
        return submittedReviews.stream()
            .allMatch(review -> review.getGrade() == firstGrade);
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