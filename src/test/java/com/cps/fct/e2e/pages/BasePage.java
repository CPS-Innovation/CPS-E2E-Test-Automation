package com.cps.fct.e2e.pages;


import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.assertj.core.api.Assertions;

import java.nio.file.Paths;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class BasePage {


    private static final int DEFAULT_TIMEOUT_MILLIS = 25_000;
    private static final int RICH_TEXT_EDITOR_WAIT_TIMEOUT_MILLIS = 10000;
    private static final int RICH_TEXT_STABLE_FOR_MILLIS = 500;
    // Fallback settle after the editor reports ready: there is no exposed "CKEditor initialised"
    // signal, and autosave can re-render the editable moments after it mounts.
    private static final int RICH_TEXT_READY_SETTLE_MILLIS = 300;

    protected Page page;

    public BasePage(PlaywrightContext context ) {
        this.page = context.getPage();
    }

    // ---------- Basic Actions ----------
    public void click(String selector) {
        Locator button = page.locator(selector);
        button.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
        button.click();
    }

    public void type(String selector, String text) {
        page.locator(selector).fill(text);
    }

    public void hover(String selector) {
        page.locator(selector).hover();
    }

    public void pressKey(String selector, String key) {
        page.locator(selector).press(key);
    }

    public void clear(String selector) {
        page.locator(selector).fill("");
    }

    public void scrollIntoView(String selector) {
        page.locator(selector).scrollIntoViewIfNeeded();
    }

    // ---------- Validations ----------
    public boolean isVisible(String selector) {
        return page.locator(selector).isVisible();
    }

    public boolean isEnabled(String selector) {
        return page.locator(selector).isEnabled();
    }

    public boolean isChecked(String selector) {
        return page.locator(selector).isChecked();
    }

    public boolean hasText(String selector, String expectedText) {
        return page.locator(selector).textContent().contains(expectedText);
    }

    public boolean hasAttribute(String selector, String attribute, String value) {
        return page.locator(selector).getAttribute(attribute).equals(value);
    }

    // ---------- Element Handling ----------
    public Locator getElement(String selector) {
        return page.locator(selector);
    }

    public List<Locator> getElements(String selector) {
        return page.locator(selector).all();
    }

    public int getElementCount(String selector) {
        return page.locator(selector).count();
    }

    // ---------- Iterate and Select ----------
    public void selectFromList(String listSelector, String itemText) {
        Locator dropdown = page.locator(listSelector);
        dropdown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        dropdown.scrollIntoViewIfNeeded();
        dropdown.selectOption(new SelectOption().setLabel(itemText));
    }

    public void checkCheckbox(String selector) {
        Locator checkbox = page.locator(selector);
        if (!checkbox.isChecked()) {
            checkbox.check();
        }
    }

    public void uncheckCheckbox(String selector) {
        Locator checkbox = page.locator(selector);
        if (checkbox.isChecked()) {
            checkbox.uncheck();
        }
    }

    // ---------- Advanced Delegator Methods ----------
    public void waitForElement(String selector, int timeoutMs) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeoutMs));
    }

    public void waitForElementVisible(String selector, int timeoutMs) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeoutMs).setState(WaitForSelectorState.VISIBLE));
    }

    public void waitForElementHidden(String selector, int timeoutMs) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeoutMs).setState(WaitForSelectorState.HIDDEN));
    }

    public void waitForURL(String urlPart, int timeoutMs) {
        page.waitForURL("**" + urlPart + "**", new Page.WaitForURLOptions().setTimeout(timeoutMs));
    }

    public void selectDropdownByValue(String selector, String value) {
        page.selectOption(selector, new SelectOption().setValue(value));

    }

    public void selectDropdownByLabel(String selector, String label) {
        page.selectOption(selector, new SelectOption().setLabel(label));
    }

    public void selectDropdownByIndex(String selector, int index) {
        page.selectOption(selector, new SelectOption().setIndex(index));
    }

    public void uploadFile(String selector, String filePath) {
        page.locator(selector).setInputFiles(Paths.get(filePath));
    }

    public void executeJS(String script) {
        page.evaluate(script);
    }

    public String getText(String selector) {
        return page.locator(selector).textContent();
    }

    public String getAttribute(String selector, String attribute) {
        return page.locator(selector).getAttribute(attribute);
    }

    public void takeScreenshot(String path) {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)));
    }

    public void assertTextIsVisible(String text) {
        assertThat(page.getByText(text)).isVisible();
    }

    public void assertTextIsNotVisible(String text) {
        assertThat(page.getByText(text)).not().isVisible();

    }

    public void enterText(String name, String input) {
        getTextboxByName(name).fill(input);
    }

    public void clickButton(String name) {
        getButtonByName(name).click();
    }


    public Locator getTextboxByName(String name) {
        final String ROLE_TEXTBOX = "textbox";
        return page.locator("role=" + ROLE_TEXTBOX + "[name='" + name + "']");
    }


    public void checkRadioByName(String name) {
        final String ROLE_RADIO = "radio";
        page.locator("role=" + ROLE_RADIO + "[name='" + name + "']").check();
    }

    public void clickLinkByName(String name) {
        final String ROLE_LINK = "link";
        page.locator("role=" + ROLE_LINK + "[name='" + name + "']").click();
    }



    public Locator getButtonByName(String name) {
        final String ROLE_BUTTON = "button";
        return page.locator("role=" + ROLE_BUTTON + "[name='" + name + "']");
    }


    public BasePage waitForLoginPageToLoadCompletely() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        return this;
    }


    protected void waitUntilLoadingIndicatorIsGone() {
        page.getByRole(AriaRole.STATUS).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }

    protected void waitUntilLoadingIndicatorIsGone(String loadingText) {
        Locator spinner = page.getByRole(AriaRole.STATUS)
                .filter(new Locator.FilterOptions().setHasText(loadingText));
        spinner.waitFor(new Locator.WaitForOptions().
                setState(WaitForSelectorState.HIDDEN)
                .setTimeout(DEFAULT_TIMEOUT_MILLIS));
    }

    protected void waitForTextToAppear(String expectedText) {
        page.waitForSelector("text='" + expectedText + "'",
                new Page.WaitForSelectorOptions().setTimeout(DEFAULT_TIMEOUT_MILLIS));
    }


    public void assertUrlContains(Page page, String expectedQueryString) {
        String currentUrl = page.url();
        Assertions.assertThat(currentUrl)
                .as("Check if URL contains expected query string")
                .contains(expectedQueryString);
    }


    public void assertElementTextPresent(String selector) {
        Assertions.assertThat(page.locator(selector).isVisible()).isTrue();
    }

    public void waitForTextInLocator( String selector, String expectedText) {
        page.waitForCondition(() -> {
            try {
                String actualText = page.locator(selector).innerText();
                return actualText.contains(expectedText);
            } catch (PlaywrightException e) {
                if (isGenuineLocatorError(e)) {
                    throw e;
                }
                return false;
            }
        });
    }

        public void waitForText(String expectedText) {
            page.waitForCondition(() -> {
                try {
                    return page.content().contains(expectedText);
                } catch (PlaywrightException e) {
                    if (isGenuineLocatorError(e)) {
                        throw e;
                    }
                    return false;
                }
            });
        }


    public void selectComboBoxByVisibleText(String expectedText) {
        Locator comboBox = page.getByRole(AriaRole.COMBOBOX);
        Locator options = comboBox.locator("option");
        int count = options.count();

        for (int i = 0; i < count; i++) {
            String optionText = options.nth(i).innerText().trim();
            if (optionText.equalsIgnoreCase(expectedText)) {
                comboBox.selectOption(new SelectOption().setIndex(i));
                return;
            }
        }

    }
        public void waitForElement(Page page, AriaRole role, String expectedTexts) {
        Locator locator = page.getByRole(
                role,
                new Page.GetByRoleOptions().setName(expectedTexts)
        );
        locator.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT_MILLIS));
    }


    public void waitForLocatorAndAssertText(Page page, String cssSelector, String expectedText) {
        Locator locator = page.locator(cssSelector);
        locator.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT_MILLIS));
        assertThat(locator).containsText(expectedText);
    }


    protected void waitUntilSpinnersAreGone(String... spinnerTexts) {
        for (String text : spinnerTexts) {
            waitUntilLoadingIndicatorIsGone(text);
        }
    }


    /**
     * Tells a genuine locator/selector bug apart from an expected transient condition while polling.
     * A strict-mode violation (selector matched multiple elements) or a malformed selector is a test
     * bug that must surface immediately rather than being retried until timeout. Absence/slowness
     * ({@link TimeoutError}) and other transient states are legitimately treated as "not yet".
     */
    protected static boolean isGenuineLocatorError(RuntimeException error) {
        if (error instanceof TimeoutError) {
            return false;
        }
        String message = error.getMessage();
        if (message == null) {
            return false;
        }
        return message.contains("strict mode violation")
                || message.contains("Unknown engine")
                || message.contains("Unexpected token")
                || message.contains("Malformed selector")
                || message.contains("Cannot parse selector");
    }

    public void fillRichTextEditor(String editorSelector, String textToEnter) {
        // Resolve to a single element so lingering editors from earlier sections can't cause a
        // strict-mode violation (which the commit wait would otherwise swallow into a timeout).
        Locator editor = page.locator(editorSelector).first();
        waitForRichTextEditorReady(editor);
        editor.scrollIntoViewIfNeeded();
        editor.click();
        editor.press("Control+A");
        editor.press("Backspace");
        // Insert the whole string in a single operation. Character-by-character typing
        // (pressSequentially) races the editor's autosave/re-render: when autosave detaches and
        // re-mounts the editable mid-type, the remaining keystrokes are dropped, leaving only a
        // prefix (e.g. "...On my e"). insertText delivers one input event, so it can't be
        // truncated that way.
        page.keyboard().insertText(textToEnter);

        waitForRichTextEditorToCommit(editor, textToEnter);
    }

    private void waitForRichTextEditorReady(Locator editor) {
        // Node present and shown.
        editor.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(RICH_TEXT_EDITOR_WAIT_TIMEOUT_MILLIS));
        // CKEditor attaches asynchronously after the node becomes visible; wait until it is actually
        // editable so keystrokes/insertText are not lost against an un-initialised editor.
        assertThat(editor).isEditable();
        // Fallback: no reliable "initialised" event is exposed and autosave can re-render the
        // editable just after mount, so give it a brief moment to settle before inserting.
        page.waitForTimeout(RICH_TEXT_READY_SETTLE_MILLIS);
    }

    private void waitForRichTextEditorToCommit(Locator editor, String expectedText) {
        waitForRichTextEditorToContain(editor, expectedText);
        editor.press("Tab");
        dispatchRichTextEditorCommitEvents(editor);
        waitForRichTextEditorTextToSettle(editor, expectedText);
    }

    private void waitForRichTextEditorToContain(Locator editor, String expectedText) {
        String normalizedExpectedText = normalizeRichText(expectedText);
        page.waitForCondition(() -> {
            try {
                return normalizeRichText(editor.innerText()).contains(normalizedExpectedText);
            } catch (PlaywrightException e) {
                if (isGenuineLocatorError(e)) {
                    throw e;
                }
                return false;
            }
        });
    }

    private void waitForRichTextEditorTextToSettle(Locator editor, String expectedText) {
        String[] lastEditorText = {null};
        long[] stableSince = {0};
        String normalizedExpectedText = normalizeRichText(expectedText);

        page.waitForCondition(() -> {
            try {
                String currentEditorText = editor.innerText();
                if (!normalizeRichText(currentEditorText).contains(normalizedExpectedText)) {
                    stableSince[0] = 0;
                    lastEditorText[0] = currentEditorText;
                    return false;
                }

                long now = System.currentTimeMillis();
                if (!currentEditorText.equals(lastEditorText[0])) {
                    stableSince[0] = now;
                    lastEditorText[0] = currentEditorText;
                    return false;
                }

                return now - stableSince[0] >= RICH_TEXT_STABLE_FOR_MILLIS
                        && !richTextEditorHasFocus(editor);
            } catch (PlaywrightException e) {
                if (isGenuineLocatorError(e)) {
                    throw e;
                }
                return false;
            }
        });
    }

    private String normalizeRichText(String text) {
        return text
                .replace("\u00A0", " ")
                .replace("\u2026", "...")
                .replace("\u2018", "'")
                .replace("\u2019", "'")
                .replace("\u201C", "\"")
                .replace("\u201D", "\"")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private void dispatchRichTextEditorCommitEvents(Locator editor) {
        editor.evaluate("""
                element => {
                    element.dispatchEvent(new InputEvent('input', { bubbles: true, inputType: 'insertText' }));
                    element.dispatchEvent(new Event('change', { bubbles: true }));
                    element.dispatchEvent(new FocusEvent('blur', { bubbles: true }));
                    element.dispatchEvent(new FocusEvent('focusout', { bubbles: true }));
                    element.blur();
                }
                """);
    }

    private boolean richTextEditorHasFocus(Locator editor) {
        Object hasFocus = editor.evaluate("element => element === document.activeElement || element.contains(document.activeElement)");
        return Boolean.TRUE.equals(hasFocus);
    }


    public void clickOkSectionLink(String selector) {
        Locator link = page.locator(selector);

        link.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));// optional timeout in milliseconds
        link.click();
    }























}
