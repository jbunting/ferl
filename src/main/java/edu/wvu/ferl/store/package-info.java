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

/**
 * This package is the primary mechanism used by ferl to store and retrieve rules.  It provides three interfaces:
 * {@code RuleStore}, {@code StoredRule}, {@code StoredRuleExecutionSet}.  The {@code StoredRule} and
 * {@code StoredRuleExecutionSet} correspond to {@code Rule} and {@code RuleExecutionSet}.  These are both stored
 * independently as first-order objects, and keyed by a unique uri.  The {@code RuleStore} is the repository for
 * the rules and rule execution sets.  It is acceptable to access the interfaces in this package directly from client
 * code.  This should primarily be done for purposes such as an administrative interface or someting of that natures.
 * This is due to the fact that, even though the JSR provides for an "administration" portion of the interface,
 * the mechanism in which rules are stored, or represented is still too abstract for the administration portion of the
 * interface to be wholly useful.  Also, if you were to switch rule providers, chances are you will have to rewrite
 * your administrative interface given the differences in semantics of rules between rule engines.
 */
package edu.wvu.ferl.store;