package boho.lottonumbergenerator.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boho.lottonumbergenerator.domain.dto.AdminLottoGenerateRequest;
import boho.lottonumbergenerator.domain.dto.LottoRandomOption;
import boho.lottonumbergenerator.domain.entity.lotto.GeneratedLotto;
import boho.lottonumbergenerator.domain.entity.member.Member;
import boho.lottonumbergenerator.domain.entity.member.MemberRole;
import boho.lottonumbergenerator.domain.entity.member.Role;
import boho.lottonumbergenerator.repository.GeneratedLottoRepository;
import boho.lottonumbergenerator.repository.MemberRoleRepository;
import boho.lottonumbergenerator.repository.RoleRepository;
import boho.lottonumbergenerator.service.AdminService;
import boho.lottonumbergenerator.service.GeneratedLottoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final Random random = new Random();
	private final RoleRepository roleRepository;
	private final MemberRoleRepository memberRoleRepository;
	private final GeneratedLottoRepository generatedLottoRepository;
	private final GeneratedLottoService generatedLottoService;

	@Override
	@Transactional
	public void lottoGenerateByAdmin(AdminLottoGenerateRequest request) {

		List<Member> members = getMembers(request);
		Integer drawNumber = generatedLottoService.getNextDrawNumber();
		LocalDate drawDate = generatedLottoService.getNextDrawDate();

		members.forEach(
			member -> IntStream.rangeClosed(1, request.count())
				.forEach(
					count -> {
						List<Integer> includeNumbers = generateIncludeNumbers(request);
						List<Integer> excludeNumbers = generateExcludeNumbers(request, includeNumbers);

						List<Integer> numbers = Stream.concat(
								Stream.generate(() -> random.nextInt(45) + 1)
									.filter(n -> !includeNumbers.contains(n))
									.filter(n -> !excludeNumbers.contains(n))
									.distinct()
									.limit(6 - includeNumbers.size()),
								includeNumbers.stream()
							)
							.sorted()
							.toList();

						generatedLottoRepository.save(GeneratedLotto.from(
							drawNumber, drawDate, numbers, includeNumbers, excludeNumbers, member));
					}
				));
	}

	private List<Member> getMembers(AdminLottoGenerateRequest request) {
		Role role = roleRepository.findByDescription(request.roleDescription())
			.orElseThrow(() -> new EntityNotFoundException("[" + request.roleDescription() + "] role not found"));

		return memberRoleRepository.findByRole(role)
			.stream()
			.map(MemberRole::getMember)
			.toList();
	}

	private List<Integer> generateIncludeNumbers(AdminLottoGenerateRequest request) {
		if (request.randomOption() == LottoRandomOption.EXCLUDE_ONLY) {
			return List.of();
		}

		int count = random.nextInt(6);
		return IntStream.generate(() -> random.nextInt(45) + 1)
			.distinct()
			.limit(count)
			.boxed()
			.sorted()
			.toList();
	}

	private List<Integer> generateExcludeNumbers(AdminLottoGenerateRequest request, List<Integer> includeNumbers) {
		if (request.randomOption() == LottoRandomOption.INCLUDE_ONLY) {
			return List.of();
		}

		int count = random.nextInt(11);
		return IntStream.generate(() -> random.nextInt(45) + 1)
			.filter(number -> !includeNumbers.contains(number))
			.distinct()
			.limit(count)
			.boxed()
			.sorted()
			.toList();
	}
}
