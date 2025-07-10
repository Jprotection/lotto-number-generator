package boho.lottonumbergenerator.service;

import org.springframework.stereotype.Service;

import boho.lottonumbergenerator.dto.MemberInfoResponse;
import boho.lottonumbergenerator.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberInfoResponse getMemberInfo(String username) {
		return memberRepository.findMemberInfo(username);
	}
}
