package com.example.abo.domain.member;

import com.example.abo.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Entity(name = "member")
public class Member extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String knunum;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime lastLoginTime;

    @Builder
    public Member(Long id, String knunum, String password, String name, String phone) {
        this.id = id;
        this.knunum = knunum;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    @Getter
    @Setter
    public static class RequestDto {
        private String knunum;
        private String password;
        private String name;
        private String phone;

        public Member toEntity() {
            return Member.builder()
                    .knunum(knunum)
                    .password(password)
                    .name(name)
                    .phone(phone)
                    .build();
        }
    }

    @Getter
    public static class ResponseDto {
        private Long id;
        private String knunum;
        private String password;
        private String name;
        private String phone;
        private String lastLoginTime;
        private String registerTime;
        private String modifyTime;

        public ResponseDto(Member member) {
            this.id = member.id;
            this.knunum = member.knunum;
            this.password = member.password;
            this.name = member.name;
            this.phone = member.phone;
            this.lastLoginTime = member.toStringDateTime(member.getLastLoginTime());
            this.registerTime = member.toStringDateTime(member.getRegisterTime());
            this.modifyTime = member.toStringDateTime(member.getModifyTime());
        }
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.knunum;
    }

    //????????? ???????????? ?????? ????????? ??????
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection <GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return "????????? ????????? ??????";
        });

        //collectors.add(new SimpleGrantedAuthority("Role"));

        return collectors;
    }

    //????????? ???????????? ???????????? ?????? (true: ?????? ??????)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //????????? ??????????????? ???????????? ??????. (true: ????????? ??????)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //??????????????? ???????????? ???????????? ????????????. (true: ?????? ??????)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //????????? ?????????(????????????)?????? ?????? (true: ?????????)
    @Override
    public boolean isEnabled() {
        return true;
    }
}