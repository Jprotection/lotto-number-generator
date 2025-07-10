package boho.lottonumbergenerator.common.event;

import org.springframework.context.ApplicationEvent;

public class MemberRegisterSuccessEvent extends ApplicationEvent {

	public MemberRegisterSuccessEvent(Object source) {
		super(source);
	}
}
