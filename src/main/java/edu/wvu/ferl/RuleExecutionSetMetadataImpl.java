/*
 * RuleExecutionSetMetadataImpl.java
 *
 * Created on May 5, 2007, 3:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl;

import edu.wvu.ferl.spi.StoredRuleExecutionSet;
import javax.rules.RuleExecutionSetMetadata;

/**
 *
 * @author jbunting
 */
public class RuleExecutionSetMetadataImpl implements RuleExecutionSetMetadata {
  
  private StoredRuleExecutionSet storedRuleExecutionSet;
  
  /** Creates a new instance of RuleExecutionSetMetadataImpl */
  public RuleExecutionSetMetadataImpl(StoredRuleExecutionSet storedRuleExecutionSet) {
    this.storedRuleExecutionSet = storedRuleExecutionSet;
  }

  public String getUri() {
    return storedRuleExecutionSet.getUri();
  }

  public String getName() {
    return storedRuleExecutionSet.getName();
  }

  public String getDescription() {
    return storedRuleExecutionSet.getDescription();
  }
  
}
