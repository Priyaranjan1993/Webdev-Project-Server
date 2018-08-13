package com.example.webdev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.webdev.models.Lesson;
import com.example.webdev.models.Widget;
import com.example.webdev.repositories.LessonRepository;
import com.example.webdev.repositories.WidgetRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WidgetService {

	@Autowired
	WidgetRepository repository;
	@Autowired
	LessonRepository lessonRepository;

/*	@GetMapping("/api/widget")
	public List<Widget> findAllWidgets()
	{
		return (List<Widget>) repository.findAll();

	}

	
	@GetMapping("/api/lesson/{lessonId}/widget")
	public List<Widget> findAllWidgetsByLessonId(@PathVariable("lessonId") int lessonId)
	{
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if(data.isPresent()) {
			Lesson lesson = data.get();
			return lesson.getWidget();
		}
		return null;
	}
	
	@GetMapping("/api/widget/{widgetId}")
	public Widget findAllWidgetsByWidgetId(@PathVariable("widgetId") int widgetId)
	{
		Optional<Widget> data = repository.findById(widgetId);
		if(data.isPresent()) {
			Widget widget = data.get();
			return widget;
		}
		return null;
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value="/api/widget/{widgetId}")
	public Widget updateWidgetById(@RequestBody Widget newWidget,@PathVariable("widgetId") int widgetId)
	{
		Optional<Widget> data = repository.findById(widgetId);
		if(data.isPresent()) {
			Widget widget = data.get();
			widget.setText(newWidget.getText());
			widget.setWidgetType(newWidget.getWidgetType());
			widget.setOrderNum(newWidget.getOrderNum());
			widget.setSize(newWidget.getSize());
			widget.setItems(newWidget.getItems());
			widget.setWidgetName(newWidget.getWidgetName());
			widget.setParagraphText(newWidget.getParagraphText());
			widget.setWidgetNamePara(newWidget.getWidgetNamePara());;
			widget.setListSelect(newWidget.getListSelect());
			widget.setWidgetNameList(newWidget.getWidgetNameList());
			widget.setListText(newWidget.getListText());
			widget.setListTextToArray(newWidget.getListTextToArray());
			widget.setSearchName(newWidget.getSearchName());
			widget.setSrc(newWidget.getSrc());
			widget.setWidgetNameImage(newWidget.getWidgetNameImage());
			widget.setImageArray(newWidget.getImageArray());
			widget.setLinkText(newWidget.getLinkText());
			widget.setLinkName(newWidget.getLinkName());
			widget.setLinkUrl(newWidget.getLinkUrl());
			widget.setInnerPreview(newWidget.getInnerPreview());

			repository.save(widget);
			return widget;
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/api/widget/{widgetId}")
	public void deleteWidget(@PathVariable("widgetId") int widgetId)
	{
		repository.deleteById(widgetId);
	}
	

	@PostMapping("/api/lesson/{lessonId}/widget")
	public List<String> saveAllWidgets(@RequestBody List<Widget> widgets,@PathVariable("lessonId") int lessonId) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		List<String> result = new ArrayList<String>();
		//Set<String> names = new HashSet<String>();
		ArrayList<String> header = new ArrayList<String>();
		ArrayList<String> para = new ArrayList<String>();
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> image = new ArrayList<String>();
		ArrayList<String> link = new ArrayList<String>();
		Boolean headerCheck = false;
		Boolean paraCheck = false;
		Boolean listCheck = false;
		Boolean imageCheck = false;
		Boolean linkCheck = false;
		Boolean commonCheck = false;
		if(data.isPresent()) {
			Lesson lesson = data.get();
			for(Widget w : widgets)
			{
				if(w.getWidgetType().equals("Heading"))
				{
					header.add(w.getWidgetName());
				}
				if(w.getWidgetType().equals("Paragraph"))
				{
					para.add(w.getWidgetNamePara());
				}
				if(w.getWidgetType().equals("List"))
				{
					list.add(w.getWidgetNameList());
				}
				if(w.getWidgetType().equals("Image"))
				{
					image.add(w.getWidgetNameImage());
				}
				if(w.getWidgetType().equals("Link"))
				{
					link.add(w.getLinkName());
				}

				
				for(String str : header) {
					if((para.contains(str)) || list.contains(str) || image.contains(str) || link.contains(str))
					{
						commonCheck = true;
					}
				}
				
				for(String str : para) {
					if((header.contains(str)) || list.contains(str) || image.contains(str) || link.contains(str))
					{
						commonCheck = true;
					}
				}
				
				for(String str : list) {
					if((para.contains(str)) || header.contains(str) || image.contains(str) || link.contains(str))
					{
						commonCheck = true;
					}
				}
				
				for(String str : image) {
					if((para.contains(str)) || list.contains(str) || header.contains(str) || link.contains(str))
					{
						commonCheck = true;
					}
				}
				
				for(String str : link) {
					if((para.contains(str)) || list.contains(str) || image.contains(str) || header.contains(str))
					{
						commonCheck = true;
					}
				}
				
				for(int m = 0; m<header.size(); m++)
				{
					for(int n=m+1; n< header.size();n++)
					{
						if(header.get(m).equalsIgnoreCase(header.get(n)))
						{
							headerCheck = true;
						}
					}
				}
				
				for(int a = 0; a<para.size(); a++)
				{
					for(int b=a+1; b< para.size();b++)
					{
						if(para.get(a).equalsIgnoreCase(para.get(b)))
						{
							paraCheck = true;
						}
					}
				}
				
				for(int c = 0; c<list.size(); c++)
				{
					for(int d=c+1; d< list.size();d++)
					{
						if(list.get(c).equalsIgnoreCase(list.get(d)))
						{
							listCheck = true;
						}
					}
				}
				
				for(int e = 0; e<image.size(); e++)
				{
					for(int f=e+1; f< image.size();f++)
					{
						if(image.get(e).equalsIgnoreCase(image.get(f)))
						{
							imageCheck = true;
						}
					}
				}
				
				for(int g = 0; g<link.size(); g++)
				{
					for(int h=g+1; h< link.size();h++)
					{
						if(link.get(g).equalsIgnoreCase(link.get(h)))
						{
							linkCheck = true;
						}
					}
				}
				
			}
			
			if(headerCheck || paraCheck || listCheck || imageCheck || linkCheck|| commonCheck)
			{
				String str = "error";
				result.add(str);
				return result;
			}
			else {
				repository.deleteByLessonId(lesson.getId());
				for(Widget widget: widgets) {
					widget.setLesson(lesson);
					repository.save(widget);
				}
				String str2 = "success";
				result.add(str2);
				return result;
			}
		}
		return null;
	}*/
}