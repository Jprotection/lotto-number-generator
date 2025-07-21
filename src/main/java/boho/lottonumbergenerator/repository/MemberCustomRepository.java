package boho.lottonumbergenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import boho.lottonumbergenerator.domain.dto.MemberInfoResponse;
import boho.lottonumbergenerator.domain.dto.MemberLottoSearchRequest;
import boho.lottonumbergenerator.domain.dto.MemberLottoSearchResponse;

public interface MemberCustomRepository {

	MemberInfoResponse findMemberInfo(String username);

	Page<MemberLottoSearchResponse> findMemberLottoWithPaging(Long id, MemberLottoSearchRequest request, Pageable pageable);
}
