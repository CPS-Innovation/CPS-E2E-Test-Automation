package com.cps.fct.e2e.pages;

import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.SecurePassCode;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppsLoginPage {
    private static final Logger logger =
            LoggerFactory.getLogger(AppsLoginPage.class);

    @Inject private ScenarioContext context;
    private static final String CPS_USER_KEY = "CPS_USER";
    private static final String PASSWORD_KEY = "PASSWORD";

    public String vcaUsername() {
        String envSuffix = requiredContextValue("envSuffix");
        return requiredEnvValue(CPS_USER_KEY) + envSuffix;
    }

    public String vcaPassword() {
        return SecurePassCode.decode(requiredEnvValue(PASSWORD_KEY));
    }

    public String requiredContextValue(String key) {
        String value = context.getAsString(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("No value found in scenario context for key: " + key);
        }
        return value;
    }


    private String requiredEnvValue(String key) {
        String value = EnvConfig.getEnv(key);
        if (isBlank(value)) {
            throw new IllegalStateException("No value found in environment for key: " + key);
        }
        return value;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }




}
