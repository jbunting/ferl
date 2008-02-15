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
 * StatefulRuleSessionImpl.java
 *
 * Created on May 5, 2007, 4:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.runtime;

import edu.wvu.ferl.store.StoredRuleExecutionSet;
import edu.wvu.ferl.util.ListMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.rules.Handle;
import javax.rules.InvalidHandleException;
import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleRuntime;
import javax.rules.StatefulRuleSession;

import org.apache.commons.collections15.Transformer;

/**
 * The implementation of the stateful rules session.
 * <p/>
 * It is important to note, that if during execution, a script removes an object from the passed list, and then re-adds
 * it, it will have a new handle.  If the object is changed, it will retain its
 *
 * @author jbunting
 */
class StatefulRuleSessionImpl extends AbstractRuleSession implements StatefulRuleSession {

  private static final Transformer<Object, Handle> HANDLE_CREATION_TRANSFORMER = new Transformer<Object, Handle>() {
    public Handle transform(Object o) {
      return new HandleImpl(o);
    }
  };

  private ListMap<Handle, Object> state = new ListMap<Handle, Object>();

  /**
   * Creates a new instance with the provided rule execution set and properties attached to the provided runtime.
   *
   * @param storedRuleExecutionSet the rule set to create this session for
   * @param properties             properties to be applied to this session
   * @param ruleRuntime            the runtime this session is attached to
   */
  public StatefulRuleSessionImpl(StoredRuleExecutionSet storedRuleExecutionSet, Map<?, ?> properties, RuleRuntimeImpl ruleRuntime) {
    super(storedRuleExecutionSet, properties, ruleRuntime);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@link RuleRuntime#STATEFUL_SESSION_TYPE}
   * @throws InvalidRuleSessionException if this session has already been released
   */
  public int getType() throws InvalidRuleSessionException {
    checkRelease();
    return RuleRuntime.STATEFUL_SESSION_TYPE;
  }

  /**
   * {@inheritDoc}
   *
   * @param handle {@inheritDoc}
   * @return {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   * @throws InvalidHandleException      {@inheritDoc}
   */
  public boolean containsObject(Handle handle) throws InvalidRuleSessionException, InvalidHandleException {
    checkRelease();
    return state.containsKey(handle);
  }

  /**
   * {@inheritDoc}
   *
   * @param object {@inheritDoc}
   * @return {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public Handle addObject(Object object) throws InvalidRuleSessionException {
    checkRelease();
    HandleImpl handle = new HandleImpl(object);
    state.put(handle, object);
    return handle;
  }

  /**
   * {@inheritDoc}
   *
   * @param list {@inheritDoc}
   * @return {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public List<Handle> addObjects(List list) throws InvalidRuleSessionException {
    checkRelease();
    List<Handle> handles = new ArrayList<Handle>();
    for(Object object : list) {
      handles.add(this.addObject(object));
    }
    return handles;
  }


  /**
   * {@inheritDoc}
   *
   * @param handle {@inheritDoc}
   * @param object {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   * @throws InvalidHandleException      {@inheritDoc}
   */
  public void updateObject(Handle handle, Object object) throws InvalidRuleSessionException, InvalidHandleException {
    checkRelease();
    if(handle instanceof HandleImpl) {
      state.put(handle, object);
    } else {
      throw new InvalidHandleException("Handle " + handle + " was not created by ferl.");
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param handle {@inheritDoc}
   * @throws InvalidHandleException      {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public void removeObject(Handle handle) throws InvalidHandleException, InvalidRuleSessionException {
    checkRelease();
    state.remove(handle);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public List<Object> getObjects() throws InvalidRuleSessionException {
    checkRelease();
    return this.getObjects(null);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public List<Handle> getHandles() throws InvalidRuleSessionException {
    checkRelease();
    return Collections.unmodifiableList(new ArrayList<Handle>(state.keySet()));
  }

  /**
   * {@inheritDoc}
   *
   * @param objectFilter {@inheritDoc}
   * @return {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public List<Object> getObjects(final ObjectFilter objectFilter) throws InvalidRuleSessionException {
    checkRelease();
    return super.filter(objectFilter, state.values());
  }

  /**
   * {@inheritDoc}
   *
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public void executeRules() throws InvalidRuleSessionException {
    checkRelease();
    super.doExecuteRules(state.asList(HANDLE_CREATION_TRANSFORMER));
  }

  /**
   * {@inheritDoc}
   *
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public void reset() throws InvalidRuleSessionException {
    checkRelease();
    state.clear();
  }

  /**
   * {@inheritDoc}
   *
   * @param handle {@inheritDoc}
   * @return {@inheritDoc}
   * @throws InvalidHandleException      {@inheritDoc}
   * @throws InvalidRuleSessionException {@inheritDoc}
   */
  public Object getObject(Handle handle) throws InvalidHandleException, InvalidRuleSessionException {
    checkRelease();
    return state.get(handle);
  }

}
