/*
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
 * RuleRuntime.java
 *
 * Created on May 5, 2007, 2:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.runtime;

import edu.wvu.ferl.eval.RuleEvaluator;
import edu.wvu.ferl.store.RuleStore;
import edu.wvu.ferl.store.StoredRuleExecutionSet;
import edu.wvu.ferl.RuleServiceProvider;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.rules.RuleExecutionSetNotFoundException;
import javax.rules.RuleRuntime;
import javax.rules.RuleSession;
import javax.rules.RuleSessionCreateException;
import javax.rules.RuleSessionTypeUnsupportedException;

/**
 * The ferl implementation of {@link RuleRuntime}.
 *
 * @author jbunting
 */
public class RuleRuntimeImpl implements RuleRuntime {

  private RuleServiceProvider serviceProvider;

  protected RuleEvaluator ruleEvaluator = new RuleEvaluator(this);

  /**
   * Creates a new instance of the ferl {@link RuleRuntime}.
   *
   * @param serviceProvider the service provider that created this runtime
   */
  public RuleRuntimeImpl(RuleServiceProvider serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  public RuleSession createRuleSession(String uri, Map properties, int i)
          throws RuleSessionTypeUnsupportedException,
          RuleSessionCreateException,
          RuleExecutionSetNotFoundException,
          RemoteException {
    StoredRuleExecutionSet set = serviceProvider.getRuleStore().lookupRuleSet(uri);
    if(set == null) {
      throw new RuleExecutionSetNotFoundException("No RuleExecutionSet registered at uri " + uri);
    }

    if(RuleRuntime.STATELESS_SESSION_TYPE == i) {
      return new StatelessRuleSessionImpl(set, properties, this);
    } else {
      throw new RuleSessionTypeUnsupportedException("Don't support RuleSession type " + i);
    }
  }

  public List getRegistrations() throws RemoteException {
    return serviceProvider.getRuleStore().listRuleSets();
  }

  public RuleStore getRuleStore() {
    return serviceProvider.getRuleStore();
  }

  public RuleServiceProvider getRuleServiceProvider() {
    return serviceProvider;
  }
}
