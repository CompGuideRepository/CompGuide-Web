/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.google.calendar.api;

import com.compguide.web.Persistence.Entities.User;
import com.compguide.web.google.calendar.controllers.GoogleCalendarController;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.F;
import javax.faces.context.FacesContext;

/**
 *
 * @author anton
 */
public class GoogleCalendar {

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME
            = "CompGuide";

    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/client_secret.json");

    private static String RedirectURI = "http://localhost:8081/CompGuide/clinical-tasks";
    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY
            = JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials at
     * ~/.credentials/calendar-java-quickstart.json
     */
    private static final List<String> SCOPES
            = Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Loads client secrets and Creates an Google Authorization Code Flow
     * object.
     *
     * @return an GoogleAuthorizationCodeFlow object.
     * @throws IOException
     */
    public static GoogleAuthorizationCodeFlow authorizationCodeFlow(String accessType) throws IOException {
        // Load client secrets.
        InputStream in
                = GoogleCalendar.class.getResourceAsStream("/client_id.json");
        GoogleClientSecrets clientSecrets
                = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow
                = new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType(accessType)
                .build();

        return flow;
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow
                = authorizationCodeFlow("online");

        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().
                setRedirectUri(RedirectURI);

        FacesContext.getCurrentInstance().getExternalContext().redirect(
                authorizationUrl.build()
        );
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Creates URL for an authorization web page to allow the end user to
     * authorize the application.
     *
     * @return an URL.
     * @throws IOException
     */
    public static String authorizationRequest() throws IOException {

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow
                = authorizationCodeFlow("online");

        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().
                setRedirectUri(RedirectURI);

        return authorizationUrl.build();
    }

    /**
     * Request for an access token using an authorization code.
     *
     * @throws IOException
     */
    static Credential requestAccessToken(String code, User user) throws IOException {

        Credential credential = null;

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow
                = authorizationCodeFlow("online");

        AuthorizationCodeTokenRequest tokenRequest = flow.newTokenRequest(code);
        tokenRequest.setRedirectUri(RedirectURI);

        try {
//            TokenResponse response
//                    = flow.newTokenRequest(code).execute();
            TokenResponse tokenResponse = tokenRequest.execute();

            System.out.println("Access token: " + tokenResponse.getAccessToken());

            //Now, with the token and user id, we have credentials
            credential = flow.createAndStoreCredential(tokenResponse, user.getIduser().toString());

        } catch (TokenResponseException e) {
            if (e.getDetails() != null) {
                Logger.getLogger(GoogleCalendarController.class.getName()).log(Level.SEVERE, e.getDetails().getError(), e);

                if (e.getDetails().getErrorDescription() != null) {
                    Logger.getLogger(GoogleCalendarController.class.getName()).log(Level.SEVERE, e.getDetails().getErrorDescription(), e);
                }
                if (e.getDetails().getErrorUri() != null) {
                    Logger.getLogger(GoogleCalendarController.class.getName()).log(Level.SEVERE, e.getDetails().getErrorUri(), e);
                }
            } else {
                Logger.getLogger(GoogleCalendarController.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            }
        }

        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     *
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static Calendar
            getCalendarService(User user) throws IOException {
        String code = (String) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("code");

        Credential credential = null;

        if ((code == null || code.isEmpty()) && !user.hasGoogleCalendarToken()) {
            String redirect = authorizationRequest();

            FacesContext.getCurrentInstance().
                    getExternalContext().redirect(redirect);
        } else if (user.hasGoogleCalendarToken()) {
            credential = loadCredentialFromAccessToken(user);
        } else {
            credential = requestAccessToken(code, user);
        }

        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Build and return an Credential.
     *
     * @return an Credential
     * @throws IOException
     */
    public static Credential loadCredentialFromAccessToken(User user) throws IOException {

        Credential credential = null;

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow
                = authorizationCodeFlow("online");

        //Now, with the token and user id, we have credentials
        credential = flow.loadCredential(user.getIduser().toString());

        if (credential.getExpiresInSeconds() < 0) {
            refreshToken(user, credential);
        }
        return credential;
    }

    public static Credential refreshToken(User user, Credential oldCredential) throws IOException {

        InputStream in
                = GoogleCalendar.class.getResourceAsStream("/client_id.json");
        GoogleClientSecrets clientSecrets
                = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        TokenResponse response
                = new GoogleRefreshTokenRequest(
                        HTTP_TRANSPORT, JSON_FACTORY,
                        oldCredential.getRefreshToken(), clientSecrets.getDetails().getClientId(),
                        clientSecrets.getDetails().getClientSecret())
                .execute();

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow
                = authorizationCodeFlow("online");

        //Now, with the token and user id, we have credentials
        Credential credential = flow.createAndStoreCredential(response, user.getIduser().toString());

        return credential;

    }
}
