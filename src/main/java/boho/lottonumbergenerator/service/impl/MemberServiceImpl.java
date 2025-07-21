package boho.lottonumbergenerator.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boho.lottonumbergenerator.domain.dto.MemberInfoResponse;
import boho.lottonumbergenerator.domain.dto.MemberLottoSearchRequest;
import boho.lottonumbergenerator.domain.dto.MemberLottoSearchResponse;
import boho.lottonumbergenerator.domain.entity.member.Member;
import boho.lottonumbergenerator.repository.MemberRepository;
import boho.lottonumbergenerator.service.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	@Override
	public MemberInfoResponse getMemberInfo(String username) {
		return memberRepository.findMemberInfo(username);
	}

	@Override
	public Page<MemberLottoSearchResponse> findMemberLotto(Long id, MemberLottoSearchRequest request, Pageable pageable) {
		return memberRepository.findMemberLottoWithPaging(id, request, pageable);
	}

	@Override
	@Transactional
	public void withdrawMember(Long id) {
		memberRepository.findById(id)
			.ifPresent(Member::withdraw);
	}
}
