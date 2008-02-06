package edu.wvu.ferl.eval;

import java.text.MessageFormat;

/**
 * Created by IntelliJ IDEA.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 10:19:46 AM
 * To change this template use File | Settings | File Templates.
 */
class ScriptLoadingException extends Exception {

  private static final String RULE_NOT_EXIST_MESSAGE = "Rule {0} does not exist, cannot be loaded.";
  private static final String NOT_VALID_SCRIPT_TYPE_MESSAGE = "Script type {0} specified by rule {1} is not valid.";
  private static final String NOT_COMPILABLE_MESSAGE = "Script type {0} specified in rule {1} is not Compilable.";
  private static final String ERROR_COMPILING_MESSAGE = "Encountered an error compiling script specified by {1} in language {0}.";

  private static final MessageFormat RULE_NOT_EXIST_FORMAT = new MessageFormat(RULE_NOT_EXIST_MESSAGE);
  private static final MessageFormat NOT_VALID_SCRIPT_TYPE_FORMAT = new MessageFormat(NOT_VALID_SCRIPT_TYPE_MESSAGE);
  private static final MessageFormat NOT_COMPILABLE_FORMAT = new MessageFormat(NOT_COMPILABLE_MESSAGE);
  private static final MessageFormat ERROR_COMPILING_FORMAT = new MessageFormat(ERROR_COMPILING_MESSAGE);

  private ScriptLoadingException() {
  }

  private ScriptLoadingException(String message) {
    super(message);
  }

  private ScriptLoadingException(String message, Throwable cause) {
    super(message, cause);
  }

  private ScriptLoadingException(Throwable cause) {
    super(cause);
  }

  public static ScriptLoadingException newRuleNotExist(String ruleName) {
    return new ScriptLoadingException(format(RULE_NOT_EXIST_FORMAT, ruleName));
  }

  public static ScriptLoadingException newNotValidScriptType(String scriptType, String ruleName) {
    return new ScriptLoadingException(format(NOT_VALID_SCRIPT_TYPE_FORMAT, scriptType, ruleName));
  }

  public static ScriptLoadingException newNotCompilable(String scriptType, String ruleName) {
    return new ScriptLoadingException(format(NOT_COMPILABLE_FORMAT, scriptType, ruleName));
  }

  public static ScriptLoadingException newErrorCompiling(String scriptType, String ruleName, Throwable cause) {
    return new ScriptLoadingException(format(ERROR_COMPILING_FORMAT, scriptType, ruleName), cause);
  }

  private static String format(MessageFormat format, Object ... args) {
    return format.format(args);
  }
}
