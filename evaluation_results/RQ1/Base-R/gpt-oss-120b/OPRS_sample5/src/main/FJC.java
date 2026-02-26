import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Base class for all users of the review system.
 */
class User {
    private String name;

    /** Unparameterized constructor */
    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    /** Getter for name */
    public String getName() {
        return name;
    }

    /** Setter for name */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an author who can submit papers.
 */
class Author extends User {
    private List<Paper> submittedPapers = new ArrayList<>();

    /** Unparameterized constructor */
    public Author() {
        super();
    }

    public Author(String name) {
        super(name);
    }

    /** Adds a paper to the author's list of submissions. */
    public void addPaper(Paper paper) {
        if (paper != null && !submittedPapers.contains(paper)) {
            submittedPapers.add(paper);
            paper.setAuthor(this);
        }
    }

    /** Getter for the list of submitted papers. */
    public List<Paper> getSubmittedPapers() {
        return Collections.unmodifiableList(submittedPapers);
    }

    /** Setter for the list of submitted papers (used mainly for testing). */
    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers == null ? new ArrayList<>() : new ArrayList<>(submittedPapers);
    }

    /**
     * Counts the total number of papers submitted by this author.
     *
     * @return number of submitted papers
     */
    public int getSubmittedPaperCount() {
        return submittedPapers.size();
    }

    /**
     * Calculates the acceptance rate of the author's papers.
     * Acceptance rate = (number of accepted papers) / (total submitted papers).
     *
     * @return acceptance rate as a value between 0.0 and 1.0; 0.0 if no papers submitted
     */
    public double getAcceptanceRate() {
        if (submittedPapers.isEmpty()) {
            return 0.0;
        }
        long accepted = submittedPapers.stream()
                .filter(p -> p.getFinalDecision() == Decision.ACCEPT)
                .count();
        return (double) accepted / submittedPapers.size();
    }
}

/**
 * Represents a reviewer who can be assigned papers and submit reviews.
 */
class Reviewer extends User {
    private List<Paper> assignedPapers = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    /** Unparameterized constructor */
    public Reviewer() {
        super();
    }

    public Reviewer(String name) {
        super(name);
    }

    /** Assigns a paper to this reviewer. */
    public void assignPaper(Paper paper) {
        if (paper != null && !assignedPapers.contains(paper)) {
            assignedPapers.add(paper);
            paper.addAssignedReviewer(this);
        }
    }

    /** Submits a review for a given paper. */
    public void submitReview(Paper paper, String feedback, Grade grade) {
        if (paper == null || grade == null) {
            throw new IllegalArgumentException("Paper and grade must not be null.");
        }
        if (!assignedPapers.contains(paper)) {
            throw new IllegalStateException("Reviewer is not assigned to the given paper.");
        }
        Review review = new Review(this, paper, feedback, grade);
        reviews.add(review);
        paper.addReview(review);
    }

    /** Getter for assigned papers (read‑only). */
    public List<Paper> getAssignedPapers() {
        return Collections.unmodifiableList(assignedPapers);
    }

    /** Setter for assigned papers (used in tests). */
    public void setAssignedPapers(List<Paper> assignedPapers) {
        this.assignedPapers = assignedPapers == null ? new ArrayList<>() : new ArrayList<>(assignedPapers);
    }

    /** Getter for reviews (read‑only). */
    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    /** Setter for reviews (used in tests). */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews == null ? new ArrayList<>() : new ArrayList<>(reviews);
    }

    /**
     * Calculates the number of papers assigned to this reviewer for which a review
     * has not yet been submitted.
     *
     * @return count of unsubmitted reviews (0 if all are submitted)
     */
    public int getUnsubmittedReviewCount() {
        int count = 0;
        for (Paper p : assignedPapers) {
            boolean submitted = reviews.stream()
                    .anyMatch(r -> r.getPaper().equals(p));
            if (!submitted) {
                count++;
            }
        }
        return count;
    }

    /**
     * Computes the average grade score based on the reviewer's historical reviews.
     * Accept = 1, Reject = 0.
     *
     * @return average score between 0.0 and 1.0; 0.0 if the reviewer has no reviews yet
     */
    public double getAverageGradeScore() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Review r : reviews) {
            sum += (r.getGrade() == Grade.ACCEPT) ? 1 : 0;
        }
        return (double) sum / reviews.size();
    }
}

/**
 * Represents the program chair who makes final decisions for papers.
 */
class Chair extends User {
    /** Unparameterized constructor */
    public Chair() {
        super();
    }

    public Chair(String name) {
        super(name);
    }

    /**
     * Checks whether all reviews for the given paper are uniformly Accept or
     * uniformly Reject. Returns true only if every review shares the same grade.
     *
     * @param paper the paper to be examined
     * @return true if all reviews are the same grade, false otherwise
     * @throws IllegalArgumentException if the paper is null
     */
    public boolean canMakeFinalDecision(Paper paper) {
        if (paper == null) {
            throw new IllegalArgumentException("Paper must not be null.");
        }
        return paper.areReviewsUniform();
    }

    /**
     * Sets the final decision for a paper. This method should be called only
     * after {@link #canMakeFinalDecision(Paper)} returns true.
     *
     * @param paper    the paper to decide upon
     * @param decision the final decision (ACCEPT or REJECT)
     * @throws IllegalArgumentException if paper or decision is null
     * @throws IllegalStateException    if the reviews are not uniform
     */
    public void makeFinalDecision(Paper paper, Decision decision) {
        if (paper == null || decision == null) {
            throw new IllegalArgumentException("Paper and decision must not be null.");
        }
        if (!paper.areReviewsUniform()) {
            throw new IllegalStateException("Cannot make final decision: reviews are not uniform.");
        }
        paper.setFinalDecision(decision);
    }
}

/**
 * Represents a paper submitted to the conference.
 */
class Paper {
    private String title;
    private PaperType type;
    private Author author;
    private List<Review> reviews = new ArrayList<>();
    private List<Reviewer> assignedReviewers = new ArrayList<>();
    private Decision finalDecision = Decision.PENDING;

    /** Unparameterized constructor */
    public Paper() {
    }

    public Paper(String title, PaperType type) {
        this.title = title;
        this.type = type;
    }

    /** Getter and setter for title */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /** Getter and setter for type */
    public PaperType getType() {
        return type;
    }

    public void setType(PaperType type) {
        this.type = type;
    }

    /** Getter and setter for author */
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    /** Returns an unmodifiable view of the reviews */
    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    /** Internal method to add a review (called by Reviewer) */
    void addReview(Review review) {
        if (review != null && !reviews.contains(review)) {
            reviews.add(review);
        }
    }

    /** Returns an unmodifiable view of assigned reviewers */
    public List<Reviewer> getAssignedReviewers() {
        return Collections.unmodifiableList(assignedReviewers);
    }

    /** Internal method to record an assigned reviewer */
    void addAssignedReviewer(Reviewer reviewer) {
        if (reviewer != null && !assignedReviewers.contains(reviewer)) {
            assignedReviewers.add(reviewer);
        }
    }

    /** Getter and setter for final decision */
    public Decision getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(Decision finalDecision) {
        this.finalDecision = finalDecision;
    }

    /**
     * Checks whether all reviews for this paper are either all Accept or all Reject.
     *
     * @return true if reviews are uniform, false otherwise (including when there are no reviews)
     */
    public boolean areReviewsUniform() {
        if (reviews.isEmpty()) {
            return false;
        }
        Grade first = reviews.get(0).getGrade();
        for (Review r : reviews) {
            if (r.getGrade() != first) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Represents a review written by a reviewer for a paper.
 */
class Review {
    private Reviewer reviewer;
    private Paper paper;
    private String feedback;
    private Grade grade;

    /** Unparameterized constructor */
    public Review() {
    }

    public Review(Reviewer reviewer, Paper paper, String feedback, Grade grade) {
        this.reviewer = reviewer;
        this.paper = paper;
        this.feedback = feedback;
        this.grade = grade;
    }

    /** Getter and setter for reviewer */
    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    /** Getter and setter for paper */
    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    /** Getter and setter for feedback */
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /** Getter and setter for grade */
    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}

/** Enum describing the type of a paper. */
enum PaperType {
    RESEARCH,
    EXPERIENCE_REPORT
}

/** Enum describing the grade given in a review. */
enum Grade {
    ACCEPT,
    REJECT
}

/** Enum describing the final decision made by the chair. */
enum Decision {
    ACCEPT,
    REJECT,
    PENDING
}