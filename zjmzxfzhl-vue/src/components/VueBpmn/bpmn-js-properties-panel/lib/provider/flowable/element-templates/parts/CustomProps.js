'use strict';

var assign = require('lodash/assign');

var entryFactory = require('../../../../factory/EntryFactory'),
    getBusinessObject = require('bpmn-js/lib/util/ModelUtil').getBusinessObject,
    getTemplate = require('../Helper').getTemplate,
    cmdHelper = require('../../../../helper/CmdHelper'),
    elementHelper = require('bpmn-js-properties-panel/lib/helper/ElementHelper');

var findExtension = require('../Helper').findExtension,
    findExtensions = require('../Helper').findExtensions,
    findInputParameter = require('../Helper').findInputParameter,
    findOutputParameter = require('../Helper').findOutputParameter,
    findFlowableProperty = require('../Helper').findFlowableProperty,
    findFlowableInOut = require('../Helper').findFlowableInOut;

var createFlowableProperty = require('../CreateHelper').createFlowableProperty,
    createInputParameter = require('../CreateHelper').createInputParameter,
    createOutputParameter = require('../CreateHelper').createOutputParameter,
    createFlowableIn = require('../CreateHelper').createFlowableIn,
    createFlowableOut = require('../CreateHelper').createFlowableOut,
    createFlowableInWithBusinessKey = require('../CreateHelper').createFlowableInWithBusinessKey,
    createFlowableFieldInjection = require('../CreateHelper').createFlowableFieldInjection;

var FLOWABLE_PROPERTY_TYPE = 'flowable:property',
    FLOWABLE_INPUT_PARAMETER_TYPE = 'flowable:inputParameter',
    FLOWABLE_OUTPUT_PARAMETER_TYPE = 'flowable:outputParameter',
    FLOWABLE_IN_TYPE = 'flowable:in',
    FLOWABLE_OUT_TYPE = 'flowable:out',
    FLOWABLE_IN_BUSINESS_KEY_TYPE = 'flowable:in:businessKey',
    FLOWABLE_EXECUTION_LISTENER_TYPE = 'flowable:executionListener',
    FLOWABLE_FIELD = 'flowable:field';

var BASIC_MODDLE_TYPES = [
  'Boolean',
  'Integer',
  'String'
];

var EXTENSION_BINDING_TYPES = [
  FLOWABLE_PROPERTY_TYPE,
  FLOWABLE_INPUT_PARAMETER_TYPE,
  FLOWABLE_OUTPUT_PARAMETER_TYPE,
  FLOWABLE_IN_TYPE,
  FLOWABLE_OUT_TYPE,
  FLOWABLE_IN_BUSINESS_KEY_TYPE,
  FLOWABLE_FIELD
];

var IO_BINDING_TYPES = [
  FLOWABLE_INPUT_PARAMETER_TYPE,
  FLOWABLE_OUTPUT_PARAMETER_TYPE
];

var IN_OUT_BINDING_TYPES = [
  FLOWABLE_IN_TYPE,
  FLOWABLE_OUT_TYPE,
  FLOWABLE_IN_BUSINESS_KEY_TYPE
];

/**
 * Injects custom properties into the given group.
 *
 * @param {djs.model.Base} element
 * @param {ElementTemplates} elementTemplates
 * @param {BpmnFactory} bpmnFactory
 * @param {Function} translate
 */
module.exports = function(element, elementTemplates, bpmnFactory, translate) {

  var template = getTemplate(element, elementTemplates);

  if (!template) {
    return [];
  }

  var renderCustomField = function(id, p, idx) {
    var propertyType = p.type;

    var entryOptions = {
      id: id,
      description: p.description,
      label: p.label ? translate(p.label) : p.label,
      modelProperty: id,
      get: propertyGetter(id, p),
      set: propertySetter(id, p, bpmnFactory),
      validate: propertyValidator(id, p, translate)
    };

    var entry;

    if (propertyType === 'Boolean') {
      entry = entryFactory.checkbox(entryOptions);
    }

    if (propertyType === 'String') {
      entry = entryFactory.textField(entryOptions);
    }

    if (propertyType === 'Text') {
      entry = entryFactory.textBox(entryOptions);
    }

    if (propertyType === 'Dropdown') {
      entryOptions.selectOptions = p.choices;

      entry = entryFactory.selectBox(entryOptions);
    }

    return entry;
  };

  var groups = [];
  var id, entry;

  var customFieldsGroup = {
    id: 'customField',
    label: translate('Custom Fields'),
    entries: []
  };
  template.properties.forEach(function(p, idx) {

    id = 'custom-' + template.id + '-' + idx;

    entry = renderCustomField(id, p, idx);
    if (entry) {
      customFieldsGroup.entries.push(entry);
    }
  });
  if (customFieldsGroup.entries.length > 0) {
    groups.push(customFieldsGroup);
  }

  if (template.scopes) {
    for (var scopeName in template.scopes) {

      var scope = template.scopes[scopeName];
      var idScopeName = scopeName.replace(/:/g, '_');

      var customScopeFieldsGroup = {
        id: 'customField-' + idScopeName,
        label: translate('Custom Fields for scope: ') + scopeName,
        entries: []
      };

      scope.properties.forEach(function(p, idx) {

        var propertyId = 'custom-' + template.id + '-' + idScopeName + '-' + idx;

        var scopedProperty = propertyWithScope(p, scopeName);

        entry = renderCustomField(propertyId, scopedProperty, idx);
        if (entry) {
          customScopeFieldsGroup.entries.push(entry);
        }
      });

      if (customScopeFieldsGroup.entries.length > 0) {
        groups.push(customScopeFieldsGroup);
      }
    }
  }

  return groups;
};


// getters, setters and validators ///////////////


/**
 * Return a getter that retrieves the given property.
 *
 * @param {String} name
 * @param {PropertyDescriptor} property
 *
 * @return {Function}
 */
function propertyGetter(name, property) {

  /* getter */
  return function get(element) {
    var value = getPropertyValue(element, property);

    return objectWithKey(name, value);
  };
}

/**
 * Return a setter that updates the given property.
 *
 * @param {String} name
 * @param {PropertyDescriptor} property
 * @param {BpmnFactory} bpmnFactory
 *
 * @return {Function}
 */
function propertySetter(name, property, bpmnFactory) {

  /* setter */
  return function set(element, values) {

    var value = values[name];

    return setPropertyValue(element, property, value, bpmnFactory);
  };
}

/**
 * Return a validator that ensures the property is ok.
 *
 * @param {String} name
 * @param {PropertyDescriptor} property
 * @param {Function} translate
 *
 * @return {Function}
 */
function propertyValidator(name, property, translate) {

  /* validator */
  return function validate(element, values) {
    var value = values[name];

    var error = validateValue(value, property, translate);

    if (error) {
      return objectWithKey(name, error);
    }
  };
}


// get, set and validate helpers ///////////////////

/**
 * Return the value of the specified property descriptor,
 * on the passed diagram element.
 *
 * @param {djs.model.Base} element
 * @param {PropertyDescriptor} property
 *
 * @return {Any}
 */
function getPropertyValue(element, property) {

  var bo = getBusinessObject(element);

  var binding = property.binding,
      scope = property.scope;

  var bindingType = binding.type,
      bindingName = binding.name;

  var propertyValue = property.value || '';

  if (scope) {
    bo = findExtension(bo, scope.name);
    if (!bo) {
      return propertyValue;
    }
  }

  // property
  if (bindingType === 'property') {

    var value = bo.get(bindingName);

    if (bindingName === 'conditionExpression') {
      if (value) {
        return value.body;
      } else {
        // return defined default
        return propertyValue;
      }
    } else {
      // return value; default to defined default
      return typeof value !== 'undefined' ? value : propertyValue;
    }
  }

  var flowableProperties,
      flowableProperty;

  if (bindingType === FLOWABLE_PROPERTY_TYPE) {
    if (scope) {
      flowableProperties = bo.get('properties');
    } else {
      flowableProperties = findExtension(bo, 'flowable:Properties');
    }

    if (flowableProperties) {
      flowableProperty = findFlowableProperty(flowableProperties, binding);

      if (flowableProperty) {
        return flowableProperty.value;
      }
    }

    return propertyValue;
  }

  var inputOutput,
      ioParameter;

  if (IO_BINDING_TYPES.indexOf(bindingType) !== -1) {

    if (scope) {
      inputOutput = bo.get('inputOutput');
    } else {
      inputOutput = findExtension(bo, 'flowable:InputOutput');
    }

    if (!inputOutput) {
      // ioParameter cannot exist yet, return property value
      return propertyValue;
    }
  }

  // flowable input parameter
  if (bindingType === FLOWABLE_INPUT_PARAMETER_TYPE) {
    ioParameter = findInputParameter(inputOutput, binding);

    if (ioParameter) {
      if (binding.scriptFormat) {
        if (ioParameter.definition) {
          return ioParameter.definition.value;
        }
      } else {
        return ioParameter.value || '';
      }
    }

    return propertyValue;
  }

  // flowable output parameter
  if (binding.type === FLOWABLE_OUTPUT_PARAMETER_TYPE) {
    ioParameter = findOutputParameter(inputOutput, binding);

    if (ioParameter) {
      return ioParameter.name;
    }

    return propertyValue;
  }


  var ioElement;

  if (IN_OUT_BINDING_TYPES.indexOf(bindingType) != -1) {
    ioElement = findFlowableInOut(bo, binding);

    if (ioElement) {
      if (bindingType === FLOWABLE_IN_BUSINESS_KEY_TYPE) {
        return ioElement.businessKey;
      } else
      if (bindingType === FLOWABLE_OUT_TYPE) {
        return ioElement.target;
      } else
      if (bindingType === FLOWABLE_IN_TYPE) {
        return ioElement[binding.expression ? 'sourceExpression' : 'source'];
      }
    }

    return propertyValue;
  }

  if (bindingType === FLOWABLE_EXECUTION_LISTENER_TYPE) {
    var executionListener;
    if (scope) {
      executionListener = bo.get('executionListener');
    } else {
      executionListener = findExtension(bo, 'flowable:ExecutionListener');
    }

    return executionListener.script.value;
  }

  var fieldInjection;
  if (FLOWABLE_FIELD === bindingType) {
    var fieldInjections = findExtensions(bo, [ 'flowable:Field' ]);
    fieldInjections.forEach(function(item) {
      if (item.name === binding.name) {
        fieldInjection = item;
      }
    });
    if (fieldInjection) {
      return fieldInjection.string || fieldInjection.expression;
    } else {
      return '';
    }
  }

  throw unknownPropertyBinding(property);
}

module.exports.getPropertyValue = getPropertyValue;


/**
 * Return an update operation that changes the diagram
 * element's custom property to the given value.
 *
 * The response of this method will be processed via
 * {@link PropertiesPanel#applyChanges}.
 *
 * @param {djs.model.Base} element
 * @param {PropertyDescriptor} property
 * @param {String} value
 * @param {BpmnFactory} bpmnFactory
 *
 * @return {Object|Array<Object>} results to be processed
 */
function setPropertyValue(element, property, value, bpmnFactory) {
  var bo = getBusinessObject(element);

  var binding = property.binding,
      scope = property.scope;

  var bindingType = binding.type,
      bindingName = binding.name;

  var propertyValue;

  var updates = [];

  var extensionElements;

  if (EXTENSION_BINDING_TYPES.indexOf(bindingType) !== -1) {
    extensionElements = bo.get('extensionElements');

    // create extension elements, if they do not exist (yet)
    if (!extensionElements) {
      extensionElements = elementHelper.createElement('bpmn:ExtensionElements', null, element, bpmnFactory);

      updates.push(cmdHelper.updateBusinessObject(
        element, bo, objectWithKey('extensionElements', extensionElements)
      ));
    }
  }

  if (scope) {
    bo = findExtension(bo, scope.name);
    if (!bo) {
      bo = elementHelper.createElement(scope.name, null, element, bpmnFactory);

      updates.push(cmdHelper.addElementsTolist(
        bo, extensionElements, 'values', [ bo ]
      ));
    }
  }

  // property
  if (bindingType === 'property') {

    if (bindingName === 'conditionExpression') {

      propertyValue = elementHelper.createElement('bpmn:FormalExpression', {
        body: value,
        language: binding.scriptFormat
      }, bo, bpmnFactory);
    } else {

      var moddlePropertyDescriptor = bo.$descriptor.propertiesByName[bindingName];

      var moddleType = moddlePropertyDescriptor.type;

      // make sure we only update String, Integer, Real and
      // Boolean properties (do not accidentally override complex objects...)
      if (BASIC_MODDLE_TYPES.indexOf(moddleType) === -1) {
        throw new Error('cannot set moddle type <' + moddleType + '>');
      }

      if (moddleType === 'Boolean') {
        propertyValue = !!value;
      } else
      if (moddleType === 'Integer') {
        propertyValue = parseInt(value, 10);

        if (isNaN(propertyValue)) {
          // do not write NaN value
          propertyValue = undefined;
        }
      } else {
        propertyValue = value;
      }
    }

    if (propertyValue !== undefined) {
      updates.push(cmdHelper.updateBusinessObject(
        element, bo, objectWithKey(bindingName, propertyValue)
      ));
    }
  }

  // flowable:property
  var flowableProperties,
      existingFlowableProperty,
      newFlowableProperty;

  if (bindingType === FLOWABLE_PROPERTY_TYPE) {

    if (scope) {
      flowableProperties = bo.get('properties');
    } else {
      flowableProperties = findExtension(extensionElements, 'flowable:Properties');
    }

    if (!flowableProperties) {
      flowableProperties = elementHelper.createElement('flowable:Properties', null, bo, bpmnFactory);

      if (scope) {
        updates.push(cmdHelper.updateBusinessObject(
          element, bo, { properties: flowableProperties }
        ));
      }
      else {
        updates.push(cmdHelper.addElementsTolist(
          element, extensionElements, 'values', [ flowableProperties ]
        ));
      }
    }

    existingFlowableProperty = findFlowableProperty(flowableProperties, binding);

    newFlowableProperty = createFlowableProperty(binding, value, bpmnFactory);

    updates.push(cmdHelper.addAndRemoveElementsFromList(
      element,
      flowableProperties,
      'values',
      null,
      [ newFlowableProperty ],
      existingFlowableProperty ? [ existingFlowableProperty ] : []
    ));
  }

  // flowable:inputParameter
  // flowable:outputParameter
  var inputOutput,
      existingIoParameter,
      newIoParameter;

  if (IO_BINDING_TYPES.indexOf(bindingType) !== -1) {

    if (scope) {
      inputOutput = bo.get('inputOutput');
    } else {
      inputOutput = findExtension(extensionElements, 'flowable:InputOutput');
    }

    // create inputOutput element, if it do not exist (yet)
    if (!inputOutput) {
      inputOutput = elementHelper.createElement('flowable:InputOutput', null, bo, bpmnFactory);

      if (scope) {
        updates.push(cmdHelper.updateBusinessObject(
          element, bo, { inputOutput: inputOutput }
        ));
      }
      else {
        updates.push(cmdHelper.addElementsTolist(
          element, extensionElements, 'values', inputOutput
        ));
      }
    }
  }

  if (bindingType === FLOWABLE_INPUT_PARAMETER_TYPE) {

    existingIoParameter = findInputParameter(inputOutput, binding);

    newIoParameter = createInputParameter(binding, value, bpmnFactory);

    updates.push(cmdHelper.addAndRemoveElementsFromList(
      element,
      inputOutput,
      'inputParameters',
      null,
      [ newIoParameter ],
      existingIoParameter ? [ existingIoParameter ] : []
    ));
  }

  if (bindingType === FLOWABLE_OUTPUT_PARAMETER_TYPE) {

    existingIoParameter = findOutputParameter(inputOutput, binding);

    newIoParameter = createOutputParameter(binding, value, bpmnFactory);

    updates.push(cmdHelper.addAndRemoveElementsFromList(
      element,
      inputOutput,
      'outputParameters',
      null,
      [ newIoParameter ],
      existingIoParameter ? [ existingIoParameter ] : []
    ));
  }


  // flowable:in
  // flowable:out
  // flowable:in:businessKey
  var existingInOut,
      newInOut;

  if (IN_OUT_BINDING_TYPES.indexOf(bindingType) !== -1) {

    existingInOut = findFlowableInOut(bo, binding);

    if (bindingType === FLOWABLE_IN_TYPE) {
      newInOut = createFlowableIn(binding, value, bpmnFactory);
    } else
    if (bindingType === FLOWABLE_OUT_TYPE) {
      newInOut = createFlowableOut(binding, value, bpmnFactory);
    } else {
      newInOut = createFlowableInWithBusinessKey(binding, value, bpmnFactory);
    }

    updates.push(cmdHelper.addAndRemoveElementsFromList(
      element,
      extensionElements,
      'values',
      null,
      [ newInOut ],
      existingInOut ? [ existingInOut ] : []
    ));
  }

  if (bindingType === FLOWABLE_FIELD) {
    var existingFieldInjections = findExtensions(bo, [ 'flowable:Field' ]);
    var newFieldInjections = [];

    if (existingFieldInjections.length > 0) {
      existingFieldInjections.forEach(function(item) {
        if (item.name === binding.name) {
          newFieldInjections.push(createFlowableFieldInjection(binding, value, bpmnFactory));
        } else {
          newFieldInjections.push(item);
        }
      });
    } else {
      newFieldInjections.push(createFlowableFieldInjection(binding, value, bpmnFactory));
    }

    updates.push(cmdHelper.addAndRemoveElementsFromList(
      element,
      extensionElements,
      'values',
      null,
      newFieldInjections,
      existingFieldInjections ? existingFieldInjections : []
    ));
  }

  if (updates.length) {
    return updates;
  }

  // quick warning for better debugging
  console.warn('no update', element, property, value);
}

module.exports.setPropertyValue = setPropertyValue;

/**
 * Validate value of a given property.
 *
 * @param {String} value
 * @param {PropertyDescriptor} property
 * @param {Function} translate
 *
 * @return {Object} with validation errors
 */
function validateValue(value, property, translate) {

  var constraints = property.constraints || {};

  if (constraints.notEmpty && isEmpty(value)) {
    return translate('Must not be empty');
  }

  if (constraints.maxLength && value.length > constraints.maxLength) {
    return translate('Must have max length {length}', { length: constraints.maxLength });
  }

  if (constraints.minLength && value.length < constraints.minLength) {
    return translate('Must have min length {length}', { length: constraints.minLength });
  }

  var pattern = constraints.pattern,
      message;

  if (pattern) {

    if (typeof pattern !== 'string') {
      message = pattern.message;
      pattern = pattern.value;
    }

    if (!matchesPattern(value, pattern)) {
      return message || translate('Must match pattern {pattern}', { pattern: pattern });
    }
  }
}


// misc helpers ///////////////////////////////

function propertyWithScope(property, scopeName) {
  if (!scopeName) {
    return property;
  }

  return assign({}, property, {
    scope: {
      name: scopeName
    }
  });
}

/**
 * Return an object with a single key -> value association.
 *
 * @param {String} key
 * @param {Any} value
 *
 * @return {Object}
 */
function objectWithKey(key, value) {
  var obj = {};

  obj[key] = value;

  return obj;
}

/**
 * Does the given string match the specified pattern?
 *
 * @param {String} str
 * @param {String} pattern
 *
 * @return {Boolean}
 */
function matchesPattern(str, pattern) {
  var regexp = new RegExp(pattern);

  return regexp.test(str);
}

function isEmpty(str) {
  return !str || /^\s*$/.test(str);
}

/**
 * Create a new {@link Error} indicating an unknown
 * property binding.
 *
 * @param {PropertyDescriptor} property
 *
 * @return {Error}
 */
function unknownPropertyBinding(property) {
  var binding = property.binding;

  return new Error('unknown binding: <' + binding.type + '>');
}
