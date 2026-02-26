import java.util.ArrayList;
import java.util.List;

 class User {
    private String name;
    private List<Paper> submittedPapers;
    private List<Review> reviews;

    public User() {
        this.name = "";
        this.submittedPapers = new ArrayList<>();
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addSubmittedPaper(Paper paper) {
        this.submittedPapers.add(paper);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }
}

 class Paper {
    private String title;
    private boolean isResearchPaper;
    private List<Review> reviews;
    private String finalDecision;

    public Paper() {
        this.title = "";
        this.isResearchPaper = true;
        this.reviews = new ArrayList<>();
        this.finalDecision = "";
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

    public String getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }
}

 class Review {
    private String feedback;
    private String grade;
    private boolean isSubmitted;

    public Review() {
        this.feedback = "";
        this.grade = "";
        this.isSubmitted = false;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }
}

 class ReviewSystem {
    private List<User> users;
    private List<Paper> papers;

    public ReviewSystem() {
        this.users = new ArrayList<>();
        this.papers = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Calculates the number of unsubmitted reviews for a given reviewer.
     *
     * @param reviewer The reviewer whose unsubmitted reviews are to be counted.
     * @return The number of unsubmitted reviews. Returns 0 if all reviews are submitted.
     */
    public int countUnsubmittedReviews(User reviewer) {
        int count = 0;
        for (Review review : reviewer.getReviews()) {
            if (!review.isSubmitted()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if all reviews for a paper are either exclusively Accept or exclusively Reject.
     *
     * @param paper The paper to check the reviews for.
     * @return true if all reviews are either Accept or Reject, false otherwise.
     */
    public boolean areReviewsConsistent(Paper paper) {
        boolean allAccept = true;
        boolean allReject = true;

        for (Review review : paper.getReviews()) {
            if (!review.getGrade().equals("Accept")) {
                allAccept = false;
            }
            if (!review.getGrade().equals("Reject")) {
                allReject = false;
            }
        }

        return allAccept || allReject;
    }

    /**
     * Counts the total number of papers submitted by an author.
     *
     * @param author The author whose submitted papers are to be counted.
     * @return The number of papers submitted by the author.
     */
    public int countSubmittedPapers(User author) {
        return author.getSubmittedPapers().size();
    }

    /**
     * Calculates the acceptance rate of papers for an author.
     *
     * @param author The author whose acceptance rate is to be calculated.
     * @return The acceptance rate as a double between 0.0 and 1.0.
     */
    public double calculateAcceptanceRate(User author) {
        if (author.getSubmittedPapers().isEmpty()) {
            return 0.0;
        }

        int acceptedPapers = 0;
        for (Paper paper : author.getSubmittedPapers()) {
            if (paper.getFinalDecision().equals("Accept")) {
                acceptedPapers++;
            }
        }

        return (double) acceptedPapers / author.getSubmittedPapers().size();
    }

    /**
     * Converts a reviewer's historical grades into a numerical average score between 0.0 and 1.0.
     *
     * @param reviewer The reviewer whose grades are to be converted.
     * @return The numerical average score.
     */
    public double calculateReviewerScore(User reviewer) {
        if (reviewer.getReviews().isEmpty()) {
            return 0.0;
        }

        int total = 0;
        for (Review review : reviewer.getReviews()) {
            if (review.getGrade().equals("Accept")) {
                total += 1;
            }
        }

        return (double) total / reviewer.getReviews().size();
    }
}