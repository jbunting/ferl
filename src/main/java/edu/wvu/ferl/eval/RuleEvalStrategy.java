/*
 * RuleEvalStrategy.java
 *
 * Created on May 5, 2007, 7:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.eval;

import edu.wvu.ferl.spi.StoredRule;
import javax.rules.InvalidRuleSessionException;
import javax.script.ScriptContext;
import javax.script.ScriptEngineManager;

/**
 *
 * @author jbunting
 */
public interface RuleEvalStrategy {
  public Object evaluateRule(StoredRule rule, ScriptContext context, ScriptEngineManager engineManager) throws InvalidRuleSessionException ;
}
