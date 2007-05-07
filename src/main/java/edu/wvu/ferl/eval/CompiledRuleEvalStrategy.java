/*
 * CompiledRuleEvalStrategy.java
 *
 * Created on May 5, 2007, 7:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.eval;

import edu.wvu.ferl.spi.StoredRule;
import javax.rules.InvalidRuleSessionException;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author jbunting
 */
public class CompiledRuleEvalStrategy implements RuleEvalStrategy {
  
  private CompiledScriptCache cache;
  
  /** Creates a new instance of CompiledRuleEvalStrategy */
  public CompiledRuleEvalStrategy(CompiledScriptCache cache) {
    this.cache = cache;
  }

  public Object evaluateRule(StoredRule rule, ScriptContext context, ScriptEngineManager engineManager) throws InvalidRuleSessionException {
    CompiledScript compiledScript = cache.retrieveCompiledScript(rule.getName(), rule.getScript());
    if(compiledScript == null) {
      ScriptEngine engine = engineManager.getEngineByName(rule.getLanguage());
      Compilable compiler = (Compilable) engine;
      try {
        compiledScript = compiler.compile(rule.getScript());
      } catch (ScriptException ex) {
        throw new InvalidRuleSessionException("Error compiling script...", ex);
      }
      cache.storeCompiledScript(rule.getName(), rule.getScript(), compiledScript);
    }
    try {
      return compiledScript.eval(context);
    } catch (ScriptException ex) {
      throw new InvalidRuleSessionException("Error running script...", ex);
    }
  }
  
}
