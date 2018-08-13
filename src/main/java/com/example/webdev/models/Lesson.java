package com.example.webdev.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Lesson {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	
	@ManyToOne
	@JsonIgnore
	private Module module;
	
	@OneToMany(mappedBy="lesson")
	private List<Topic> topic;
	
	@OneToMany(mappedBy="lesson")
	@JsonIgnore
	private List<Widget> widget;
	
	@OneToMany(mappedBy="lesson")
	@JsonIgnore
	private List<WidgetNative> widgetNative;
	
	public List<WidgetNative> getWidgetNative() {
		return widgetNative;
	}
	public void setWidgetNative(List<WidgetNative> widgetNative) {
		this.widgetNative = widgetNative;
	}
	
	public List<Topic> getTopic() {
		return topic;
	}
	public void setTopic(List<Topic> topic) {
		this.topic = topic;
	}

	public List<Widget> getWidget() {
		return widget;
	}
	public void setWidget(List<Widget> widget) {
		this.widget = widget;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}

}
