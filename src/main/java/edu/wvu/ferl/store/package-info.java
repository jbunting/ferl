/**
 * This package is the primary mechanism used by ferl to store and retrieve rules.  It provides three interfaces:
 * {@link RuleStore}, {@link StoredRule}, {@link StoredRuleExecutionSet}.  The {@code StoredRule} and
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