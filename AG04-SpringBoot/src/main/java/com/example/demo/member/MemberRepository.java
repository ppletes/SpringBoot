package com.example.demo.member;

import com.example.demo.member.objects.Member;
import com.example.demo.member.viewHelp.GetHeistMembers;
import com.example.demo.member.viewHelp.GetMemberSkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Long type for id
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.email = ?1")
    Optional<Member> findMemberByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.name = ?1")
    Optional<Member> findMemberByName(String name);

    @Query("SELECT m FROM Member m WHERE m.id = ?1")
    List<Member> findMemberListById(Long id);

    GetMemberSkills findMemberById(Long id);

    GetHeistMembers findHeistMembersById(Long id);


}
