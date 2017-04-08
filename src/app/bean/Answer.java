package app.bean;


import net.sf.json.JSONString;


public class Answer implements java.io.Serializable ,JSONString{

	// Fields

	private Integer aid;
	private String acontent;


	public Answer() {

	}	
	public Answer(Integer aid, String acontent) {
		this.aid = aid;
		this.acontent = acontent;
	}


	// Property accessors

	public Integer getAid() {
		return this.aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}


	public String getAcontent() {
		return this.acontent;
	}

	public void setAcontent(String acontent) {
		this.acontent = acontent;
	}

	@Override
	public String toJSONString() {
		return  "{aid:'"+aid+"',acontent:'"+acontent+"'}";
	}

}