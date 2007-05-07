/*
 * SimpleRuleEvalStrategy.java
 *
 * Created on May 5, 2007, 7:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.eval;

import edu.wvu.ferl.spi.StoredRule;
import javax.rules.InvalidRuleSessionException;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author jbunting
 */
public class SimpleRuleEvalStrategy implements RuleEvalStrategy {
  
  /** Creates a new instance of SimpleRuleEvalStrategy */
  public SimpleRuleEvalStrategy() {
  }

  public Object evaluateRule(StoredRule rule, ScriptContext context, ScriptEngineManager engineManager) throws InvalidRuleSessionException {
    ScriptEngine engine = engineManager.getEngineByName(rule.getLanguage());

    if(engine == null) {
      throw new InvalidRuleSessionException("ScriptEngine " + rule.getLanguage() + " specified by rule " + rule.getUri() + " is not available...");
    }
    try {
      return engine.eval(rule.getScript(), context);
    } catch (ScriptException ex) {
      throw new InvalidRuleSessionException("Error executing rule: " + rule.getUri(), ex);
    }
  }
  
}
