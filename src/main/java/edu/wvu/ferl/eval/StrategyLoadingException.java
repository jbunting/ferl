package edu.wvu.ferl.eval;

import java.text.MessageFormat;

/**
 * Created by IntelliJ IDEA.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 11:56:46 AM
 * To change this template use File | Settings | File Templates.
 */
class StrategyLoadingException extends Exception {
  private static final String LANGUAGE_NOT_EXIST_MESSAGE = "Rule {0} does not exist, cannot be loaded.";
  private static final MessageFormat LANGUAGE_NOT_EXIST_FORMAT = new MessageFormat(LANGUAGE_NOT_EXIST_MESSAGE);

  private StrategyLoadingException() {
  }

  private StrategyLoadingException(String message) {
    super(message);
  }

  private StrategyLoadingException(String message, Throwable cause) {
    super(message, cause);
  }

  private StrategyLoadingException(Throwable cause) {
    super(cause);
  }

  public static StrategyLoadingException newLanguageNotExist(String language) {
    return new StrategyLoadingException(format(LANGUAGE_NOT_EXIST_FORMAT, language));
  }
  
  private static String format(MessageFormat format, Object ... args) {
    return format.format(args);
  }
}
