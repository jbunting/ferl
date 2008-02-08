/**
 * This package contains the classes used for evaluating a rule.  The primary, and only public, class is
 * {@link RuleEvaluator}.  The rule evaluator uses a number of helping classes to determine a strategy for invoking
 * a rule based on the scripting language used, and, in the case of languages that support compilation, for caching
 * the compiled scripts.  This package is really meant to be used by the implementations of {@link RuleSession}, and
 * should not be invoked by clients. 
 */
package edu.wvu.ferl.eval;