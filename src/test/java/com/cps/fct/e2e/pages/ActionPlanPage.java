package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.common.FakerUtils;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.microsoft.playwright.assertions.LocatorAssertions;

import static com.cps.fct.e2e.utils.playwright.PlaywrightNetworkUtils.waitForResponseTriggeredBy;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ActionPlanPage extends BasePage {

    // Constants
    private static final String CASE_ACTION_PLAN_HEADER = "Case action plan";
    private static final String CONTINUE_WITHOUT_ACTION_PLAN_BUTTON = "Continue without action plan";
    private static final String ADD_ACTION_POINT_BUTTON_SELECTOR = "[id$='ActionPlanButton']";
    private static final String DATE_REQUIRED_BY_INPUT_SELECTOR = "[data-e2e-action-date-input='true']";
    private static final String ORIGINAL_DATE_REQUIRED_BY_INPUT_SELECTOR = "#b10-b16-b3-b3-b4-b5-Input_Date";
    private static final String CHASER_TASK_DATE_INPUT_SELECTOR = "[data-e2e-chaser-date-input='true']";
    private static final String CHASER_TASK_SHORTCUT_TEXT = "1 day before date required";
    private static final String RELATED_TO_SUSPECTS_DROPDOWN_SELECTOR = "[data-e2e-related-suspect-dropdown='true']";
    private static final String ACTION_DESCRIPTION_TEXTAREA_SELECTOR = "[data-e2e-action-description='true']";
    private static final String ACTION_POINT_PREVIEW_SELECTOR = "[data-block$='.ActionPointPreview']";
//    private static final String ACTION_POINT_PREVIEW_SELECTOR = "[data-block='ActionPlanTWIF.ActionPointPreview']";
    private static final String GOVUK_BUTTON_SELECTOR = "button.govuk-button[type='button']";
    private static final String SAVE_AND_CONTINUE_BUTTON_TEXT = "Save and continue";
    private static final String SAVE_DRAFT_BUTTON_TEXT = "Save draft";
    private static final String DEFAULT_RELATED_SUSPECT = "All";
    private static final String LOADING_INDICATOR_TEXT = "Loading...";
    private static final String SAVING_INDICATOR_TEXT = "Saving...";
    private static final Pattern DAYS_PATTERN = Pattern.compile("(\\d+)");
    private static final int ACTION_POINT_PREVIEW_TIMEOUT_MILLIS = 20_000;
    // Some action point options are shown by an abbreviation in the preview (e.g. ROTI),
    // rather than the full checkbox label. Map the checkbox label (lower-cased) to the
    // text rendered in the action point preview; anything not listed falls back to the
    // option text itself (matched case-insensitively).
    private static final Map<String, String> ACTION_POINT_PREVIEW_LABELS = Map.of(
            "record of taped interview", "ROTI"
    );
    private static final DateTimeFormatter INPUT_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.UK);
    private static final DateTimeFormatter PREVIEW_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.UK);
    private static final List<DateTimeFormatter> DATE_INPUT_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("M/d/yyyy", Locale.UK),
            DateTimeFormatter.ofPattern("d/M/yyyy", Locale.UK),
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.UK),
            DateTimeFormatter.ISO_LOCAL_DATE
    );
    private static final int DATE_SELECTION_TIMEOUT_MILLIS = 10_000;
    // Continuing past the action plan navigates to the Complete submission details page. Wait on that
    // page's investigative-stages load (a stable action name, not tied to review type or environment)
    // so the next step doesn't race the navigation.
    private static final String SUBMISSION_DETAILS_LOAD_ENDPOINT = "ScreenDataSetGetInvestigativeStages";
    private static final String POST_METHOD = "POST";
    private static final int SUCCESS_STATUS = 200;

    public ActionPlanPage(PlaywrightContext context) {
        super(context);
    }

    public void addActionPointPlan(String daysToAdd, String actionPointOption) {
        addActionPointPlan(daysToAdd, actionPointOption, DEFAULT_RELATED_SUSPECT, false);
    }

    public void addActionPointPlan(
            String daysToAdd,
            String actionPointOption,
            String relatedSuspect,
            boolean addChaserOneDayBefore
    ) {
        waitForTextInLocator("h1", CASE_ACTION_PLAN_HEADER);

        int days = daysFrom(daysToAdd);
        LocalDate dueDate = LocalDate.now().plusDays(days);
        String dueDatePreviewValue = dueDate.format(PREVIEW_DATE_FORMATTER);
        String actionPointDetails = FakerUtils.populateSentences();

        addActionPointButton().click();
        clickDateShortcut(days);
        assertDateRequiredBy(dueDate);
        if (addChaserOneDayBefore) {
            selectChaserTaskOneDayBefore(dueDate);
        }
        selectRelatedSuspect(relatedSuspect);
        selectWitnessOrVictimAction(actionPointOption);
        enterActionPointDetails(actionPointOption, actionPointDetails);
        clickSaveDraft();
        waitUntilSpinnersAreGone(SAVING_INDICATOR_TEXT, LOADING_INDICATOR_TEXT);

        assertActionPointPreview(dueDatePreviewValue, actionPointOption, actionPointDetails);
        clickSaveAndContinue();
    }

    public void continueWithOutActionPlan() {
        waitForTextInLocator("h1", CASE_ACTION_PLAN_HEADER);
        waitForResponseTriggeredBy(
                page,
                "Continue without action plan",
                SUBMISSION_DETAILS_LOAD_ENDPOINT,
                POST_METHOD,
                SUCCESS_STATUS,
                this::clickContinueWithoutActionPlan
        );
        waitUntilLoadingIndicatorIsGone(LOADING_INDICATOR_TEXT);
    }

    private void clickContinueWithoutActionPlan() {
        Locator continueWithoutActionPlanButton = page.locator(GOVUK_BUTTON_SELECTOR)
                .filter(new Locator.FilterOptions().setHasText(CONTINUE_WITHOUT_ACTION_PLAN_BUTTON))
                .first();
        assertThat(continueWithoutActionPlanButton).isVisible();
        continueWithoutActionPlanButton.scrollIntoViewIfNeeded();
        continueWithoutActionPlanButton.click();
    }

    private Locator addActionPointButton() {
        Locator addActionPoint = page.locator(ADD_ACTION_POINT_BUTTON_SELECTOR).first();
        assertThat(addActionPoint).isVisible();
        addActionPoint.scrollIntoViewIfNeeded();
        return addActionPoint;
    }

    private void clickDateShortcut(int days) {
        Locator dateRequiredByInput = dateRequiredByInput(days);
        assertThat(dateRequiredByInput).isVisible();
        scrollIntoStablePanelView(dateRequiredByInput);

        Locator dateShortcut = page.getByText(
                dateShortcutText(days),
                new Page.GetByTextOptions().setExact(true)
        ).first();
        scrollIntoStablePanelView(dateShortcut);
        assertThat(dateShortcut).isVisible();
        dateShortcut.click();
    }

    private Locator dateRequiredByInput(int days) {
        String shortcutText = dateShortcutText(days);
        long deadline = System.currentTimeMillis() + DATE_SELECTION_TIMEOUT_MILLIS;

        while (System.currentTimeMillis() < deadline) {
            boolean inputMarked = Boolean.TRUE.equals(page.evaluate("""
                    ([originalDateSelector, shortcutText]) => {
                        const normalize = value => (value || '').replace(/\\s+/g, ' ').trim().toLowerCase();
                        const isRendered = element => {
                            if (!element) {
                                return false;
                            }

                            const style = window.getComputedStyle(element);
                            const rect = element.getBoundingClientRect();
                            return style.display !== 'none'
                                && style.visibility !== 'hidden'
                                && rect.width > 0
                                && rect.height > 0;
                        };
                        const inputFrom = element => {
                            if (!element) {
                                return null;
                            }

                            return element.matches?.('input')
                                ? element
                                : element.querySelector?.('input');
                        };
                        const isDateInput = input => {
                            const searchableText = normalize([
                                input.id,
                                input.name,
                                input.placeholder,
                                input.type,
                                input.getAttribute('aria-label')
                            ].join(' '));

                            return input.type === 'date'
                                || searchableText.includes('date')
                                || searchableText.includes('mm/dd/yyyy')
                                || searchableText.includes('dd/mm/yyyy');
                        };
                        const pageY = element => element.getBoundingClientRect().top + window.scrollY;

                        const exactInput = inputFrom(document.querySelector(originalDateSelector));
                        const dateInputs = Array.from(document.querySelectorAll('input')).filter(isDateInput);
                        const shortcut = Array.from(document.querySelectorAll('a, button, span'))
                            .filter(element => normalize(element.innerText || element.textContent) === normalize(shortcutText))
                            .find(isRendered);

                        let input = exactInput && isRendered(exactInput) ? exactInput : null;
                        if (!input && shortcut) {
                            const shortcutY = pageY(shortcut);
                            input = dateInputs
                                .filter(isRendered)
                                .filter(candidate => pageY(candidate) <= shortcutY + 5)
                                .sort((left, right) => pageY(right) - pageY(left))[0];
                        }

                        if (!input) {
                            input = dateInputs.find(isRendered) || exactInput || dateInputs[0];
                        }

                        if (!input) {
                            return false;
                        }

                        document.querySelectorAll("[data-e2e-action-date-input='true']")
                            .forEach(element => element.removeAttribute('data-e2e-action-date-input'));
                        input.setAttribute('data-e2e-action-date-input', 'true');
                        input.scrollIntoView({ block: 'center', inline: 'nearest' });
                        return true;
                    }
                    """, List.of(ORIGINAL_DATE_REQUIRED_BY_INPUT_SELECTOR, shortcutText)));

            if (inputMarked) {
                return page.locator(DATE_REQUIRED_BY_INPUT_SELECTOR);
            }

            page.waitForTimeout(250);
        }

        throw new IllegalStateException("Date required by input was not found.");
    }

    private void scrollIntoStablePanelView(Locator locator) {
        locator.evaluate("""
                element => {
                    const scrollableParentFor = node => {
                        let parent = node.parentElement;
                        while (parent) {
                            const style = window.getComputedStyle(parent);
                            const canScroll = parent.scrollHeight > parent.clientHeight;
                            const scrollsVertically = ['auto', 'scroll'].includes(style.overflowY);
                            if (canScroll && scrollsVertically) {
                                return parent;
                            }
                            parent = parent.parentElement;
                        }

                        return document.scrollingElement || document.documentElement;
                    };

                    const parent = scrollableParentFor(element);
                    const parentRect = parent === document.scrollingElement || parent === document.documentElement
                        ? { top: 0, height: window.innerHeight }
                        : parent.getBoundingClientRect();
                    const elementRect = element.getBoundingClientRect();
                    const centeredTop = parent.scrollTop
                        + elementRect.top
                        - parentRect.top
                        - (parentRect.height / 2)
                        + (elementRect.height / 2);

                    parent.scrollTop = Math.max(0, centeredTop);
                    element.scrollIntoView({ block: 'center', inline: 'nearest' });
                }
                """);
        page.waitForTimeout(100);
    }

    private void assertDateRequiredBy(LocalDate expectedDate) {
        long deadline = System.currentTimeMillis() + DATE_SELECTION_TIMEOUT_MILLIS;
        String actualDateValue = "";

        while (System.currentTimeMillis() < deadline) {
            actualDateValue = dateRequiredByValue();
            LocalDate actualDate = parseDate(actualDateValue);
            if (expectedDate.equals(actualDate)) {
                return;
            }

            page.waitForTimeout(250);
        }

        throw new IllegalStateException("Date required by was not set to "
                + expectedDate.format(INPUT_DATE_FORMATTER)
                + " / " + expectedDate.format(PREVIEW_DATE_FORMATTER)
                + ". Actual value: " + actualDateValue);
    }

    private void selectChaserTaskOneDayBefore(LocalDate dueDate) {
        Locator chaserInput = chaserTaskDateInput();
        assertThat(chaserInput).isVisible();
        scrollIntoStablePanelView(chaserInput);

        Locator chaserShortcut = page.getByText(
                CHASER_TASK_SHORTCUT_TEXT,
                new Page.GetByTextOptions().setExact(true)
        ).first();
        scrollIntoStablePanelView(chaserShortcut);
        assertThat(chaserShortcut).isVisible();
        chaserShortcut.click();

        assertChaserTaskDate(dueDate.minusDays(1));
    }

    private Locator chaserTaskDateInput() {
        long deadline = System.currentTimeMillis() + DATE_SELECTION_TIMEOUT_MILLIS;

        while (System.currentTimeMillis() < deadline) {
            boolean inputMarked = Boolean.TRUE.equals(page.evaluate("""
                    shortcutText => {
                        const normalize = value => (value || '').replace(/\\s+/g, ' ').trim().toLowerCase();
                        const isRendered = element => {
                            if (!element) {
                                return false;
                            }

                            const style = window.getComputedStyle(element);
                            const rect = element.getBoundingClientRect();
                            return style.display !== 'none'
                                && style.visibility !== 'hidden'
                                && rect.width > 0
                                && rect.height > 0;
                        };
                        const isDateInput = input => {
                            const searchableText = normalize([
                                input.id,
                                input.name,
                                input.placeholder,
                                input.type,
                                input.getAttribute('aria-label')
                            ].join(' '));

                            return input.type === 'date'
                                || searchableText.includes('date')
                                || searchableText.includes('mm/dd/yyyy')
                                || searchableText.includes('dd/mm/yyyy');
                        };
                        const pageY = element => element.getBoundingClientRect().top + window.scrollY;

                        const shortcut = Array.from(document.querySelectorAll('a, button, span'))
                            .filter(element => normalize(element.innerText || element.textContent) === normalize(shortcutText))
                            .find(isRendered);
                        if (!shortcut) {
                            return false;
                        }

                        const shortcutY = pageY(shortcut);
                        const input = Array.from(document.querySelectorAll('input'))
                            .filter(isDateInput)
                            .filter(isRendered)
                            .filter(candidate => pageY(candidate) <= shortcutY + 5)
                            .sort((left, right) => pageY(right) - pageY(left))[0];
                        if (!input) {
                            return false;
                        }

                        document.querySelectorAll("[data-e2e-chaser-date-input='true']")
                            .forEach(element => element.removeAttribute('data-e2e-chaser-date-input'));
                        input.setAttribute('data-e2e-chaser-date-input', 'true');
                        input.scrollIntoView({ block: 'center', inline: 'nearest' });
                        return true;
                    }
                    """, CHASER_TASK_SHORTCUT_TEXT));

            if (inputMarked) {
                return page.locator(CHASER_TASK_DATE_INPUT_SELECTOR);
            }

            page.waitForTimeout(250);
        }

        throw new IllegalStateException("Chaser task date input was not found.");
    }

    private void assertChaserTaskDate(LocalDate expectedDate) {
        Locator chaserInput = page.locator(CHASER_TASK_DATE_INPUT_SELECTOR);
        long deadline = System.currentTimeMillis() + DATE_SELECTION_TIMEOUT_MILLIS;
        String actualDateValue = "";

        while (System.currentTimeMillis() < deadline) {
            actualDateValue = chaserInput.inputValue();
            if (expectedDate.equals(parseDate(actualDateValue))) {
                return;
            }

            page.waitForTimeout(250);
        }

        throw new IllegalStateException("Chaser task date was not set to "
                + expectedDate.format(INPUT_DATE_FORMATTER)
                + " / " + expectedDate.format(PREVIEW_DATE_FORMATTER)
                + ". Actual value: " + actualDateValue);
    }

    private String dateRequiredByValue() {
        return String.valueOf(page.evaluate("""
                dateSelector => {
                    const normalize = value => (value || '').replace(/\\s+/g, ' ').trim();
                    const isVisible = element => {
                        const style = window.getComputedStyle(element);
                        const rect = element.getBoundingClientRect();
                        return style.display !== 'none'
                            && style.visibility !== 'hidden'
                            && rect.width > 0
                            && rect.height > 0;
                    };
                    const valueFor = element => {
                        if (!element) {
                            return '';
                        }

                        const input = element.matches?.('input')
                            ? element
                            : element.querySelector?.('input');
                        return normalize(input?.value
                            || input?.getAttribute('value')
                            || element.getAttribute?.('value')
                            || '');
                    };

                    const exactInput = document.querySelector(dateSelector);
                    const exactValue = valueFor(exactInput);
                    if (exactValue) {
                        return exactValue;
                    }

                    const dateInputs = Array.from(document.querySelectorAll('input'))
                        .filter(isVisible)
                        .filter(input => {
                            const dateText = normalize([
                                input.id,
                                input.name,
                                input.placeholder,
                                input.type,
                                input.getAttribute('aria-label')
                            ].join(' ')).toLowerCase();
                            return dateText.includes('date')
                                || dateText.includes('mm/dd/yyyy')
                                || dateText.includes('dd/mm/yyyy');
                        });
                    const filledDateInput = dateInputs.find(input => normalize(input.value));
                    return valueFor(filledDateInput || dateInputs[0]);
                }
                """, DATE_REQUIRED_BY_INPUT_SELECTOR));
    }

    private LocalDate parseDate(String dateValue) {
        if (dateValue == null || dateValue.isBlank()) {
            return null;
        }

        for (DateTimeFormatter formatter : DATE_INPUT_FORMATTERS) {
            try {
                return LocalDate.parse(dateValue.trim(), formatter);
            } catch (DateTimeParseException ignored) {
                // Try the next supported UI date format.
            }
        }

        return null;
    }

    private void selectRelatedSuspect(String suspectOption) {
        Locator dropdown = relatedSuspectDropdown();
        assertThat(dropdown).isVisible();
        dropdown.selectOption(suspectOption);
    }

    private Locator relatedSuspectDropdown() {
        boolean dropdownMarked = Boolean.TRUE.equals(page.evaluate("""
                () => {
                    const normalize = value => (value || '').replace(/\\s+/g, ' ').trim().toLowerCase();
                    const isVisible = element => {
                        const style = window.getComputedStyle(element);
                        const rect = element.getBoundingClientRect();
                        return style.display !== 'none'
                            && style.visibility !== 'hidden'
                            && rect.width > 0
                            && rect.height > 0;
                    };
                    const hasExpectedOptions = select => Array.from(select.options || [])
                        .some(option => ['all', 'none'].includes(normalize(option.textContent || option.label)));
                    const select = Array.from(document.querySelectorAll('select'))
                        .filter(isVisible)
                        .find(hasExpectedOptions);

                    if (!select) {
                        return false;
                    }

                    document.querySelectorAll("[data-e2e-related-suspect-dropdown='true']")
                        .forEach(element => element.removeAttribute('data-e2e-related-suspect-dropdown'));
                    select.setAttribute('data-e2e-related-suspect-dropdown', 'true');
                    select.scrollIntoView({ block: 'center', inline: 'nearest' });
                    return true;
                }
                """));

        if (!dropdownMarked) {
            throw new IllegalStateException("Related suspect dropdown was not found.");
        }

        return page.locator(RELATED_TO_SUSPECTS_DROPDOWN_SELECTOR);
    }

    private void selectWitnessOrVictimAction(String actionPointOption) {
        boolean checked = Boolean.TRUE.equals(page.evaluate("""
                actionPointOption => {
                    const normalize = value => (value || '').replace(/\\s+/g, ' ').trim().toLowerCase();
                    const expected = normalize(actionPointOption);
                    const isVisible = element => {
                        const style = window.getComputedStyle(element);
                        const rect = element.getBoundingClientRect();
                        return style.display !== 'none'
                            && style.visibility !== 'hidden'
                            && rect.width > 0
                            && rect.height > 0;
                    };
                    const checkboxFor = label => {
                        const forId = label.getAttribute('for');
                        if (forId) {
                            return document.getElementById(forId);
                        }

                        return label.closest('div, li, p, section')?.querySelector('input[type="checkbox"]')
                            || label.parentElement?.querySelector('input[type="checkbox"]');
                    };
                    const labels = Array.from(document.querySelectorAll('label, span, div'))
                        .filter(isVisible)
                        .filter(element => normalize(element.innerText || element.textContent) === expected);
                    const label = labels[0];
                    const checkbox = label ? checkboxFor(label) : null;

                    if (!checkbox) {
                        return false;
                    }

                    checkbox.scrollIntoView({ block: 'center', inline: 'nearest' });
                    if (!checkbox.checked) {
                        checkbox.click();
                        checkbox.dispatchEvent(new Event('input', { bubbles: true }));
                        checkbox.dispatchEvent(new Event('change', { bubbles: true }));
                    }

                    return checkbox.checked;
                }
                """, actionPointOption));

        if (!checked) {
            throw new IllegalStateException("Action checkbox was not found or checked: " + actionPointOption);
        }
    }

    private void enterActionPointDetails(String actionPointOption, String actionPointDetails) {
        Locator textArea = actionPointDescriptionTextArea(actionPointOption);
        scrollIntoStablePanelView(textArea);
        assertThat(textArea).isVisible();
        textArea.fill(actionPointDetails);
    }

    private Locator actionPointDescriptionTextArea(String actionPointOption) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            boolean textAreaFound = Boolean.TRUE.equals(page.evaluate("""
                    actionPointOption => {
                        const normalize = value => (value || '').replace(/\\s+/g, ' ').trim().toLowerCase();
                        const isVisible = element => {
                            const style = window.getComputedStyle(element);
                            const rect = element.getBoundingClientRect();
                            return style.display !== 'none'
                                && style.visibility !== 'hidden'
                                && rect.width > 0
                                && rect.height > 0;
                        };

                        const expectedDescriptionLabel = `description of ${normalize(actionPointOption)}`;
                        const label = Array.from(document.querySelectorAll('label, div, span, p'))
                            .find(element => normalize(element.innerText || element.textContent)
                                .startsWith(expectedDescriptionLabel));

                        let textArea = null;
                        if (label) {
                            const forId = label.getAttribute('for');
                            if (forId) {
                                textArea = document.getElementById(forId);
                            }

                            let parent = label;
                            for (let i = 0; !textArea && parent && i < 6; i++) {
                                textArea = parent.querySelector?.('textarea');
                                parent = parent.parentElement;
                            }
                        }

                        if (!textArea || !isVisible(textArea)) {
                            textArea = Array.from(document.querySelectorAll('textarea')).find(isVisible);
                        }

                        if (!textArea || !isVisible(textArea)) {
                            return false;
                        }

                        document.querySelectorAll("[data-e2e-action-description='true']")
                            .forEach(element => element.removeAttribute('data-e2e-action-description'));
                        textArea.setAttribute('data-e2e-action-description', 'true');
                        textArea.scrollIntoView({ block: 'center', inline: 'nearest' });
                        return true;
                    }
                    """, actionPointOption));

            if (textAreaFound) {
                return page.locator(ACTION_DESCRIPTION_TEXTAREA_SELECTOR);
            }

            page.waitForTimeout(250);
        }

        throw new IllegalStateException("Action point description textarea was not found for: " + actionPointOption);
    }

    private void clickSaveDraft() {
        // Filling the description fires an autosave. "Save draft" is an OutSystems loading button
        // that re-renders (and briefly changes its accessible name) while a request is in flight,
        // so wait for the app to settle before locating/clicking to avoid a stale-element race.
        waitUntilSpinnersAreGone(SAVING_INDICATOR_TEXT, LOADING_INDICATOR_TEXT);

        // OutSystems renders hidden duplicate copies of this button, so target the visible one
        // explicitly rather than relying on DOM order (first/last).
        Locator saveDraftButton = page.locator(
                "button:visible",
                new Page.LocatorOptions().setHasText(SAVE_DRAFT_BUTTON_TEXT)
        ).first();

        saveDraftButton.scrollIntoViewIfNeeded();
        assertThat(saveDraftButton).isEnabled();
        saveDraftButton.click();
    }

    private void assertActionPointPreview(String expectedDate, String actionPointOption, String actionPointDetails) {
        Locator actionPoint = page.locator(ACTION_POINT_PREVIEW_SELECTOR)
                .filter(new Locator.FilterOptions().setHasText(expectedDate))
                .filter(new Locator.FilterOptions().setHasText(actionPointDetails))
                .last();

        assertThat(actionPoint).isVisible(new LocatorAssertions.IsVisibleOptions()
                .setTimeout(ACTION_POINT_PREVIEW_TIMEOUT_MILLIS));
        assertThat(actionPoint).containsText(expectedDate);
        assertThat(actionPoint).containsText(
                previewLabelFor(actionPointOption),
                new LocatorAssertions.ContainsTextOptions().setIgnoreCase(true));
        assertThat(actionPoint).containsText(actionPointDetails);
    }

    private String previewLabelFor(String actionPointOption) {
        String key = actionPointOption.replaceAll("\\s+", " ").trim().toLowerCase(Locale.UK);
        return ACTION_POINT_PREVIEW_LABELS.getOrDefault(key, actionPointOption);
    }

    private void clickSaveAndContinue() {
        Locator saveAndContinueButton = page.locator(GOVUK_BUTTON_SELECTOR)
                .filter(new Locator.FilterOptions().setHasText(SAVE_AND_CONTINUE_BUTTON_TEXT))
                .first();
        assertThat(saveAndContinueButton).isVisible();
        saveAndContinueButton.scrollIntoViewIfNeeded();
        saveAndContinueButton.click();
        waitUntilSpinnersAreGone(SAVING_INDICATOR_TEXT, LOADING_INDICATOR_TEXT);
    }

    private int daysFrom(String daysToAdd) {
        Matcher matcher = DAYS_PATTERN.matcher(daysToAdd);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Could not parse number of days from: " + daysToAdd);
        }

        return Integer.parseInt(matcher.group(1));
    }

    private String dateShortcutText(int days) {
        if (days == 0) {
            return "Today";
        }

        if (days == 1) {
            return "+1 day";
        }

        return "+" + days + " days";
    }
}
