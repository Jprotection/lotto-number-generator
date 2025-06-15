package boho.lottonumbergenerator.repository;

import static boho.lottonumbergenerator.entity.lotto.QOfficialLotto.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import boho.lottonumbergenerator.dro.OfficialLottoSearchRequest;
import boho.lottonumbergenerator.dro.OfficialLottoSearchResponse;
import boho.lottonumbergenerator.entity.lotto.OfficialLotto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OfficialLottoCustomRepositoryImpl implements OfficialLottoCustomRepository {

	// 터무니없이 큰 페이지 사이즈 요청에 대해 서버를 보호하기 위한 maxPageSize 설정
	private static final int MAX_PAGE_SIZE = 30;
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<OfficialLottoSearchResponse> findOfficialLottoWithPaging(
		OfficialLottoSearchRequest request, Pageable pageable) {

		int validPageSize = Math.min(pageable.getPageSize(), MAX_PAGE_SIZE);
		Pageable validPageable = PageRequest.of(pageable.getPageNumber(), validPageSize, pageable.getSort());

		List<OfficialLottoSearchResponse> content = queryFactory
			.select(Projections.constructor(OfficialLottoSearchResponse.class,
				officialLotto.drawNumber,
				officialLotto.drawDate,
				officialLotto.firstPrizeWinnerCount,
				officialLotto.firstPrizeAmount,
				officialLotto.totalSalesAmount,
				officialLotto.firstNumber,
				officialLotto.secondNumber,
				officialLotto.thirdNumber,
				officialLotto.fourthNumber,
				officialLotto.fifthNumber,
				officialLotto.sixthNumber,
				officialLotto.bonusNumber))
			.from(officialLotto)
			.where(
				drawNumberGoe(request.startDrawNumber()),
				drawNumberLoe(request.endDrawNumber()),
				drawDateGoe(request.startDrawDate()),
				drawDateLoe(request.endDrawDate()),
				firstIncludeNumberEquals(request.firstIncludeNumber()),
				secondIncludeNumberEquals(request.secondIncludeNumber()),
				thirdIncludeNumberEquals(request.thirdIncludeNumber()),
				bonusNumberEquals(request.bonusNumber()))
			.orderBy(getOrderSpecifier(validPageable.getSort()))
			.offset(validPageable.getOffset())
			.limit(validPageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(Wildcard.count)
			.from(officialLotto)
			.where(
				drawNumberGoe(request.startDrawNumber()),
				drawNumberLoe(request.endDrawNumber()),
				drawDateGoe(request.startDrawDate()),
				drawDateLoe(request.endDrawDate()),
				firstIncludeNumberEquals(request.firstIncludeNumber()),
				secondIncludeNumberEquals(request.secondIncludeNumber()),
				thirdIncludeNumberEquals(request.thirdIncludeNumber()),
				bonusNumberEquals(request.bonusNumber()));

		return PageableExecutionUtils.getPage(content, validPageable, countQuery::fetchOne);
	}

	// 정렬 정보 추출
	private OrderSpecifier<?>[] getOrderSpecifier(Sort sort) {
		List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
		for (Sort.Order order : sort) {
			PathBuilder<?> entityPath = new PathBuilder<>(OfficialLotto.class, "officialLotto");
			OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
				order.isAscending() ? Order.ASC : Order.DESC,
				entityPath.getComparable(order.getProperty(), Comparable.class)
			);
			orderSpecifiers.add(orderSpecifier);
		}
		return orderSpecifiers.toArray(new OrderSpecifier[0]);
	}

	// 동적 쿼리 메서드 목록
	private BooleanExpression drawNumberGoe(Long drawNumber) {
		return drawNumber != null ? officialLotto.drawNumber.goe(drawNumber) : null;
	}

	private BooleanExpression drawNumberLoe(Long drawNumber) {
		return drawNumber != null ? officialLotto.drawNumber.loe(drawNumber) : null;
	}

	private BooleanExpression drawDateGoe(LocalDate drawDate) {
		return drawDate != null ? officialLotto.drawDate.goe(drawDate) : null;
	}

	private BooleanExpression drawDateLoe(LocalDate drawDate) {
		return drawDate != null ? officialLotto.drawDate.loe(drawDate) : null;
	}

	private BooleanExpression firstIncludeNumberEquals(Integer firstIncludeNumber) {
		return firstIncludeNumber != null ?
			officialLotto.firstNumber.eq(firstIncludeNumber)
				.or(officialLotto.secondNumber.eq(firstIncludeNumber))
				.or(officialLotto.thirdNumber.eq(firstIncludeNumber))
				.or(officialLotto.fourthNumber.eq(firstIncludeNumber))
				.or(officialLotto.fifthNumber.eq(firstIncludeNumber))
				.or(officialLotto.sixthNumber.eq(firstIncludeNumber)) : null;
	}

	private BooleanExpression secondIncludeNumberEquals(Integer secondIncludeNumber) {
		return secondIncludeNumber != null ?
			officialLotto.firstNumber.eq(secondIncludeNumber)
				.or(officialLotto.secondNumber.eq(secondIncludeNumber))
				.or(officialLotto.thirdNumber.eq(secondIncludeNumber))
				.or(officialLotto.fourthNumber.eq(secondIncludeNumber))
				.or(officialLotto.fifthNumber.eq(secondIncludeNumber))
				.or(officialLotto.sixthNumber.eq(secondIncludeNumber)) : null;
	}

	private BooleanExpression thirdIncludeNumberEquals(Integer thirdIncludeNumber) {
		return thirdIncludeNumber != null ?
			officialLotto.firstNumber.eq(thirdIncludeNumber)
				.or(officialLotto.secondNumber.eq(thirdIncludeNumber))
				.or(officialLotto.thirdNumber.eq(thirdIncludeNumber))
				.or(officialLotto.fourthNumber.eq(thirdIncludeNumber))
				.or(officialLotto.fifthNumber.eq(thirdIncludeNumber))
				.or(officialLotto.sixthNumber.eq(thirdIncludeNumber)) : null;
	}

	private BooleanExpression bonusNumberEquals(Integer bonusNumber) {
		return bonusNumber != null ? officialLotto.bonusNumber.eq(bonusNumber) : null;
	}
}
