'use strict';

var map = require('lodash/map');

var extensionElementsHelper = require('./ExtensionElementsHelper');

/**
 * Returns true if the attribute 'flowable:asyncBefore' is set
 * to true.
 *
 * @param  {ModdleElement} bo
 *
 * @return {boolean} a boolean value
 */
function isAsyncBefore(bo) {
  return !!(bo.get('flowable:asyncBefore') || bo.get('flowable:async'));
}

module.exports.isAsyncBefore = isAsyncBefore;

/**
 * Returns true if the attribute 'flowable:asyncAfter' is set
 * to true.
 *
 * @param  {ModdleElement} bo
 *
 * @return {boolean} a boolean value
 */
function isAsyncAfter(bo) {
  return !!bo.get('flowable:asyncAfter');
}

module.exports.isAsyncAfter = isAsyncAfter;

/**
 * Returns true if the attribute 'flowable:exclusive' is set
 * to true.
 *
 * @param  {ModdleElement} bo
 *
 * @return {boolean} a boolean value
 */
function isExclusive(bo) {
  return !!bo.get('flowable:exclusive');
}

module.exports.isExclusive = isExclusive;

/**
 * Get first 'flowable:FailedJobRetryTimeCycle' from the business object.
 *
 * @param  {ModdleElement} bo
 *
 * @return {Array<ModdleElement>} a list of 'flowable:FailedJobRetryTimeCycle'
 */
function getFailedJobRetryTimeCycle(bo) {
  return (extensionElementsHelper.getExtensionElements(bo, 'flowable:FailedJobRetryTimeCycle') || [])[0];
}

module.exports.getFailedJobRetryTimeCycle = getFailedJobRetryTimeCycle;

/**
 * Removes all existing 'flowable:FailedJobRetryTimeCycle' from the business object
 *
 * @param  {ModdleElement} bo
 *
 * @return {Array<ModdleElement>} a list of 'flowable:FailedJobRetryTimeCycle'
 */
function removeFailedJobRetryTimeCycle(bo, element) {
  var retryTimeCycles = extensionElementsHelper.getExtensionElements(bo, 'flowable:FailedJobRetryTimeCycle');
  return map(retryTimeCycles, function(cycle) {
    return extensionElementsHelper.removeEntry(bo, element, cycle);
  });
}

module.exports.removeFailedJobRetryTimeCycle = removeFailedJobRetryTimeCycle;