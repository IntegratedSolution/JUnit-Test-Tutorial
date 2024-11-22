package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.Member;

@Mapper
public interface MemberMapper {
	
	 List<Member> getAllMembers();
	 void insertMember(Member member);
	 
	 @Select("SELECT id, member_no memberNo, name, address, phone, gender, comment, created_at createdAt FROM members WHERE id = #{id}")
	 Member getMemberById(int id);

}
