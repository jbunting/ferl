/*
 * StrategyCompiled.java
 *
 * Created on May 5, 2007, 7:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.eval;

import edu.wvu.ferl.store.StoredRule;
import edu.wvu.ferl.cache.Cache;

import javax.rules.InvalidRuleSessionException;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * A {@link Strategy} used for scripting engines that support compilation.  This {@code Strategy} will invoke a
 * {@link CompiledScript} as retrieved from the script cache.
 *
 * @author jbunting
 */
class StrategyCompiled implements Strategy {

  private Cache<String, ScriptCompilation> scriptCache;

  /**
   * Creates a new object using the provided script cache.
   *
   * @param scriptCache the script cache to obtain {@link CompiledScript CompiledScripts} from
   */
  StrategyCompiled(Cache<String, ScriptCompilation> scriptCache) {
    this.scriptCache = scriptCache;
  }

  /**
   * Evalutes the compiled script after retrieving it from the script cache.
   *
   * @param rule          {@inheritDoc}
   * @param context       {@inheritDoc}
   * @param engineManager {@inheritDoc}
   * @return the output of the rule invocation
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public Object evaluateRule(StoredRule rule, ScriptContext context, ScriptEngineManager engineManager) throws InvalidRuleSessionException {
    CompiledScript compiledScript = scriptCache.lookup(rule.getUri()).getCompiled();
    try {
      return compiledScript.eval(context);
    } catch(ScriptException ex) {
      throw new InvalidRuleSessionException("Error running script...", ex);
    }
  }
}
