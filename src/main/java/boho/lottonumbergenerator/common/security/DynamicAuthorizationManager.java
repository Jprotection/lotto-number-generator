package boho.lottonumbergenerator.common.security;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

	private static final AuthorizationDecision DENY = new AuthorizationDecision(false);
	private final List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings = new ArrayList<>();
	private final HandlerMappingIntrospector handlerMappingIntrospector;
	private final PersistentUrlRoleMapper urlRoleMapper;

	@PostConstruct
	public void setMappings() {
		urlRoleMapper.getUrlRoleMappings()
			.entrySet()
			.stream()
			.map(entry -> new RequestMatcherEntry<>(
				new MvcRequestMatcher(handlerMappingIntrospector, entry.getKey()),
				selectAuthorizationManager(entry.getValue())))
			.forEach(mappings::add);
	}

	/**
	 * Deprecated 되었지만 여전히 abstract 이므로 구현 필수
	 * 내부적으로 authorize()를 호출해서 기존 방식과 연동
	 */
	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
		AuthorizationResult result = authorize(authentication, context);
		return (result != null && result.isGranted()) ? new AuthorizationDecision(true) :
			new AuthorizationDecision(false);
	}

	@Override
	public AuthorizationResult authorize(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
		for (RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> mapping : this.mappings) {

			RequestMatcher matcher = mapping.getRequestMatcher();
			RequestMatcher.MatchResult matchResult = matcher.matcher(context.getRequest());
			if (matchResult.isMatch()) {
				AuthorizationManager<RequestAuthorizationContext> manager = mapping.getEntry();
				return manager.authorize(authentication,
					new RequestAuthorizationContext(context.getRequest(), matchResult.getVariables()));
			}
		}
		return DENY;
	}

	private AuthorizationManager<RequestAuthorizationContext> selectAuthorizationManager(String role) {
		if (role.startsWith("ROLE_")) {
			return AuthorityAuthorizationManager.hasAuthority(role);
		}
		return new WebExpressionAuthorizationManager(role);
	}
}
