package app.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Question implements java.io.Serializable {


	private Integer qid;
	private Integer seid;
	private Integer cid;
	private String content;
	private List<Answer> answers = new ArrayList<Answer>();


	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	/** default constructor */
	public Question() {
	}

	public Integer getSeid() {
		return seid;
	}

	public void setSeid(Integer seid) {
		this.seid = seid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}



	public Integer getQid() {
		return this.qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}



	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}




}