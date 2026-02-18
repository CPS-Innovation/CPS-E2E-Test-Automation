package com.cps.fct.e2e.pages;


import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.assertj.core.api.Assertions;

import java.nio.file.Paths;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class BasePage {


    private static final int DEFAULT_TIMEOUT_MILLIS = 10000;

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
            } catch (Exception e) {
                return false;
            }
        });
    }

        public void waitForText(String expectedText) {
            page.waitForCondition(() -> {
                try {
                    return page.content().contains(expectedText);
                } catch (Exception e) {
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
        Locator locator = page.getByRole(role);
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


    public void fillRichTextEditor(String editorSelector, String textToEnter) {
        Locator editor = page.locator(editorSelector);
        editor.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // Click to focus
        editor.click();

        // Fill or type the text
        editor.type(textToEnter, new Locator.TypeOptions().setDelay(5));

    }


    public void clickOkSectionLink(String selector) {
        Locator link = page.locator(selector);

        link.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));// optional timeout in milliseconds
        link.click();
    }























}
