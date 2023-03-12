package donga.merchant.web.service;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long addMember(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> result = memberRepository.findByStudentNumber(member.getStudentNumber());

        if(!result.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }
}
