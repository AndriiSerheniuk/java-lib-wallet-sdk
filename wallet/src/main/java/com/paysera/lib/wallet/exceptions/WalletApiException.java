package com.paysera.lib.wallet.exceptions;

import com.paysera.lib.wallet.entities.RecaptchaHeaders;
import com.paysera.lib.wallet.entities.WalletApiErrorProperty;
import org.json.JSONObject;

import java.util.List;

public class WalletApiException extends WalletSdkException {
    public static final String ERROR_CODE_INVALID_GRANT = "invalid_grant";
    public static final String ERROR_CODE_INVALID_TIMESTAMP = "invalid_timestamp";
    public static final String ERROR_CODE_RATE_LIMIT_EXCEEDED = "rate_limit_exceeded";
    public static final String ERROR_CODE_PHONE_ALREADY_ASSIGNED = "phone_already_assigned";
    public static final String ERROR_CODE_INVALID_CODE = "invalid_code";
    public static final String ERROR_CODE_INVALID_PARAMETERS = "invalid_parameters";
    public static final String ERROR_CODE_INVALID_PASSWORD = "invalid_password";
    public static final String ERROR_CODE_INVALID_IDENTITY = "invalid_identity";
    public static final String ERROR_CODE_IDENTITY_REQUIRED = "identity_required";
    public static final String ERROR_CODE_NOT_FOUND = "not_found";
    public static final String ERROR_CODE_USER_ALREADY_EXISTS = "user_already_exists";
    public static final String ERROR_CODE_PASSWORD_TOO_SHORT = "password_too_short";
    public static final String ERROR_CODE_PASSWORD_TOO_RISKY = "password_too_risky";
    public static final String ERROR_CODE_REGISTRATION_FROM_PROHIBITED_COUNTRY = "phone_from_prohibited_country";
    public static final String ERROR_CODE_SIGNING_REQUEST = "signing_request";
    private static final String ERROR_LIVENESS_CHECKED_MAX_ATTEMPTS_REACHED = "rate_limit_exceeded_ge";

    public static final String ERROR_DESCRIPTION_EXPIRED_TOKEN = "Token has expired";
    public static final String ERROR_DESCRIPTION_REFRESH_TOKEN_EXPIRED = "Refresh token expired";
    public static final String ERROR_DESCRIPTION_NO_SUCH_REFRESH_TOKEN = "No such refresh token";
    public static final String ERROR_DESCRIPTION_REFRESH_TOKEN_INVALID = "Refresh token status invalid";

    private static final String ERROR_DESCRIPTION_TIMESTAMP_IS_IN_THE_FUTURE = "Timestamp is in future";
    private static final String ERROR_DESCRIPTION_TIMESTAMP_LIFETIME_EXCEEDED = "Timestamp lifetime exceeded";
    public static final String ERROR_DESCRIPTION_SIGNING_REQUEST = "An error occurred while signing the request";

    private String errorDescription;
    private String errorCode;
    private List<WalletApiErrorProperty> errorProperties;
    private Integer statusCode;
    private RecaptchaHeaders recaptchaHeaders;
    private JSONObject errorData;

    public WalletApiException(String detailMessage) {
        super(detailMessage);
    }

    public WalletApiException(String errorDescription, String errorCode, Integer statusCode) {
        this.errorDescription = errorDescription;
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    public WalletApiException(Throwable throwable) {
        super(throwable);
    }

    public WalletApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public WalletApiException(String detailMessage, int statusCode) {
        super(detailMessage);
        this.statusCode = statusCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public List<WalletApiErrorProperty> getErrorProperties() {
        return errorProperties;
    }

    public void setErrorProperties(List<WalletApiErrorProperty> errorProperties) {
        this.errorProperties = errorProperties;
    }

    public JSONObject getErrorData() {
        return errorData;
    }

    public void setErrorData(JSONObject errorData) {
        this.errorData = errorData;
    }

    public RecaptchaHeaders getRecaptchaHeaders() {
        return recaptchaHeaders;
    }

    public void setRecaptchaHeaders(RecaptchaHeaders recaptchaHeaders) {
        this.recaptchaHeaders = recaptchaHeaders;
    }

    public Boolean isServerError() {
        return this.statusCode == 500;
    }

    public Boolean isNetworkError() {
        return this.errorCode == null;
    }

    public Boolean isInvalidTimestampError() {
        return
            !this.isNetworkError()
                && this.errorDescription.contains(WalletApiException.ERROR_DESCRIPTION_TIMESTAMP_IS_IN_THE_FUTURE)
                || this.errorDescription.equals(WalletApiException.ERROR_DESCRIPTION_TIMESTAMP_LIFETIME_EXCEEDED)
                || this.errorCode.equals(WalletApiException.ERROR_CODE_INVALID_TIMESTAMP);
    }

    public Boolean isRefreshTokenExpiredError() {
        return isInvalidGrantError() && (
            errorDescription.equals(WalletApiException.ERROR_DESCRIPTION_REFRESH_TOKEN_EXPIRED)
                || errorDescription.equals(WalletApiException.ERROR_DESCRIPTION_NO_SUCH_REFRESH_TOKEN)
                || errorDescription.equals(WalletApiException.ERROR_DESCRIPTION_REFRESH_TOKEN_INVALID)
        );
    }

    public Boolean isTokenExpired() {
        return isInvalidGrantError()
            && errorDescription.equals(WalletApiException.ERROR_DESCRIPTION_EXPIRED_TOKEN);
    }

    public Boolean isInvalidGrantError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_INVALID_GRANT);
    }

    public Boolean isRateLimitExceededError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_RATE_LIMIT_EXCEEDED)
                && this.statusCode == 429;
    }

    public Boolean isPhoneAlreadyAssignedError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_PHONE_ALREADY_ASSIGNED);
    }

    public Boolean isInvalidCodeError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_INVALID_CODE);
    }

    public Boolean isInvalidParametersError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_INVALID_PARAMETERS);
    }

    public Boolean isInvalidPasswordError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_INVALID_PASSWORD);
    }

    public Boolean isInvalidIdentityError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_INVALID_IDENTITY);
    }

    public Boolean isIdentityRequiredError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_IDENTITY_REQUIRED);
    }

    public Boolean isNotFoundError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_NOT_FOUND);
    }

    public Boolean isUserAlreadyExistsError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_USER_ALREADY_EXISTS);
    }

    public Boolean isPasswordTooShortError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_PASSWORD_TOO_SHORT);
    }

    public Boolean isPasswordTooRiskyError() {
        return
            this.errorCode != null
                && this.errorCode.equals(WalletApiException.ERROR_CODE_PASSWORD_TOO_RISKY);
    }

    public Boolean isRegistrationFromProhibitedCountry() {
        return this.errorCode != null && this.errorCode.equals(WalletApiException.ERROR_CODE_REGISTRATION_FROM_PROHIBITED_COUNTRY);
    }

    public Boolean isSigningRequest() {
        return errorCode != null && errorCode.equals(ERROR_CODE_SIGNING_REQUEST);
    }
  
    public Boolean isLivenessCheckMaxAttemptsReached() {
        return this.errorCode != null && this.errorCode.equals(WalletApiException.ERROR_LIVENESS_CHECKED_MAX_ATTEMPTS_REACHED);
    }

    @Override
    public String toString() {
        String message = String.format("code=%s, desc=%s, properties=%s, statusCode=%d",
            errorCode,
            errorDescription,
            errorProperties,
            statusCode
        );
        return super.toString() + message;
    }
}