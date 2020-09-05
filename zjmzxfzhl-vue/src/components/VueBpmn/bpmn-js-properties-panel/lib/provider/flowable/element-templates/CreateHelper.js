'use strict';

var assign = require('lodash/assign');

/**
 * Create an input parameter representing the given
 * binding and value.
 *
 * @param {PropertyBinding} binding
 * @param {String} value
 * @param {BpmnFactory} bpmnFactory
 *
 * @return {ModdleElement}
 */
function createInputParameter(binding, value, bpmnFactory) {
  var scriptFormat = binding.scriptFormat,
      parameterValue,
      parameterDefinition;

  if (scriptFormat) {
    parameterDefinition = bpmnFactory.create('flowable:Script', {
      scriptFormat: scriptFormat,
      value: value
    });
  } else {
    parameterValue = value;
  }

  return bpmnFactory.create('flowable:InputParameter', {
    name: binding.name,
    value: parameterValue,
    definition: parameterDefinition
  });
}

module.exports.createInputParameter = createInputParameter;


/**
 * Create an output parameter representing the given
 * binding and value.
 *
 * @param {PropertyBinding} binding
 * @param {String} value
 * @param {BpmnFactory} bpmnFactory
 *
 * @return {ModdleElement}
 */
function createOutputParameter(binding, value, bpmnFactory) {
  var scriptFormat = binding.scriptFormat,
      parameterValue,
      parameterDefinition;

  if (scriptFormat) {
    parameterDefinition = bpmnFactory.create('flowable:Script', {
      scriptFormat: scriptFormat,
      value: binding.source
    });
  } else {
    parameterValue = binding.source;
  }

  return bpmnFactory.create('flowable:OutputParameter', {
    name: value,
    value: parameterValue,
    definition: parameterDefinition
  });
}

module.exports.createOutputParameter = createOutputParameter;


/**
 * Create flowable property from the given binding.
 *
 * @param {PropertyBinding} binding
 * @param {String} value
 * @param {BpmnFactory} bpmnFactory
 *
 * @return {ModdleElement}
 */
function createFlowableProperty(binding, value, bpmnFactory) {
  return bpmnFactory.create('flowable:Property', {
    name: binding.name,
    value: value || ''
  });
}

module.exports.createFlowableProperty = createFlowableProperty;


/**
 * Create flowable:in element from given binding.
 *
 * @param {PropertyBinding} binding
 * @param {String} value
 * @param {BpmnFactory} bpmnFactory
 *
 * @return {ModdleElement}
 */
function createFlowableIn(binding, value, bpmnFactory) {

  var properties = createFlowableInOutAttrs(binding, value);

  return bpmnFactory.create('flowable:In', properties);
}

module.exports.createFlowableIn = createFlowableIn;


/**
 * Create flowable:in with businessKey element from given binding.
 *
 * @param {PropertyBinding} binding
 * @param {String} value
 * @param {BpmnFactory} bpmnFactory
 *
 * @return {ModdleElement}
 */
function createFlowableInWithBusinessKey(binding, value, bpmnFactory) {
  return bpmnFactory.create('flowable:In', {
    businessKey: value
  });
}

module.exports.createFlowableInWithBusinessKey = createFlowableInWithBusinessKey;


/**
 * Create flowable:out element from given binding.
 *
 * @param {PropertyBinding} binding
 * @param {String} value
 * @param {BpmnFactory} bpmnFactory
 *
 * @return {ModdleElement}
 */
function createFlowableOut(binding, value, bpmnFactory) {
  var properties = createFlowableInOutAttrs(binding, value);

  return bpmnFactory.create('flowable:Out', properties);
}

module.exports.createFlowableOut = createFlowableOut;


/**
 * Create flowable:executionListener element containing an inline script from given binding.
 *
 * @param {PropertyBinding} binding
 * @param {String} value
 * @param {BpmnFactory} bpmnFactory
 *
 * @return {ModdleElement}
 */
function createFlowableExecutionListenerScript(binding, value, bpmnFactory) {
  var scriptFormat = binding.scriptFormat,
      parameterValue,
      parameterDefinition;

  if (scriptFormat) {
    parameterDefinition = bpmnFactory.create('flowable:Script', {
      scriptFormat: scriptFormat,
      value: value
    });
  } else {
    parameterValue = value;
  }

  return bpmnFactory.create('flowable:ExecutionListener', {
    event: binding.event,
    value: parameterValue,
    script: parameterDefinition
  });
}

module.exports.createFlowableExecutionListenerScript = createFlowableExecutionListenerScript;

/**
 * Create flowable:field element containing string or expression from given binding.
 *
 * @param {PropertyBinding} binding
 * @param {String} value
 * @param {BpmnFactory} bpmnFactory
 *
 * @return {ModdleElement}
 */
function createFlowableFieldInjection(binding, value, bpmnFactory) {
  var DEFAULT_PROPS = {
    'string': undefined,
    'expression': undefined,
    'name': undefined
  };

  var props = assign({}, DEFAULT_PROPS);

  if (!binding.expression) {
    props.string = value;
  } else {
    props.expression = value;
  }
  props.name = binding.name;

  return bpmnFactory.create('flowable:Field', props);
}
module.exports.createFlowableFieldInjection = createFlowableFieldInjection;


// helpers ////////////////////////////

/**
 * Create properties for flowable:in and flowable:out types.
 */
function createFlowableInOutAttrs(binding, value) {

  var properties = {};

  // flowable:in source(Expression) target
  if (binding.target) {

    properties.target = binding.target;

    if (binding.expression) {
      properties.sourceExpression = value;
    } else {
      properties.source = value;
    }
  } else

  // flowable:(in|out) variables local
  if (binding.variables) {
    properties.variables = 'all';

    if (binding.variables === 'local') {
      properties.local = true;
    }
  }

  // flowable:out source(Expression) target
  else {
    properties.target = value;

    [ 'source', 'sourceExpression' ].forEach(function(k) {
      if (binding[k]) {
        properties[k] = binding[k];
      }
    });
  }

  return properties;
}
