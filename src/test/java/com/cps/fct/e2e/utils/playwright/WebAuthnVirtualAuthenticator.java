package com.cps.fct.e2e.utils.playwright;

import com.google.gson.JsonObject;
import com.microsoft.playwright.CDPSession;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;

public class WebAuthnVirtualAuthenticator {

    private static final String SUPPORTED_BROWSER_EDGE = "edge";
    private static final String SUPPORTED_BROWSER_CHROME = "chrome";
    private static final String AUTHENTICATOR_PROTOCOL = "ctap2";
    private static final String AUTHENTICATOR_TRANSPORT = "usb";

    private CDPSession cdpSession;
    private String authenticatorId;

    public void attach(Page page) {
        if (!isChromiumBrowser()) {
            return;
        }

        if (cdpSession != null && authenticatorId != null) {
            return;
        }

        try {
            cdpSession = page.context().newCDPSession(page);
            enableWebAuthn();
            authenticatorId = addVirtualAuthenticator();
            enableAutomaticPresenceSimulation();
            setUserVerified();
        } catch (PlaywrightException exception) {
            detach();
            throw new IllegalStateException("Failed to attach CDP WebAuthn virtual authenticator.", exception);
        }
    }

    public void detach() {
        if (cdpSession == null) {
            return;
        }

        removeVirtualAuthenticator();
        disableWebAuthn();
        detachCdpSession();
        reset();
    }

    private boolean isChromiumBrowser() {
        String browserName = System.getProperty("browser", SUPPORTED_BROWSER_EDGE).toLowerCase();
        return SUPPORTED_BROWSER_EDGE.equals(browserName) || SUPPORTED_BROWSER_CHROME.equals(browserName);
    }

    private void enableWebAuthn() {
        JsonObject params = new JsonObject();
        params.addProperty("enableUI", false);
        cdpSession.send("WebAuthn.enable", params);
    }

    private String addVirtualAuthenticator() {
        JsonObject options = new JsonObject();
        options.addProperty("protocol", AUTHENTICATOR_PROTOCOL);
        options.addProperty("transport", AUTHENTICATOR_TRANSPORT);
        options.addProperty("hasResidentKey", true);
        options.addProperty("hasUserVerification", true);
        options.addProperty("automaticPresenceSimulation", true);
        options.addProperty("isUserVerified", true);

        JsonObject params = new JsonObject();
        params.add("options", options);

        JsonObject response = cdpSession.send("WebAuthn.addVirtualAuthenticator", params);
        return response.get("authenticatorId").getAsString();
    }

    private void enableAutomaticPresenceSimulation() {
        JsonObject params = new JsonObject();
        params.addProperty("authenticatorId", authenticatorId);
        params.addProperty("enabled", true);
        cdpSession.send("WebAuthn.setAutomaticPresenceSimulation", params);
    }

    private void setUserVerified() {
        JsonObject params = new JsonObject();
        params.addProperty("authenticatorId", authenticatorId);
        params.addProperty("isUserVerified", true);
        cdpSession.send("WebAuthn.setUserVerified", params);
    }

    private void removeVirtualAuthenticator() {
        if (authenticatorId == null) {
            return;
        }

        try {
            JsonObject params = new JsonObject();
            params.addProperty("authenticatorId", authenticatorId);
            cdpSession.send("WebAuthn.removeVirtualAuthenticator", params);
        } catch (PlaywrightException ignored) {
            // Browser/context teardown may already have removed the virtual authenticator.
        }
    }

    private void disableWebAuthn() {
        try {
            cdpSession.send("WebAuthn.disable");
        } catch (PlaywrightException ignored) {
            // CDP session can already be closed during browser teardown.
        }
    }

    private void detachCdpSession() {
        try {
            cdpSession.detach();
        } catch (PlaywrightException ignored) {
            // CDP session can already be detached during browser teardown.
        }
    }

    private void reset() {
        cdpSession = null;
        authenticatorId = null;
    }
}
