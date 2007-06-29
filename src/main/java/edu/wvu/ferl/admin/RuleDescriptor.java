/*
 * RuleDescriptor.java
 *
 * Created on May 7, 2007, 10:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.admin;

import edu.wvu.ferl.spi.RuleStore;
import edu.wvu.ferl.spi.StoredRuleExecutionSet;
import javax.rules.ConfigurationException;
import javax.rules.admin.Rule;

/**
 *
 * @author jbunting
 */
interface RuleDescriptor extends Rule {
  public String generateRule(RuleStore ruleStore) throws ConfigurationException;
}
