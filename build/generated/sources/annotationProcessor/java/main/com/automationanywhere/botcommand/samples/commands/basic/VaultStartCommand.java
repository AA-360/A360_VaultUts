package com.automationanywhere.botcommand.samples.commands.basic;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.core.security.SecureString;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class VaultStartCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(VaultStartCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.entrySet().stream().filter(en -> !Arrays.asList( new String[] {}).contains(en.getKey()) && en.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    VaultStart command = new VaultStart();
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("SessionName") && parameters.get("SessionName") != null && parameters.get("SessionName").get() != null) {
      convertedParameters.put("SessionName", parameters.get("SessionName").get());
      if(convertedParameters.get("SessionName") !=null && !(convertedParameters.get("SessionName") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","SessionName", "String", parameters.get("SessionName").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("SessionName") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","SessionName"));
    }

    if(parameters.containsKey("Host") && parameters.get("Host") != null && parameters.get("Host").get() != null) {
      convertedParameters.put("Host", parameters.get("Host").get());
      if(convertedParameters.get("Host") !=null && !(convertedParameters.get("Host") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","Host", "String", parameters.get("Host").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("Host") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","Host"));
    }

    if(parameters.containsKey("Token") && parameters.get("Token") != null && parameters.get("Token").get() != null) {
      convertedParameters.put("Token", parameters.get("Token").get());
      if(convertedParameters.get("Token") !=null && !(convertedParameters.get("Token") instanceof SecureString)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","Token", "SecureString", parameters.get("Token").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("Token") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","Token"));
    }

    command.setSessionMap(sessionMap);
    try {
      command.action((String)convertedParameters.get("SessionName"),(String)convertedParameters.get("Host"),(SecureString)convertedParameters.get("Token"));Optional<Value> result = Optional.empty();
      return logger.traceExit(result);
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","action"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }
}
