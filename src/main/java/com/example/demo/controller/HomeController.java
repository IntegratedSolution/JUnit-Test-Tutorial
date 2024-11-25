package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Member;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	MemberService memberService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "ホームページ");
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(@ModelAttribute("member") Member member, Model model) {    	 
    	if (member == null) {
             member = new Member();
         }
         model.addAttribute("member", member);
         return "register"; 
    }
    
    @GetMapping("/register/confirm")
    public String showRegistrationFormWithData(HttpSession session, Model model) {
    	 Member member = (Member) session.getAttribute("member");
    	 if (member == null) {
             member = new Member();
         }
         model.addAttribute("member", member);
        return "confirm"; 
    }
    
    @PostMapping("/register/confirm")
    public String registerConfirm(Model model, HttpSession session) {
    	Member member = (Member) session.getAttribute("member");
        if (member == null) {
            member = new Member(); 
        }
        memberService.addMember(member); 
        session.removeAttribute("member"); 
        model.addAttribute("member", member);
        return "redirect:/member-list";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute Member member,HttpSession session,Model model) {
    	session.setAttribute("member", member);
        if (member.getName() == null || member.getName().isEmpty()) {
            model.addAttribute("nameError", "名前は必須です。");
        }

        // Validate the phone field
        if (member.getPhone() == null ) { //|| !member.getPhone().matches("\\d{10}")
            model.addAttribute("phoneError", "電話番号は10桁の数字である必要があります。");
        }
        
        // Validate the address field
        if (member.getAddress()== null ||member.getName().isEmpty()) {
            model.addAttribute("addressError", "住所は必須です。");
        }

        // Validate the gender field
        if (member.getGender() == null || member.getGender().isEmpty()) {
            model.addAttribute("genderError", "性別は必須です。");
        }

        if (model.containsAttribute("nameError") || model.containsAttribute("phoneError") || model.containsAttribute("genderError")) {
            return "register";
        }

    	 return "redirect:/register/confirm"; 
    }
    
    @GetMapping("/register/back")
    public String backToRegistrationForm(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            member = new Member(); 
        }
        model.addAttribute("member", member);
        return "register";  
    }

    
    @GetMapping("/member-list")
    public String getAllMembers(@RequestParam(required = false) String memberNo,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String phone,
                                 @RequestParam(required = false) String gender,
                                 Model model) {
        List<Member> members = memberService.getAllMembers(memberNo, name, phone, gender);

        model.addAttribute("members", members);
        model.addAttribute("title", "会員検索");

        return "members";
    }
    
    @GetMapping("/member/{id}")
    public String getMember(@PathVariable("id") int id,Model model) {
    	Member member = memberService.getMemberById(id);
    	model.addAttribute("member",member);
    	return "member";
    }
}
