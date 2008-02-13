package edu.wvu.ferl.store;

import java.util.Map;

/**
 * This is a stored representation of {@link javax.rules.admin.Rule}.  It is used during execution as well as
 * administration.
 * <p/>
 * Date: June 28, 2007
 * Time: 9:33 PM
 *
 * @author jbunting
 */
public interface StoredRule {

  /**
   * A human readable description of what this rule is, or what it does.
   *
   * @return the description of this rule
   * @see javax.rules.admin.Rule#getDescription()
   */
  public String getDescription();

  /**
   * The language that is used for the script contained in this rule.  This should be a string that will return a valid
   * script engine when passed to {@link javax.script.ScriptEngineManager#getEngineByName(String)} .
   *
   * @return the language used for this rule
   */
  public String getLanguage();

  /**
   * A simple name for this rule.
   *
   * @return the name of this rule
   * @see javax.rules.admin.Rule#getName()
   */
  public String getName();


  /**
   * Properties to be stored with this rule.  This map should be read-only.
   *
   * @return the map of properties
   * @see javax.rules.admin.Rule#getProperty(Object)
   */
  public Map<Object, Object> getProperties();

  /**
   * The script for this rule.  This is the actual substance of the rule, what it does, what it decides.  This script
   * should be written in the language specified by {@link #getLanguage()}.  See
   * {@link edu.wvu.ferl.runtime.RuleRuntimeImpl} for more information on how the rule inputs and outputs are adapted
   * to the {@link javax.script.ScriptContext}.
   *
   * @return the text of the script for this rule
   */
  public String getScript();


  /**
   * The uniquely identifiable uri of this rule.  This uri is what is used to reference this rule in other contexts,
   * including in the rule execution set.
   *
   * @return the uri of this rule execution set
   * @see edu.wvu.ferl.store.StoredRuleExecutionSet#getRuleUris()
   */
  public String getUri();

}
