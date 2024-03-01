package co.com.nequi.model.config;

import co.com.nequi.model.user.User;
import lombok.Getter;

@Getter
public class OnboardingException extends RuntimeException {
    private final ErrorCode error;
    private final Object object;

    public OnboardingException(ErrorCode error, String message) {
        super(message);
        this.error = error;
        this.object = null;
    }
        public OnboardingException(ErrorCode errorCode) {
        super(errorCode.getLog());
        this.error = errorCode;
        this.object = null;
    }

    public OnboardingException(ErrorCode errorCode, User user) {
        super(errorCode.getLog());
        this.error = errorCode;
        this.object = user;
    }
}
