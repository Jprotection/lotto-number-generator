package boho.lottonumbergenerator.repository;

import static boho.lottonumbergenerator.entity.lotto.QGeneratedLotto.*;
import static boho.lottonumbergenerator.entity.member.QMember.*;
import static boho.lottonumbergenerator.entity.member.QMemberTitle.*;
import static boho.lottonumbergenerator.entity.member.QTitle.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import boho.lottonumbergenerator.dto.MemberInfoResponse;
import boho.lottonumbergenerator.dto.MemberLottoSearchRequest;
import boho.lottonumbergenerator.dto.MemberLottoSearchResponse;
import boho.lottonumbergenerator.entity.lotto.GeneratedLotto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

	// 터무니없이 큰 페이지 사이즈 요청에 대해 서버를 보호하기 위한 maxPageSize 설정
	private static final int MAX_PAGE_SIZE = 30;
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

	@Override
	public Page<MemberLottoSearchResponse> findMemberLottoWithPaging(
		Long id, MemberLottoSearchRequest request, Pageable pageable) {

		int validPageSize = Math.min(pageable.getPageSize(), MAX_PAGE_SIZE);
		Pageable validPageable = PageRequest.of(pageable.getPageNumber(), validPageSize, pageable.getSort());

		List<GeneratedLotto> memberLottoList = queryFactory
			.selectFrom(generatedLotto)
			.innerJoin(member).on(generatedLotto.member.eq(member))
			.where(
				member.id.eq(id),
				prizeRankEquals(request.prizeRank()),
				firstIncludeNumberEquals(request.firstIncludeNumber()),
				secondIncludeNumberEquals(request.secondIncludeNumber()),
				thirdIncludeNumberEquals(request.thirdIncludeNumber()))
			.orderBy(getOrderSpecifier(validPageable.getSort()))
			.offset(validPageable.getOffset())
			.limit(validPageable.getPageSize())
			.fetch();

		List<MemberLottoSearchResponse> content = memberLottoList.stream()
			.map(MemberLottoSearchResponse::of)
			.toList();

		JPAQuery<Long> countQuery = queryFactory
			.select(Wildcard.count)
			.from(generatedLotto)
			.innerJoin(member).on(generatedLotto.member.eq(member))
			.where(
				member.id.eq(id),
				prizeRankEquals(request.prizeRank()),
				firstIncludeNumberEquals(request.firstIncludeNumber()),
				secondIncludeNumberEquals(request.secondIncludeNumber()),
				thirdIncludeNumberEquals(request.thirdIncludeNumber()));

		return PageableExecutionUtils.getPage(content, validPageable, countQuery::fetchOne);
	}

	// 정렬 정보 추출
	private OrderSpecifier<?>[] getOrderSpecifier(Sort sort) {
		List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
		for (Sort.Order order : sort) {
			PathBuilder<?> entityPath = new PathBuilder<>(GeneratedLotto.class, "generatedLotto");
			OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
				order.isAscending() ? Order.ASC : Order.DESC,
				entityPath.getComparable(order.getProperty(), Comparable.class)
			);
			orderSpecifiers.add(orderSpecifier);
		}
		return orderSpecifiers.toArray(new OrderSpecifier[0]);
	}

	// 동적 쿼리 메서드 목록
	private BooleanExpression prizeRankEquals(Integer prizeRank) {
		return prizeRank != null ? generatedLotto.prizeRank.eq(prizeRank) : null;
	}

	private BooleanExpression firstIncludeNumberEquals(Integer firstIncludeNumber) {
		return firstIncludeNumber != null ?
			generatedLotto.firstNumber.eq(firstIncludeNumber)
				.or(generatedLotto.secondNumber.eq(firstIncludeNumber))
				.or(generatedLotto.thirdNumber.eq(firstIncludeNumber))
				.or(generatedLotto.fourthNumber.eq(firstIncludeNumber))
				.or(generatedLotto.fifthNumber.eq(firstIncludeNumber))
				.or(generatedLotto.sixthNumber.eq(firstIncludeNumber)) : null;
	}

	private BooleanExpression secondIncludeNumberEquals(Integer secondIncludeNumber) {
		return secondIncludeNumber != null ?
			generatedLotto.firstNumber.eq(secondIncludeNumber)
				.or(generatedLotto.secondNumber.eq(secondIncludeNumber))
				.or(generatedLotto.thirdNumber.eq(secondIncludeNumber))
				.or(generatedLotto.fourthNumber.eq(secondIncludeNumber))
				.or(generatedLotto.fifthNumber.eq(secondIncludeNumber))
				.or(generatedLotto.sixthNumber.eq(secondIncludeNumber)) : null;
	}

	private BooleanExpression thirdIncludeNumberEquals(Integer thirdIncludeNumber) {
		return thirdIncludeNumber != null ?
			generatedLotto.firstNumber.eq(thirdIncludeNumber)
				.or(generatedLotto.secondNumber.eq(thirdIncludeNumber))
				.or(generatedLotto.thirdNumber.eq(thirdIncludeNumber))
				.or(generatedLotto.fourthNumber.eq(thirdIncludeNumber))
				.or(generatedLotto.fifthNumber.eq(thirdIncludeNumber))
				.or(generatedLotto.sixthNumber.eq(thirdIncludeNumber)) : null;
	}
}
