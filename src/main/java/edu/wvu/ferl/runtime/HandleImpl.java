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
 * HandleImpl.java
 *
 * Created on May 5, 2007, 4:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.runtime;

import javax.rules.Handle;

/**
 * @author jbunting
 */
class HandleImpl implements Handle {

  private Object object;

  public HandleImpl(Object object) {
    this.object = object;
  }

  public boolean equals(Object other) {
    return object == ((HandleImpl) other).object;
  }

  public int hashCode() {
    return object.hashCode() * 3;
  }

}
