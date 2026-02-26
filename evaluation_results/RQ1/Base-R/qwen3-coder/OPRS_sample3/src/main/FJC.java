import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the review system.
 * Users can be authors, reviewers, or co-chairs.
 */
class User {
    private String name;
    
    /**
     * Default constructor for User.
     */
    public User() {
        this.name = "";
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
}

/**
 * Represents an author in the review system.
 * Authors can submit papers.
 */
class Author extends User {
    private List<Paper> submittedPapers;
    
    /**
     * Default constructor for Author.
     */
    public Author() {
        super();
        this.submittedPapers = new ArrayList<>();
    }
    
    /**
     * Gets the list of papers submitted by this author.
     * @return the list of submitted papers
     */
    public List<Paper> getSubmittedPapers() {
        return submittedPapers;
    }
    
    /**
     * Sets the list of papers submitted by this author.
     * @param submittedPapers the list of papers to set
     */
    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }
    
    /**
     * Adds a paper to the list of papers submitted by this author.
     * @param paper the paper to add
     */
    public void addSubmittedPaper(Paper paper) {
        this.submittedPapers.add(paper);
    }
    
    /**
     * Counts the total number of papers submitted by this author.
     * @return the total number of submitted papers
     */
    public int countSubmittedPapers() {
        return this.submittedPapers.size();
    }
    
    /**
     * Calculates the acceptance rate of papers for this author.
     * Example: If an author submitted 10 papers with 6 accepted, return 0.60.
     * @return the acceptance rate as a decimal between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        if (submittedPapers.isEmpty()) {
            return 0.0;
        }
        
        int acceptedCount = 0;
        for (Paper paper : submittedPapers) {
            if (paper.getFinalDecision() == Paper.Decision.ACCEPT) {
                acceptedCount++;
            }
        }
        
        return (double) acceptedCount / submittedPapers.size();
    }
}

/**
 * Represents a reviewer in the review system.
 * Reviewers can review papers assigned to them.
 */
class Reviewer extends User {
    private List<Review> assignedReviews;
    
    /**
     * Default constructor for Reviewer.
     */
    public Reviewer() {
        super();
        this.assignedReviews = new ArrayList<>();
    }
    
    /**
     * Gets the list of reviews assigned to this reviewer.
     * @return the list of assigned reviews
     */
    public List<Review> getAssignedReviews() {
        return assignedReviews;
    }
    
    /**
     * Sets the list of reviews assigned to this reviewer.
     * @param assignedReviews the list of reviews to set
     */
    public void setAssignedReviews(List<Review> assignedReviews) {
        this.assignedReviews = assignedReviews;
    }
    
    /**
     * Adds a review to the list of reviews assigned to this reviewer.
     * @param review the review to add
     */
    public void addAssignedReview(Review review) {
        this.assignedReviews.add(review);
    }
    
    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * Returns 0 if all reviews are submitted by the reviewer.
     * @return the number of unsubmitted reviews
     */
    public int countUnsubmittedReviews() {
        int unsubmittedCount = 0;
        for (Review review : assignedReviews) {
            if (!review.isSubmitted()) {
                unsubmittedCount++;
            }
        }
        return unsubmittedCount;
    }
    
    /**
     * Converts this reviewer's historical grades into a numerical average score.
     * Accept = 1, Reject = 0.
     * Example: 3 Accept and 1 Reject → (3×1 + 1×0)/4 = 0.75.
     * @return the average score between 0.0 and 1.0
     */
    public double calculateAverageGrade() {
        if (assignedReviews.isEmpty()) {
            return 0.0;
        }
        
        int totalScore = 0;
        int submittedReviewsCount = 0;
        
        for (Review review : assignedReviews) {
            if (review.isSubmitted()) {
                if (review.getGrade() == Review.Grade.ACCEPT) {
                    totalScore += 1;
                }
                submittedReviewsCount++;
            }
        }
        
        if (submittedReviewsCount == 0) {
            return 0.0;
        }
        
        return (double) totalScore / submittedReviewsCount;
    }
}

/**
 * Represents a co-chair in the review system.
 * Co-chairs can make final decisions on papers.
 */
class CoChair extends User {
    /**
     * Default constructor for CoChair.
     */
    public CoChair() {
        super();
    }
}

/**
 * Represents a paper in the review system.
 * Papers can be research papers or experience report papers.
 */
class Paper {
 enum PaperType {
        RESEARCH, EXPERIENCE_REPORT
    }
    
 enum Decision {
        ACCEPT, REJECT, PENDING
    }
    
    private String title;
    private PaperType type;
    private List<Author> authors;
    private List<Review> reviews;
    private Decision finalDecision;
    
    /**
     * Default constructor for Paper.
     */
    public Paper() {
        this.title = "";
        this.type = PaperType.RESEARCH;
        this.authors = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.finalDecision = Decision.PENDING;
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
     * Gets the list of authors of the paper.
     * @return the authors
     */
    public List<Author> getAuthors() {
        return authors;
    }
    
    /**
     * Sets the list of authors of the paper.
     * @param authors the authors to set
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    
    /**
     * Adds an author to the paper.
     * @param author the author to add
     */
    public void addAuthor(Author author) {
        this.authors.add(author);
    }
    
    /**
     * Gets the list of reviews for the paper.
     * @return the reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }
    
    /**
     * Sets the list of reviews for the paper.
     * @param reviews the reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    /**
     * Adds a review to the paper.
     * @param review the review to add
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }
    
    /**
     * Gets the final decision for the paper.
     * @return the final decision
     */
    public Decision getFinalDecision() {
        return finalDecision;
    }
    
    /**
     * Sets the final decision for the paper.
     * @param finalDecision the final decision to set
     */
    public void setFinalDecision(Decision finalDecision) {
        this.finalDecision = finalDecision;
    }
    
    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject.
     * @return true if all reviews are exclusively Accept or exclusively Reject, false otherwise
     */
    public boolean areReviewsConsensus() {
        if (reviews.isEmpty()) {
            return false;
        }
        
        boolean hasAccept = false;
        boolean hasReject = false;
        
        for (Review review : reviews) {
            if (review.isSubmitted()) {
                if (review.getGrade() == Review.Grade.ACCEPT) {
                    hasAccept = true;
                } else if (review.getGrade() == Review.Grade.REJECT) {
                    hasReject = true;
                }
            }
        }
        
        // Return true only if all reviews are either Accept or Reject, but not both
        return (hasAccept && !hasReject) || (!hasAccept && hasReject);
    }
}

/**
 * Represents a review in the review system.
 * Reviews are submitted by reviewers for papers.
 */
class Review {
 enum Grade {
        ACCEPT, REJECT
    }
    
    private Paper paper;
    private Reviewer reviewer;
    private String feedback;
    private Grade grade;
    private boolean submitted;
    
    /**
     * Default constructor for Review.
     */
    public Review() {
        this.feedback = "";
        this.grade = Grade.REJECT;
        this.submitted = false;
    }
    
    /**
     * Gets the paper associated with this review.
     * @return the paper
     */
    public Paper getPaper() {
        return paper;
    }
    
    /**
     * Sets the paper associated with this review.
     * @param paper the paper to set
     */
    public void setPaper(Paper paper) {
        this.paper = paper;
    }
    
    /**
     * Gets the reviewer who submitted this review.
     * @return the reviewer
     */
    public Reviewer getReviewer() {
        return reviewer;
    }
    
    /**
     * Sets the reviewer who submitted this review.
     * @param reviewer the reviewer to set
     */
    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
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
    
    /**
     * Checks if this review has been submitted.
     * @return true if submitted, false otherwise
     */
    public boolean isSubmitted() {
        return submitted;
    }
    
    /**
     * Sets the submission status of this review.
     * @param submitted the submission status to set
     */
    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }
}