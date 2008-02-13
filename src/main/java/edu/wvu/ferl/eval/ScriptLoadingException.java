package edu.wvu.ferl.eval;

import java.text.MessageFormat;

/**
 * Used in case of an error during script loading.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 10:19:46 AM
 */
@SuppressWarnings({"ThrowableInstanceNeverThrown"})
class ScriptLoadingException extends Exception {

  private static final String RULE_NOT_EXIST_MESSAGE = "Rule {0} does not exist, cannot be loaded.";
  private static final String NOT_VALID_SCRIPT_TYPE_MESSAGE = "Script type {0} specified by rule {1} is not valid.";
  private static final String NOT_COMPILABLE_MESSAGE = "Script type {0} specified in rule {1} is not Compilable.";
  private static final String ERROR_COMPILING_MESSAGE = "Encountered an error compiling script specified by {1} in language {0}.";

  private static final MessageFormat RULE_NOT_EXIST_FORMAT = new MessageFormat(RULE_NOT_EXIST_MESSAGE);
  private static final MessageFormat NOT_VALID_SCRIPT_TYPE_FORMAT = new MessageFormat(NOT_VALID_SCRIPT_TYPE_MESSAGE);
  private static final MessageFormat NOT_COMPILABLE_FORMAT = new MessageFormat(NOT_COMPILABLE_MESSAGE);
  private static final MessageFormat ERROR_COMPILING_FORMAT = new MessageFormat(ERROR_COMPILING_MESSAGE);

  /**
   * Constructs a new exception with <code>null</code> as its detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   */
  @SuppressWarnings({"UnusedDeclaration"})
  private ScriptLoadingException() {
  }

  /**
   * Constructs a new exception with the specified detail message.  The
   * cause is not initialized, and may subsequently be initialized by
   * a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  private ScriptLoadingException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified detail message and
   * cause.  <p>Note that the detail message associated with
   * <code>cause</code> is <i>not</i> automatically incorporated in
   * this exception's detail message.
   *
   * @param message the detail message (which is saved for later retrieval
   *                by the {@link #getMessage()} method).
   * @param cause   the cause (which is saved for later retrieval by the
   *                {@link #getCause()} method).  (A <tt>null</tt> value is
   *                permitted, and indicates that the cause is nonexistent or
   *                unknown.)
   */
  private ScriptLoadingException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified cause and a detail
   * message of <tt>(cause==null ? null : cause.toString())</tt> (which
   * typically contains the class and detail message of <tt>cause</tt>).
   * This constructor is useful for exceptions that are little more than
   * wrappers for other throwables (for example, {@link
   * java.security.PrivilegedActionException}).
   *
   * @param cause the cause (which is saved for later retrieval by the
   *              {@link #getCause()} method).  (A <tt>null</tt> value is
   *              permitted, and indicates that the cause is nonexistent or
   *              unknown.)
   */
  @SuppressWarnings({"UnusedDeclaration"})
  private ScriptLoadingException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new {@code ScriptLoadingException} to indicate that a given rule does not exist.
   *
   * @param ruleName the name of the rule that does not exist
   * @return the new exception
   */
  public static ScriptLoadingException newRuleNotExist(String ruleName) {
    return new ScriptLoadingException(format(RULE_NOT_EXIST_FORMAT, ruleName));
  }

  /**
   * Creates a new {@code ScriptLoadingException} to indicate that a given rule specifies a script type that is not
   * recognized by the current runtime.
   *
   * @param scriptType the type of script that is not recognized
   * @param ruleName   the name of the rule that specified the script
   * @return the new exception
   */
  public static ScriptLoadingException newNotValidScriptType(String scriptType, String ruleName) {
    return new ScriptLoadingException(format(NOT_VALID_SCRIPT_TYPE_FORMAT, scriptType, ruleName));
  }

  /**
   * Creates a new {@code ScriptLoadingException} to indicate that the language specified by a given rule is not
   * compilable.
   *
   * @param scriptType the language that is not compilable
   * @param ruleName   the name of the rule that specified the script
   * @return the new exception
   */
  public static ScriptLoadingException newNotCompilable(String scriptType, String ruleName) {
    return new ScriptLoadingException(format(NOT_COMPILABLE_FORMAT, scriptType, ruleName));
  }

  /**
   * Creates a new {@code ScriptLoadingException} to indicate that the script specified by a given rule did not
   * successfully compile.
   *
   * @param scriptType the language of the script that won't compile
   * @param ruleName   the name of the rule that specified the script
   * @param cause      the underlying cause of the issue
   * @return the new exception
   */
  public static ScriptLoadingException newErrorCompiling(String scriptType, String ruleName, Throwable cause) {
    return new ScriptLoadingException(format(ERROR_COMPILING_FORMAT, scriptType, ruleName), cause);
  }

  /**
   * Convenience method for doing the array conversions.
   *
   * @param format the format to build an error message from
   * @param args   the arguments to pass into the format
   * @return the new error message
   */
  private static String format(MessageFormat format, Object... args) {
    return format.format(args);
  }
}
