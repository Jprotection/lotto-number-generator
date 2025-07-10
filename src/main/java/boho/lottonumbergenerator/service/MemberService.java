package boho.lottonumbergenerator.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import boho.lottonumbergenerator.dto.MemberInfoRequest;
import boho.lottonumbergenerator.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberInfoRequest getMemberInfo(String username) {
		return memberRepository.findByUsername(username)
			.map(MemberInfoRequest::of)
			.orElseThrow(() -> new UsernameNotFoundException(username + "은(는) 존재하지 않는 아이디입니다"));
	}
}
