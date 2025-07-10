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
			.innerJoin(memberTitle).on(member.eq(memberTitle.member))
			.innerJoin(title).on(memberTitle.title.eq(title))
			.where(member.username.eq(username))
			.transform(groupBy(member.username).as(
				Projections.constructor(MemberInfoResponse.class,
					member.username,
					member.gender,
					member.authority,
					member.createDate,
					member.lastLoginDate,
					list(title.name)
				)
			));

		return resultMap.get(username);
	}
}
