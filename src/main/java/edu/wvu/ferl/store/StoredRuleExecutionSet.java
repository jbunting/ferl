package edu.wvu.ferl.store;

import java.util.List;
import java.util.Map;

/**
 * This is the stored representation of the {@link javax.rules.admin.RuleExecutionSet RuleExecutionSet}.  It is used
 * during execution as well as administration.
 *
 * Date: June 28, 2007
 * Time: 9:37 PM
 * @author jbunting
 */
public interface StoredRuleExecutionSet {

  /**
   * The name of a class to be used as an {@link javax.rules.ObjectFilter}.
   * @see javax.rules.admin.RuleExecutionSet#getDefaultObjectFilter()
   * @return the name of the object filter class
   */
  public String getDefaultObjectFilter();

  /**
   * A human readable description of what this execution set is, or what it does.
   * @see javax.rules.admin.RuleExecutionSet#getDescription()
   * @return the description of this set
   */
  public String getDescription();

  /**
   * A simple name for this execution set.
   * @see javax.rules.admin.RuleExecutionSet#getName()
   * @return the name of this execution set
   */
  public String getName();

  /**
   * Properties to be stored with this execution set.  This map should be read-only.
   * @see javax.rules.admin.RuleExecutionSet#getProperty(Object) 
   * @return the map of properties
   */
  public Map<Object, Object> getProperties();

  /**
   * A list of uris that reference the rules that make up this execution set. This differs from
   * {@link javax.rules.admin.RuleExecutionSet#getRules()} by simply referring to rules, rather than including them.
   * This allows rules to be first-order entities and therefore reused among multiple sets.  This list should be
   * read-only.
   * @return the list of rule uris
   */
  public List<String> getRuleUris();

  /**
   * The uniquely identifiable uri of this rule execution set.  This uri is what is used to reference this set in other
   * contexts.
   * @see javax.rules.RuleRuntime#createRuleSession(String, java.util.Map, int) 
   * @return the uri of this rule execution set
   */
  public String getUri();
  
}
