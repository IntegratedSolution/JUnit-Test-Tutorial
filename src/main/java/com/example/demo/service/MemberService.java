package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Member;

@Service
public class MemberService {
	@Autowired
    private MemberMapper memberMapper;

	public List<Member> getAllMembers(String memberNo, String name, String phone, String gender) {
	    return memberMapper.getAllMembers(memberNo, name, phone, gender);
	}
	
    public void addMember(Member member) {
    	memberMapper.insertMember(member);
    }

    public Member getMemberById(int id) {
    	return memberMapper.getMemberById(id);
    }
}
