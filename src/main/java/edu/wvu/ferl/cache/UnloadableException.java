package edu.wvu.ferl.cache;

/**
 * Created by IntelliJ IDEA.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 10:54:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class UnloadableException extends CacheException {

  public UnloadableException() {
    super();
  }

  public UnloadableException(String message) {
    super(message);
  }

  public UnloadableException(Throwable cause) {
    super(cause);
  }

  public UnloadableException(String message, Throwable cause) {
    super(message, cause);
  }
}
