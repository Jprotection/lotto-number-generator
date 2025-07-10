package boho.lottonumbergenerator.repository;

import boho.lottonumbergenerator.dto.MemberInfoResponse;

public interface MemberCustomRepository {

	MemberInfoResponse findMemberInfo(String username);
}
