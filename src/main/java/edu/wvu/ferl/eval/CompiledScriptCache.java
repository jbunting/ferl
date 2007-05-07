/*
 * CompiledScriptCache.java
 *
 * Created on May 5, 2007, 7:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.eval;

import javax.script.CompiledScript;

/**
 *
 * @author jbunting
 */
public interface CompiledScriptCache {
  public void storeCompiledScript(String ruleName, String script, CompiledScript compiledScript);
  public CompiledScript retrieveCompiledScript(String ruleName, String script);
}
