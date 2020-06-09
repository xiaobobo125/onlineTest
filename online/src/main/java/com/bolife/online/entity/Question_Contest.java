package com.bolife.online.entity;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/9 18:06
 * @Description:
 */
public class Question_Contest {
    private Integer id;
    private Integer contestId;
    private Integer questionId;

    private Contest contest;
    private List<Question> questions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Question_Contest{" +
                "id=" + id +
                ", contestId=" + contestId +
                ", questionId=" + questionId +
                ", contest=" + contest +
                ", questions=" + questions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question_Contest that = (Question_Contest) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (contestId != null ? !contestId.equals(that.contestId) : that.contestId != null) return false;
        if (questionId != null ? !questionId.equals(that.questionId) : that.questionId != null) return false;
        if (contest != null ? !contest.equals(that.contest) : that.contest != null) return false;
        return questions != null ? questions.equals(that.questions) : that.questions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (contestId != null ? contestId.hashCode() : 0);
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        result = 31 * result + (contest != null ? contest.hashCode() : 0);
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        return result;
    }
}
