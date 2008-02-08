package edu.wvu.ferl.eval;

import java.text.MessageFormat;

/**
 * Indicates when an error occurs during the strategy loading process.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 11:56:46 AM
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings({"ThrowableInstanceNeverThrown"})
class StrategyLoadingException extends Exception {
  
  private static final String LANGUAGE_NOT_EXIST_MESSAGE =
          "Language {0} does not exist, a strategy cannot be loaded for it.";
  private static final MessageFormat LANGUAGE_NOT_EXIST_FORMAT = new MessageFormat(LANGUAGE_NOT_EXIST_MESSAGE);

  /**
   * Constructs a new exception with <code>null</code> as its detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   */
  @SuppressWarnings({"UnusedDeclaration"})
  private StrategyLoadingException() {
  }

  /**
   * Constructs a new exception with the specified detail message.  The
   * cause is not initialized, and may subsequently be initialized by
   * a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  private StrategyLoadingException(String message) {
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
  @SuppressWarnings({"UnusedDeclaration"})
  private StrategyLoadingException(String message, Throwable cause) {
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
  private StrategyLoadingException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new exception that indicates that the language requested does not exist.
   * @param language the language that does not exist
   * @return the new exception
   */
  public static StrategyLoadingException newLanguageNotExist(String language) {
    return new StrategyLoadingException(format(LANGUAGE_NOT_EXIST_FORMAT, language));
  }
  
  /**
   * Convenience method for doing the array conversions.
   * @param format the format to build an error message from
   * @param args the arguments to pass into the format
   * @return the new error message
   */
  private static String format(MessageFormat format, Object ... args) {
    return format.format(args);
  }
}
