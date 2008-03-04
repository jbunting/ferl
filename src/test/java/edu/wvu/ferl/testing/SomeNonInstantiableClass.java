package edu.wvu.ferl.testing;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Mar 4, 2008
 * Time: 7:58:06 AM
 */
public class SomeNonInstantiableClass implements SomeRandomInterface {
  public SomeNonInstantiableClass() throws Exception {
    throw new Exception("You will never be able to instantiate me!");
  }
}
