package com.automationanywhere.botcommand.samples.commands.basic;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
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

public final class GetCredencialCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(GetCredencialCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.entrySet().stream().filter(en -> !Arrays.asList( new String[] {}).contains(en.getKey()) && en.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    GetCredencial command = new GetCredencial();
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("sessionName") && parameters.get("sessionName") != null && parameters.get("sessionName").get() != null) {
      convertedParameters.put("sessionName", parameters.get("sessionName").get());
      if(convertedParameters.get("sessionName") !=null && !(convertedParameters.get("sessionName") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","sessionName", "String", parameters.get("sessionName").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("sessionName") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","sessionName"));
    }

    if(parameters.containsKey("secret") && parameters.get("secret") != null && parameters.get("secret").get() != null) {
      convertedParameters.put("secret", parameters.get("secret").get());
      if(convertedParameters.get("secret") !=null && !(convertedParameters.get("secret") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","secret", "String", parameters.get("secret").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("secret") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","secret"));
    }

    if(parameters.containsKey("path") && parameters.get("path") != null && parameters.get("path").get() != null) {
      convertedParameters.put("path", parameters.get("path").get());
      if(convertedParameters.get("path") !=null && !(convertedParameters.get("path") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","path", "String", parameters.get("path").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("path") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","path"));
    }

    command.setSessionMap(sessionMap);
    try {
      Optional<Value> result =  Optional.ofNullable(command.action((String)convertedParameters.get("sessionName"),(String)convertedParameters.get("secret"),(String)convertedParameters.get("path")));
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
