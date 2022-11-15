package com.example.abo.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    String updateMemberLastLoginTime = "update member set last_login_time = NOW() where knunum = :knunum";

    @Transactional
    @Modifying
    @Query(value = updateMemberLastLoginTime, nativeQuery = true)
    public int updateMemberLastLogin(@Param("knunum") String knunum);
    public Optional<Member> findByKnunum(String knunum);
    public int countByKnunumAndPhone(String knunum, String phone);
}