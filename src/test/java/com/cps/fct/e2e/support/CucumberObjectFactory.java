package com.cps.fct.e2e.support;


import com.cps.fct.e2e.pages.BasePage;
import com.cps.fct.e2e.stepdefs.ui.PageObjects;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.DefaultHttpService;
import com.cps.fct.e2e.utils.httpClient.HttpStatusValidator;
import com.cps.fct.e2e.utils.payloadBuilders.dcf.DCFPayloadBuilderForCM01;
import com.cps.fct.e2e.utils.payloadBuilders.dcf.DCFPayloadBuilderForLM04;
import com.cps.fct.e2e.utils.payloadBuilders.twif.TWIFPayloadBuilderForCM01;
import com.cps.fct.e2e.utils.playwright.PlaywrightContext;
import com.cps.fct.e2e.utils.playwright.PlaywrightManager;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.ddei.CommonService;
import com.cps.fct.e2e.utils.services.ddei.WitnessService;
import com.cps.fct.e2e.utils.services.messagaingApi.DCFMessageService;
import com.cps.fct.e2e.utils.services.messagaingApi.TWIFMessageService;
import io.cucumber.core.backend.ObjectFactory;
import io.cucumber.picocontainer.PicoFactory;
import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;

public class CucumberObjectFactory implements ObjectFactory {

    private final PicoFactory delegate = new PicoFactory();

    public CucumberObjectFactory() {
        delegate.addClass(CaseService.class);
        delegate.addClass(CommonService.class);
        delegate.addClass(ScenarioContext.class);
        delegate.addClass(DefaultHttpService.class);
        delegate.addClass(HttpStatusValidator.class);
        delegate.addClass(DCFMessageService.class);
        delegate.addClass(WitnessService.class);
        delegate.addClass(DCFPayloadBuilderForCM01.class);
        delegate.addClass(DCFPayloadBuilderForLM04.class);
        delegate.addClass(TWIFMessageService.class);
        delegate.addClass(TWIFPayloadBuilderForCM01.class);
        delegate.addClass(PlaywrightManager.class);
        delegate.addClass(PlaywrightContext.class);
        delegate.addClass(PageObjects.class);
        addedAllPageClass();
    }

    private void addedAllPageClass() {
        Reflections reflections = new Reflections("com.cps.fct.e2e.pages");
        Set<Class<? extends BasePage>> pageClasses = reflections.getSubTypesOf(BasePage.class);
        for (Class<? extends BasePage> pageClass : pageClasses) {
            if (pageClass.getSimpleName().endsWith("Page")) {
                delegate.addClass(pageClass);
            }
        }
    }

    @Override
    public void start() {
        delegate.start();
    }

    @Override
    public void stop() {
        delegate.stop();
    }

    @Override
    public boolean addClass(Class<?> aClass) {
        return delegate.addClass(aClass);
    }

    @Override
    public <T> T getInstance(Class<T> aClass) {
        return delegate.getInstance(aClass);
    }

    private Set<Class<?>> addAllPages() {
            Reflections reflections = new Reflections("com.cps.fct.e2e.pages"); // replace with your actual package
             return reflections.getSubTypesOf(Object.class).stream()
                    .filter(clazz -> clazz.getSimpleName().endsWith("Page"))
                    .collect(Collectors.toSet());
    }

}


