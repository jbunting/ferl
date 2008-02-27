/**
 * Copyright 2008 West Virginia University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

/*
 * Strategy.java
 *
 * Created on May 5, 2007, 7:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.eval;

import edu.wvu.ferl.store.StoredRule;

import javax.rules.RuleExecutionException;
import javax.script.ScriptContext;
import javax.script.ScriptEngineManager;

/**
 * The {@code Strategy} is invoked by the {@link RuleEvaluator} in order to actually run the rule.  The
 * {@code Strategy} does the actual mechanics of invoking the scripting engine, setting up the context, and evaluating
 * the rule.
 *
 * @author jbunting
 */
interface Strategy {
  /**
   * The method to be invoked in order to evaluate the rule.
   *
   * @param rule          the rule to evaluate
   * @param context       the context in which to evaluate the rule
   * @param engineManager the engine manager to retrieve the appropriate engine from
   * @return the output of the rule invocation
   * @throws RuleExecutionException if there was an error in the rule evaluation process
   */
  public Object evaluateRule(StoredRule rule, ScriptContext context, ScriptEngineManager engineManager)
          throws RuleExecutionException;
}
