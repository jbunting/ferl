package edu.wvu.ferl.runtime;

import edu.wvu.ferl.store.StoredRuleExecutionSet;

import javax.rules.RuleExecutionSetMetadata;

/**
 * An implementation of {@code RuleExecutionSetMetadata}.  Essentially an adapter to the {@link StoredRuleExecutionSet}.
 * <p/>
 * Date: May 5, 2007
 * Time: 3:15 PM
 *
 * @author jbunting
 */
class RuleExecutionSetMetadataImpl implements RuleExecutionSetMetadata {

  private StoredRuleExecutionSet storedRuleExecutionSet;

  /**
   * Creates a new instance of {@code RuleExecutionSetMetadataImpl}
   *
   * @param storedRuleExecutionSet the execution set to adapt
   */
  public RuleExecutionSetMetadataImpl(StoredRuleExecutionSet storedRuleExecutionSet) {
    this.storedRuleExecutionSet = storedRuleExecutionSet;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getUri() {
    return storedRuleExecutionSet.getUri();
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getName() {
    return storedRuleExecutionSet.getName();
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getDescription() {
    return storedRuleExecutionSet.getDescription();
  }

}
