package com.example.Todo_App.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Todo_App.model.ToDo;
import com.example.Todo_App.service.ToDoService;



@Controller
public class TodoController {
	@Autowired
    private ToDoService service;

    @GetMapping({"/", "ViewToDoList"})
    public String viewAllToDoItems(Model model, @ModelAttribute("message") String message) {
       
        model.addAttribute("list",service.getAllToDoItens());
        model.addAttribute("msg", message);
        return "ViewToDoList";
    }
    @GetMapping("/updateToDoStatus/{id}")
    public String updateToDoStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean isUpdated = service.updateStatus(id);

        if (isUpdated) {
            redirectAttributes.addFlashAttribute("message", "Update Success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Update Failure: Item not found");
        }

        return "redirect:/ViewToDoList";
    }
    @GetMapping("/addToDoItem")
    public String addToDoItem(Model model) {
        model.addAttribute("todo", new ToDo());
        return "AddToDoItem";
    }
    @PostMapping("/saveToDoItem")
    public String saveToDoItem(ToDo todo, RedirectAttributes redirectAttributes) {
        if (service.saveOrUpdateToDoItem(todo)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/ViewToDoList";
        }
        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addToDoItem";
    }
    @PostMapping("/editSaveToDoItem")
    public String editSaveToDoItem(ToDo todo, RedirectAttributes redirectAttributes) {
        if (service.saveOrUpdateToDoItem(todo)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/ViewToDoList";
        }
        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editToDoItem/" + todo.getId();
    }
    @GetMapping("/editToDoItem/{id}")
    public String editToDoItem(@PathVariable Long id, Model model) { 
    	model.addAttribute("todo", service.getToDoItemById(id));
    return "EditToDoItem";
    }
    @GetMapping("/deleteToDoItem/{id}")
    public String deleteToDoItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = service.deleteToDoItem(id); // Lưu kết quả xóa vào biến

        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Delete Success"); // Thông điệp thành công
        } else {
            redirectAttributes.addFlashAttribute("message", "Delete Failure"); // Thông điệp thất bại
        }

        return "redirect:/ViewToDoList"; // Chuyển hướng đến danh sách ToDo
    }




    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
