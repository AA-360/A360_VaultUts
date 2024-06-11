/*
 * Copyright (c) 2020 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */

/**
 *
 */
package com.automationanywhere.botcommand.samples.commands.basic;


import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.botcommand.samples.commands.utils.Debugger;
import com.automationanywhere.botcommand.samples.commands.utils.httpHelper;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.core.security.SecureString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.automationanywhere.commandsdk.model.AttributeType.CREDENTIAL;
import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.DataType.STRING;

@BotCommand
@CommandPkg(
        label = "[[VaultStart.label]]",
        description = "[[VaultStart.description]]",
        icon = "vault.svg",
        name = "VaultStart"
)
public class VaultStart {

    @Sessions
    private Map<String, Object> sessionMap;


    @Execute
    public void action(
            @Idx(index = "1", type = TEXT)
            @Pkg(label = "[[VaultStart.name.label]]",default_value = "VAULT",default_value_type = STRING)
            @NotEmpty
                    String SessionName,
            @Idx(index = "2", type = TEXT)
            @Pkg(label = "[[VaultStart.host.label]]")
            @NotEmpty
            String Host,
            @Idx(index = "3", type = CREDENTIAL)
            @Pkg(label = "[[VaultStart.token.label]]")
            @NotEmpty
            SecureString Token
    ) {

        Debugger db = new Debugger();
        this.testConnection(Host,Token.getInsecureString());
        if (!sessionMap.containsKey(SessionName)) {
            Map<String, Object> Data = new HashMap<>();
            Data.put("host", Host);
            Data.put("token", Token.getInsecureString());
            sessionMap.put(SessionName, Data);
        }
    }

    public void action2(
            String SessionName,
            String Host,
            String Token
    ) {

        Debugger db = new Debugger();
        this.testConnection(Host,Token);
        if (!sessionMap.containsKey(SessionName)) {
            Map<String, Object> Data = new HashMap<>();
            Data.put("host", Host);
            Data.put("token", Token);
            sessionMap.put(SessionName, Data);
        }
    }



    void testConnection(String host,String token){
        Map<String, String> Data = new HashMap<>();
        Data.put("X-Vault-Token", token);

        httpHelper.get(host + "/v1/auth/token/lookup-self",Data);

    }

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }


}


