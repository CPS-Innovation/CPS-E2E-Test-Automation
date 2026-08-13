package com.cps.fct.e2e.utils.playwright;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import lombok.Setter;


public class PlaywrightContext {
    private Page page;
    private final WebAuthnVirtualAuthenticator webAuthnVirtualAuthenticator = new WebAuthnVirtualAuthenticator();

    @Setter
    private BrowserContext browserContext;

    public PlaywrightContext() {
        // Empty constructor for PicoContainer
    }

    public Page getPage() {
        if (page == null && browserContext != null) {
            page = browserContext.newPage(); // Create only when needed
        }
        return page;
    }

    public void attachVirtualWebAuthnAuthenticator(Page page) {
        webAuthnVirtualAuthenticator.attach(page);
    }

    public void detachVirtualWebAuthnAuthenticator() {
        webAuthnVirtualAuthenticator.detach();
    }

    public void close() {
        detachVirtualWebAuthnAuthenticator();
        if (browserContext != null) browserContext.close();
    }
}
