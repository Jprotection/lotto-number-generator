package boho.lottonumbergenerator.repository;

import static boho.lottonumbergenerator.entity.member.QMember.*;
import static boho.lottonumbergenerator.entity.member.QMemberTitle.*;
import static boho.lottonumbergenerator.entity.member.QTitle.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.Map;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import boho.lottonumbergenerator.dto.MemberInfoResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public MemberInfoResponse findMemberInfo(String username) {
		Map<String, MemberInfoResponse> resultMap = queryFactory
			.from(member)
			// memberTitle이 없는 member는 INNER JOIN에 의해 조회되지 않아서 resultMap.get(username) 결과가 null이 됨
			.leftJoin(memberTitle).on(member.eq(memberTitle.member))
			.leftJoin(title).on(memberTitle.title.eq(title))
			.where(member.username.eq(username))
			.transform(groupBy(member.username).as(
				Projections.constructor(MemberInfoResponse.class,
					member.username,
					member.gender,
					member.authority,
					member.createDate,
					member.lastLoginDate,
					// title이 없어도 빈 리스트로 들어감
					list(title.name)
				)
			));

		return resultMap.get(username);
	}
}
