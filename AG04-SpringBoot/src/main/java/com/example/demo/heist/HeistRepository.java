package com.example.demo.heist;

import com.example.demo.heist.objects.Heist;
import com.example.demo.heist.objects.MemberHeist;
import com.example.demo.heist.viewHelp.GetHeistSkills;
import com.example.demo.heist.viewHelp.GetHeistStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Long type for id
@Repository
public interface HeistRepository extends JpaRepository<Heist, Long> {

    @Query("SELECT h FROM Heist h WHERE h.name = ?1")
    Optional<Heist> findHeistByName(String name);

    @Query("SELECT h FROM MemberHeist h WHERE h.member_id = ?1")
    List<MemberHeist> findMemberHeistByHeistId(Long id);

    GetHeistSkills findHeistById(Long id);

    GetHeistStatus findHeistStatusById(Long id);

}
