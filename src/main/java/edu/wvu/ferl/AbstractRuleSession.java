/*
 * AbstractRuleSession.java
 *
 * Created on May 5, 2007, 3:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl;

import edu.wvu.ferl.eval.CompiledRuleEvalStrategy;
import edu.wvu.ferl.eval.RuleEvalStrategy;
import edu.wvu.ferl.eval.SimpleRuleEvalStrategy;
import edu.wvu.ferl.spi.StoredRule;
import edu.wvu.ferl.spi.StoredRuleExecutionSet;
import java.rmi.RemoteException;
import java.util.Map;
import javax.rules.InvalidRuleSessionException;
import javax.rules.RuleExecutionException;
import javax.rules.RuleExecutionSetMetadata;
import javax.rules.RuleSession;
import javax.script.Compilable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

/**
 *
 * @author jbunting
 */
public abstract class AbstractRuleSession implements RuleSession {
  
  protected StoredRuleExecutionSet storedRuleExecutionSet;
  protected RuleRuntimeImpl ruleRuntime;
  protected Map properties;
  protected ScriptEngineManager scriptEngineManager;
  
  /** Creates a new instance of AbstractRuleSession */
  public AbstractRuleSession(StoredRuleExecutionSet storedRuleExecutionSet, Map properties, RuleRuntimeImpl ruleRuntime) {
    this.storedRuleExecutionSet = storedRuleExecutionSet;
    this.properties = properties;
    this.ruleRuntime = ruleRuntime;
    scriptEngineManager = new ScriptEngineManager();
  }

  public RuleExecutionSetMetadata getRuleExecutionSetMetadata() throws InvalidRuleSessionException, RemoteException {
    return new RuleExecutionSetMetadataImpl(this.storedRuleExecutionSet);
  }

  public void release() throws RemoteException, InvalidRuleSessionException {
    // don't really know what to do here...
  }
  
  protected void executeRules(ExecuteRulesHook hook) throws InvalidRuleSessionException, RemoteException {
    ScriptContext context = new SimpleScriptContext();
    hook.populateScriptContext(context);
    for(String ruleUri: this.storedRuleExecutionSet.getRuleUris()) {
      StoredRule rule = this.ruleRuntime.getRuleStore().lookupRule(ruleUri);
      if(rule == null) {
        throw new InvalidRuleSessionException("Cannot locate rule by uri: " + ruleUri);
      }
      hook.handleOutput(context, determineStrategy(rule.getLanguage()).evaluateRule(rule, context, scriptEngineManager));
    }
  }
  
  private Object evaluateRule(StoredRule rule, ScriptContext context) throws InvalidRuleSessionException {
    ScriptEngine engine = scriptEngineManager.getEngineByName(rule.getLanguage());

    if(engine == null) {
      throw new InvalidRuleSessionException("ScriptEngine " + rule.getLanguage() + " specified by rule " + rule.getUri() + " is not available...");
    }
    try {
      return engine.eval(rule.getScript(), context);
    } catch (ScriptException ex) {
      throw new InvalidRuleSessionException("Error executing rule: " + rule.getUri(), ex);
    }
  }
  
  private RuleEvalStrategy determineStrategy(String language) {
    RuleEvalStrategy strategy = null;
    synchronized(this) {
      strategy = ruleRuntime.strategies.get(language);
      if(strategy == null) {
        ScriptEngine engine = scriptEngineManager.getEngineByName(language);
        if(engine instanceof Compilable) {
          strategy = new CompiledRuleEvalStrategy(ruleRuntime.compiledScriptCache);
        } else {
          strategy = new SimpleRuleEvalStrategy();
        }
        ruleRuntime.strategies.put(language, strategy);
      }
    }
    return strategy;
  }

  protected interface ExecuteRulesHook {
    public void populateScriptContext(ScriptContext scriptContext);
    public void handleOutput(ScriptContext context, Object output);
  }
}
