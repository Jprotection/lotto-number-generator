package boho.lottonumbergenerator.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import boho.lottonumbergenerator.domain.dto.MemberInfoResponse;
import boho.lottonumbergenerator.domain.dto.MemberLottoSearchRequest;
import boho.lottonumbergenerator.domain.dto.MemberLottoSearchResponse;

public interface MemberService {

	MemberInfoResponse getMemberInfo(String username);

	Page<MemberLottoSearchResponse> findMemberLotto(Long id, MemberLottoSearchRequest request, Pageable pageable);

	void withdrawMember(Long id);
}
