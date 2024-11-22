package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("title", "会員検索");
        return "search";
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
        return "register"; 
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute Member member, Model model, HttpSession session) {
    	session.setAttribute("member", member);
        model.addAttribute("member", member); 
        memberService.addMember(member);
        return "confirm";
    }
    
    @GetMapping("/member-list")
    public String getAllMembers(@RequestParam(required = false) String memberNo,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String phone,
                                 Model model) {
        List<Member> members = memberService.getMembers();
        
        model.addAttribute("members", members);
        return "members";
    }
    
    @GetMapping("/member/{id}")
    public String getMember(@PathVariable("id") int id,Model model) {
    	Member member = memberService.getMemberById(id);
    	model.addAttribute("member",member);
    	return "member";
    }
}
