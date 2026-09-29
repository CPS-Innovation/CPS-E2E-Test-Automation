package com.cps.fct.e2e.general;

import com.cps.fct.e2e.utils.services.ddei.VictimService;
import org.picocontainer.annotations.Inject;


import static com.cps.fct.e2e.utils.common.DateTimeUtils.*;
import static com.cps.fct.e2e.utils.common.FakerUtils.*;

public class Sample {
    @Inject
    private VictimService victimService;

    public static void main(String[] args) {
//        System.out.println("Hello World");
        String victimName = "Koelpin, Ernest";

        String victimFullName = victimName.substring(1, victimName.indexOf(",")).toUpperCase()
                + victimName.substring(victimName.indexOf(",") + victimName.length() - 1);


        System.out.println(victimFullName);
    }


}


