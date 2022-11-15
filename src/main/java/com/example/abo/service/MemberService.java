package com.example.abo.service;

import com.example.abo.domain.member.Member;
import com.example.abo.domain.member.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Long save(Member.RequestDto requestDto) {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    public Member findByKnunum(String knunum) {
        return memberRepository.findByKnunum(knunum)
                .orElseThrow(() -> new UsernameNotFoundException("Could not found user" + knunum));
    }

    public int countByKnunumAndPhone(String knunum, String phone) {
        return memberRepository.countByKnunumAndPhone(knunum, phone);
    }

    @Override
    public UserDetails loadUserByUsername(String knunum) throws UsernameNotFoundException {
        return memberRepository.findByKnunum(knunum)
                .orElseThrow(() -> new UsernameNotFoundException("Could not found user" + knunum));
    }
}
