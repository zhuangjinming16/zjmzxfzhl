'use strict';

var ModelUtil = require('bpmn-js/lib/util/ModelUtil'),
    is = ModelUtil.is,
    getBusinessObject = ModelUtil.getBusinessObject;

var eventDefinitionHelper = require('./EventDefinitionHelper');
var extensionsElementHelper = require('./ExtensionElementsHelper');

var ImplementationTypeHelper = {};

module.exports = ImplementationTypeHelper;

/**
 * Returns 'true' if the given element is 'flowable:ServiceTaskLike'
 *
 * @param {djs.model.Base} element
 *
 * @return {boolean} a boolean value
 */
ImplementationTypeHelper.isServiceTaskLike = function(element) {
  return is(element, 'flowable:ServiceTaskLike');
};

/**
 * Returns 'true' if the given element is 'flowable:DmnCapable'
 *
 * @param {djs.model.Base} element
 *
 * @return {boolean} a boolean value
 */
ImplementationTypeHelper.isDmnCapable = function(element) {
  return is(element, 'flowable:DmnCapable');
};

/**
 * Returns 'true' if the given element is 'flowable:ExternalCapable'
 *
 * @param {djs.model.Base} element
 *
 * @return {boolean} a boolean value
 */
ImplementationTypeHelper.isExternalCapable = function(element) {
  return is(element, 'flowable:ExternalCapable');
};

/**
 * Returns 'true' if the given element is 'flowable:TaskListener'
 *
 * @param {djs.model.Base} element
 *
 * @return {boolean} a boolean value
 */
ImplementationTypeHelper.isTaskListener = function(element) {
  return is(element, 'flowable:TaskListener');
};

/**
 * Returns 'true' if the given element is 'flowable:ExecutionListener'
 *
 * @param {djs.model.Base} element
 *
 * @return {boolean} a boolean value
 */
ImplementationTypeHelper.isExecutionListener = function(element) {
  return is(element, 'flowable:ExecutionListener');
};

/**
 * Returns 'true' if the given element is 'flowable:ExecutionListener' or
 * 'flowable:TaskListener'
 *
 * @param {djs.model.Base} element
 *
 * @return {boolean} a boolean value
 */
ImplementationTypeHelper.isListener = function(element) {
  return this.isTaskListener(element) || this.isExecutionListener(element);
};

/**
 * Returns 'true' if the given element is 'bpmn:SequenceFlow'
 *
 * @param {djs.model.Base} element
 *
 * @return {boolean} a boolean value
 */
ImplementationTypeHelper.isSequenceFlow = function(element) {
  return is(element, 'bpmn:SequenceFlow');
};

/**
 * Get a 'flowable:ServiceTaskLike' business object.
 *
 * If the given element is not a 'flowable:ServiceTaskLike', then 'false'
 * is returned.
 *
 * @param {djs.model.Base} element
 *
 * @return {ModdleElement} the 'flowable:ServiceTaskLike' business object
 */
ImplementationTypeHelper.getServiceTaskLikeBusinessObject = function(element) {

  if (is(element, 'bpmn:IntermediateThrowEvent') || is(element, 'bpmn:EndEvent')) {

    // change business object to 'messageEventDefinition' when
    // the element is a message intermediate throw event or message end event
    // because the flowable extensions (e.g. flowable:class) are in the message
    // event definition tag and not in the intermediate throw event or end event tag
    var messageEventDefinition = eventDefinitionHelper.getMessageEventDefinition(element);
    if (messageEventDefinition) {
      element = messageEventDefinition;
    }
  }

  return this.isServiceTaskLike(element) && getBusinessObject(element);

};

/**
 * Returns the implementation type of the given element.
 *
 * Possible implementation types are:
 * - dmn
 * - connector
 * - external
 * - class
 * - expression
 * - delegateExpression
 * - script
 * - or undefined, when no matching implementation type is found
 *
 * @param  {djs.model.Base} element
 *
 * @return {String} the implementation type
 */
ImplementationTypeHelper.getImplementationType = function(element) {

  var bo = this.getServiceTaskLikeBusinessObject(element);

  if (!bo) {
    if (this.isListener(element)) {
      bo = element;
    } else {
      return;
    }
  }

  if (this.isDmnCapable(bo)) {
    var decisionRef = bo.get('flowable:decisionRef');
    if (typeof decisionRef !== 'undefined') {
      return 'dmn';
    }
  }

  if (this.isServiceTaskLike(bo)) {
    var connectors = extensionsElementHelper.getExtensionElements(bo, 'flowable:Connector');
    if (typeof connectors !== 'undefined') {
      return 'connector';
    }
  }

  if (this.isExternalCapable(bo)) {
    var type = bo.get('flowable:type');
    if (type === 'external') {
      return 'external';
    }
  }

  var cls = bo.get('flowable:class');
  if (typeof cls !== 'undefined') {
    return 'class';
  }

  var expression = bo.get('flowable:expression');
  if (typeof expression !== 'undefined') {
    return 'expression';
  }

  var delegateExpression = bo.get('flowable:delegateExpression');
  if (typeof delegateExpression !== 'undefined') {
    return 'delegateExpression';
  }

  if (this.isListener(bo)) {
    var script = bo.get('script');
    if (typeof script !== 'undefined') {
      return 'script';
    }
  }

};
