package beans;

public class BasicInfo {
	public String sno;// 学号
	public String name;// 姓名
	public String sex;// 性别
	public String examNum;// 考生号
	public String IDNum;// 身份证号
	public String campus;// 校区
	public String college;// 学院
	public String major;// 录取专业
	public String classNum;// 班级
	public String dormNum; // 宿舍
	public String paySta; // 缴费情况
	public String collegeTel;// 学院联系方式
	public String schoolTel; // 学校联系方式
	public String stuPhoto; // 学生照片
	public BasicInfo(){}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getExamNum() {
		return examNum;
	}
	public void setExamNum(String examNum) {
		this.examNum = examNum;
	}
	public String getIDNum() {
		return IDNum;
	}
	public void setIDNum(String iDNum) {
		IDNum = iDNum;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getClassNum() {
		return classNum;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	public String getDormNum() {
		return dormNum;
	}
	public void setDormNum(String dormNum) {
		this.dormNum = dormNum;
	}
	public String getPaySta() {
		return paySta;
	}
	public void setPaySta(String paySta) {
		this.paySta = paySta;
	}
	public String getCollegeTel() {
		return collegeTel;
	}
	public void setCollegeTel(String collegeTel) {
		this.collegeTel = collegeTel;
	}
	public String getSchoolTel() {
		return schoolTel;
	}
	public void setSchoolTel(String schoolTel) {
		this.schoolTel = schoolTel;
	}
	public String getStuPhoto() {
		return stuPhoto;
	}
	public void setStuPhoto(String stuPhoto) {
		this.stuPhoto = stuPhoto;
	}

    public String toString() {
        return "BasicInfo [sno=" + sno + ", name=" + name + ", sex=" + sex
                + ", examNum=" + examNum + ", IDNum=" + IDNum + ", campus="
                + campus + ", college=" + college + ", major=" + major
                + ", classNum=" + classNum + ", dormNum=" + dormNum
                + ", paySta=" + paySta + ", collegeTel=" + collegeTel
                + ", schoolTel=" + schoolTel + ", stuPhoto=" + stuPhoto + "]";
    }
	
}
