package com.example.demo.heist;

import com.example.demo.heist.objects.MemberHeist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberHeistRepository extends JpaRepository<MemberHeist, Long> {

    @Query("SELECT mh FROM MemberHeist mh WHERE mh.heist_id=:heist_id AND mh.member_id=:member_id")
    Optional<MemberHeist> findMemberHeist(Long heist_id, Long member_id);
}
