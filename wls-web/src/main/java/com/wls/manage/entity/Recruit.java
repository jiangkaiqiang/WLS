package com.wls.manage.entity;

import java.util.Date;

public class Recruit {
    private Integer id;

    private String title;//招聘信息标题

    private String cover;//封面图片，即列表页图片

    private String company;//发布该招聘信息的公司

    private String industry;//公司所属行业

    private String category;//分类；1：实习；2：全职；3：兼职

    private String province;//工作地点：省市

    private String internship_days;//实习信息需要该字段，一周实习天数

    private String salary;//工资

    private Date publishTime;//信息发布时间

    private String content;//内容（及整个二级页面的html代码）

    private String position;//工作职位：例如软件设计师

    private String address;//工作详细地址

    private Date endTime;//有效时间

    private String education;//学历要求

    private String internship_length;//实习需要该字段  最少实习几个月

    private String source;//信息来源 例如：实习僧

    private String recruit_num;//招聘人数

    private String advantage;//职位诱惑

    private String url;//链接地址，即爬取得数据源地址 参考百度百聘的立即申请

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getInternship_days() {
        return internship_days;
    }

    public void setInternship_days(String internship_days) {
        this.internship_days = internship_days == null ? null : internship_days.trim();
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary == null ? null : salary.trim();
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getInternship_length() {
        return internship_length;
    }

    public void setInternship_length(String internship_length) {
        this.internship_length = internship_length == null ? null : internship_length.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getRecruit_num() {
        return recruit_num;
    }

    public void setRecruit_num(String recruit_num) {
        this.recruit_num = recruit_num == null ? null : recruit_num.trim();
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage == null ? null : advantage.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

	@Override
	public String toString() {
		return "Recruit [id=" + id + ", title=" + title + ", cover=" + cover
				+ ", company=" + company + ", industry=" + industry
				+ ", category=" + category + ", province=" + province
				+ ", internship_days=" + internship_days + ", salary=" + salary
				+ ", publishTime=" + publishTime + ", content=" + content
				+ ", position=" + position + ", address=" + address
				+ ", endTime=" + endTime + ", education=" + education
				+ ", internship_length=" + internship_length + ", source="
				+ source + ", recruit_num=" + recruit_num + ", advantage="
				+ advantage + ", url=" + url + "]";
	}
    
    
}