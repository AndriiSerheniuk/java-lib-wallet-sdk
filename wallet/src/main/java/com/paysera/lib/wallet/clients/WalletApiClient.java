package com.paysera.lib.wallet.clients;

import com.google.gson.JsonObject;
import com.paysera.lib.wallet.entities.*;
import com.paysera.lib.wallet.entities.card.Card;
import com.paysera.lib.wallet.entities.client.Client;
import com.paysera.lib.wallet.entities.confirmations.Confirmation;
import com.paysera.lib.wallet.entities.easypay.EasyPayCreateTransfer;
import com.paysera.lib.wallet.entities.easypay.EasyPayFees;
import com.paysera.lib.wallet.entities.easypay.EasyPayTransfer;
import com.paysera.lib.wallet.entities.generator.Generator;
import com.paysera.lib.wallet.entities.locations.Location;
import com.paysera.lib.wallet.entities.locations.LocationCategory;
import com.paysera.lib.wallet.entities.notification.NotificationSubscriber;
import com.paysera.lib.wallet.entities.pos.Spot;
import com.paysera.lib.wallet.entities.requests.*;
import com.paysera.lib.wallet.entities.transfer.Transfer;
import com.paysera.lib.wallet.entities.transfer.TransferPassword;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface WalletApiClient {

    // Misc endpoints
    @GET("server")
    Call<ServerInformation> getServerInformation();

    @GET("configuration")
    Call<ServerConfiguration> getServerConfiguration();

    @GET("currencies")
    Call<List<String>> getCurrencies();

    // Client endpoint
    @POST("client")
    Call<Client> createClient(
        @Body Client client
    );

    @GET("client/info/{clientId}/status")
    Call<UnknownDevice> getClientStatus(
        @Path("clientId") Integer clientId
    );

    @GET("client/locations")
    Call<List<Location>> getClientLocations();

    // Currency Conversion
    @GET("currency-conversion")
    Call<CurrencyConversionCalculation> calculateCurrencyConversion(
        @Query("from_amount_decimal") String fromAmountDecimal,
        @Query("from_currency") String fromCurrency,
        @Query("to_amount_decimal") String toAmountDecimal,
        @Query("to_currency") String toCurrency,
        @Query("account_number") String accountNumber
    );

    @POST("currency-conversion")
    Call<CurrencyConversionResult> convertCurrency(@Body ConvertCurrencyCurrencyRequest request);

    // User endpoint
    @GET("user/me")
    Call<User> getUser();

    // User endpoint
    @GET("user/me/wallets")
    Call<List<Wallet>> getUserWallets(@Query("inactive_included") Boolean isInactiveIncluded);

    @GET("user/{id}/questionnaire")
    Call<Questionnaire> getUserQuestionnaire(@Path("id") Integer userId);

    @GET("user/{id}")
    Call<User> getUser(
        @Path("id") Integer userId
    );

    @GET("user/me/projects")
    Call<List<Project>> getUserProjects();

    @GET("user/me/projects")
    Call<List<Project>> getUserProjects(
        @Query("fields") String fields
    );

    @GET("user/{id}/projects")
    Call<List<Project>> getUserProjects(
        @Path("id") Integer userId
    );

    @POST("user")
    Call<User> createUser(@Body UserRegistrationRequest userRegistrationRequestRegistration);

    @POST("user/me/contact-book")
    Call<ContactBook> createContactBookForCurrentUser();

    @POST("user/{userId}/contact-book")
    Call<ContactBook> createContactBookForUser(Integer userId);

    @POST("user/password/reset")
    Call<User> requestResetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    @PUT("user/{userId}/password")
    Call<User> resetPassword(
        @Path("userId") Integer id,
        @Body ResetPasswordConfirmRequest userPasswordResetConfirm
    );

    @PUT("user/{userId}/password")
    Call<User> changePassword(
        @Path("userId") Integer id,
        @Body ChangePasswordRequest changePasswordRequest
    );

    @GET("user")
    Call<User> getUser(
        @Query("email") String email,
        @Query("phone") String phone,
        @Query("person_code") String personCode,
        @Query("nationality") String nationality
    );

    @PUT("user/{userId}/phone/confirm")
    Call<User> confirmPhone(
        @Path("userId") Integer userId,
        @Body ConfirmPhoneRequest confirmPhoneRequest
    );

    @PUT("user/me/email/confirm")
    Call<User> confirmEmail(@Body ConfirmEmailRequest confirmEmailRequest);

    @GET("user/me/identification-requests")
    Call<MetadataAwareResponse<IdentificationRequest>> getIdentificationRequests(
        @Query("statuses[]") List<String> statuses,
        @Query("order_by") String orderBy,
        @Query("order_direction") String orderDirection,
        @Query("limit") Integer limit,
        @Query("offset") Integer offset
    );

    @GET("user/{userId}/identification-requests")
    Call<MetadataAwareResponse<IdentificationRequest>> getIdentificationRequests(
        @Path("userId") Integer userId,
        @Query("statuses[]") List<String> statuses
    );

    @POST("user/{userId}/phone")
    Call<User> assignPhoneNumber(
        @Path("userId") Integer userId,
        @Body AssignPhoneNumberRequest assignPhoneNumberRequest
    );

    @POST("user/me/email")
    Call<User> assignEmail(@Body AssignEmailRequest assignEmailRequest);

    @PUT("user/{userId}/avatar")
    Call<Void> setUserAvatar(@Path("userId") Integer userId, @Body RequestBody avatar);

    @PUT("user/me/avatar")
    Call<Void> setUserAvatar(@Body RequestBody avatar);

    @DELETE("user/{userId}/avatar")
    Call<Void> deleteUserAvatar(@Path("userId") Integer userId);

    @PUT("user/me/position")
    Call<UserPosition> provideUserPosition(@Body UserPosition provideUserPositionRequest);

    @GET("user/{userId}/services")
    Call<UserServiceResponse> getUserServices(@Path("userId") Integer userId);

    @PUT("user/{userId}/service/{service}")
    Call<Void> enableUserService(
        @Path("userId") Integer userId,
        @Path("service") String service
    );

    // Wallet endpoint
    @GET("wallet/{id}")
    Call<Wallet> getWallet(@Path("id") Integer walletId);

    @GET("wallet/{id}/balance")
    Call<WalletBalance> getWalletBalance(
        @Path("id") Integer walletId,
        @Query("convert_to") String convertToCurrency,
        @Query("include_convert_to_currency") Boolean includeConvertToCurrency,
        @Query("show_historical_currencies") Boolean showHistoricalCurrencies
    );

    @DELETE("wallet/{id}/description")
    Call<Void> deleteWalletDescription(@Path("id") Integer id);

    @PUT("wallet/{walletId}")
    Call<Wallet> changeWalletDescription(
        @Path("walletId") Integer id,
        @Body ChangeWalletDescriptionRequest changeWalletDescriptionRequest
    );

    @GET("wallet")
    Call<Wallet> getWallet(
        @Query("account_number") String accountNumber,
        @Query("phone") String phone,
        @Query("email") String email,
        @Query("user_id") Integer userId
    );

    @GET("wallets")
    Call<Map<String, Wallet>> getWallets(
        @Query("email") String emailList,
        @Query("phone") String phoneList,
        @Query("email_hash") String emailHashList,
        @Query("phone_hash") String phoneHashList,
        @Query("limit") Integer limit
    );

    @GET("wallet/{id}/statements")
    Call<MetadataAwareResponse<Statement>> getStatements(
        @Path("id") Integer walletId,
        @Query("currency") String currencies,
        @Query("direction") String direction,
        @Query("text") String text,
        @Query("from") Long from,
        @Query("to") Long to,
        @Query("limit") Integer limit,
        @Query("offset") Integer offset,
        @Query("after") String after,
        @Query("before") String before,
        @Query("order_by") String orderBy,
        @Query("order_direction") String orderDirection
    );

    @GET("wallet/{id}/pending-payments")
    Call<MetadataAwareResponse<PendingPayment>> getPendingPayments(
        @Path("id") Integer walletId,
        @Query("limit") Integer limit,
        @Query("offset") Integer offset
    );

    @GET("wallet/{id}/reservation-statements")
    Call<MetadataAwareResponse<ReservationStatement>> getReservationStatements(
        @Path("id") Integer walletId,
        @Query("limit") Integer limit,
        @Query("offset") Integer offset
    );

    @DELETE("wallet/{walletId}/pending-payment/{pendingPaymentId}")
    Call<Void> cancelPendingPayment(
        @Path("walletId") Integer walletId,
        @Path("pendingPaymentId") long pendingPaymentId
    );

    // Card endpoint
    @POST("card")
    Call<Card> createCard(@Body Card card);

    @GET("cards")
    Call<MetadataAwareResponse<Card>> getCards(
        @Query("user_id") Integer userId,
        @Query("limit") Integer limit,
        @Query("offset") Integer offset
    );

    @GET("card/{id}")
    Call<Card> getCard(@Path("id") Integer id);

    @DELETE("card/{id}")
    Call<Void> deleteCard(@Path("id") Integer id);

    // Locations endpoint
    @GET("locations/pay-categories")
    Call<List<LocationCategory>> getLocationCategories(@Query("locale") String locale);

    @GET("locations")
    Call<MetadataAwareResponse<Location>> getLocations(
        @Query("locale") String locale,
        @Query("lat") Float lat,
        @Query("lng") Float lng,
        @Query("distance") Float distance,
        @Query("updated_after") Long updatedAfter,
        @Query("status") String status,
        @Query("limit") Integer limit,
        @Query("offset") Integer offset
    );

    // Transaction endpoint
    @POST("transaction/{transactionKey}/request")
    Call<TransactionRequest> createTransactionRequest(
        @Path("transactionKey") String transactionKey,
        @Body TransactionRequest transactionRequest
    );

    @POST("transaction")
    Call<Transaction> createTransaction(@Body Transaction transaction);

    @GET("transaction/{transactionKey}")
    Call<Transaction> getTransaction(@Path("transactionKey") String transactionKey);

    @GET("transaction/{transactionKey}")
    Call<Transaction> getTransaction(
        @Path("transactionKey") String transactionKey,
        @Query("fields") String fields
    );

    @GET("transactions")
    Call<MetadataAwareResponse<Transaction>> getTransactions(
        @Query("project_id") Integer projectId,
        @Query("location_id") Integer locationId,
        @Query("status") String status,
        @Query("limit") Integer limit,
        @Query("offset") Integer offset,
        @Query("from") Integer from
    );

    @PUT("transaction/{transactionKey}/confirm")
    Call<Transaction> confirmTransaction(@Path("transactionKey") String transactionKey);

    @DELETE("transaction/{transactionKey}")
    Call<Void> cancelTransaction(@Path("transactionKey") String transactionKey);

    // Contact book endpoint
    @PUT("contact-book/{contactBookId}/append")
    Call<Void> appendContactsToContactBook(
        @Path("contactBookId") Integer contactBookId,
        @Body AppendContactsToContactBookRequest request
    );

    @DELETE("contact-book/{contactBookId}/contacts")
    Call<Void> removeFromContactBook(
        @Path("contactBookId") Integer contactBookId,
        @Query("email") String emailList,
        @Query("phone") String phoneList,
        @Query("email_hash") String emailHashList,
        @Query("phone_hash") String phoneHashList
    );

    // Subscriber endpoint
    @DELETE("subscriber/{subscriberId}")
    Call<Void> unregisterSubscriber(@Path("subscriberId") Integer subscriberId);

    @DELETE("subscribers")
    Call<Void> unregisterSubscribers();

    // Transfers endpoint
    @POST("transfers")
    Call<Transfer> createTransfer(@Body Transfer transfer);

    @POST("simulated-transfers")
    Call<Transfer> simulateTransfer(@Body Transfer transfer);

    @PUT("transfers/{transferId}/reserve")
    Call<Transfer> reserveTransferById(@Path("transferId") String transferId);

    @PUT("transfers/{transferId}/sign")
    Call<Transfer> signTransferById(@Path("transferId") String transferId);

    @DELETE("transfers/{transferId}")
    Call<Transfer> deleteTransferById(@Path("transferId") String transferId);

    @GET("transfers/{transferId}")
    Call<Transfer> getTransferById(@Path("transferId") String transferId);

    @GET("transfers")
    Call<MetadataAwareResponse<Transfer>> getTransfers(
        @Query("credit_account_number") String creditAccountNumber,
        @Query("statuses[]") List<String> statuses,
        @Query("offset") Integer offset,
        @Query("limit") Integer limit
    );

    @PUT("transfers/{transferId}/provide-password")
    Call<Transfer> provideTransferPassword(
        @Path("transferId") Long transferId,
        @Body TransferPassword password
    );

    @POST("jwt/tokens")
    Call<JWTTokenResponse> getJWTToken(@Body JWTScope scope);

    @GET("confirmations/me")
    Call<MetadataAwareResponse<Confirmation>> getConfirmations(
        @Query("limit") Integer limit,
        @Query("offset") Integer offset,
        @Query("order_by") String orderBy,
        @Query("order_direction") String orderDirection,
        @Query("status") String status
    );

    @GET("confirmations/{identifier}")
    Call<Confirmation> getConfirmation(@Path("identifier") String identifier);

    @PUT("confirmations/{identifier}/confirm")
    Call<Confirmation> acceptConfirmation(@Path("identifier") String identifier);

    @PUT("confirmations/{identifier}/reject")
    Call<Confirmation> rejectConfirmation(@Path("identifier") String identifier);

    @POST("generator/code")
    Call<Void> generateCode(@Body GenerateCodeRequest generateCodeRequest);

    @PUT("transaction/{transactionKey}/reserve")
    Call<Void> reserveTransaction(
        @Path("transactionKey") String transactionKey,
        @Body ReserveTransactionRequest reserveTransactionRequest
    );

    @POST("auth-token/token")
    Call<AuthTokenResponse> createAuthToken();

    @GET("spot/{spotId}")
    Call<Spot> getSpotById(@Path("spotId") Long spotId, @Query("fields") String fields);

    @PUT("spot/{spotId}/check-in")
    Call<Spot> checkIntoSpot(@Path("spotId") Long spotId, @Query("fields") String fields);

    @POST("user/me/identification-request")
    Call<IdentificationRequest> createIdentificationRequest();

    @POST("identification-request/{identificationRequestId}/identity-document")
    Call<CreateDocumentIdentificationResponse> createDocumentIdentificationRequest(
        @Path("identificationRequestId") Long identificationRequestId,
        @Body CreateDocumentIdentificationRequest request
    );

    @PUT("identification-request/{identificationRequestId}/face-photo/image/{order}")
    Call<Void> identificationRequestFileUpload(
        @Path("identificationRequestId") Long identificationRequestId,
        @Path("order") Integer order,
        @Body RequestBody body
    );

    @PUT("identity-document/{identificationDocumentId}/image/{order}")
    Call<Void> identificationDocumentFileUpload(
        @Path("identificationDocumentId") Long identificationDocumentId,
        @Path("order") Integer order,
        @Body RequestBody body
    );

    @PUT("identification-request/{identificationRequestId}/submit")
    Call<IdentificationRequest> submitIdentificationRequest(@Path("identificationRequestId") Long identificationRequestId);

    @POST("identification-request/{identificationRequestId}/additional-document")
    Call<CreateDocumentIdentificationResponse> createAdditionalDocumentRequest(
        @Path("identificationRequestId") Long identificationDocumentId,
        @Body CreateDocumentIdentificationRequest request
    );

    @POST("additional-document/{additionalDocumentId}/file")
    Call<Void> additionalDocumentUpload(
        @Path("additionalDocumentId") Long additionalDocumentId,
        @Body RequestBody body
    );

    @GET("generator/{id}")
    Call<Generator> getGenerator(@Path("id") Integer generatorId);

    @POST("generator")
    Call<Generator> createGenerator(@Body JsonObject body);

    @POST("subscriber")
    Call<NotificationSubscriber> createNotificationsSubscriber(@Body NotificationSubscriber notificationSubscriber);

    @PUT("subscriber/{subscriberId}")
    Call<NotificationSubscriber> editNotificationsSubscriber(
        @Path("subscriberId") Integer subscriberId,
        @Body NotificationSubscriber notificationSubscriber
    );

    @FormUrlEncoded
    @POST
    Call<Void> unlockRecaptcha(
        @Url String unlockUrl,
        @Field("g-recaptcha-response") String gRecaptchaResponse
    );

    @FormUrlEncoded
    @POST
    Call<Void> unlockRecaptcha(
        @Url String unlockUrl,
        @Header("g-recaptcha-response") String gRecaptchaResponse,
        @Field("grant_type") String grantType,
        @Field("username") String username,
        @Field("password") String password,
        @Field("scope") String scopes
    );

    @POST("contacts")
    Call<Void> collectContact(@Body ContactCollectionRequest contactCollectionRequest);

    // EasyPay endpoints

    @GET("epay/fees")
    Call<EasyPayFees> getEasyPayFees(
        @Query("amount") String amount,
        @Query("currency") String currency
    );

    @GET("epay/transfers")
    Call<CommonMetadataAwareResponse<EasyPayTransfer>> getEasyPayTransfers(
        @Query("status") String status,
        @Query("beneficiary_user_id") Integer beneficiaryUserId,
        @Query("payer_wallet_id") Integer payerWalletId,
        @Query("limit") Integer limit,
        @Query("offset") Integer offset,
        @Query("order_by") String orderBy,
        @Query("order_direction") String orderDirection
    );

    @POST("epay/transfers")
    Call<EasyPayTransfer> createEasyPayTransfer(
        @Body EasyPayCreateTransfer easyPayCreateTransfer
    );

    @PUT("epay/transfers/{easy_pay_transfer_id}/cancel")
    Call<EasyPayTransfer> cancelEasyPayTransfer(
        @Path("easy_pay_transfer_id") Long easyPayTransferId
    );

    // End of EasyPay endpoints
}