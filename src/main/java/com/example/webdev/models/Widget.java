package com.example.webdev.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Widget {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String text;
	private String widgetType;
	private int orderNum;
	private int size;
	private String items;
	private String widgetName;

	private String paragraphText;
	private String widgetNamePara;

	private int listSelect;
	private String widgetNameList;
	private String listText;
	private String[] listTextToArray;

	private String searchName;
	private String src;
	private String widgetNameImage;
	private String[] imageArray;

	private String linkText;
	private String linkUrl;
	private String linkName;
	
	private String innerPreview;

	public String getInnerPreview() {
		return innerPreview;
	}
	public void setInnerPreview(String innerPreview) {
		this.innerPreview = innerPreview;
	}
	@ManyToOne
	@JsonIgnore
	private Lesson lesson;

	public Lesson getLesson() {
		return lesson;
	}
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public String[] getImageArray() {
		return imageArray;
	}
	public void setImageArray(String[] imageArray) {
		this.imageArray = imageArray;
	}
	public String getLinkText() {
		return linkText;
	}
	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getWidgetNameImage() {
		return widgetNameImage;
	}
	public void setWidgetNameImage(String widgetNameImage) {
		this.widgetNameImage = widgetNameImage;
	}
	public String[] getListTextToArray() {
		return listTextToArray;
	}
	public void setListTextToArray(String[] listTextToArray) {
		this.listTextToArray = listTextToArray;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	public int getListSelect() {
		return listSelect;
	}
	public void setListSelect(int listSelect) {
		this.listSelect = listSelect;
	}
	public String getWidgetNameList() {
		return widgetNameList;
	}
	public void setWidgetNameList(String widgetNameList) {
		this.widgetNameList = widgetNameList;
	}
	public String getListText() {
		return listText;
	}
	public void setListText(String listText) {
		this.listText = listText;
	}
	public String getParagraphText() {
		return paragraphText;
	}
	public void setParagraphText(String paragraphText) {
		this.paragraphText = paragraphText;
	}
	public String getWidgetNamePara() {
		return widgetNamePara;
	}
	public void setWidgetNamePara(String widgetNamePara) {
		this.widgetNamePara = widgetNamePara;
	}
	public String getWidgetName() {
		return widgetName;
	}
	public void setWidgetName(String widgetName) {
		this.widgetName = widgetName;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getWidgetType() {
		return widgetType;
	}
	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}

	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}

}
