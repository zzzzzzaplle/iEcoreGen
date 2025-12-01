import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the conference review system.
 * Can be an author, reviewer, or co-chair.
 */
class User {
    private String name;
    private List<Paper> submittedPapers;
    private List<Paper> assignedPapers;
    private List<Review> reviews;

    /**
     * Default constructor initializes empty lists for papers and reviews.
     */
    public User() {
        this.submittedPapers = new ArrayList<>();
        this.assignedPapers = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Paper> getSubmittedPapers() {
        return submittedPapers;
    }

    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }

    public List<Paper> getAssignedPapers() {
        return assignedPapers;
    }

    public void setAssignedPapers(List<Paper> assignedPapers) {
        this.assignedPapers = assignedPapers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}

/**
 * Represents a paper submitted to the conference.
 * Can be either a research paper or experience report paper.
 */
class Paper {
    private String title;
    private boolean isResearchPaper;
    private List<Review> reviews;
    private User author;
    private Decision finalDecision;

    /**
     * Default constructor initializes empty reviews list.
     */
    public Paper() {
        this.reviews = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isResearchPaper() {
        return isResearchPaper;
    }

    public void setResearchPaper(boolean researchPaper) {
        isResearchPaper = researchPaper;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Decision getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(Decision finalDecision) {
        this.finalDecision = finalDecision;
    }
}

/**
 * Represents a review submitted by a reviewer for a paper.
 * Contains feedback and a grade (Accept or Reject).
 */
class Review {
    private String feedback;
    private Grade grade;
    private User reviewer;
    private Paper paper;
    private boolean submitted;

    /**
     * Default constructor sets review as unsubmitted by default.
     */
    public Review() {
        this.submitted = false;
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

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }
}

/**
 * Represents the possible grades for a review.
 */
enum Grade {
    ACCEPT, REJECT
}

/**
 * Represents the final decision made by the chair for a paper.
 */
enum Decision {
    ACCEPTED, REJECTED, PENDING
}

/**
 * Contains utility methods for the conference review system.
 */
class ReviewSystem {
    
    /**
     * Calculates the number of unsubmitted reviews for a given reviewer.
     * 
     * @param reviewer The reviewer for whom to count unsubmitted reviews
     * @return The number of unsubmitted reviews, returns 0 if all reviews are submitted
     */
    public static int calculateUnsubmittedReviews(User reviewer) {
        int unsubmittedCount = 0;
        for (Review review : reviewer.getReviews()) {
            if (!review.isSubmitted()) {
                unsubmittedCount++;
            }
        }
        return unsubmittedCount;
    }
    
    /**
     * Checks if all reviews for a paper are either exclusively Accept or exclusively Reject.
     * This should be checked before the chair makes the final decision.
     * 
     * @param paper The paper to check reviews for
     * @return true if all reviews are either all Accept or all Reject, false otherwise
     * @throws IllegalArgumentException if the paper has no reviews
     */
    public static boolean areReviewsUnanimous(Paper paper) {
        List<Review> reviews = paper.getReviews();
        if (reviews.isEmpty()) {
            throw new IllegalArgumentException("Paper has no reviews");
        }
        
        Grade firstGrade = reviews.get(0).getGrade();
        for (Review review : reviews) {
            if (review.getGrade() != firstGrade) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Counts the total number of papers submitted by an author.
     * 
     * @param author The author whose papers to count
     * @return The total number of papers submitted by the author
     */
    public static int countPapersByAuthor(User author) {
        return author.getSubmittedPapers().size();
    }
    
    /**
     * Calculates the acceptance rate of papers for an author.
     * 
     * @param author The author for whom to calculate acceptance rate
     * @return The acceptance rate as a double between 0.0 and 1.0, or 0.0 if no papers submitted
     */
    public static double calculateAcceptanceRate(User author) {
        List<Paper> papers = author.getSubmittedPapers();
        if (papers.isEmpty()) {
            return 0.0;
        }
        
        int acceptedCount = 0;
        for (Paper paper : papers) {
            if (paper.getFinalDecision() == Decision.ACCEPTED) {
                acceptedCount++;
            }
        }
        
        return (double) acceptedCount / papers.size();
    }
    
    /**
     * Converts a reviewer's historical grades into a numerical average score.
     * Accept = 1.0, Reject = 0.0
     * 
     * @param reviewer The reviewer whose historical grades to analyze
     * @return The average score between 0.0 and 1.0, or 0.0 if no reviews submitted
     */
    public static double calculateReviewerAverageScore(User reviewer) {
        List<Review> reviews = reviewer.getReviews();
        if (reviews.isEmpty()) {
            return 0.0;
        }
        
        double totalScore = 0.0;
        int submittedCount = 0;
        
        for (Review review : reviews) {
            if (review.isSubmitted()) {
                submittedCount++;
                if (review.getGrade() == Grade.ACCEPT) {
                    totalScore += 1.0;
                }
                // Reject adds 0.0, so no need to add anything
            }
        }
        
        if (submittedCount == 0) {
            return 0.0;
        }
        
        return totalScore / submittedCount;
    }
}