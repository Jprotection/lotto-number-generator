package boho.lottonumbergenerator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boho.lottonumbergenerator.config.security.UsernameDuplicateException;
import boho.lottonumbergenerator.dto.MemberRegisterRequest;
import boho.lottonumbergenerator.entity.member.Member;
import boho.lottonumbergenerator.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;

	@Transactional
	public void registerMember(MemberRegisterRequest request) {

		memberRepository.findByUsername(request.username())
			.ifPresent(member -> {
				throw new UsernameDuplicateException("Member Already Exists with username: " + member.getUsername());
			});

		Member member = memberRepository.save(Member.from(request));
		log.info("New Member Registered - ID: [{}] | username: [{}]", member.getId(), member.getUsername());
	}
}
