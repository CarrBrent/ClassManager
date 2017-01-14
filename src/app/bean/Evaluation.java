package app.bean;

public class Evaluation {
	int seId;
	int object;//student ID
	int eeId;
	String evalRank;
	public Evaluation(int seId,int sId,int eeId,String evalRank){
		this.setEeId(eeId);
		this.setSeId(seId);
		this.setObject(sId);
		this.setEvalRank(evalRank);
		
	}
	
	public int getObject() {
		return object;
	}

	public void setObject(int object) {
		this.object = object;
	}

	public int getSeId() {
		return seId;
	}
	public void setSeId(int seId) {
		this.seId = seId;
	}
	public int getEeId() {
		return eeId;
	}
	public void setEeId(int eeId) {
		this.eeId = eeId;
	}
	public String getEvalRank() {
		return evalRank;
	}
	public void setEvalRank(String evalRank) {
		this.evalRank = evalRank;
	}

	
	

}
