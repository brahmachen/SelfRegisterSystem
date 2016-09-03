package beans;

public class StCompleteInfo {

	public StCompleteInfo(){}
	public String sno; // 学号
	public String bedding;// 是否需要卧具
	public String phoneNum; // 联系方式
	public String parentName;// 家长姓名
	public String parentPhoneNum; // 家长联系方式
	public String homeAddr;// 家庭住址
	public String militaryClothing; // 是否购买军训服装
	public int shoeNum; // 鞋号
	public int height;// 身高
	public int weight; // 体重
	public String loan; // 是否办理生源地助学贷款
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getBedding() {
		return bedding;
	}
	public void setBedding(String bedding) {
		this.bedding = bedding;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentPhoneNum() {
		return parentPhoneNum;
	}
	public void setParentPhoneNum(String parentPhoneNum) {
		this.parentPhoneNum = parentPhoneNum;
	}
	public String getHomeAddr() {
		return homeAddr;
	}
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}
	public String getMilitaryClothing() {
		return militaryClothing;
	}
	public void setMilitaryClothing(String militaryClothing) {
		this.militaryClothing = militaryClothing;
	}
	public int getShoeNum() {
		return shoeNum;
	}
	public void setShoeNum(int shoeNum) {
		this.shoeNum = shoeNum;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getLoan() {
		return loan;
	}
	public void setLoan(String loan) {
		this.loan = loan;
	}
	@Override
	public String toString() {
		return "StCompleteInfo [sno=" + sno + ", bedding=" + bedding
				+ ", phoneNum=" + phoneNum + ", parentName=" + parentName
				+ ", parentPhoneNum=" + parentPhoneNum + ", homeAddr="
				+ homeAddr + ", militaryClothing=" + militaryClothing
				+ ", shoeNum=" + shoeNum + ", height=" + height + ", weight="
				+ weight + ", loan=" + loan + "]";
	}
	
}
