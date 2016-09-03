package beans;

public class TrafficInfo {

	public TrafficInfo(){}
	public String sno;// 学号
	public String trafficWay;// 到校方式
	public int company;// 同行人数
	public String registerTime;// 预计报到时间
	public String campus;



	public String registerDate1;
	public String registerDate2;
	public String registerDate3;
	public String maxDate;
	public String getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	public String getRegisterDate1() {
		return registerDate1;
	}
	public void setRegisterDate1(String registerDate1) {
		this.registerDate1 = registerDate1;
	}
	public String getRegisterDate2() {
		return registerDate2;
	}
	public void setRegisterDate2(String registerDate2) {
		this.registerDate2 = registerDate2;
	}
	public String getRegisterDate3() {
		return registerDate3;
	}
	public void setRegisterDate3(String registerDate3) {
		this.registerDate3 = registerDate3;
	}

	//	public String countRegisterTimeCount;//数据库中无该字段，计算某时刻预计报的人数
//	
//	public String getCountRegisterTimeCount() {
//		return countRegisterTimeCount;
//	}
//	public void setCountRegisterTimeCount(String countRegisterTimeCount) {
//		this.countRegisterTimeCount = countRegisterTimeCount;
//	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getTrafficWay() {
		return trafficWay;
	}
	public void setTrafficWay(String trafficWay) {
		this.trafficWay = trafficWay;
	}

	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	
}
