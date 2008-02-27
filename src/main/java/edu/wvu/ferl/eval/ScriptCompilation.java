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

package edu.wvu.ferl.eval;

import edu.wvu.ferl.store.StoredRule;

import javax.script.CompiledScript;

/**
 * Used to pair the compiled version of a script and the information used to create that version.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 10:58:42 AM
 */
class ScriptCompilation {
  private CompiledScript compiled;
  private String script;
  private String language;

  /**
   * Creates a new {@code ScriptCompilation}.
   *
   * @param compiled the compiled version
   * @param script   the uncompiled version
   * @param language the language of the script
   */
  public ScriptCompilation(CompiledScript compiled, String script, String language) {
    this.compiled = compiled;
    this.script = script;
    this.language = language;
  }

  /**
   * Gets the compiled version of the script.
   *
   * @return the compiled version
   */
  public CompiledScript getCompiled() {
    return compiled;
  }

  /**
   * Gets the uncompiled version of the script.
   *
   * @return the uncompiled version
   */
  public String getScript() {
    return script;
  }

  /**
   * Gets the language of the script.
   *
   * @return the language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Tests if this compilation matches the rule passed in.  If this is the same rule that was originally used to
   * create the compilation, then a false result indicates that the rule has changed.  In that case, this compilation
   * should be discared.
   *
   * @param rule the rule to test against
   * @return true if this compilation matches {@code rule}, false otherwise
   */
  public boolean matches(StoredRule rule) {
    return rule.getLanguage().equals(getLanguage()) && rule.getScript().equals(this.getScript());
  }
}
