package boho.lottonumbergenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import boho.lottonumbergenerator.dto.MemberInfoResponse;
import boho.lottonumbergenerator.dto.MemberLottoSearchRequest;
import boho.lottonumbergenerator.dto.MemberLottoSearchResponse;

public interface MemberCustomRepository {

	MemberInfoResponse findMemberInfo(String username);

	Page<MemberLottoSearchResponse> findMemberLottoWithPaging(Long id, MemberLottoSearchRequest request, Pageable pageable);
}
