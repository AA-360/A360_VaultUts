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

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.DictionaryValue;
import com.automationanywhere.botcommand.samples.commands.utils.Debugger;
import com.automationanywhere.botcommand.samples.commands.utils.JsonUtils;
import com.automationanywhere.botcommand.samples.commands.utils.httpHelper;


import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.automationanywhere.commandsdk.model.AttributeType.CREDENTIAL;
import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.DataType.DICTIONARY;
import static com.automationanywhere.commandsdk.model.DataType.STRING;

@BotCommand
@CommandPkg(
        label = "[[GetCredential.label]]",
        description = "[[GetCredential.description]]",
        node_label = "[[GetCredential.node_label]]",
        icon = "vault.svg",
        name = "GetCredential",
        return_type = DICTIONARY,
        return_required = true
)
public class GetCredencial {

    @Sessions
    private Map<String, Object> sessionMap;

    @Execute
    public DictionaryValue action(
            @Idx(index = "1", type = TEXT)
            @Pkg(label = "[[GetCredential.name.label]]",default_value = "VAULT",default_value_type = STRING)
            @NotEmpty
                    String sessionName,
            @Idx(index = "2", type = TEXT)
            @Pkg(label = "[[GetCredential.secret.label]]")
            @NotEmpty
            String secret,
            @Idx(index = "3", type = TEXT)
            @Pkg(label = "[[GetCredential.path.label]]")
            @NotEmpty
            String path
    ) {
        Map<String, Object> Session = new HashMap<>();
        Debugger db = new Debugger();

        if (!sessionMap.containsKey(sessionName)) {
            throw new BotCommandException("Error Vault: Session not found!");
        }
        Session = (Map<String, Object>) sessionMap.get(sessionName);

        JSONObject response = getVaultData(Session.get("host").toString(),Session.get("token").toString(),secret,path);

        return JsonUtils.JsonObjToAADict(response);


    }

    private JSONObject getVaultData(String host,String token,String secret,String path){
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Vault-Token", token);

        String jsonString = httpHelper.get(host + "/v1/" + secret + "/data/" + path,headers);

        JSONObject data = JsonUtils.StringToObj(jsonString);

        JSONObject response_get = (JSONObject)JsonUtils.getKeyJson("data.data",data);

        return response_get;
    }



    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

}


