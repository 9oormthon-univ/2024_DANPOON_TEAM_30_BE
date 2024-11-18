package soon.ready_action.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soon.ready_action.domain.member.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    
}
