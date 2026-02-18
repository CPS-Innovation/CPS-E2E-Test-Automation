package com.cps.fct.e2e.utils.playwright;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import lombok.Setter;


public class PlaywrightContext {
    private Page page;

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

    public void close() {
        if (browserContext != null) browserContext.close();
    }
}