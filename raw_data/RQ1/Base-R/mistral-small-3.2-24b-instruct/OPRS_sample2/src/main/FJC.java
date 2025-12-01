import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

/**
 * Represents a user in the computer-assisted review system.
 */
class User {
    private String name;

    /**
     * Constructs a new User with an empty name.
     */
    public User() {
        this.name = "";
    }

    /**
     * Constructs a new User with the specified name.
     * @param name the name of the user
     */
    public User(String name) {
        this.name = name;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an author who submits papers.
 */
class Author extends User {
    private List<Paper> submittedPapers;

    /**
     * Constructs a new Author with an empty list of submitted papers.
     */
    public Author() {
        super();
        this.submittedPapers = new ArrayList<>();
    }

    /**
     * Constructs a new Author with the specified name and an empty list of submitted papers.
     * @param name the name of the author
     */
    public Author(String name) {
        super(name);
        this.submittedPapers = new ArrayList<>();
    }

    // Getter for submittedPapers
    public List<Paper> getSubmittedPapers() {
        return submittedPapers;
    }

    /**
     * Adds a paper to the author's list of submitted papers.
     * @param paper the paper to add
     */
    public void addSubmittedPaper(Paper paper) {
        this.submittedPapers.add(paper);
    }

    /**
     * Calculates the total number of papers submitted by the author.
     * @return the total number of submitted papers
     */
    public int countTotalPapersSubmitted() {
        return submittedPapers.size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * @return the acceptance rate as a double between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        if (submittedPapers.isEmpty()) {
            return 0.0;
        }
        int acceptedPapers = 0;
        for (Paper paper : submittedPapers) {
            if (paper.getFinalDecision() == Grade.Accept) {
                acceptedPapers++;
            }
        }
        return (double) acceptedPapers / submittedPapers.size();
    }
}

/**
 * Represents a reviewer who reviews papers.
 */
class Reviewer extends User {
    private List<Review> reviews;

    /**
     * Constructs a new Reviewer with an empty list of reviews.
     */
    public Reviewer() {
        super();
        this.reviews = new ArrayList<>();
    }

    /**
     * Constructs a new Reviewer with the specified name and an empty list of reviews.
     * @param name the name of the reviewer
     */
    public Reviewer(String name) {
        super(name);
        this.reviews = new ArrayList<>();
    }

    // Getter for reviews
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Adds a review to the reviewer's list of reviews.
     * @param review the review to add
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * @return the number of unsubmitted reviews
     */
    public int countUnsubmittedReviews() {
        int unsubmittedReviews = 0;
        for (Review review : reviews) {
            if (!review.isSubmitted()) {
                unsubmittedReviews++;
            }
        }
        return unsubmittedReviews;
    }

    /**
     * Converts the reviewer's historical grades into a numerical average score.
     * @return the average score between 0.0 and 1.0
     */
    public double calculateAverageGrade() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        int totalGrades = 0;
        for (Review review : reviews) {
            if (review.isSubmitted()) {
                totalGrades += review.getGrade().ordinal();
            }
        }
        return (double) totalGrades / reviews.size();
    }
}

/**
 * Represents a paper that can be submitted by an author and reviewed by reviewers.
 */
class Paper {
    private String title;
    private boolean isResearchPaper;
    private List<Reviewer> reviewers;
    private Grade finalDecision;

    /**
     * Constructs a new Paper with an empty title, false for isResearchPaper, an empty list of reviewers, and no final decision.
     */
    public Paper() {
        this.title = "";
        this.isResearchPaper = false;
        this.reviewers = new ArrayList<>();
        this.finalDecision = null;
    }

    /**
     * Constructs a new Paper with the specified title, isResearchPaper, an empty list of reviewers, and no final decision.
     * @param title the title of the paper
     * @param isResearchPaper whether the paper is a research paper
     */
    public Paper(String title, boolean isResearchPaper) {
        this.title = title;
        this.isResearchPaper = isResearchPaper;
        this.reviewers = new ArrayList<>();
        this.finalDecision = null;
    }

    // Getters and Setters for title, isResearchPaper, and finalDecision
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

    public Grade getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(Grade finalDecision) {
        this.finalDecision = finalDecision;
    }

    // Getter for reviewers
    public List<Reviewer> getReviewers() {
        return reviewers;
    }

    /**
     * Adds a reviewer to the paper's list of reviewers.
     * @param reviewer the reviewer to add
     */
    public void addReviewer(Reviewer reviewer) {
        this.reviewers.add(reviewer);
    }

    /**
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject.
     * @return true if all reviews are exclusively Accept or exclusively Reject, false otherwise
     */
    public boolean checkConsistentReviews() {
        if (reviewers.isEmpty()) {
            return false;
        }
        Grade firstGrade = reviewers.get(0).getReviews().get(0).getGrade();
        for (Reviewer reviewer : reviewers) {
            for (Review review : reviewer.getReviews()) {
                if (review.getPaper().equals(this) && review.getGrade() != firstGrade) {
                    return false;
                }
            }
        }
        return true;
    }
}

/**
 * Represents a review submitted by a reviewer for a paper.
 */
class Review {
    private Paper paper;
    private String feedback;
    private Grade grade;
    private boolean submitted;

    /**
     * Constructs a new Review with no paper, empty feedback, no grade, and not submitted.
     */
    public Review() {
        this.paper = null;
        this.feedback = "";
        this.grade = null;
        this.submitted = false;
    }

    /**
     * Constructs a new Review with the specified paper, empty feedback, no grade, and not submitted.
     * @param paper the paper being reviewed
     */
    public Review(Paper paper) {
        this.paper = paper;
        this.feedback = "";
        this.grade = null;
        this.submitted = false;
    }

    // Getters and Setters for paper, feedback, grade, and submitted
    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
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
    Accept,
    Reject
}

/**
 * Represents the chair who makes the final decision for each paper.
 */
class Chair extends User {
    /**
     * Constructs a new Chair with an empty name.
     */
    public Chair() {
        super();
    }

    /**
     * Constructs a new Chair with the specified name.
     * @param name the name of the chair
     */
    public Chair(String name) {
        super(name);
    }

    /**
     * Makes the final decision for a paper.
     * @param paper the paper to make a decision for
     * @param decision the final decision to set
     */
    public void makeFinalDecision(Paper paper, Grade decision) {
        paper.setFinalDecision(decision);
    }
}