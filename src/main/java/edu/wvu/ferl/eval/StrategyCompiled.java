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
 *
 * @author jbunting
 */
class StrategyCompiled implements Strategy {

  private Cache<String, ScriptCompilation> scriptCache;

  StrategyCompiled(Cache<String, ScriptCompilation> scriptCache) {
    this.scriptCache = scriptCache;
  }

  public Object evaluateRule(StoredRule rule, ScriptContext context, ScriptEngineManager engineManager) throws InvalidRuleSessionException {
    CompiledScript compiledScript = scriptCache.lookup(rule.getUri()).getCompiled();
    try {
      return compiledScript.eval(context);
    } catch (ScriptException ex) {
      throw new InvalidRuleSessionException("Error running script...", ex);
    }
  }
}
