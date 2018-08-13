package com.example.webdev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webdev.models.Assignment;
import com.example.webdev.models.BaseExamQuestion;
import com.example.webdev.models.EssayExamQuestion;
import com.example.webdev.models.Exam;
import com.example.webdev.models.FillInTheBlanksExamQuestion;
import com.example.webdev.models.Lesson;
import com.example.webdev.models.MultipleChoiceExamQuestion;
import com.example.webdev.models.TrueOrFalseExamQuestion;
import com.example.webdev.models.WidgetNative;
import com.example.webdev.repositories.AssignmentRepository;
import com.example.webdev.repositories.BaseExamQuestionRepository;
import com.example.webdev.repositories.EssayRepository;
import com.example.webdev.repositories.ExamRepository;
import com.example.webdev.repositories.FillInTheBlanksRepository;
import com.example.webdev.repositories.LessonRepository;
import com.example.webdev.repositories.MultipleChoiceQuestionRepository;
import com.example.webdev.repositories.TrueFalseRepository;
import com.example.webdev.repositories.WidgetNativeRepository;




@RestController
@CrossOrigin(origins = "*")
public class WidgetNativeService {
	@Autowired
	AssignmentRepository assignmentRepository;
	@Autowired
	LessonRepository lessonRepository;
	@Autowired
	WidgetNativeRepository widgetNativeRepository;
	@Autowired
	EssayRepository essayRepository;
	@Autowired
	ExamRepository examRepository;
	@Autowired
	TrueFalseRepository trueFalseRepository;
	@Autowired
	MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
	@Autowired
	FillInTheBlanksRepository fillInTheBlanksRepository;
	@Autowired
	BaseExamQuestionRepository baseExamQuestionRepository;


	@GetMapping("/api/assignment")
	public Iterable<Assignment> findAllAssigments() {
		return assignmentRepository.findAll();
	}

	@GetMapping("/api/assignment/{aid}")	
	public Assignment findAssignmentById(@PathVariable("aid") int id) {
		Optional<Assignment> data = assignmentRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}

	@DeleteMapping("/api/assignment/{aid}")
	public void deleteAssignment(@PathVariable("aid") int assignmentId) {
		assignmentRepository.deleteById(assignmentId);
	}

	@PostMapping("/api/lesson/{lid}/assignment")
	public Assignment createAssignment(@RequestBody Assignment newAssignment,@PathVariable("lid") int lessonId) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if(data.isPresent()) {
			Lesson lesson = data.get();
			newAssignment.setLesson(lesson);
			return assignmentRepository.save(newAssignment);
		}
		return null;
	}
	
	@PostMapping("/api/lesson/assignment/{wid}")
	public Assignment updateAssignment(@RequestBody Assignment assignmentInfo,@PathVariable("wid") int widgetId) {
		Optional<Assignment> data = assignmentRepository.findById(widgetId);
		if(data.isPresent()) {
			Assignment oldAssignment = data.get();
			oldAssignment.setPoints(assignmentInfo.getPoints());
			oldAssignment.setDescription(assignmentInfo.getDescription());
			oldAssignment.setTitle(assignmentInfo.getTitle());
			return assignmentRepository.save(oldAssignment);
		}
		return null;

	}

	@GetMapping("/api/lesson/{lid}/assignment")
	public List<WidgetNative> findAllAssigmentsForLesson(@PathVariable("lid") int lessonId) 
	{
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if(data.isPresent()) {
			Lesson lesson = data.get();
			ArrayList<WidgetNative> assignment = new ArrayList<WidgetNative>();
			ArrayList<WidgetNative> exam = new ArrayList<WidgetNative>();
			String str = "Assignment";
			//return (List<WidgetNative>) widgetNativeRepository.findAssignmentByLesson(lessonId);
			for(WidgetNative w : lesson.getWidgetNative())
			{
				if(w.getWidgetType()!= null)
				{
					if(w.getWidgetType().equalsIgnoreCase(str))
					{
						assignment.add(w);
					}
					else {
						exam.add(w);
					}
				}
			}
			return assignment;
		}
		return null;
	}

	@GetMapping("/api/lesson/{lid}/exam")
	public List<WidgetNative> findAllExamsForLesson(@PathVariable("lid") int lessonId) 
	{
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if(data.isPresent()) {
			Lesson lesson = data.get();
			ArrayList<WidgetNative> assignment = new ArrayList<WidgetNative>();
			ArrayList<WidgetNative> exam = new ArrayList<WidgetNative>();
			String str = "Assignment";
			//return (List<WidgetNative>) widgetNativeRepository.findAssignmentByLesson(lessonId);
			for(WidgetNative w : lesson.getWidgetNative())
			{
				if(w.getWidgetType()!= null)
				{
					if(w.getWidgetType().equalsIgnoreCase(str))
					{
						assignment.add(w);
					}
					else {
						exam.add(w);
					}
				}
			}
			return exam;
		}
		return null;
	}


	@GetMapping("/api/lesson/{lid}/widgetType/{wid}")
	public WidgetNative findWidgetType(@PathVariable("lid") int lessonId,@PathVariable("wid") int widgetId) 
	{
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if(data.isPresent()) {
			Lesson lesson = data.get();
			//ArrayList<WidgetNative> widgetList = new ArrayList<WidgetNative>();
			//widgetList = (ArrayList<WidgetNative>) lesson.getWidgetNative();
			for(WidgetNative w : lesson.getWidgetNative())
			{
				if(w.getWidgetType()!= null && w.getId()==widgetId)
				{
					return w;
				}
			}
		}
		return null;
	}


	/*	@RequestMapping(method = RequestMethod.GET, value="/api/lesson/widgetType/{wid}",produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody*/
	@GetMapping("/api/lesson/widgetType/{wid}")
	public List<String> findExamTypeById(@PathVariable("wid") int widgetId) 
	{
		Optional<BaseExamQuestion> data = baseExamQuestionRepository.findByExamId(widgetId);
		if(data.isPresent()) {
			List<String> ls = new ArrayList<String>();
			BaseExamQuestion question = data.get();
			ls.add(question.getType());
			return ls;
		}
		return null;
	}


	@GetMapping("/api/lesson/{lessonId}/widget")
	public List<WidgetNative> findAllWidgetsForLesson(@PathVariable("lessonId") int lessonId) {
		Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
		if(optionalLesson.isPresent()) {
			Lesson lesson = optionalLesson.get();
			return lesson.getWidgetNative();
		}
		return null;
	}


	@PostMapping("/api/lesson/{lid}/exam")
	public Exam createExam(@RequestBody Exam examWidgetInfo,@PathVariable("lid") int lessonId) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if(data.isPresent()) {
			Lesson lesson = data.get();
			examWidgetInfo.setLesson(lesson);
			return examRepository.save(examWidgetInfo);
		}
		return null;
	}

	@PostMapping("/api/lesson/exam/{wid}")
	public Exam updateExam(@RequestBody Exam examWidgetInfo,@PathVariable("wid") int widgetId) {
		Optional<Exam> data = examRepository.findById(widgetId);
		if(data.isPresent()) {
			Exam oldExam = data.get();
			oldExam.setPoints(examWidgetInfo.getPoints());
			oldExam.setDescription(examWidgetInfo.getDescription());
			oldExam.setTitle(examWidgetInfo.getTitle());
			return examRepository.save(oldExam);
		}
		return null;

	}

	@PostMapping("/api/exam/{eid}/essay")
	public EssayExamQuestion createEssay(@RequestBody EssayExamQuestion essay,@PathVariable("eid") int examId) {
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			essay.setExam(exam);
			return essayRepository.save(essay);
		}
		return null;
	}

	@DeleteMapping("/api/essay/{eid}")
	public void deleteEssay(@PathVariable("eid") int essayId) {
		Optional<EssayExamQuestion> data = essayRepository.findByExamId(essayId);
		if(data.isPresent())
		{
			EssayExamQuestion e = data.get();
			essayRepository.deleteById(essayId);
		}
	}

	@PostMapping("/api/exam/essay/{wid}")
	public EssayExamQuestion updateEssay(@RequestBody EssayExamQuestion essay,@PathVariable("wid") int widgetId) {
		Optional<EssayExamQuestion> ex = essayRepository.findByExamId(widgetId);
		if(ex.isPresent())
		{
			EssayExamQuestion oldEssay = ex.get();
			oldEssay.setPoints(essay.getPoints());
			oldEssay.setDescription(essay.getDescription());
			oldEssay.setTitle(essay.getTitle());
			return essayRepository.save(oldEssay);
		}
		return null;
	}

	@PostMapping("/api/exam/{eid}/truefalse")
	public TrueOrFalseExamQuestion createTrueFalse(@RequestBody TrueOrFalseExamQuestion trueFalse,@PathVariable("eid") int examId) {
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			trueFalse.setExam(exam);
			return trueFalseRepository.save(trueFalse);
		}
		return null;
	}

	@DeleteMapping("/api/trueFalse/{tfId}")
	public void deleteTrueFalse(@PathVariable("tfId") int trueFalseId) {
		Optional<TrueOrFalseExamQuestion> data = trueFalseRepository.findByExamId(trueFalseId);
		if(data.isPresent())
		{
			trueFalseRepository.deleteById(trueFalseId);
		}
	}

	

	@PostMapping("/api/exam/trueFalse/{wid}")
	public TrueOrFalseExamQuestion updateTrueFalse(@RequestBody TrueOrFalseExamQuestion trueFalse,@PathVariable("wid") int widgetId) {
		Optional<TrueOrFalseExamQuestion> ex = trueFalseRepository.findByExamId(widgetId);
		if(ex.isPresent())
		{
			TrueOrFalseExamQuestion oldTrueFalse = ex.get();
			oldTrueFalse.setPoints(trueFalse.getPoints());
			oldTrueFalse.setDescription(trueFalse.getDescription());
			oldTrueFalse.setTitle(trueFalse.getTitle());
			oldTrueFalse.setAnswer(trueFalse.isAnswer());
			return trueFalseRepository.save(oldTrueFalse);
		}
		return null;
	}

	@PostMapping("/api/exam/{eid}/choice")
	public MultipleChoiceExamQuestion createChoice(@RequestBody MultipleChoiceExamQuestion multiple,@PathVariable("eid") int examId) {
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			multiple.setExam(exam);
			return multipleChoiceQuestionRepository.save(multiple);
		}
		return null;
	}

	@DeleteMapping("/api/choice/{mcqid}")
	public void deleteChoice(@PathVariable("mcqid") int mcqid) {
		Optional<MultipleChoiceExamQuestion> data = multipleChoiceQuestionRepository.findByExamId(mcqid);
		if(data.isPresent())
		{
			MultipleChoiceExamQuestion e = data.get();
			multipleChoiceQuestionRepository.deleteById(mcqid);
		}
	}

	@PostMapping("/api/exam/choice/{wid}")
	public MultipleChoiceExamQuestion updateChoice(@RequestBody MultipleChoiceExamQuestion mcq,@PathVariable("wid") int widgetId) {
		Optional<MultipleChoiceExamQuestion> ex = multipleChoiceQuestionRepository.findByExamId(widgetId);
		if(ex.isPresent())
		{
			MultipleChoiceExamQuestion oldMcq= ex.get();
			oldMcq.setPoints(mcq.getPoints());
			oldMcq.setOptions(mcq.getOptions());
			oldMcq.setTitle(mcq.getTitle());
			oldMcq.setDescription(mcq.getDescription());
			oldMcq.setCorrectOption(mcq.getCorrectOption());
			return multipleChoiceQuestionRepository.save(oldMcq);
		}
		return null;
	}

	@PostMapping("/api/exam/{eid}/blanks")
	public FillInTheBlanksExamQuestion createFillInTheBlanks(@RequestBody FillInTheBlanksExamQuestion blanks,@PathVariable("eid") int examId) {
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			blanks.setExam(exam);
			return fillInTheBlanksRepository.save(blanks);
		}
		return null;
	}
	
	@DeleteMapping("/api/fillInTheBlanks/{fid}")
	public void deleteFillInTheBlanks(@PathVariable("fid") int fId) {
		Optional<FillInTheBlanksExamQuestion> data = fillInTheBlanksRepository.findByExamId(fId);
		if(data.isPresent())
		{
			FillInTheBlanksExamQuestion e = data.get();
			fillInTheBlanksRepository.deleteById(fId);
		}
	}

	@PostMapping("/api/exam/fillInTheBlanks/{wid}")
	public FillInTheBlanksExamQuestion updateBlanks(@RequestBody FillInTheBlanksExamQuestion fib,@PathVariable("wid") int widgetId) {
		Optional<FillInTheBlanksExamQuestion> ex = fillInTheBlanksRepository.findByExamId(widgetId);
		if(ex.isPresent())
		{
			FillInTheBlanksExamQuestion oldfib = ex.get();
			oldfib.setPoints(fib.getPoints());
			oldfib.setQuestion(fib.getQuestion());
			oldfib.setTitle(fib.getTitle());
			oldfib.setDescription(fib.getDescription());
			oldfib.setVariable(fib.getVariable());
			return fillInTheBlanksRepository.save(oldfib);
		}
		return null;
	}

	@GetMapping("/api/exam")
	public Iterable<Exam> findAllExams() {
		return examRepository.findAll();
	}

	@GetMapping("/api/exam/{eid}")	
	public Exam findExamById(@PathVariable("eid") int id) {
		Optional<Exam> data = examRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}

	@GetMapping("/api/question/{wid}")	
	public BaseExamQuestion findQuestionById(@PathVariable("wid") int id) {
		Optional<BaseExamQuestion> data = baseExamQuestionRepository.findByExamId(id);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}

	@DeleteMapping("/api/exam/{eid}")
	public void deleteExam(@PathVariable("eid") int examId) {
		examRepository.deleteById(examId);
	}

}