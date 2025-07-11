package boho.lottonumbergenerator.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boho.lottonumbergenerator.dto.MemberInfoResponse;
import boho.lottonumbergenerator.dto.MemberLottoSearchRequest;
import boho.lottonumbergenerator.dto.MemberLottoSearchResponse;
import boho.lottonumbergenerator.entity.member.Member;
import boho.lottonumbergenerator.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberInfoResponse getMemberInfo(String username) {
		return memberRepository.findMemberInfo(username);
	}

	public Page<MemberLottoSearchResponse> findMemberLotto(Long id, MemberLottoSearchRequest request, Pageable pageable) {
		return memberRepository.findMemberLottoWithPaging(id, request, pageable);
	}

	@Transactional
	public void withdrawMember(Long id) {
		memberRepository.findById(id)
			.ifPresent(Member::withdraw);
	}
}
